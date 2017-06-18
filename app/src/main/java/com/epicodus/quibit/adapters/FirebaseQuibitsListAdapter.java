package com.epicodus.quibit.adapters;

import android.content.Context;
import android.content.Intent;

import com.epicodus.quibit.models.Quibit;
import com.epicodus.quibit.ui.AboutActivity;
import com.epicodus.quibit.util.ItemTouchHelperAdapter;
import com.epicodus.quibit.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseQuibitsListAdapter extends FirebaseRecyclerAdapter<Quibit, FirebaseQuibitsViewHolder > implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Quibit> mQuibits = new ArrayList<>();



    public FirebaseQuibitsListAdapter(Class<Quibit> modelClass, int modelLayout,
                                      Class<FirebaseQuibitsViewHolder> viewHolderClass,
                                      Query ref, OnStartDragListener onStartDragListener, Context context){
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mQuibits.add(dataSnapshot.getValue(Quibit.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mQuibits, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mQuibits.remove(position);
        getRef(position).removeValue();
    }

    @Override
    protected void populateViewHolder(final FirebaseQuibitsViewHolder viewHolder, Quibit model, int position) {
        viewHolder.bindQuibit(model);
    }

    private void setIndexInFirebase(){
        for (Quibit quibit : mQuibits){
            int index = mQuibits.indexOf(quibit);
            DatabaseReference ref = getRef(index);
            quibit.setIndex(Integer.toString(index));
            ref.setValue(quibit);
        }
    }

    @Override
    public void cleanup(){
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }
}
