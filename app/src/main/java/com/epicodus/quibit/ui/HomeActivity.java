package com.epicodus.quibit.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.epicodus.quibit.R;
import com.epicodus.quibit.adapters.SectionsPageAdapter;
import com.epicodus.quibit.fragments.tab1Fragment;
import com.epicodus.quibit.fragments.ProgressFragment;
import com.epicodus.quibit.fragments.QuibitsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    public static final String TAG = "TAB1Fragment";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acitivity_home);
            ButterKnife.bind(this);

            mAuth = FirebaseAuth.getInstance();

            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                }
            };

            mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

            mViewPager = (ViewPager) findViewById(R.id.container);
            setupViewPager(mViewPager);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProgressFragment(), "Progress");
        adapter.addFragment(new QuibitsFragment(), "Quibits");
        adapter.addFragment(new tab1Fragment(), "About Us");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}

