package com.example.urpet.Utils

import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


/**
 * Created by Carlos Elliot Frias Mercado (Maku) on 28/09/2020.
 */

@RequiresApi(Build.VERSION_CODES.M)
object EncryptedSharedPreferencesUtils{

    private const val PASSWORD_API = "passss"
    private const val MAIL_KEY = "mail"
    private const val LOGIN_KEY = "login"
    private const val MASTER_KEY_ALIAS = "masterhand"
    private const val FILENAME = "espu"
    private const val KEY_SIZE = 256
    private var sharedPreferences: SharedPreferences

    init{

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(KEY_SIZE)
                .build()

        val masterKeyAlias = MasterKey.Builder(UrPetApplication.getApplication(), MASTER_KEY_ALIAS)
                .setKeyGenParameterSpec(keyGenParameterSpec)
                .build()

        sharedPreferences = EncryptedSharedPreferences.create(
                UrPetApplication.getApplication(),
                FILENAME,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun insertLogin(mail: String, pass: String, tipoLogin: Int){
        sharedPreferences.edit()
                .putString(PASSWORD_API, pass)
                .apply()

        sharedPreferences.edit()
                .putString(MAIL_KEY, mail)
                .apply()

        sharedPreferences.edit()
                .putInt(LOGIN_KEY, tipoLogin)
                .apply()
    }

    fun getPass():String{
        return sharedPreferences.getString(PASSWORD_API, "")!!
    }

    fun getMail():String{
        return sharedPreferences.getString(MAIL_KEY, "")!!
    }
}