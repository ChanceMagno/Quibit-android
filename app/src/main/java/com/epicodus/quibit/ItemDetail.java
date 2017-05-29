package com.epicodus.quibit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemDetail extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.itemTextView) TextView mItemTextView;
    @Bind(R.id.setItemAsGoalButton) Button mSetItemAsGoalButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("selectedItem");
        mItemTextView.setText(String.format("item detail here %s", selectedItem));

        mSetItemAsGoalButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
}
