package com.epicodus.quibit.ui;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.epicodus.quibit.R;
import com.epicodus.quibit.adapters.SectionsPageAdapter;
import com.epicodus.quibit.fragments.tab1Fragment;
import com.epicodus.quibit.fragments.tab2Fragment;
import com.epicodus.quibit.fragments.tab3Fragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {
    public static final String TAG = "TAB1Fragment";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    Button testbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

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
        adapter.addFragment(new tab3Fragment(), "Quibits");
        viewPager.setAdapter(adapter);
    }











}
