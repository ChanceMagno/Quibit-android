package com.epicodus.quibit;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.quibit.models.Item;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemDetail extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.itemNameTextView) TextView mItemNameTextView;
    @Bind(R.id.setGoalActionButton) FloatingActionButton mSetItemAsGoalButton;
    @Bind(R.id.itemImageView) ImageView mItemImageView;
    @Bind(R.id.itemDescriptionTextView) TextView mItemDescriptionTextView;
    @Bind(R.id.itemViewOnlineActionButton) FloatingActionButton mItemViewOnlineButton;
    @Bind(R.id.itemPriceTextView) TextView mItemPriceTextView;
    Item selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        selectedItem = Parcels.unwrap(getIntent().getParcelableExtra("item"));

        mItemNameTextView.setText(selectedItem.getName());
        mItemDescriptionTextView.setText(selectedItem.getDescription());
        mItemPriceTextView.setText(selectedItem.getSalePrice());


    }

    @Override
    public void onClick(View v) {

    }
}


