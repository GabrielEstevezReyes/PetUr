package com.example.urpet.login;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.urpet.R;

import java.util.Objects;

public class LoginFingerprintAlert extends DialogFragment {

    private View mView;

    private Button mAceptar, mRechazar;

    private static final String ENUM_TIPO = "tiposisimo";

    public enum TiposAlert{
        ACTIVAR, EXITO, IR_A_CONFIG
    }

    private initLogin mListener;

    public interface initLogin{
        void onSetFingerprint();
        void onIrAConfig();
    }

    public void setmListener(initLogin mListener) {
        this.mListener = mListener;
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


    public static LoginFingerprintAlert newInstance(TiposAlert tipo){
        LoginFingerprintAlert fragment = new LoginFingerprintAlert();
        Bundle data = new Bundle();
        data.putSerializable(ENUM_TIPO, tipo);
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.alert_fingerprint, container);
        this.setCancelable(true);
        bindviews();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            configureViews((TiposAlert) Objects.requireNonNull(bundle.getSerializable(ENUM_TIPO)));
        }
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getDialog()).requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void bindviews(){
        mRechazar = mView.findViewById(R.id.alert_finger_rechazar);
        mAceptar = mView.findViewById(R.id.alert_finger_aceptar);
    }

    private void configureViews(TiposAlert tipo){
        switch (tipo){
            case EXITO:
                mRechazar.setVisibility(View.GONE);
                mAceptar.setVisibility(View.GONE);
                mView.findViewById(R.id.alert_finger_exito);
                ((TextView) mView.findViewById(R.id.alert_finger_desc_tv)).setText(R.string.biom_desc_exito);
                ((TextView) mView.findViewById(R.id.alert_finger_exito)).setVisibility(View.VISIBLE);
            break;
            case ACTIVAR:
                mRechazar.setOnClickListener(v-> dismiss());
                mAceptar.setOnClickListener(v->{
                    mListener.onSetFingerprint();
                    dismiss();
                });
            break;
            case IR_A_CONFIG:
                ((TextView) mView.findViewById(R.id.alert_finger_desc_tv)).setText(R.string.biom_desc_conf);
                mAceptar.setText(R.string.biom_btn_config);
                mRechazar.setOnClickListener(v-> dismiss());
                mAceptar.setOnClickListener(v->{
                    mListener.onIrAConfig();
                    dismiss();
                });
            break;
        }
    }

}
