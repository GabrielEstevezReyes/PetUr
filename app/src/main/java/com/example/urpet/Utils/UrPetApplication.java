package com.example.urpet.Utils;


import android.app.Application;

/**
 * <p>Clase que representa al contexto principal de la aplicación. Aquí se define la aplicación que
 * está corriendo, para acceder a los recursos que sean necesarios desde cualquier clase.</p>
 */
public class UrPetApplication extends Application {

    /**
     * <p>Instancia estática de la aplicación que está en ejecución.</p>
     */
    private static UrPetApplication mApplication = null;

    /**
     * <p>Método que es ejecutado cuando la aplicación se está iniciando, antes que cualquier
     * Activity, Service o Receiver objects hayan sido creados.</p>
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    /**
     * <p>Método que permite obtener la instancia de la aplicación desde cualquier lugar.</p>
     * @return Instancia de la aplicación.
     */
    public static UrPetApplication getApplication(){
        return mApplication;
    }
}
