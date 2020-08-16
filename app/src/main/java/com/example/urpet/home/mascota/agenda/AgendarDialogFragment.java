package com.example.urpet.home.mascota.agenda;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.urpet.R;
import com.example.urpet.Utils.alert.SpinnerFechasDialogFragment;

import java.util.Objects;

public class AgendarDialogFragment extends DialogFragment implements View.OnClickListener, SpinnerFechasDialogFragment.onDateSelected {

    private View mView;
    private Button mAgendarBTN;

    private EditText mNombreET, mInicioET, mFinET;

    private boolean isFechaInicio;

    private onEventoAdd mListener;

    public interface onEventoAdd{
        void agregarEvento(String nombre, String fechaInicio, String fechaFin);
    }

    public void setmListener(onEventoAdd mListener) {
        this.mListener = mListener;
    }

    public static AgendarDialogFragment newInstance(){
        AgendarDialogFragment agenda = new AgendarDialogFragment();
        Bundle args = new Bundle();
        agenda.setArguments(args);
        return agenda;
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
        mView = inflater.inflate(R.layout.fragment_agendar, container);
        bindviews();
        configureViews();
        return mView;
    }

    private void bindviews(){
        mAgendarBTN = mView.findViewById(R.id.fragment_agenda_aceptar_btn);
        mNombreET = mView.findViewById(R.id.fragment_agendar_nombre_et);
        mInicioET = mView.findViewById(R.id.fragment_agenda_inicio_et);
        mFinET = mView.findViewById(R.id.fragment_agenda_fin_et);
    }

    private void configureViews(){
        mAgendarBTN.setOnClickListener(this);
        mInicioET.setOnClickListener(this);
        mFinET.setOnClickListener(this);
    }

    private void abreCalendario(boolean fechaInicio){
        isFechaInicio = fechaInicio;
        SpinnerFechasDialogFragment fragment = SpinnerFechasDialogFragment.newInstance();
        fragment.setmListener(this);
        assert getFragmentManager() != null;
        fragment.show(getFragmentManager(), "Spinner");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_agenda_aceptar_btn:
                enviarDatosAgenda();
                dismiss();
            break;
            case R.id.fragment_agenda_inicio_et:
                abreCalendario(true);
            break;
            case R.id.fragment_agenda_fin_et:
                abreCalendario(false);
            break;
        }
    }

    private void enviarDatosAgenda(){
        mListener.agregarEvento(
                mNombreET.getText().toString(),
                mInicioET.getText().toString(),
                mFinET.getText().toString());
        dismiss();
    }

//    @Override
//    public void onFechaSelected(SpinnerFechasDialogFragment view, int year, int monthOfYear, int dayOfMonth) {
//        if(isFechaInicio){
//            mInicioET.setText(getResources().
//                    getString(R.string.fecha_format, String.valueOf(dayOfMonth), String.valueOf(monthOfYear), String.valueOf(year)));
//        }
//        else{
//            mFinET.setText(getResources().
//                    getString(R.string.fecha_format, String.valueOf(dayOfMonth), String.valueOf(monthOfYear), String.valueOf(year)));
//        }
//    }

    @Override
    public void onFechaSelected(SpinnerFechasDialogFragment view, String fecha) {
        if(isFechaInicio){
            mInicioET.setText(fecha);
        }
        else{
            mFinET.setText(fecha);
        }
    }
}
