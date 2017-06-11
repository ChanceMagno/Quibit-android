package com.epicodus.quibit.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.quibit.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProgressFragment extends Fragment{
    public static final String TAG = "tab2fragment";

    PieChart pieChart ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_fragment, container, false);

        pieChart = (PieChart) view.findViewById(R.id.chart1);

        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();

        AddValuesToPIEENTRY();

        AddValuesToPieEntryLabels();

        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);

        pieDataSet.setColors(ColorTemplate.LIBERTY_COLORS);

        pieChart.setData(pieData);

        pieChart.animateY(5000);

        pieData.setValueTextSize(10);

        pieChart.setDescription("Your Quibit Progress");

    return view;
    }

    public void AddValuesToPIEENTRY(){



        entries.add(new BarEntry(50, 1));
        entries.add(new BarEntry(50, 1));

    }

    public void AddValuesToPieEntryLabels(){

        PieEntryLabels.add("Percent Needed");
        PieEntryLabels.add("Percent Saved");
    }
}




