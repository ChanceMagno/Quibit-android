package com.epicodus.quibit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateGoal extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.nextGoalButton) Button mNextGoalButton;
    @Bind(R.id.cancelGoalButton) Button mCancelGoalButton;

    @Bind(R.id.editTextName) EditText mEditTextName;
    @Bind(R.id.editTextItem) EditText mEditTextItem;
    @Bind(R.id.editTextItemCost) EditText mEditTextItemCost;
    @Bind(R.id.editTextItemMonthly) EditText mEditTextItemMonthly;

    @Bind(R.id.textViewName) TextView mTextViewName;
    @Bind(R.id.textViewItem) TextView mTextViewItem;
    @Bind(R.id.textViewItemCost) TextView mTextViewItemCost;
    @Bind(R.id.textViewItemMonthly) TextView mTextViewItemMonthly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
        ButterKnife.bind(this);
    }
}
