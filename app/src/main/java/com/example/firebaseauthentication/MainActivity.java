package com.example.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseauthentication.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(this,Dashboard.class));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        activityMainBinding.logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m3 = new Intent(MainActivity.this , Login.class);
                startActivity(m3);
            }
        });
        activityMainBinding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignUp();
            }
        });
    }
    private void userSignUp() {
        String email =activityMainBinding.email.getText().toString().trim();
        String password = activityMainBinding.password.getText().toString().trim();

        if (email.isEmpty()) {
            activityMainBinding.email.setError("Email is required");
            activityMainBinding.email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            activityMainBinding.email.setError("Enter a valid email");
            activityMainBinding.email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            activityMainBinding.password.setError("Password required");
            activityMainBinding.password.requestFocus();
            return;
        }

        if (password.length() < 6) {
            activityMainBinding.password.setError("Password should be atleast 6 character long");
            activityMainBinding.password.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this,Dashboard.class));
                        } else {
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        }

                    }
                });


}

}