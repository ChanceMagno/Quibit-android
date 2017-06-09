package com.epicodus.quibit.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.epicodus.quibit.R;
import com.epicodus.quibit.models.Goal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateExchangeActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.setGoalActionButton) FloatingActionButton mNextGoalButton;
    @Bind(R.id.editTextItem) EditText mEditTextItem;
    @Bind(R.id.editTextItemCost) EditText mEditTextItemCost;
    @Bind(R.id.editTextItemRate) EditText mEditTextItemRate;
    @Bind(R.id.textViewItem) TextView mTextViewItem;
    @Bind(R.id.textViewItemCost) TextView mTextViewItemCost;
    @Bind(R.id.textViewItemMonthly) TextView mTextViewItemMonthly;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
        String itemRate = mEditTextItemRate.getText().toString();

        isValidItem(item);
        isValidItemCost(itemCost);
        isValidItemRate(itemRate);


        clearTextFields();
        Goal newGoal = new Goal(item, itemCost, itemRate);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        userRef.child("exchanges").push().setValue(newGoal);
        Intent intent = new Intent(CreateExchangeActivity.this, MainActivity.class);
        startActivity(intent);

    }

    private boolean isValidItem(String item) {
        boolean isGoodName = (item != null);
        if (!isGoodName) {
            mEditTextItem.setError("Please input an item");
        }
        return isGoodName;
    }

    private boolean isValidItemCost(String itemCost){
        boolean isGoodItemCost = (itemCost != null);
        if(!isGoodItemCost){
            mEditTextItemCost.setError("Please input an average cost");
        }
        return true;
    }

    private boolean isValidItemRate(String itemRate){
        boolean isGoodItemRate = (itemRate != null);
        if(!isGoodItemRate){
            mEditTextItemCost.setError("Please input an average cost");
        }
        return true;
    }



    public void clearTextFields(){
        mEditTextItem.setText("");
        mEditTextItemCost.setText("");
        mEditTextItemRate.setText("");
    }

}
