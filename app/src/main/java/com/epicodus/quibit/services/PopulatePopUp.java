package com.epicodus.quibit.services;


import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
        final Button mGotItButton = (Button) popupWindow.getContentView().findViewById(R.id.gotItButton);
            mGotItButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                    final DatabaseReference totalCostRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_QUERY_USERS).child(mUser.getUid()).child(Constants.FIREABASE_QUERY_TOTAL);
                    final DatabaseReference goalItemRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_QUERY_USERS).child(mUser.getUid()).child(Constants.FIREABASE_QUERY_GOAL);
                    totalCostRef.setValue(0);

                    goalItemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        DatabaseReference completeGoalRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_QUERY_USERS).child(mUser.getUid()).child(Constants.FIREABASE_QUERY_COMPLETED_GOALS);
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Item goal = dataSnapshot.getValue(Item.class);
                            completeGoalRef.push().setValue(goal);
                            goalItemRef.removeValue();
                            popupWindow.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            });

    }
}
