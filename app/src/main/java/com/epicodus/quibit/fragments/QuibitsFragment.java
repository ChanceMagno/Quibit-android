package com.epicodus.quibit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.epicodus.quibit.R;
import com.epicodus.quibit.adapters.FirebaseQuibitsListAdapter;
import com.epicodus.quibit.adapters.FirebaseQuibitsViewHolder;
import com.epicodus.quibit.constants.Constants;
import com.epicodus.quibit.models.Quibit;
import com.epicodus.quibit.ui.CreateQuibitActivity;
import com.epicodus.quibit.ui.LoginActivity;
import com.epicodus.quibit.util.ItemTouchHelperAdapter;
import com.epicodus.quibit.util.OnStartDragListener;
import com.epicodus.quibit.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import static com.epicodus.quibit.R.id.quibitsRecyclerView;


public class QuibitsFragment extends Fragment implements OnStartDragListener, View.OnClickListener {
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private ItemTouchHelper mItemTouchHelper;
    private View mView;
    private FloatingActionButton mAddQuibitButton;

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
        View view = inflater.inflate(R.layout.activity_quibits_recyclerview, container, false);
        setUpFireBaseAdapter(view);
        mView = view;
        mAddQuibitButton = (FloatingActionButton) mView.findViewById(R.id.newQuibitFloatingActionButton);
        mAddQuibitButton.setOnClickListener(this);

        return view;
    }

    private void setUpFireBaseAdapter(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_QUERY_USERS).child(user.getUid()).child(Constants.FIREBASE_QUERY_QUIBITS)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        mFirebaseAdapter = new FirebaseQuibitsListAdapter(Quibit.class,
                R.layout.quibit_fragment, FirebaseQuibitsViewHolder.class, query, this, getContext());

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(quibitsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }
//
//    public void replaceFragment(Fragment someFragment) {
//        getActivity().findViewById(R.id.quibitsRecyclerView).setActivated(false);
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(mView.getId(), someFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }




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
        if(v == mAddQuibitButton){
            Intent intent = new Intent(getContext(), CreateQuibitActivity.class);
            startActivity(intent);
        }

    }
}
