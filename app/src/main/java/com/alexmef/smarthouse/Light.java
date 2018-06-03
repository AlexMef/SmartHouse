package com.alexmef.smarthouse;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Light implements ValueEventListener {

     static private final String TAG = "";
     private boolean lightState;

     final FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference reference = database.getReference("project/smarthouse-49146/database/smarthouse-49146/data");

     public Light(boolean lightState) {
          Log.d(TAG, "Light enabled = " + lightState);
     }



     @Override
     public void onDataChange(DataSnapshot dataSnapshot) {
          lightState = dataSnapshot.getValue(Boolean.class);
     }

     @Override
     public void onCancelled(DatabaseError databaseError) {

     }

     public boolean getLightState() {
          return lightState;
     }
}
