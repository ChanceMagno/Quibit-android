package com.epicodus.quibit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.quibit.adapters.ItemListAdapter;
import com.epicodus.quibit.models.Item;
import com.epicodus.quibit.services.walmartService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchRewards extends AppCompatActivity {
    public ArrayList<Item> mItemList = new ArrayList<>();
    private ItemListAdapter mAdapter;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rewards);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String goalSearch = intent.getStringExtra("goalSearch");

        getItemList(goalSearch);
    }

    private void getItemList(String goalSearch){
        final walmartService newWalmartService = new walmartService();
        newWalmartService.searchItems(goalSearch, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mItemList = newWalmartService.processResults(response);

                SearchRewards.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mAdapter = new ItemListAdapter(getApplicationContext(), mItemList);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchRewards.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                    }
                });

            }
        });
    }
}
