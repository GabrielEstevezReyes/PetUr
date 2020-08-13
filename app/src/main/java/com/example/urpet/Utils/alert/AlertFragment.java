package com.example.urpet.Utils.alert;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.urpet.R;
import com.example.urpet.Utils.UrPetApplication;

import java.util.Objects;

public class AlertFragment extends DialogFragment {

    private View mView;

    private ImageView mImagenIV;
    private TextView mTituloET, mDescripcionET;
    private Button mAceptarBtn;

    private static final String TIPO_KEY = "TIPO", TITULO_KEY = "TUITULO", DESC_KEY = "DESC", SOLO_CERRAR = "CERRAR_SOLO";

    public enum EnumTipoMensaje{
        ERROR, EXITO
    }

    public interface onAceptarClick{
        void onAceptado(boolean exito);
    }

    private onAceptarClick mListener;

    public void setmListener(onAceptarClick mListener) {
        this.mListener = mListener;
    }

    public static AlertFragment newInstance(EnumTipoMensaje tipo, String titulo, String descripcion, boolean soloCerrar){
        AlertFragment fr = new AlertFragment();
        Bundle data = new Bundle();
            data.putSerializable(TIPO_KEY, tipo);
            data.putString(TITULO_KEY, titulo);
            data.putString(DESC_KEY, descripcion);
            data.putBoolean(SOLO_CERRAR, soloCerrar);
        fr.setArguments(data);
        return fr;
    }

    /**
     * <p>Permite redimensionar correctamente el fragment del cuadro de dialogo.</p>
     */
    @Override
    public void onStart() {
        super.onStart();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        assert window != null;
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout(size.x, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_alertas_info, container);
        bindviews();
        if (getArguments() != null) {
            configureViews(getArguments());
        }
        return mView;
    }

    private void bindviews(){
        mImagenIV = mView.findViewById(R.id.alert_icon_iv);
        mTituloET = mView.findViewById(R.id.alert_titulo_tv);
        mDescripcionET = mView.findViewById(R.id.alert_descripcion_tv);
        mAceptarBtn = mView.findViewById(R.id.alert_boton_uno);
    }

    private void configureViews(Bundle data){
        mImagenIV.setImageDrawable(ContextCompat.getDrawable(UrPetApplication.getApplication(), (data.getSerializable(TIPO_KEY) == EnumTipoMensaje.EXITO ? R.drawable.ic_check_group : R.drawable.ic_cancel_group)));
        mTituloET.setText(data.getString(TITULO_KEY));
        mDescripcionET.setText(data.getString(DESC_KEY));
        mAceptarBtn.setOnClickListener(v -> {
            if(!data.getBoolean(SOLO_CERRAR)){
                mListener.onAceptado(data.getSerializable(TIPO_KEY) == EnumTipoMensaje.EXITO);
            }
            dismiss();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getDialog()).requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
    }
}
