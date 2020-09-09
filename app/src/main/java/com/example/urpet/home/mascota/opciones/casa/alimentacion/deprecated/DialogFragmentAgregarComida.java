package com.example.urpet.home.mascota.opciones.casa.alimentacion.deprecated;

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
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.urpet.R;

import java.util.Objects;

public class DialogFragmentAgregarComida extends DialogFragment {

    private View mView;

    private EditText mNombreEt, mDescrEt, mPresenEt;

    private onAddComida mListener;

    public interface onAddComida{
        void onAddcomida(String nombre, String desc, String presentacion);
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

    public void setmListener(onAddComida mListener) {
        this.mListener = mListener;
    }

    public static DialogFragmentAgregarComida newInstance(){
        return new DialogFragmentAgregarComida();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_fragment_registrar_comida, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindviews();
    }

    private void bindviews(){
        mNombreEt = mView.findViewById(R.id.fragment_nueva_comida_nombre_et);
        mDescrEt = mView.findViewById(R.id.fragment_nueva_comida_descr_et);
        //mPresenEt = mView.findViewById(R.id.fragment_comida_inicio_et);
        Button mEnviar = mView.findViewById(R.id.fragment_comida_aceptar_btn);
            mEnviar.setOnClickListener(v-> enviarData());
        mView.findViewById(R.id.fragment_comida_cerrar_iv).setOnClickListener(v-> dismiss());
    }

    private void enviarData(){
        final String tempNombre = mNombreEt.getText().toString();
        final String tempDesc = mDescrEt.getText().toString();
        final String tempPres = mPresenEt.getText().toString();
        if(!tempNombre.isEmpty() && !tempDesc.isEmpty() && !tempPres.isEmpty()){
             mListener.onAddcomida(tempNombre, tempDesc, tempPres);
        }
        dismiss();
    }
}
