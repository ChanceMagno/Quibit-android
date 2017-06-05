package com.epicodus.quibit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.quibit.models.Item;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

import static java.lang.System.load;

public class ItemDetail extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.itemNameTextView) TextView mItemNameTextView;
    @Bind(R.id.setGoalActionButton) FloatingActionButton mSetItemAsGoalButton;
    @Bind(R.id.itemImageView) ImageView mItemImageView;
    @Bind(R.id.itemDescriptionTextView) TextView mItemDescriptionTextView;
    @Bind(R.id.itemViewOnlineActionButton) FloatingActionButton mItemViewOnlineButton;
    @Bind(R.id.itemPriceTextView) TextView mItemPriceTextView;
    private static final int MAX_WIDTH = 375;
    private static final int MAX_HEIGHT = 145;
    Item selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        selectedItem = Parcels.unwrap(getIntent().getParcelableExtra("item"));
        mItemViewOnlineButton.setOnClickListener(this);
        mSetItemAsGoalButton.setOnClickListener(this);
        setContent(selectedItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setGoalActionButton:
                Toast toast = Toast.makeText(ItemDetail.this, "Boo!", Toast.LENGTH_LONG);
                toast.show();
                break;
            case R.id.itemViewOnlineActionButton:
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedItem.getPurchaseLink()));
                startActivity(webIntent);
                break;
        }
    }

    public void setContent(Item selectedItem){
        mItemNameTextView.setText(selectedItem.getName());
        mItemDescriptionTextView.setText(selectedItem.getDescription());
        mItemPriceTextView.setText(selectedItem.getSalePrice());
        Picasso.with(ItemDetail.this).load(selectedItem.getLargeImage()).resize(MAX_WIDTH, MAX_HEIGHT).centerCrop().into(mItemImageView);
    }
}


