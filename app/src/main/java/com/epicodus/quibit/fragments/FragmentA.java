package com.epicodus.quibit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.quibit.R;
import com.epicodus.quibit.adapters.SectionsPagerAdapter;
import com.epicodus.quibit.constants.Constants;
import com.epicodus.quibit.interfaces.OnFragmentInteractionListener;

import java.util.ArrayList;

public class FragmentA extends Fragment {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private OnFragmentInteractionListener mListener;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    ArrayList<String> tabName;

    public FragmentA() {
        // Required empty public constructor
    }
    public static FragmentA newInstance(String navigation) {
        FragmentA fragment = new FragmentA();
        Bundle args = new Bundle();
        args.putString(Constants.FRAG_A, navigation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        mViewPager = (ViewPager)view.findViewById(R.id.container);
        tabName=new ArrayList<String>();
        for(int i=0;i<7;i++){
            tabLayout.addTab(tabLayout.newTab().setText(getArguments().getString(Constants.FRAG_A)+" "+String.valueOf(i)));
            tabName.add(getArguments().getString(Constants.FRAG_A)+" "+String.valueOf(i));
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(),tabName);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                getChildFragmentManager().beginTransaction().addToBackStack(null).commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Note that we are passing childFragmentManager, not FragmentManager
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(),tabName);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

}