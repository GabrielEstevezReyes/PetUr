package com.example.urpet.home.mascota.opciones.grupos.posts.comentarios;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.Utils.LoaderFragment;
import com.example.urpet.Utils.alert.AlertFragment;
import com.example.urpet.Utils.alert.AlertManager;
import com.example.urpet.connections.social.Post;

import java.util.Objects;

public class CrearComentarioDialogFragment extends DialogFragment implements View.OnClickListener, CrearComentarioView {

    private OnComentarioListener mListener;

    private View mView;

    private Context mContext;

    private Post mPost;

    private LoaderFragment mLoader;

    private CrearComentarioPresenter mPresenter;

    public interface OnComentarioListener{
        void comentarioEnviado();
    }

    private EditText mComentario;
    private Button mEnviarBtn;
    private ImageView mCloseBtn;

    public void setData(OnComentarioListener mListener, Context mContext, Post mPost) {
        this.mListener = mListener;
        this.mContext = mContext;
        this.mPost = mPost;
    }

    public static CrearComentarioDialogFragment newInstance(){
        return new CrearComentarioDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.activity_crear_comentario, container, false);
        bindViews();
        configureViews();
        return mView;
    }

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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_crear_comentario_btn:
                enviarComentario();
                break;
            case R.id.crear_comentario_close:
                dismiss();
                break;
        }
    }

    private void bindViews(){
       mComentario = mView.findViewById(R.id.activity_crear_comentario_comentario_et);
       mEnviarBtn = mView.findViewById(R.id.activity_crear_comentario_btn);
       mCloseBtn = mView.findViewById(R.id.crear_comentario_close);
       mPresenter = new CrearComentarioPresenter(this, new CrearComentarioInteractor());
       mLoader = LoaderFragment.newInstance();
    }

    private void configureViews(){
        mEnviarBtn.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
    }

    private void enviarComentario(){
        if(mComentario.getText().toString().length() > 0){
            mPresenter.onComentar(mPost.getIdPost(), PersonalInfo.clickedPet.getID(), mComentario.getText().toString());
        }
    }

    @Override
    public void onComentarioEnviado() {
        mListener.comentarioEnviado();
        dismiss();
    }

    @Override
    public void onComentarioError() {
        AlertManager.muestraMensaje(getChildFragmentManager(), "Error de comentario", AlertFragment.EnumTipoMensaje.ERROR,
                getResources().getString(R.string.error_al_comentar), getResources().getString(R.string.error_coment_desc), true,null);
    }

    @Override
    public void onShowLoader() {
        mLoader = LoaderFragment.newInstance();
        mLoader.show(getChildFragmentManager(), "LoaderComent");
    }

    @Override
    public void onHideLoader() {
        mLoader.dismissAllowingStateLoss();
    }
}
