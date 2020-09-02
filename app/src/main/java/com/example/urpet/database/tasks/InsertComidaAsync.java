package com.example.urpet.database.tasks;

import android.os.AsyncTask;

import com.example.urpet.Utils.UrPetApplication;
import com.example.urpet.database.UrPetDatabase;
import com.example.urpet.database.entity.UltimaComidaEntity;

import java.util.Objects;

public class InsertComidaAsync extends AsyncTask<UltimaComidaEntity, String, String> {

    private OnAsyncResponse listener;

    public interface OnAsyncResponse{
        void onFinish();
    }

    public InsertComidaAsync(OnAsyncResponse listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(UltimaComidaEntity... ultimaComidaEntities) {
        UrPetDatabase db = UrPetDatabase.Companion.getInstance(UrPetApplication.getApplication());
        assert db != null;
        Objects.requireNonNull(db.mUltimaComidaDao()).insertUltimaComida(ultimaComidaEntities[0]);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.onFinish();
    }
}
