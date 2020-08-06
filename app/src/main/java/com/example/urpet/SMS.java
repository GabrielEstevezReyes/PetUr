package com.example.urpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SMS extends AppCompatActivity {

    private  EditText codi;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_m_s);
        codi = findViewById(R.id.sms_code);
        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.println(Log.DEBUG, "verifyPhone", "onVerificationCompleted:" + credential);
                FirebaseUser user = mAuth.getCurrentUser();
                user.updatePhoneNumber(credential);
                Intent siguiente = new Intent(SMS.this, WelcomeApp.class);
                startActivity (siguiente);
                finish();
                mVerificationInProgress = false;
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.println(Log.DEBUG, "verifyPhone", "onVerificationFailed" + e);
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(SMS.this, "Número telefónico inválido.",
                            Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(SMS.this, "Límite mensual excedido.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.println(Log.DEBUG, "verifyPhone", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        String number =  "+52" + PersonalInfo.currentUser.getPhoneNumber();
        Log.println(Log.DEBUG, "verifyPhone", "Numero[" + number + "]");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    public void btn_sig(View view){
        if(!codi.getText().toString().isEmpty()) {
            PhoneAuthCredential credential1 = PhoneAuthProvider.getCredential(mVerificationId, codi.getText().toString());
            signInWithPhoneAuthCredential(credential1);
        }
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.updatePhoneNumber(credential);
                            Intent siguiente = new Intent(SMS.this, WelcomeApp.class);
                            startActivity (siguiente);
                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(SMS.this, "Código inválido.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(SMS.this, RegistroUsuario.class);
        startActivity (siguiente);
        finish();
    }
}
