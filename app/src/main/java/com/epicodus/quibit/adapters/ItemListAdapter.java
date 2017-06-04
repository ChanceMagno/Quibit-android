package com.epicodus.quibit.adapters;






import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.epicodus.quibit.ItemDetail;
import com.epicodus.quibit.R;
import com.epicodus.quibit.models.Item;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static android.media.CamcorderProfile.get;
import static java.lang.Float.parseFloat;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{
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

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.itemNameTextView) TextView mItemNameTextView;
        @Bind(R.id.itemImageView) ImageView mImageView;
        @Bind(R.id.itemDescriptionTextView) TextView mDescriptionTextView;
        @Bind(R.id.ratingBar) RatingBar mRatingBar;
        @Bind(R.id.msrpPriceTextView) TextView mMSRP;
        @Bind(R.id.salePriceTextView) TextView mPrice;



        public ItemViewHolder (View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
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
            Intent intent = new Intent(mContext, ItemDetail.class);

            Item item = mItems.get(itemPosition);
            intent.putExtra("item", Parcels.wrap(item));
            mContext.startActivity(intent);

        }

    }
}
