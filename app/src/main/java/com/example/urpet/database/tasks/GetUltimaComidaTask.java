package com.example.urpet.database.tasks;

import android.os.AsyncTask;

import com.example.urpet.Utils.UrPetApplication;
import com.example.urpet.database.UrPetDatabase;
import com.example.urpet.database.entity.UltimaComidaEntity;

import java.util.ArrayList;
import java.util.Objects;

public class GetUltimaComidaTask extends AsyncTask<String, String, String> {

    private onQuerySuccess listener;

    private UltimaComidaEntity mData;

    public GetUltimaComidaTask(onQuerySuccess listener) {
        this.listener = listener;
    }

    public interface onQuerySuccess{
        void onGetUltima(UltimaComidaEntity data);
    }

    @Override
    protected String doInBackground(String... strings) {
        UrPetDatabase db = UrPetDatabase.Companion.getInstance(UrPetApplication.getApplication());
        assert db != null;
        mData = Objects.requireNonNull(db.mUltimaComidaDao()).getUltimaComida();
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.onGetUltima(mData);
    }
}
