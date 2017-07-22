package com.epicodus.quibit.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.quibit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private ConstraintLayout test;
    @Bind(R.id.getStartedFloatingActionButton) FloatingActionButton mGetStartedFloatingActionButton;
    @Bind(R.id.getStartedText) TextView mGetStartedText;
    @Bind(R.id.goalSearch) EditText mGoalSearch;
    @Bind(R.id.about) FloatingActionButton mAbout;
    @Bind(R.id.logout)

    Button mLogout;
    boolean goalSet = false;
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
        test = (ConstraintLayout) findViewById(R.id.test);
        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        mGetStartedText.setTypeface(pacifico);

        mAbout.setOnClickListener(this);
        Intent intent = getIntent();
         boolean goalStatus = Boolean.parseBoolean(intent.getStringExtra("goalSet"));
        String item = intent.getStringExtra("item");
        String itemCost = intent.getStringExtra("itemCost");
        String itemMonthly = intent.getStringExtra("itemMonthly");

        if(goalStatus){
            goalSet = goalStatus;
            mGetStartedFloatingActionButton.setVisibility(View.GONE);
            mGetStartedFloatingActionButton.setVisibility(View.GONE);
            mGoalSearch.setVisibility(View.GONE);

        } else {
            mGetStartedFloatingActionButton.setOnClickListener(this);
        }

        mLogout.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getStartedFloatingActionButton:
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

            case R.id.about:
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.activity_about, null);
                popupWindow = new PopupWindow(container, 400, 400, true);
                popupWindow.showAtLocation(test, Gravity.NO_GRAVITY, 500, 500);

                break;

            case R.id.logout:
                logout();
                Intent intentNav = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intentNav);
                break;
        }
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

