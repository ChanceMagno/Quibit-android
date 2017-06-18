package com.epicodus.quibit.fragments;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;


import com.epicodus.quibit.R;
import com.epicodus.quibit.adapters.FirebaseQuibitsListAdapter;
import com.epicodus.quibit.adapters.FirebaseQuibitsViewHolder;
import com.epicodus.quibit.constants.Constants;
import com.epicodus.quibit.models.Quibit;
import com.epicodus.quibit.ui.CreateQuibitActivity;
import com.epicodus.quibit.ui.LoginActivity;
import com.epicodus.quibit.ui.MainActivity;
import com.epicodus.quibit.util.ItemTouchHelperAdapter;
import com.epicodus.quibit.util.OnStartDragListener;
import com.epicodus.quibit.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.epicodus.quibit.R.id.recyclerView;


public class QuibitsFragment extends Fragment implements OnStartDragListener, View.OnClickListener {
    public static final String TAG = "Quibitsfragment";
    private DatabaseReference mQuibitsReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private ItemTouchHelper mItemTouchHelper;
    private Adapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_rewards, container, false);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mQuibitsReference = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid()).child("exchanges");
        setUpFireBaseAdapter(view);


        return view;
    }

    private void setUpFireBaseAdapter(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        Query query = FirebaseDatabase.getInstance()
                .getReference("users").child(user.getUid()).child("exchanges")
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        mFirebaseAdapter = new FirebaseQuibitsListAdapter(Quibit.class,
                R.layout.quibit_fragment, FirebaseQuibitsViewHolder.class, query, this, getContext());

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

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

    @Override
    public void onClick(View v) {

    }
}
