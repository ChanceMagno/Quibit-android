package com.epicodus.quibit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.epicodus.quibit.R;
import com.epicodus.quibit.ui.MainActivity;

import butterknife.OnClick;



import static com.epicodus.quibit.R.layout.tab1_fragment;


public class tab1Fragment extends Fragment {
    public static final String TAG = "tab1fragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(tab1_fragment, container, false);
        Button button = (Button) view.findViewById(R.id.btnTest1);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
        }
    }

