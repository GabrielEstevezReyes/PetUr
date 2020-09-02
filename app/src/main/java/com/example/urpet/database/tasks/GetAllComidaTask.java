package com.example.urpet.database.tasks;

import android.os.AsyncTask;

import com.example.urpet.Utils.UrPetApplication;
import com.example.urpet.database.UrPetDatabase;
import com.example.urpet.database.entity.UltimaComidaEntity;

import java.util.ArrayList;
import java.util.Objects;

public class GetAllComidaTask extends AsyncTask<String, String, String> {

    private onQuerySucces listener;

    private ArrayList<UltimaComidaEntity> mData;

    public GetAllComidaTask(onQuerySucces listener) {
        mData = new ArrayList<>();
        this.listener = listener;
    }

    public interface onQuerySucces{
        void onGetAll(ArrayList<UltimaComidaEntity> data);
    }

    @Override
    protected String doInBackground(String... strings) {
        UrPetDatabase db = UrPetDatabase.Companion.getInstance(UrPetApplication.getApplication());
        assert db != null;
        mData.addAll(Objects.requireNonNull(db.mUltimaComidaDao()).getAllComidas());
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.onGetAll(mData);
    }
}
