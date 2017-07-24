package com.epicodus.quibit.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.quibit.R;
import com.epicodus.quibit.constants.Constants;
import com.epicodus.quibit.services.PopulatePopUp;
import com.epicodus.quibit.ui.CreateQuibitActivity;
import com.epicodus.quibit.ui.LoginActivity;
import com.epicodus.quibit.ui.MainActivity;
import com.epicodus.quibit.ui.SearchGoalsActivity;
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

import static android.R.style.Animation_Dialog;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static java.lang.Float.parseFloat;


public class ProgressFragment extends Fragment implements View.OnClickListener {
    private DisplayMetrics mMetrics;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private ConstraintLayout test;
    private View mView;
    private DatabaseReference mQuibitsReference;
    private ValueEventListener mListener;
    private PieChart pieChart;
    private ArrayList<Entry> entries;
    private ArrayList<String> PieEntryLabels;
    private PieDataSet pieDataSet;
    private PieData pieData;
    private Integer goalValue = 0;
    private  Integer percentageRounded = 0;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private int mDisplayWidth;
    private int mDisplayHeight;
    private int mTrueDisplayWidth;
    private int mTrueDisplayHeight;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.progress_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        TextView mHeaderTextView = (TextView) mView.findViewById(R.id.headerTextView);
        Typeface pacifico = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Pacifico.ttf");
        mHeaderTextView.setTypeface(pacifico);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        test = (ConstraintLayout) mView.findViewById(R.id.test);
        mTrueDisplayWidth = (int) mMetrics.widthPixels;
        mTrueDisplayHeight = (int) mMetrics.heightPixels;
        mDisplayHeight = (int) (mMetrics.heightPixels * .8);
        mDisplayWidth = (int) (mMetrics.widthPixels * .8);

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



        FloatingActionButton addQuibitActionButton = (FloatingActionButton) mView.findViewById(R.id.addQuibitfloatingActionButton);
        addQuibitActionButton.setOnClickListener(this);

        FloatingActionButton setGoalActionButton = (FloatingActionButton) mView.findViewById(R.id.setGoalfloatingActionButton);
        setGoalActionButton.setOnClickListener(this);

        setGoalValue();

        getQuibitInfo();

        return mView;
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
        } else if(goalValue <= savedAmount) {
            generateGoalCompletionPopup();
            savedAmount = goalValue;
        }

        percentage = (double) savedAmount / goalValue * 100;
        percentageRounded = Math.round(percentage.intValue());
        createPieChart(getView());
    }



    public void createPieChart(View view){
        pieChart = (PieChart)view.findViewById(R.id.chart1);
        PieEntryLabels = new ArrayList<String>();
        addValuesToPIEENTRY();
        pieChart.setDescription("");
        pieChart.getLegend().setEnabled(false);
        pieDataSet = new PieDataSet(entries, "");
        addValuesToPieEntryLabels();
        pieData = new PieData(PieEntryLabels, pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateY(5000);
        pieData.setValueTextSize(14);
        Typeface pacifico = Typeface.createFromAsset(getActivity().getAssets(), "fonts/peralta.ttf");
        pieDataSet.setValueTypeface(pacifico);
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

    public void generateGoalCompletionPopup(){
        playMusic();
        layoutInflater = (LayoutInflater) mView.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.goal_reached_fragment, null);
        popupWindow = new PopupWindow(container, mDisplayWidth, mDisplayHeight, true);
        popupWindow.setAnimationStyle(Animation_Dialog);
        popupWindow.showAtLocation(test, Gravity.NO_GRAVITY, (mTrueDisplayWidth-mDisplayWidth)/2, (mTrueDisplayHeight - mDisplayHeight)/2);
        PopulatePopUp.setGoalCompletionPopUp(popupWindow);
    }

    public void playMusic(){
        MediaPlayer mediaPlayer = MediaPlayer.create(mView.getContext(), R.raw.goal_completion);
        mediaPlayer.start();    }

    public void setGoalValue(){
        DatabaseReference goalRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_QUERY_USERS).child(user.getUid()).child(Constants.FIREABASE_QUERY_GOAL).child(Constants.FIREABASE_QUERY_SALE_PRICE);
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