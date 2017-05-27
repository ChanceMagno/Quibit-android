package com.epicodus.quibit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.getStartedButton) Button mGetStartedButton;

//    @Bind(R.id.getStartedText) TextView mGetStartedText;
    @Bind(R.id.goalSearch) EditText mGoalSearch;

    boolean goalSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //for displaying getStarted button if no goal set
        if (goalSet) {
            mGetStartedButton.setVisibility(View.GONE);
            mGetStartedButton.setVisibility(View.GONE);
            mGoalSearch.setVisibility(View.GONE);
        } else {
            mGetStartedButton.setOnClickListener(this);
        }
    }


    //getStartedButton clicked activates search
    @Override
    public void onClick(View v) {
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


    }

}
