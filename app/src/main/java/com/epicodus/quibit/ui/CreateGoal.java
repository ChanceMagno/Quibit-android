package com.epicodus.quibit.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.quibit.R;
import com.epicodus.quibit.services.walmartService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateGoal extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.setGoalActionButton) FloatingActionButton mNextGoalButton;
    @Bind(R.id.editTextItem) EditText mEditTextItem;
    @Bind(R.id.editTextItemCost) EditText mEditTextItemCost;
    @Bind(R.id.editTextItemMonthly) EditText mEditTextItemMonthly;
    @Bind(R.id.textViewItem) TextView mTextViewItem;
    @Bind(R.id.textViewItemCost) TextView mTextViewItemCost;
    @Bind(R.id.textViewItemMonthly) TextView mTextViewItemMonthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
        ButterKnife.bind(this);

        mNextGoalButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String item = mEditTextItem.getText().toString();
        String itemCost = mEditTextItemCost.getText().toString();
        String itemMonthly = mEditTextItemMonthly.getText().toString();

        mEditTextItem.setText("");
        mEditTextItemCost.setText("");
        mEditTextItemMonthly.setText("");

        if (item.equals("") || itemCost.equals("") || itemMonthly.equals("")) {
            Toast toast = Toast.makeText(CreateGoal.this, "Please answer all Questions.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.show();
        } else {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

            userRef.push().child("goal").push().setValue("goal");
            Intent intent = new Intent(CreateGoal.this, MainActivity.class);
            intent.putExtra("item", item);
            intent.putExtra("itemCost", itemCost);
            intent.putExtra("itemMonthly", itemMonthly);
            intent.putExtra("goalSet", "true");
            startActivity(intent);
        }
    }
}
