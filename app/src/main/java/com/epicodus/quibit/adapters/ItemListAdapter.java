package com.epicodus.quibit.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.epicodus.quibit.R;
import com.epicodus.quibit.models.Item;
import com.epicodus.quibit.ui.ItemDetailActivity;
import com.epicodus.quibit.util.ItemTouchHelperAdapter;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

import static java.lang.Float.parseFloat;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> implements ItemTouchHelperAdapter{
    private ArrayList<Item> mItems = new ArrayList<>();
    private Context mContext;
    private static final int MAX_WIDTH = 300;
    private static final int MAX_HEIGHT = 300;



    public ItemListAdapter(Context context, ArrayList<Item> items) {
        mContext = context;
        mItems = items;
    }



    @Override
    public ItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemListAdapter.ItemViewHolder holder, int position) {
        holder.bindItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if(fromPosition < toPosition){
            for (int i = fromPosition; i < toPosition; i++){
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--){
                Collections.swap(mItems, i, i -1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.itemNameTextView) TextView mItemNameTextView;
        @Bind(R.id.itemImageView) ImageView mImageView;
        @Bind(R.id.itemDescriptionTextView) TextView mDescriptionTextView;
        @Bind(R.id.ratingBar) RatingBar mRatingBar;
        @Bind(R.id.msrpPriceTextView) TextView mMSRP;
        @Bind(R.id.salePriceTextView) TextView mPrice;
        @Bind(R.id.ViewItemButton) FloatingActionButton mActionButton;



        public ItemViewHolder (View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            mActionButton.setOnClickListener(this);
        }



        public void bindItem(Item item) {
            mItemNameTextView.setText(item.getName());
            if (item.getDescription().equals("")){
                mDescriptionTextView.setText("No Description Available");
            } else {
                mDescriptionTextView.setText(item.getDescription());
            }
            if (item.getMsrp().equals("")){
                mMSRP.setText("not available");
            } else {
                mMSRP.setText(String.format("$%s", item.getMsrp()));
            }
            mPrice.setText(String.format("$%s", item.getSalePrice()));
            mRatingBar.setRating(parseFloat(item.getRating()) / 2);
            Picasso.with(itemView.getContext()).load(item.getLargeImage()).resize(MAX_WIDTH, MAX_HEIGHT).centerCrop().into(mImageView);
        }

        @Override
        public void onClick(View v){
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, ItemDetailActivity.class);
            Item item = mItems.get(itemPosition);
            intent.putExtra("item", Parcels.wrap(item));
            mContext.startActivity(intent);
        }
    }
}
