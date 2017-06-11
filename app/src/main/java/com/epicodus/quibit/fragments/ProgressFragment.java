package com.epicodus.quibit.fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.quibit.R;
import com.epicodus.quibit.models.Quibit;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleEntry;
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

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class ProgressFragment extends Fragment {
    public static final String TAG = "Progressfragment";
    private DatabaseReference mQuibitsReference;
    private FirebaseAuth mAuth;
    final ArrayList<Quibit> quibits = new ArrayList<>();
    PieChart pieChart ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    Integer savedAmount = 0;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.progress_fragment, container, false);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        createPieChart(view);
        mAuth = FirebaseAuth.getInstance();
        callDatabase(view);
        return view;
    }

    public void calculatePercentage(){

        Double percentage = (double) savedAmount / 1000 * 100;
        savedAmount = percentage.intValue() ;
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
     pieChart.setDescription("Your Quibit Progress");
     pieChart.setDrawHoleEnabled(true);
     pieChart.setHoleColor(ColorTemplate.COLOR_SKIP);
     pieDataSet.setColors(new int[] { R.color.colorPrimary, R.color.positiveColor }, getActivity());

 }

    public void addValuesToPIEENTRY(){
        Integer remainingNeeded = 100 - savedAmount;
        entries = new ArrayList<Entry>();
        entries.add(new BarEntry(remainingNeeded, 1));
        entries.add(new BarEntry(savedAmount, 1));
    }

    public void addValuesToPieEntryLabels(){
        PieEntryLabels.add("Percent Needed");
        PieEntryLabels.add("Percent Saved");
    }

    public void callDatabase(final View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final ArrayList<Quibit> quibits = new ArrayList<>();
        mQuibitsReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("exchanges");
        mQuibitsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    quibits.add(snapshot.getValue(Quibit.class));
                }
                for (int i = 0; i < quibits.size(); i++){
                    Integer amount = Math.round(parseFloat(quibits.get(i).getExchangeCost()));
                    savedAmount += amount;
                }
                calculatePercentage();
                createPieChart(view);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}