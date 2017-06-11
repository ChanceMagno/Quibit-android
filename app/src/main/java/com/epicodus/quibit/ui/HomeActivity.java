package com.epicodus.quibit.ui;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.epicodus.quibit.R;
import com.epicodus.quibit.adapters.SectionsPageAdapter;
import com.epicodus.quibit.fragments.tab1Fragment;
import com.epicodus.quibit.fragments.tab2Fragment;
import com.epicodus.quibit.fragments.QuibitsFragment;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    public static final String TAG = "TAB1Fragment";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_test);
            ButterKnife.bind(this);

             mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

            mViewPager = (ViewPager) findViewById(R.id.container);
            setupViewPager(mViewPager);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new tab1Fragment(), "About Us");
        adapter.addFragment(new tab2Fragment(), "Progress");
        adapter.addFragment(new QuibitsFragment(), "Quibits");
        viewPager.setAdapter(adapter);
    }











}
