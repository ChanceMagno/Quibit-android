package com.epicodus.quibit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.getStartedButton) Button mGetStartedButton;
    boolean goalSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //for displaying getStarted button if no goal set
        if (goalSet) {
            mGetStartedButton.setVisibility(View.GONE);
        } else {
            mGetStartedButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, CreateGoal.class);
        startActivity(intent);
    }




}
