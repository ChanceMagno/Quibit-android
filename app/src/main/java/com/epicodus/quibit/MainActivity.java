package com.epicodus.quibit;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.getStartedFloatingActionButton) FloatingActionButton mGetStartedFloatingActionButton;
    @Bind(R.id.getStartedText) TextView mGetStartedText;
    @Bind(R.id.goalSearch) EditText mGoalSearch;
    @Bind(R.id.about) FloatingActionButton mAbout;
    boolean goalSet = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        Typeface salsa = Typeface.createFromAsset(getAssets(), "fonts/Salsa.ttf");
        mGetStartedText.setTypeface(pacifico);


        mAbout.setOnClickListener(this);

        Intent intent = getIntent();
         boolean goalStatus = Boolean.parseBoolean(intent.getStringExtra("goalSet"));
            if(goalStatus){
                goalSet = goalStatus;
                mGetStartedFloatingActionButton.setVisibility(View.GONE);
                mGetStartedFloatingActionButton.setVisibility(View.GONE);
                mGoalSearch.setVisibility(View.GONE);
            } else {
                mGetStartedFloatingActionButton.setOnClickListener(this);
            }
    }


    //getStartedButton clicked activates search
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
                    Intent intent = new Intent(MainActivity.this, SearchRewards.class);
                    intent.putExtra("goalSearch", goalSearch);
                    startActivity(intent);
                }
                break;

            case R.id.about:
                Intent intentAbout = new Intent(MainActivity.this, About.class);
                startActivity(intentAbout);
                break;
        }
    }
}

