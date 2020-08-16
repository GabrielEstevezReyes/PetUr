package com.example.urpet.registro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urpet.login.LoginActivity;
import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.SMS;
import com.example.urpet.connections.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class RegistroUsuario extends AppCompatActivity implements View.OnClickListener {

    public EditText namesInput =  null;
    public EditText middleNameInput =  null;
    public EditText lastNameInput =  null;
    public EditText mailInput =  null;
    public EditText cellphoneInput =  null;
    public EditText passwordInput =  null;
    public EditText confirmPasswordInput =  null;
    public TextView errorMessage = null;
    private Button mButtonRegistrar;
    List<String> phns = null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        namesInput              = findViewById(R.id.registroUsuarioNombre);
        //middleNameInput         = findViewById(R.id.registroUsuarioApellidoM);
        lastNameInput           = findViewById(R.id.registroUsuarioApellidoP);
        mailInput               = findViewById(R.id.registroUsuarioMail);
        cellphoneInput          = findViewById(R.id.registroUsuarioCelular);
        passwordInput           = findViewById(R.id.registroUsuarioPassword);
        confirmPasswordInput    = findViewById(R.id.registroUsuarioConfirmPass);
        errorMessage            = findViewById(R.id.registroUsuarioErrorMessage);
        bindviews();
        configureViews();
        User current1 = new User(mailInput.getText().toString());
        phns = current1.getAllPhoneNumbers();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null) {
            Log.println(Log.DEBUG, "login", "Usuario con sesion activa");
        }
        else {
            Log.println(Log.DEBUG, "login", "Usuario con sesion INactiva");
        }
    }

    private void bindviews(){
        mButtonRegistrar = findViewById(R.id.registro_activity_registrar_btn);
    }

    private void configureViews(){
        mButtonRegistrar.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(RegistroUsuario.this, LoginActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_sig(View view) throws IOException {
        errorMessage.setText("");
        if(allFieldsClean()) {
            if(isValid(mailInput.getText().toString())) {
                if(namesInput.getText().toString().length() > 3) {
                    if(cellphoneInput.getText().toString().length() == 10) {
                        if(phns.isEmpty() || !phns.contains(cellphoneInput.getText().toString())) {
                            if (passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
                                if (passwordInput.getText().toString().length() >= 6) {
                                    createAccount(mailInput.getText().toString(), passwordInput.getText().toString());
                                } else {
                                    errorMessage.setText("Tus contraseña debe contener al menos 6 caracteres");
                                }
                            } else {
                                errorMessage.setText("Tus contraseñas no coinciden");
                            }
                        }
                        else{
                            errorMessage.setText("Este número de teléfono ya se encuentra registrado");
                        }
                    }
                    else{
                        errorMessage.setText("Por favor ingresa un número telefónico válido");
                    }
                }
                else{
                    errorMessage.setText("Por favor ingresa un nombre válido");
                }
            }
            else{
                errorMessage.setText("Por favor ingresa un correo electrónico válido");
            }
        }
        else{
            errorMessage.setText("Faltan datos para tu registro");
        }
    }

    private void createAccount(String email, String password) {
        Log.println(Log.DEBUG, "createuser", "createAccount: [" + email + "], [" + password + "]");
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.println(Log.DEBUG, "createuser", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.println(Log.DEBUG, "createuser", "createAccount: [" + user.getEmail() + "]");
                            User current = new User(mailInput.getText().toString());
                            try {
                                current.create(namesInput.getText().toString(), lastNameInput.getText().toString(),
                                        mailInput.getText().toString(), cellphoneInput.getText().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            current.getIDSafe();
                            PersonalInfo.currentUser = current;
                            PersonalInfo.registeredName = namesInput.getText().toString();
                            Intent siguiente = new Intent(RegistroUsuario.this, SMS.class);
                            startActivity(siguiente);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.println(Log.DEBUG, "createuser", "createUserWithEmail:failure" + task.getException());
                            Toast.makeText(RegistroUsuario.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            errorMessage.setText("Ya existe un usuario con este correo electrónico");
                        }
                    }
                });
    }

    private Boolean allFieldsClean(){
        return
                !namesInput.getText().toString().isEmpty() &&
                !middleNameInput.getText().toString().isEmpty() &&
                !lastNameInput.getText().toString().isEmpty() &&
                !mailInput.getText().toString().isEmpty() &&
                !cellphoneInput.getText().toString().isEmpty() &&
                !passwordInput.getText().toString().isEmpty() &&
                !confirmPasswordInput.getText().toString().isEmpty();
    }

    private boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registro_activity_registrar_btn:
                createAccount(mailInput.getText().toString(), passwordInput.getText().toString());
            break;
        }
    }
}
