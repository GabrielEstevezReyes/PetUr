package com.example.urpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urpet.connections.User;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;

public class InicioSesion extends AppCompatActivity {

    private int selectedLogin = 0;
    private static final int RC_SIGN_IN = 0;
    public TextView error = null;
    public EditText mailInput =  null;
    public EditText passwordInput =  null;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        try {
            if (ActivityCompat.checkSelfPermission(InicioSesion.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(InicioSesion.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        error = findViewById(R.id.errorMessage);
        mailInput = findViewById(R.id.mailIF);
        passwordInput = findViewById(R.id.passwordIF);
        error = findViewById(R.id.errorMessage);
        mailInput = findViewById(R.id.mailIF);
        passwordInput = findViewById(R.id.passwordIF);
        error.setVisibility(View.INVISIBLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("37432631902-4r7icouo19sqqp8us1fvqdj0ddhen736.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null) {
            Log.println(Log.DEBUG, "login", "Usuario con sesion activa FIREBASE");
            try {
                alredyLogged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Log.println(Log.DEBUG, "login", "Usuario con sesion INactiva FIREBASE");
        }
    }

    public void btn_sig(View view) {
        error.setVisibility(View.INVISIBLE);
        String mail = mailInput.getText().toString();
        String pass = passwordInput.getText().toString();
        if(!mail.isEmpty() && !pass.isEmpty()) {
            signIn(mail, pass);
        }
        else {
            error.setText("Por favor ingresa un usuario y contraseña");
            error.setVisibility(View.VISIBLE);
        }
    }

    public void btn_ccuenta(View view){
        Intent siguiente = new Intent(InicioSesion.this, RegistroUsuario.class);
        startActivity (siguiente);
        finish();
    }

    private void alredyLogged() throws JSONException {
        FirebaseUser user = mAuth.getCurrentUser();
        User current = new User(user.getEmail());
        current.getFromMail();
        if(current.getID() == -1){
            Log.d("LOGINNODB", "se activa");
            current.createShort(user.getDisplayName(), user.getEmail());
            current.getIDSafe();
        }
        Log.println(Log.DEBUG, "login", current.toString());
        PersonalInfo.currentUser = current;
        error.setVisibility(View.INVISIBLE);
        Intent siguiente = new Intent(InicioSesion.this, MainActivity.class);
        startActivity(siguiente);
        finish();
    }

    private void alredyLogged(FirebaseUser user) throws JSONException {
        User current = new User(user.getEmail());
        current.getFromMail();
        if(current.getID() == -1){
            Log.d("LOGINNODB", "se activa");
            current.createShort(user.getDisplayName(), user.getEmail());
            current.getIDSafe();
        }
        Log.println(Log.DEBUG, "login", current.toString());
        PersonalInfo.currentUser = current;
        error.setVisibility(View.INVISIBLE);
        Intent siguiente = new Intent(InicioSesion.this, MainActivity.class);
        startActivity(siguiente);
        finish();
    }

    private void signIn(String email, String password) {
        Log.println(Log.DEBUG, "login", "signIn:" + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.println(Log.DEBUG, "login", "signInWithEmail:success");
                            User current = new User(mailInput.getText().toString());
                            current.getFromMail();
                            PersonalInfo.currentUser = current;
                            error.setVisibility(View.INVISIBLE);
                            Intent siguiente = new Intent(InicioSesion.this, MainActivity.class);
                            startActivity(siguiente);
                            finish();
                        } else {
                            Log.println(Log.DEBUG, "login", task.getException().toString());
                            error.setText("Datos de usuario incorrectos");
                            error.setVisibility(View.VISIBLE);
                            Toast.makeText(InicioSesion.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        mAuth.signOut();
    }

    public void loginGoogle(View view) {
        selectedLogin = 1;
        Log.println(Log.DEBUG, "login", "Google pressed");
        signInG();
    }

    public void loginFacebook(View view) {
        selectedLogin = 2;
        Log.println(Log.DEBUG, "login", "Facebook pressed");
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.buttonFacebookLogin);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("login", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("login", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("login", "facebook:onError" + error);
                // ...
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("login", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("login", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                alredyLogged(user);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("login", "signInWithCredential:failure", task.getException());
                            Toast.makeText(InicioSesion.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            error.setText("Datos de usuario incorrectos");
                            error.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    private void signInG() {
        Log.println(Log.DEBUG, "login", "Abut to launch intent");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Log.println(Log.DEBUG, "login", "Intent launched");
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.println(Log.DEBUG, "login", "result activity");
        if(selectedLogin == 1) {
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.println(Log.DEBUG, "login", "Google sign in failed" + e);
                }
            }
        }
        else if(selectedLogin == 2){
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.println(Log.DEBUG, "login", "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.println(Log.DEBUG, "login", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                alredyLogged(user);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.println(Log.DEBUG, "login", "signInWithCredential:failure" + task.getException());
                            Toast.makeText(InicioSesion.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            error.setText("Datos de usuario incorrectos");
                            error.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
