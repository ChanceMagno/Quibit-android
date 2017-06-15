package com.epicodus.quibit.fragments;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.epicodus.quibit.R;
import com.epicodus.quibit.adapters.FirebaseQuibitsViewHolder;
import com.epicodus.quibit.models.Quibit;
import com.epicodus.quibit.ui.CreateQuibitActivity;
import com.epicodus.quibit.ui.MainActivity;
import com.epicodus.quibit.util.ItemTouchHelperAdapter;
import com.epicodus.quibit.util.OnStartDragListener;
import com.epicodus.quibit.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.epicodus.quibit.R.id.cardView;
import static com.epicodus.quibit.R.id.recyclerView;


public class QuibitsFragment extends Fragment implements  OnStartDragListener  {
    private OnStartDragListener mOnStartDragListener;
    public static final String TAG = "Quibitsfragment";
    private DatabaseReference mQuibitsReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private DatabaseReference mRef;
    RecyclerView mRecyclerView;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_rewards, container, false);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mQuibitsReference = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid()).child("exchanges");
        setUpFireBaseAdapter(view);

        return view;
    }

    private void setUpFireBaseAdapter(View view) {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Quibit, FirebaseQuibitsViewHolder>
                (Quibit.class, R.layout.quibit_fragment_drag, FirebaseQuibitsViewHolder.class, mQuibitsReference) {

            @Override
            protected void populateViewHolder(final FirebaseQuibitsViewHolder viewHolder, Quibit model, int position) {
                viewHolder.bindQuibit(model);

            }
        };

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }




    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

}
