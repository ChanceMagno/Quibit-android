package com.epicodus.quibit.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.epicodus.quibit.R;
import com.epicodus.quibit.constants.Constants;
import com.epicodus.quibit.ui.CreateQuibitActivity;
import com.epicodus.quibit.ui.LoginActivity;
import com.epicodus.quibit.ui.MainActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import static java.lang.Float.parseFloat;


public class ProgressFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference mQuibitsReference;
    private ValueEventListener mListener;
    PieChart pieChart;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
    PieData pieData;
    Integer goalValue = 0;
    Integer percentageRounded = 0;
    String progressMessage;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.progress_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(user == null){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };



        FloatingActionButton addQuibitActionButton = (FloatingActionButton) view.findViewById(R.id.addQuibitfloatingActionButton);
        addQuibitActionButton.setOnClickListener(this);

        FloatingActionButton setGoalActionButton = (FloatingActionButton) view.findViewById(R.id.setGoalfloatingActionButton);
        setGoalActionButton.setOnClickListener(this);

        setGoalValue();

        getQuibitInfo();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setGoalfloatingActionButton:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.addQuibitfloatingActionButton:
                Intent intent1 = new Intent(getActivity(), CreateQuibitActivity.class);
                startActivity(intent1);
                break;
        }
    }

    public void calculatePercentage(Integer savedAmount){
        Double percentage;
        if (goalValue == 0){
            savedAmount = 0;
        } else if(goalValue < savedAmount) {
            savedAmount = goalValue;
        }

        percentage = (double) savedAmount / goalValue * 100;

        percentageRounded = Math.round(percentage.intValue());

        setProgressMessage(percentageRounded);

        createPieChart(getView());
    }

    public void setProgressMessage(Integer percentageRounded){
        if(percentageRounded == 0){
            progressMessage = "Start your Quibits!";
        } else if(percentageRounded < 30){
            progressMessage = "One step at a time..";
        } else if (percentageRounded < 60 && percentageRounded > 50) {
            progressMessage = "Over the hill!";
        } else if(percentageRounded > 80 && percentageRounded < 100) {
            progressMessage = "Almost there keep going!";
        } else if (percentageRounded == 100){
            progressMessage = "YOU HIT YOUR GOAL!";
        }
    }

    public void createPieChart(View view){
        pieChart = (PieChart)view.findViewById(R.id.chart1);
        PieEntryLabels = new ArrayList<String>();
        addValuesToPIEENTRY();
        pieDataSet = new PieDataSet(entries, "");
        addValuesToPieEntryLabels();
        pieData = new PieData(PieEntryLabels, pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateY(5000);
        pieData.setValueTextSize(14);
        Typeface pacifico = Typeface.createFromAsset(getActivity().getAssets(), "fonts/peralta.ttf");
        pieDataSet.setValueTypeface(pacifico);
        pieChart.setDescription(progressMessage);
        pieChart.setDescriptionTextSize(12);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(ColorTemplate.COLOR_SKIP);
        pieDataSet.setColors(new int[] { R.color.colorPrimary, R.color.positiveColor }, getActivity());
 }

    public void addValuesToPIEENTRY(){
        Integer remainingNeeded = 100 - percentageRounded;
        entries = new ArrayList<Entry>();
        entries.add(new BarEntry(remainingNeeded, 1));
        entries.add(new BarEntry(percentageRounded, 1));
    }

    public void addValuesToPieEntryLabels(){
        PieEntryLabels.add("Percent Needed");
        PieEntryLabels.add("Percent Saved");
    }

    public void getQuibitInfo() {
            mQuibitsReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("total");
            mQuibitsReference.addValueEventListener(mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer savedAmount = 0;
                if(dataSnapshot.getValue() != null) {
                    savedAmount = Math.round(parseFloat(String.valueOf(dataSnapshot.getValue())));
                }
                calculatePercentage(savedAmount);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void setGoalValue(){
        DatabaseReference goalRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("goal").child("salePrice");
        goalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    goalValue = Math.round(parseFloat(dataSnapshot.getValue(String.class)));
                } else{
                    goalValue = 0;
                    Toast.makeText(getActivity(), "No Goal has been set", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if ( mListener!= null) {
            mQuibitsReference.removeEventListener(mListener);
        }
    }

}