package com.epicodus.quibit.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.quibit.R;
import com.epicodus.quibit.constants.Constants;
import com.epicodus.quibit.models.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Math.round;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.itemNameTextView) TextView mItemNameTextView;
    @Bind(R.id.setGoalActionButton) FloatingActionButton mSetItemAsGoalButton;
    @Bind(R.id.itemImageView) ImageView mItemImageView;
    @Bind(R.id.itemDescriptionTextView) TextView mItemDescriptionTextView;
    @Bind(R.id.itemViewOnlineActionButton) FloatingActionButton mItemViewOnlineButton;
    @Bind(R.id.itemPriceTextView) TextView mItemPriceTextView;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final int MAX_WIDTH = 375;
    private static final int MAX_HEIGHT = 145;
    Item selectedItem;
    FirebaseAuth mAuth;
    String goalValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        goalValue = mSharedPreferences.getString(Constants.PREFERENCES_GOALVALUE_KEY, null);
        mEditor = mSharedPreferences.edit();

        selectedItem = Parcels.unwrap(getIntent().getParcelableExtra("item"));
        mItemViewOnlineButton.setOnClickListener(this);
        mSetItemAsGoalButton.setOnClickListener(this);
        setContent(selectedItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setGoalActionButton:
                addToSharedPreferences();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference goalRef = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid()).child("goal");
                goalRef.setValue(selectedItem);
                Intent intent = new Intent(ItemDetailActivity.this, HomeActivity.class);
                startActivity(intent);

                break;
            case R.id.itemViewOnlineActionButton:
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedItem.getPurchaseLink()));
                startActivity(webIntent);
                break;
        }
    }

    private void addToSharedPreferences(){
        goalValue = selectedItem.getSalePrice();
        mEditor.putString(Constants.PREFERENCES_GOALVALUE_KEY, goalValue).apply();
    }

    public void setContent(Item selectedItem){
        mItemNameTextView.setText(selectedItem.getName());
        mItemDescriptionTextView.setText(selectedItem.getDescription());
        mItemPriceTextView.setText(selectedItem.getSalePrice());
        Picasso.with(ItemDetailActivity.this).load(selectedItem.getLargeImage()).resize(MAX_WIDTH, MAX_HEIGHT).centerCrop().into(mItemImageView);
    }

}


