package com.epicodus.quibit;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;


public class SearchRewardsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mSearchResults;

        public SearchRewardsArrayAdapter(Context mContext, int resource, String[] mSearchResults) {
            super(mContext, resource);
            this.mContext = mContext;
            this.mSearchResults;
        }

//        @Override
//        public Object getItem(int position) {
//            String selected = mSearchResults[position];
//            Intent intent = new Intent(SearchRewards.this, ItemDetail.class);
//            intent.putExtra("selected", selected);
//            startActivity(intent);
//        }
//}
