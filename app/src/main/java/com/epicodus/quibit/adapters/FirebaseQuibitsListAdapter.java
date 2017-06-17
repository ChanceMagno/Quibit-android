package com.epicodus.quibit.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.epicodus.quibit.R;
import com.epicodus.quibit.fragments.QuibitsFragment;
import com.epicodus.quibit.models.Quibit;
import com.epicodus.quibit.util.ItemTouchHelperAdapter;
import com.epicodus.quibit.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class FirebaseQuibitsListAdapter extends FirebaseRecyclerAdapter<Quibit, FirebaseQuibitsViewHolder > implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;


    public FirebaseQuibitsListAdapter(Class<Quibit> modelClass, int modelLayout,
                                      Class<FirebaseQuibitsViewHolder> viewHolderClass,
                                      Query ref, OnStartDragListener onStartDragListener, Context context){
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
        CardView mCardView;
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    protected void populateViewHolder(final FirebaseQuibitsViewHolder viewHolder, Quibit model, int position) {
        viewHolder.bindQuibit(model);


    }
}
