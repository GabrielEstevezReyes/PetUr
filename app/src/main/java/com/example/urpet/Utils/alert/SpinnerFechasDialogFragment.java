package com.example.urpet.Utils.alert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.example.urpet.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class SpinnerFechasDialogFragment extends DialogFragment {

    public static final int MES = 1, DIA = 0, ANIO = 2, NO_OCULTAR = 3;
    public static final int RCE = 0, MI_SCORE = 1, ALERTAS = 2, BLOQUEO = 3;

    private static final String HIDE_KEY = "esconder", COLOR_KEY = "color";

    private onDateSelected mListener;

    private DatePicker mSpinnerDPD;

    private Button mAceptar, mCancelar;

    private View mView;

    private Long MinDate = null;

    private Long MaxDate = null;

    private Calendar maxDate = Calendar.getInstance();

    public void setmListener(onDateSelected mListener) {
        this.mListener = mListener;
    }

    final Calendar myCalendar = Calendar.getInstance();

    String myFormat = "dd/MMM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    private DatePicker.OnDateChangedListener date = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    };

    public interface onDateSelected{
        void onFechaSelected(SpinnerFechasDialogFragment view, String fecha);
        //void onFechaSelected(SpinnerFechasDialogFragment view, int year, int monthOfYear, int dayOfMonth);
    }

    /**
     * <p>Funciíon estática para obtener una instancia del Fragment</p>
     * */
    public static SpinnerFechasDialogFragment newInstance(){
        SpinnerFechasDialogFragment f = new SpinnerFechasDialogFragment();
        Bundle bundle = new Bundle();
        f.setArguments(bundle);
        return f;
    }

    /**
     * <p>Método para asignar a cada variable un elemento de la vista y configurar dichas variables.</p>
     * */
    private void bindviews(){
        mSpinnerDPD = mView.findViewById(R.id.dialog_date_datepicker);
        if (MinDate != null) {
            mSpinnerDPD.setMinDate(MinDate);
        }
        if (MaxDate != null) {
            mSpinnerDPD.setMaxDate(MaxDate);
            myCalendar.set(Calendar.YEAR, maxDate.get(Calendar.YEAR));
        }

        mSpinnerDPD.init(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH), date);


        mAceptar = mView.findViewById(R.id.spinner_calendario_btn_aceptar);
        mAceptar.setOnClickListener(v-> {
            mListener.onFechaSelected(this, sdf.format(myCalendar.getTime()));
//            mListener.onFechaSelected(this,
//                    (myCalendar.get(Calendar.YEAR),
//                    myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
            dismiss();
        });

        mSpinnerDPD.setMaxDate(maxDate.getTimeInMillis());

        mCancelar = mView.findViewById(R.id.spinner_calendario_btn_cancel);
        mCancelar.setOnClickListener(v-> dismiss());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_fragment_spinner_calendar, container);
        bindviews();
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getDialog()).requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
