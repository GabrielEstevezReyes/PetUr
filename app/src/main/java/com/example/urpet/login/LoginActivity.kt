package com.example.urpet.login


import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.urpet.PersonalInfo
import com.example.urpet.R
import com.example.urpet.Utils.EncryptedSharedPreferencesUtils
import com.example.urpet.Utils.LoaderFragment
import com.example.urpet.connections.User
import com.example.urpet.databinding.ActivityInicioSesionBinding
import com.example.urpet.home.MainActivity
import com.example.urpet.registro.RegistroUsuario
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import org.json.JSONException
import java.util.concurrent.Executor

/**
 * Created by Carlos Elliot Frias Mercado (Maku) on 29/09/2020.
 */
class LoginActivity: AppCompatActivity() {

    public enum class TipoLogin(val id: Int){
        GOOGLE(0), FACEBOOK(1), CLASICO(2)
    }

    private lateinit var binding: ActivityInicioSesionBinding

    private var loader = LoaderFragment.newInstance()

    //Biometric
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var executor: Executor

    //Google
    private var selectedLogin: Int = 0
    private val RC_SIGN_IN = 0
    private var mAuth: FirebaseAuth? = null
    private var mCallbackManager: CallbackManager? = null
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inicio_sesion)
        binding.lifecycleOwner = this
        configureBiometrics()
        configureGoogleData()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bindViews()
        }
    }

    override fun onStart() {
        super.onStart()
        checkFirebaseStatus()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun bindViews(){
        binding.loginActivityFacebookBtn.setOnClickListener { loginFacebook() }
        binding.loginActivityFingerprintBtn.setOnClickListener { biometricPrompt.authenticate(promptInfo) }
        binding.loginActivityGoogleBtn.setOnClickListener { loginGoogle() }
        binding.loginActivitySignupBtn.setOnClickListener { gotoSignUp() }
        binding.loginActivityLoginBtn.setOnClickListener { loginClasico(false) }
    }

    private fun configureGoogleData(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("37432631902-4r7icouo19sqqp8us1fvqdj0ddhen736.apps.googleusercontent.com")
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun configureBiometrics() {
        // Biometric
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {

                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        loginClasico(true)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(applicationContext, "Authentication failed",
                                Toast.LENGTH_SHORT)
                                .show()
                    }
                })


        promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(resources.getString(R.string.biom_acceso))
                .setSubtitle(resources.getString(R.string.biom_subt))
                .setNegativeButtonText(resources.getString(R.string.biom_fail))
                .build()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun loginClasico(isBiometric : Boolean){
        val email = if(isBiometric) EncryptedSharedPreferencesUtils.getMail() else binding.loginActivityCorreoEt.text.toString()
        val password = if(isBiometric) EncryptedSharedPreferencesUtils.getPass() else binding.loginActivityPasswordEt.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            showLoader()
            mAuth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                        if (task.isSuccessful) {
                            //Log.println(Log.DEBUG, "login", "signInWithEmail:success")
                            val current = User(email)
                            current.getFromMail()
                            PersonalInfo.currentUser = current
                            //error.setVisibility(View.INVISIBLE)
                            val siguiente = Intent(this, MainActivity::class.java)
                            EncryptedSharedPreferencesUtils.insertLogin(email, password, TipoLogin.CLASICO.id)
                            startActivity(siguiente)
                            finish()
                        } else {
                            Log.println(Log.DEBUG, "login", task.exception.toString())

                            Toast.makeText(this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }
        else{
            binding.errorMessage.text = resources.getString(R.string.empty_fields)
            binding.errorMessage.visibility = View.VISIBLE
        }
    }

    private fun loginGoogle(){
        selectedLogin = 1
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun loginFacebook(){
        showLoader()
        selectedLogin = 2
        Log.println(Log.DEBUG, "login", "Facebook pressed")
        mCallbackManager = CallbackManager.Factory.create()
        val loginButton = findViewById<LoginButton>(R.id.login_activity_facebook_btn)
        loginButton.setReadPermissions("email", "public_profile", "user_friends")
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("login", "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d("login", "facebook:onCancel")
                hideLoader()
            }

            override fun onError(error: FacebookException) {
                Log.d("login", "facebook:onError$error")
                hideLoader()
                // ...
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("login", "handleFacebookAccessToken:$token")
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("login", "signInWithCredential:success")
                        val user = mAuth!!.currentUser
                        try {
                            iniciarSesionFirebase(user!!)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    } else {
                        hideLoader()
                        // If sign in fails, display a message to the user.
                        Log.w("login", "signInWithCredential:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }


    private fun gotoSignUp(){
        val siguiente = Intent(this, RegistroUsuario::class.java)
        startActivity(siguiente)
    }

    private fun checkFirebaseStatus(){
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            Log.d("login", "Usuario con sesion activa FIREBASE")
            try {
                loginFirebase()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            Log.println(Log.DEBUG, "login", "Usuario con sesion INactiva FIREBASE")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Throws(JSONException::class)
    private fun iniciarSesionFirebase(user: FirebaseUser) {
        val current = User(user.email)
        current.getFromMail()
        if (current.id == -1) {
            Log.d("LOGINNODB", "se activa")
            current.createShort(user.displayName, user.email)
            current.getIDSafe()
        }
        Log.println(Log.DEBUG, "login", current.toString())
        PersonalInfo.currentUser = current

        EncryptedSharedPreferencesUtils.insertLogin(binding.loginActivityCorreoEt.text.toString(), "", TipoLogin.FACEBOOK.id)
        gotoMain()
    }

    @Throws(JSONException::class)
    private fun loginFirebase() {
        showLoader()
        val user = mAuth!!.currentUser
        val current = User(user!!.email)
        current.getFromMail()
        if (current.id == -1) {
            Log.d("LOGINNODB", "se activa")
            current.createShort(user.displayName, user.email)
            current.getIDSafe()
        }
        Log.println(Log.DEBUG, "login", current.toString())
        PersonalInfo.currentUser = current
        gotoMain()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.println(Log.DEBUG, "login", "result activity")
        if (selectedLogin == 1) {
            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.println(Log.DEBUG, "login", "Google sign in failed$e")
                }
            }
        } else if (selectedLogin == 2) {
            mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.println(Log.DEBUG, "login", "firebaseAuthWithGoogle:" + acct.id)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.println(Log.DEBUG, "login", "signInWithCredential:success")
                        val user = mAuth!!.currentUser
                        try {
                            iniciarSesionFirebase(user!!)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.println(Log.DEBUG, "login", "signInWithCredential:failure" + task.exception)
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        //error.setText("Datos de usuario incorrectos")
                        //error.setVisibility(View.VISIBLE)
                    }
                }
    }

    private fun gotoMain(){
        val siguiente = Intent(this, MainActivity::class.java)
        startActivity(siguiente)
        hideLoader()
        finish()
    }

    private fun showLoader(){
        loader.show(supportFragmentManager, "Loader")
    }

    private fun hideLoader(){
        loader.dismiss()
    }

    /**
     *
     * Método que cierra el Softkeyboard si se hace click afuera de él.
     * @param event El evento registrado en el dispositivo.
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mAuth!!.signOut()
    }
}