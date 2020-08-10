package com.example.urpet.Utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.urpet.login.LoginFingerprintAlert;

/**
 * <p>Clase que permite almacenar en el dispositivo los datos de la aplicación.</p>
 */
public class SharedPreferencesUtil {

    public enum EnumTipoLogin{
        GOOGLE, FACEBOOK, PASSWORD, NULL
    }

    private static final String NAME_UTILS = "preferences";

    private static final String IS_LOGGED_KEY = "isLogged";
    private static final String CORREO_KEY = "correo";
    private static final String CORREO_BIOMETRICO_KEY = "correometrico";
    private static final String PASSWORD_KEY = "passwordseña";
    private static final String PASSWORD_BIOM_KEY = "passwordbiomseña";
    private static final String BIOM_ENABLED_KEY = "tieneBiometrico";
    private static final String TIPO_LOGIN_KEY = "loginTipo";

    private static SharedPreferencesUtil mSingleton;
    private static SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;

    /**
     * <p>Constructor que permite generar el SharedPreferences.</p>
     */
    private SharedPreferencesUtil(){
        this.mSharedPreferences = UrPetApplication.getApplication()
                .getSharedPreferences(NAME_UTILS, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    /**
     * <p>Método que permite crear una instancia en caso de que esta se a null.</p>
     */
    private static synchronized void createInstance(){
        if (mSingleton == null){
            mSingleton = new SharedPreferencesUtil();
        }
    }

    /**
     * <p>Método que permite obtener una instancia del SharedPreferences.</p>
     * @return instancia de SharedPreferences.
     */
    public static SharedPreferencesUtil getInstance(){
        if (mSingleton == null){
            createInstance();
        }
        return mSingleton;
    }


    /**
     * <p>Método que reinicia los valores guardados en el dispositivo una vez que el usuario cerró
     * sesión exitosamente.</p>
     */
    public final void closeSession(){
        mEditor.putBoolean(IS_LOGGED_KEY, false);
        mEditor.putString(PASSWORD_KEY, "");
        mEditor.putString(CORREO_KEY, "");
        mEditor.putBoolean(TIPO_LOGIN_KEY, false);
        mEditor.apply();
    }


    /**
     * <p>Método que guarda en el dispositivo la información de un usuario una vez que se realizó el
     * login exitosamente.</p>
     */
    public final void createLogin(String correo, String pass, EnumTipoLogin tipoLogin){
        mEditor.putBoolean(IS_LOGGED_KEY, true);
        mEditor.putString(PASSWORD_KEY, pass);
        mEditor.putString(CORREO_KEY, correo);
        mEditor.putBoolean(TIPO_LOGIN_KEY, tipoLogin == EnumTipoLogin.PASSWORD);
        mEditor.apply();
    }

    /**
     * <p>Método que guarda en el dispositivo la información de un usuario una vez que se realizó el
     *    login exitosamente y se usará para el desbloqueo por huella digital.</p>
     * */
    public final void createBiometricUser(String mail, String pass){
        mEditor.putString(CORREO_BIOMETRICO_KEY, mail);
        mEditor.putString(PASSWORD_BIOM_KEY, pass);
        mEditor.putBoolean(BIOM_ENABLED_KEY, true);
        mEditor.apply();
    }

//    public final void set() {
//        mEditor.putBoolean(ALERTAS_ACQUIRED, isEnable);
//        mEditor.apply();
//    }

    public final String getCorreoBiometrico() {
        return this.mSharedPreferences.getString(CORREO_BIOMETRICO_KEY, "");
    }

    public final String getPasswordBiometrico(){
        return this.mSharedPreferences.getString(PASSWORD_BIOM_KEY, "");
    }

    public final boolean isBiometricenabled(){
        return this.mSharedPreferences.getBoolean(BIOM_ENABLED_KEY, false);
    }

    public final boolean isLoginClasico(){
        return this.mSharedPreferences.getBoolean(TIPO_LOGIN_KEY, false);
    }


    public final String getCorreo() {
        return this.mSharedPreferences.getString(CORREO_KEY, "");
    }

    public final String getPassword(){
        return this.mSharedPreferences.getString(PASSWORD_KEY, "");
    }


}