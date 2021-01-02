package com.example.dhoni.job4utrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class verify extends AppCompatActivity {

    Button verify;
    EditText rec_OTP;
    FirebaseAuth mAuth;
    String Phonenumber;
    String otpid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        mAuth = FirebaseAuth.getInstance();

        Phonenumber = getIntent().getStringExtra("mobile").toString();
        verify = (Button) findViewById(R.id.verify);
        rec_OTP = (EditText)findViewById(R.id.rec_otp);

        intiateotp();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rec_OTP.getText().toString().isEmpty())
                    rec_OTP.setError("Please Enter OTP");
                else if (rec_OTP.getText().toString().length()!=6)
                    rec_OTP.setError("OTP cannot be less than 6 characters");
                else
                {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid,rec_OTP.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });




    }

    private void intiateotp() {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(Phonenumber)
                        .setTimeout(60L ,TimeUnit.SECONDS)
                        .setActivity(verify.this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(verify.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(verify.this, "Verification Succesfull", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(verify.this, "Verification Faield", Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }
}