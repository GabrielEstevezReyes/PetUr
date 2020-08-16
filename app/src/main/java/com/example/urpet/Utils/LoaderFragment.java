package com.example.urpet.Utils;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.urpet.R;

import java.util.Objects;

/**
 * <p>Fragment que representa a un loader para mostrar que las vistas y la información se están
 * cargando.</p>
 */
public class LoaderFragment extends DialogFragment {

    private ProgressBar mProgressBar;

    /**
     * <p>Método que permite generar una instancia del Loader Fragment.</p>
     * @return instancia del Fragment.
     */
    public static LoaderFragment newInstance(){
        Bundle args = new Bundle();
        LoaderFragment loaderFragment = new LoaderFragment();
        loaderFragment.setArguments(args);
        return loaderFragment;
    }

    /**
     * <p>Método en el que se reescriben comportamientos que tiene por defecto un DialogFragment.
     * En este caso se indica que que el Dialog no necesita mostrar un título, ya que hay varios
     * dispositivos que dejan un espacio en blanco.</p>
     * @param savedInstanceState Paquete con los datos que se almacenan para conservar el estado de
     *                           la instancia.
     * @return DialogFragment rediseñado.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //Indica que se genere una ventana sin título.
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    /**
     * <p>Método que permite inflar las vistas que se encuentran en el archivo XML del Dialog.</p>
     * @param inflater Objeto que infla las vistas del XML.
     * @param container Vista padre que contiene al Fragment.
     * @param savedInstanceState Objeto con los datos que se almacenan para conservar el estado de
     *                           la instancia.
     * @return DialogFragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_loader, container, false);
        mProgressBar = mView.findViewById(R.id.progress_bar_loader);
        mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        setCancelable(false);
        return mView;
    }

}
