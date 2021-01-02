package com.example.dhoni.job4utrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(FirebaseAuth.getInstance().getCurrentUser() != null)
                {
                    Intent i = new Intent(MainActivity.this,PhoneNumber.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(MainActivity.this,Login.class);
                    startActivity(i);
                    finish();
                }


            }
        },5*1000);

    }
}