package com.epicodus.quibit.fragments;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.epicodus.quibit.R;
import com.epicodus.quibit.adapters.FirebaseQuibitsViewHolder;
import com.epicodus.quibit.models.Quibit;
import com.epicodus.quibit.ui.CreateQuibitActivity;
import com.epicodus.quibit.ui.MainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.epicodus.quibit.R.id.recyclerView;


public class QuibitsFragment extends Fragment{
    public static final String TAG = "Quibitsfragment";
    private DatabaseReference mQuibitsReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_rewards, container, false);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mQuibitsReference = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid()).child("exchanges");
        setUpFireBaseAdapter(view);


        return view;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.saveMoneyQuibitButton:
//                Toast.makeText(getActivity(), "This part is still under construction", Toast.LENGTH_LONG).show();
//                break;
//        }
//    }

    private void setUpFireBaseAdapter(View view){
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Quibit, FirebaseQuibitsViewHolder>
                (Quibit.class, R.layout.quibit_fragment, FirebaseQuibitsViewHolder.class, mQuibitsReference){

            @Override
            protected void populateViewHolder(FirebaseQuibitsViewHolder viewHolder, Quibit model, int position){
                viewHolder.bindQuibit(model);
            }
        };

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

}
