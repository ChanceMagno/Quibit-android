package com.epicodus.quibit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Format;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchRewards extends AppCompatActivity {
    @Bind(R.id.searchGoalHeader) TextView mSearchGoalHeader;
    @Bind(R.id.goalSearchResults) ListView mgoalSearchResults;

    private String[] SearchResults = new String[] {"item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rewards);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String goalSearch = intent.getStringExtra("goalSearch");
        mSearchGoalHeader.setText(String.format("Searching by: %s", goalSearch));
    }
}
