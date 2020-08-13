package com.example.urpet.home.social.grupos.listado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.Utils.LoaderFragment;
import com.example.urpet.Utils.alert.AlertFragment;
import com.example.urpet.Utils.alert.AlertManager;
import com.example.urpet.connections.BelongGroup;
import com.example.urpet.connections.Group;
import com.example.urpet.home.social.grupos.CrearGrupoActivity;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ListadoGruposActivity extends AppCompatActivity implements ListadoGruposView, View.OnClickListener {

    private Button mCrearBtn;
    private TextView mTodosBtnTv, mMiosBtnTv, mUnidoBtnTv;
    private RecyclerView mReciclerListadoRV;
    private SearchView mBuscadorSV;
    private GrupoAdapter mAdapter;

    private ListadoGruposPresenter mPresenter;

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private LoaderFragment mLoader;

    private ArrayList<Group> mListadoTodosGrupos = null;
    private ArrayList<Group> mListadoMisGrupos = null;
    private ArrayList<Group> mListadoGruposUnidos = null;
    private ArrayList<Group> mListadoBusqueda = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_grupos);
        bindviews();
        configureviews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PersonalInfo.selectedGroup = null;
        BelongGroup myBelongs = new BelongGroup(PersonalInfo.clickedPet.getID(), -1);
        PersonalInfo.belong = myBelongs.getFromUser();
        mPresenter.onCallGrupos();
    }

    private void bindviews(){
        mCrearBtn = findViewById(R.id.activity_listado_grupos_crear_btn);
        mTodosBtnTv = findViewById(R.id.activity_listado_grupos_todos_btn_tv);
        mMiosBtnTv = findViewById(R.id.activity_listado_grupos_mios_btn_tv);
        mUnidoBtnTv = findViewById(R.id.activity_listado_grupos_unidos_btn_tv);
        mBuscadorSV = findViewById(R.id.activity_listado_grupos_buscador_sv);
        mReciclerListadoRV = findViewById(R.id.activity_listado_grupos_recycler_grupos_rv);
        mLoader = LoaderFragment.newInstance();
        mListadoMisGrupos = new ArrayList<>();
        mListadoGruposUnidos = new ArrayList<>();
        mListadoTodosGrupos = new ArrayList<>();
        mListadoBusqueda = new ArrayList<>();
    }

    private void configureviews(){
        mCrearBtn.setOnClickListener(this);
        mTodosBtnTv.setOnClickListener(this);
        mMiosBtnTv.setOnClickListener(this);
        mUnidoBtnTv.setOnClickListener(this);

        mAdapter = new GrupoAdapter(this, storage);
        mPresenter = new ListadoGruposPresenter(this, new ListadoGrupsInteractor());

        LinearLayoutManager mManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mReciclerListadoRV.setLayoutManager(mManager);
        mReciclerListadoRV.setAdapter(mAdapter);
        mBuscadorSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                realizaBusqueda(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                realizaBusqueda(newText);
                return false;
            }
        });
    }



    private void fillGruposUnidos(ArrayList<Group> todos){
        mListadoGruposUnidos.clear();
        if(PersonalInfo.belong != null) {
            for (int i = 0; i < todos.size(); i++) {
                Group grupoActual = todos.get(i);
                if (PersonalInfo.belong.contains(grupoActual.getID())) {
                    mListadoGruposUnidos.add(grupoActual);
                }
            }
        }
    }

    private void fillMisGrupos(ArrayList<Group> todos){
        mListadoMisGrupos.clear();
        for (int i=0; i < todos.size(); i++){
            if(todos.get(i).getCreatorID() == PersonalInfo.clickedPet.getID()){
                mListadoMisGrupos.add(todos.get(i));
            }
        }
    }

    private void setTodosGrupos(){
        mAdapter.setmListadoGrupos(mListadoTodosGrupos);
    }

    private void setGruposMios(){
        mAdapter.setmListadoGrupos(mListadoMisGrupos);
    }

    private void setGruposUnidos(){
        mAdapter.setmListadoGrupos(mListadoGruposUnidos);
    }

    private void highlighButton(int tipo){
        mTodosBtnTv.setBackground(ContextCompat.getDrawable(this, R.drawable.camp2));
        mUnidoBtnTv.setBackground(ContextCompat.getDrawable(this, R.drawable.camp2));
        mMiosBtnTv.setBackground(ContextCompat.getDrawable(this, R.drawable.camp2));

        mTodosBtnTv.setTextColor(ContextCompat.getColor(this,R.color.texto_color_gris));
        mUnidoBtnTv.setTextColor(ContextCompat.getColor(this,R.color.texto_color_gris));
        mMiosBtnTv.setTextColor(ContextCompat.getColor(this,R.color.texto_color_gris));

        switch (tipo){
            case 1: //todos
                mTodosBtnTv.setBackground(ContextCompat.getDrawable(this, R.drawable.camp_naranja));
                mTodosBtnTv.setTextColor(ContextCompat.getColor(this,R.color.blanco));
            break;
            case 2: //unidos
                mUnidoBtnTv.setBackground(ContextCompat.getDrawable(this, R.drawable.camp_naranja));
                mUnidoBtnTv.setTextColor(ContextCompat.getColor(this,R.color.blanco));
            break;
            case 3: //mios
                mMiosBtnTv.setBackground(ContextCompat.getDrawable(this, R.drawable.camp_naranja));
                mMiosBtnTv.setTextColor(ContextCompat.getColor(this,R.color.blanco));
            break;
        }

    }

    public void onCrearGrupo(){
        Intent siguiente = new Intent(this, CrearGrupoActivity.class);
        startActivity (siguiente);
    }

    private void realizaBusqueda(String cadena){
        mListadoBusqueda.clear();
        if(cadena.isEmpty()){
            setTodosGrupos();
            highlighButton(1);
        }
        else{
            for(int i = 0; i < mListadoTodosGrupos.size(); i++){
                Group actual = mListadoTodosGrupos.get(i);
                if((actual.getName().toLowerCase()).contains(cadena.toLowerCase())){
                    mListadoBusqueda.add(actual);
                }
            }
            mAdapter.clearData();
            mAdapter.setmListadoGrupos(mListadoBusqueda);
        }
    }

    @Override
    public void onListadoPbtenido(ArrayList<Group> data) {
        mAdapter.setmListadoGrupos(data);
        mListadoTodosGrupos.addAll(data);
        fillGruposUnidos(data);
        fillMisGrupos(data);
    }

    @Override
    public void onErrorListado() {
        AlertManager.muestraMensaje(getSupportFragmentManager(), "Error de post", AlertFragment.EnumTipoMensaje.ERROR,
                getResources().getString(R.string.error_de_conexion), getResources().getString(R.string.error_conexion_desc), true,null);
    }

    @Override
    public void onShowLoader() {
        mLoader.show(getSupportFragmentManager(), "Loader grupos");
    }

    @Override
    public void onHideLoader() {
        mLoader.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.listado_grupos_crear_btn:
                onCrearGrupo();
            break;
            case R.id.activity_listado_grupos_todos_btn_tv:
                setTodosGrupos();
                highlighButton(1);
            break;
            case R.id.activity_listado_grupos_mios_btn_tv:
                setGruposMios();
                highlighButton(3);
            break;
            case R.id.activity_listado_grupos_unidos_btn_tv:
                setGruposUnidos();
                highlighButton(2);
            break;
        }
    }
}
