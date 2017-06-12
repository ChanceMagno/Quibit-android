package com.epicodus.quibit.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    @Bind(R.id.registTextView) TextView mRegisterTextView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
          @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
              FirebaseUser user = firebaseAuth.getCurrentUser();
              if(user !=null){
                  mAuthProgressDialog.dismiss();
                  checkIfEmailVerified();
              }
          }
        };

        createAuthProgressDialog();

        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        mLoginTitleTextView.setTypeface(pacifico);

        mRegisterTextView.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view){
        if(view == mLoginButton){
            logInExistingUser();

        }
        if(view == mRegisterTextView){
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
            finish();
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
        mAuthProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               mAuthProgressDialog.dismiss();
                if(!task.isSuccessful()){
                    mAuthProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkIfEmailVerified(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.isEmailVerified()){
            Log.i("user id", user.getUid());
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            mAuthProgressDialog.dismiss();
            mPasswordLoginEditText.setText("");
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(LoginActivity.this, "Please verify your email to login", Toast.LENGTH_LONG).show();
        }
    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
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
