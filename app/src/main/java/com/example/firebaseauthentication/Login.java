package com.example.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseauthentication.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        activityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m3 = new Intent(Login.this,MainActivity.class);
                startActivity(m3);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });
        activityLoginBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }
    private void userLogin() {
        String email =activityLoginBinding.email1.getText().toString().trim();
        String password = activityLoginBinding.password1.getText().toString().trim();

        if (email.isEmpty()) {
            activityLoginBinding.email1.setError("Email is required");
            activityLoginBinding.email1.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            activityLoginBinding.email1.setError("Enter a valid email");
            activityLoginBinding.email1.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            activityLoginBinding.password1.setError("Password required");
            activityLoginBinding.password1.requestFocus();
            return;
        }

        if (password.length() < 6) {
            activityLoginBinding.password1.setError("Password should be atleast 6 character long");
            activityLoginBinding.password1.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(Login.this,Dashboard.class));
                        } else {
                            Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

}}