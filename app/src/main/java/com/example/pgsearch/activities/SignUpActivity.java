package com.example.pgsearch.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pgsearch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    EditText edtFullName, edtEmail, edtPassword, edtCnfPassword;
    TextView btnSignUp, tvSignIn;
    FirebaseAuth mAuth;

    String email, password, cnfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtFullName = findViewById(R.id.full_name);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        edtCnfPassword = findViewById(R.id.confirm_password);
        btnSignUp = findViewById(R.id.sign_up);
        tvSignIn = findViewById(R.id.sign_in);

        mAuth = FirebaseAuth.getInstance();

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                cnfPassword = edtCnfPassword.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }else if(!password.equals(cnfPassword)){
                    Toast.makeText(SignUpActivity.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(SignUpActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    } else {
                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}