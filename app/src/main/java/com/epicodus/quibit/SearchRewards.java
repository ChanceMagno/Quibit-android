package com.epicodus.quibit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Format;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchRewards extends AppCompatActivity {
    @Bind(R.id.searchGoalHeader) TextView mSearchGoalHeader;
    @Bind(R.id.listView) ListView mListView;

    private String[] searchResults = new String[] {"ipod", "canoe", "ps4", "awesome car part", "test item", " test item6", " test item7", " test item8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rewards);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String goalSearch = intent.getStringExtra("goalSearch");
        mSearchGoalHeader.setText(String.format("Searching by: %s", goalSearch));

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchResults);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = ((TextView)view).getText().toString();
                Intent intent = new Intent(SearchRewards.this, ItemDetail.class);
                intent.putExtra("selectedItem",selectedItem);
                startActivity(intent);
            }
        });
    }
}
