package com.example.dhoni.job4utrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Signup extends AppCompatActivity {

    EditText email,pass,firstname,lastname,confpass;
    Button signup;
    TextView acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        confpass = (EditText)findViewById(R.id.confpass);
        email = (EditText)findViewById(R.id.signup_email);
        pass = (EditText)findViewById(R.id.signup_pass);
        signup = (Button)findViewById(R.id.signup_btn);
        acc = (TextView)findViewById(R.id.already);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registeruser();
            }

            private void Registeruser() {
                String Firstname = firstname.getText().toString();
                String Lastname = lastname.getText().toString();
                String Confpass = confpass.getText().toString();
                String mail = email.getText().toString();
                String password = pass.getText().toString();

                if(Firstname.isEmpty()){
                    firstname.setError("First Name is Required");
                    firstname.requestFocus();
                    return;
                }
                if (Lastname.isEmpty())
                {
                    lastname.setError("Last Name is Required");
                    lastname.requestFocus();
                    return;
                }
                if(mail.isEmpty())
                {
                    email.setError("Email is Required");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                {
                    email.setError("Please Provide Valid Email");
                    email.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    pass.setError("Password is Required");
                    pass.requestFocus();
                    return;
                }
                if(Confpass.isEmpty())
                {
                    confpass.setError("Please Re-enter Password ");
                    confpass.requestFocus();
                    return;

                }

                if (password.length() < 6 )
                {
                    pass.setError("Password must be atleast 6 characters");
                    pass.requestFocus();
                    return;
                }
                if(!password.matches(Confpass))
                {
                    Toast.makeText(Signup.this, "Password Don't Match", Toast.LENGTH_LONG).show();

                }
                else
                {

                    firebaseAuth.createUserWithEmailAndPassword(mail,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        user userclass = new user(Firstname, Lastname, mail);

                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(userclass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Signup.this, "User has been registered successfully ", Toast.LENGTH_LONG).show();
                                                    Intent i = new Intent(Signup.this,Login.class);
                                                    startActivity(i);
                                                    finishAfterTransition();
                                                } else {
                                                    Toast.makeText(Signup.this, "Failed", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }

                                }
                            });

                }
            }


        });

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this,Login.class);
                startActivity(i);
            }
        });
    }
}
