package com.epicodus.quibit.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.epicodus.quibit.R;
import com.epicodus.quibit.constants.Constants;
import com.epicodus.quibit.models.Quibit;
import com.epicodus.quibit.util.ItemTouchHelperViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import static java.lang.Float.parseFloat;

public class FirebaseQuibitsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {

    View mView;
    Context mContext;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth mAuth;
    public CardView mCardView;

    public FirebaseQuibitsViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        FloatingActionButton mSaveMoneyQuibitButton = (FloatingActionButton) itemView.findViewById(R.id.saveMoneyQuibitButton);
        mSaveMoneyQuibitButton.setOnClickListener(this);
        CardView mCardView = (CardView) mView.findViewById(R.id.cardView);
    }

    public void bindQuibit(Quibit quibit){
        TextView quibitItemTextView = (TextView) mView.findViewById(R.id.quibitTextView);
        quibitItemTextView.setText(String.format("Skip %s Today!", quibit.getExchangeItem()));
        CardView mCardView = (CardView) mView.findViewById(R.id.cardView);
    }

    @Override
    public void onClick(final View view){

        Query ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("exchanges").orderByChild(Constants.FIREBASE_QUERY_INDEX);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final ArrayList<Quibit> quibits = new ArrayList<>();
                final ArrayList<String> quibitsKey = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    quibitsKey.add(snapshot.getKey());
                    quibits.add(snapshot.getValue(Quibit.class));
                }

                int itemPosition = getLayoutPosition();

                switch (view.getId()) {
                    case R.id.saveMoneyQuibitButton:
                       updateQuibit(quibits, quibitsKey, itemPosition);
                        break;
                 }
            }
            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        });

    }

   public void updateQuibit(ArrayList<Quibit> quibits , ArrayList<String> quibitsKey , Integer itemPosition){
        mAuth = FirebaseAuth.getInstance();
        Float amount = parseFloat(quibits.get(itemPosition).getExchangeCost());
        int totalAmount = quibits.get(itemPosition).getTotalQuibits();
        totalAmount += amount;
        String quibitKey = quibitsKey.get(itemPosition);
        DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("exchanges").child(quibitKey).child("totalQuibits");
        updateRef.setValue(totalAmount);
    }



    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);

    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
}
