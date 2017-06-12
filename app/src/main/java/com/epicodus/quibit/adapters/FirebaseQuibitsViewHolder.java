package com.epicodus.quibit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.epicodus.quibit.R;
import com.epicodus.quibit.models.Quibit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class FirebaseQuibitsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    View mView;
    Context mContext;

    public FirebaseQuibitsViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindQuibit(Quibit quibit){
        TextView quibitItemTextView = (TextView) mView.findViewById(R.id.quibitTextView);
        quibitItemTextView.setText(quibit.getExchangeItem());
    }

    @Override
    public void onClick(final View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final ArrayList<Quibit> quibits = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("exchanges");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    quibits.add(snapshot.getValue(Quibit.class));
                }

                int itemPosition = getLayoutPosition();

            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }

        });
    }









}
