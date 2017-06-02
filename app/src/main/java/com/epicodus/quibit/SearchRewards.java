package com.epicodus.quibit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
    @Bind(R.id.searchGoalHeader) TextView mSearchGoalHeader;
    @Bind(R.id.listView) ListView mListView;
    public ArrayList<Item> mItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rewards);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String goalSearch = intent.getStringExtra("goalSearch");
        mSearchGoalHeader.setText(String.format("Searching by: %s", goalSearch));

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
                        Log.i("item", mItemList.get(0).getName());
                    }
                });

            }
        });
    }
}
