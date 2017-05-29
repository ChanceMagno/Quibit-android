package com.epicodus.quibit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemDetail extends AppCompatActivity {
    @Bind(R.id.itemTextView)
    TextView mItemTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("selectedItem");
        mItemTextView.setText(String.format("item detail here %s", selectedItem));
    }
}
