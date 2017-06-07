package com.epicodus.quibit.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.quibit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.emailLoginEditText) EditText mEmailLoginEditText;
    @Bind(R.id.passwordLoginEditText) EditText mPasswordLoginEditText;
    @Bind(R.id.loginButton) Button mLoginButton;
    @Bind(R.id.loginTitleTextView) TextView mLoginTitleTextView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //listener checking login status of user
        mAuthListener = new FirebaseAuth.AuthStateListener() {
          @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
              FirebaseUser user = firebaseAuth.getCurrentUser();
              if(user !=null){
                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  startActivity(intent);
                  finish();
              }
          }
        };

        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        mLoginTitleTextView.setTypeface(pacifico);
        mLoginButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view){
        if(view == mLoginButton){
            logInExistingUser();
        }
    }

    public void logInExistingUser(){
        String email = mEmailLoginEditText.getText().toString().trim();
        String password = mPasswordLoginEditText.getText().toString().trim();
        if(email.equals("")){
            mEmailLoginEditText.setError("Please enter an email to login");
            return;
        }
        if(password.equals("")){
            mPasswordLoginEditText.setError("Please enter a password");
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
