package com.epicodus.quibit;

import android.content.Intent;
import android.graphics.Typeface;
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

public class CreateGoal extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.nextGoalButton) Button mNextGoalButton;
//    @Bind(R.id.cancelGoalButton) Button mCancelGoalButton;

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

//        setting textView font
        Typeface formTitle = (Typeface.createFromAsset(getAssets(), "Pacifico.ttf"));
        mTextViewName.setTypeface(formTitle);
        mTextViewItem.setTypeface(formTitle);
        mTextViewItemCost.setTypeface(formTitle);
        mTextViewItemMonthly.setTypeface(formTitle);

        mNextGoalButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String name = mEditTextName.getText().toString();
        String item = mEditTextItem.getText().toString();
        String itemCost = mEditTextItemCost.getText().toString();
        String itemMonthly = mEditTextItemMonthly.getText().toString();

        if (name.equals("") || item.equals("") || itemCost.equals("") || itemMonthly.equals("")) {
            Toast toast = Toast.makeText(CreateGoal.this, "Please answer all Questions.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.show();
        } else {
//            Intent intent = new Intent(CreateGoal.this, SearchRewards.class);
            //send to firebase when integrated
//            intent.putExtra("name", name);
//            intent.putExtra("item", item);
//            intent.putExtra("itemCost", itemCost);
//            intent.putExtra("itemMonthly", itemMonthly);
//            startActivity(intent);
        }
    }
}
