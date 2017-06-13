package com.epicodus.quibit.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.epicodus.quibit.R;
import com.epicodus.quibit.constants.Constants;
import com.epicodus.quibit.models.Quibit;
import com.epicodus.quibit.ui.CreateQuibitActivity;
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
    private SharedPreferences mSharedPreferences;
    private SharedPreferences mSharedPreferenceSavedAmount;
    private SharedPreferences.Editor mSavedAmountEditor;
    public static final String TAG = "Progressfragment";
    private DatabaseReference mQuibitsReference;
    private ValueEventListener mListener;
    PieChart pieChart;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
    PieData pieData;
    Integer goalValue = 0;
    String mSavedPreferenceValue;
    String mSavedAmountPreferenceValue;
    Integer percentageRounded = 0;
    String progressMessage;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.progress_fragment, container, false);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mSavedPreferenceValue = mSharedPreferences.getString(Constants.PREFERENCES_GOALVALUE_KEY, "goalValue");

        mSharedPreferenceSavedAmount = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mSavedAmountEditor =  mSharedPreferenceSavedAmount.edit();
        mSavedAmountPreferenceValue =  mSharedPreferenceSavedAmount.getString(Constants.PREFERENCES_SAVEDAMOUNT_KEY, "0");

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
        mSavedAmountEditor.putString(Constants.PREFERENCES_SAVEDAMOUNT_KEY, savedAmount.toString()).apply();

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mQuibitsReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("exchanges");
            mQuibitsReference.addValueEventListener(mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer savedAmount = 0;
                final ArrayList<Quibit> quibits = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    quibits.add(snapshot.getValue(Quibit.class));
                }
                for (int i = 0; i < quibits.size(); i++) {
                    Integer amount = Math.round(quibits.get(i).getTotalQuibits());
                    savedAmount += amount;
                }
                calculatePercentage(savedAmount);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void setGoalValue(){
        if (mSavedPreferenceValue != "goalValue"){
            goalValue = Math.round(parseFloat(mSavedPreferenceValue));
        } else { goalValue = 0;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if ( mListener!= null) {
            mQuibitsReference.removeEventListener(mListener);
        }
    }

}