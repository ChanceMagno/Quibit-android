package com.epicodus.quibit.services;


import android.widget.PopupWindow;
import android.widget.TextView;

import com.epicodus.quibit.R;
import com.epicodus.quibit.constants.Constants;
import com.epicodus.quibit.models.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopulatePopUp {

    public static void setGoalCompletionPopUp(final PopupWindow popupWindow){
        final TextView mTextView = (TextView) popupWindow.getContentView().findViewById(R.id.testtext);

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference goalRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_QUERY_USERS).child(mUser.getUid()).child(Constants.FIREABASE_QUERY_GOAL);
        goalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            Item goalItem;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 goalItem = dataSnapshot.getValue(Item.class);
                mTextView.setText(goalItem.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }



        });


    }
}
