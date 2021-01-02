package com.example.dhoni.job4utrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText forgotpass;
    Button btn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgotpass = (EditText)findViewById(R.id.forgot_email);
        btn = (Button)findViewById(R.id.btn_reset);
        firebaseAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();

            }

            private void resetpassword() {
                String email = forgotpass.getText().toString();

                if(email.isEmpty())
                {
                    forgotpass.setError("Email is required");
                    forgotpass.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    forgotpass.setError("Please Provide Valid Email");
                    forgotpass.requestFocus();
                    return;
                }
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgotPassword.this, "Please Check Your Email to Reset You Password", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(ForgotPassword.this, "Try Again!!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}