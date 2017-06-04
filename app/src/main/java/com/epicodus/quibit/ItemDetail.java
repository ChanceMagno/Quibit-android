package com.epicodus.quibit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.quibit.models.Item;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemDetail extends AppCompatActivity {
    @Bind(R.id.itemTextView) TextView mItemTextView;
    @Bind(R.id.setItemAsGoalButton) Button mSetItemAsGoalButton;
    Item selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        selectedItem = Parcels.unwrap(getIntent().getParcelableExtra("item"));



    }

}
