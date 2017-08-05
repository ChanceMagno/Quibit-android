package com.epicodus.quibit.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.quibit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.searchButton) Button mSearchButton;
    @Bind(R.id.getStartedText) TextView mGetStartedText;
    @Bind(R.id.goalSearch) EditText mGoalSearch;
    @Bind(R.id.createGoalButton) Button mCreateGoalButton;
    @Bind(R.id.orTextview) TextView mOrTextView;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        mGetStartedText.setTypeface(pacifico);
        mOrTextView.setTypeface(pacifico);

        mCreateGoalButton.setVisibility(View.INVISIBLE);
        mOrTextView.setVisibility(View.INVISIBLE);

        mSearchButton.setOnClickListener(this);
        mCreateGoalButton.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                String goalSearch = mGoalSearch.getText().toString();
                if (goalSearch.equals("")) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please input an item to search for.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 100);
                    toast.show();
                } else {
                    mGoalSearch.setText("");
                    Intent intent = new Intent(MainActivity.this, SearchGoalsActivity.class);
                    intent.putExtra("goalSearch", goalSearch);
                    startActivity(intent);
                }
                break;
            case R.id.createGoalButton:
                Intent intent = new Intent(this, ItemDetailActivity.class);
        }
    }

    public void replaceFragment(Fragment someFragment) {

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
   public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}

