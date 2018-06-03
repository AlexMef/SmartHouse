package com.alexmef.smarthouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.SharedPreferences.*;

public class Main extends AppCompatActivity implements View.OnClickListener {

    //private static final String TAG = "";
    ImageButton light;
    ImageButton heating;
    ImageButton cooling;
    ImageButton settings;


    boolean lightState;


    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        light = findViewById(R.id.imageButton_light);
        heating = findViewById(R.id.imageButton_heating);
        cooling = findViewById(R.id.imageButton_cooling);
        settings = findViewById(R.id.imageButton_settings);

        light.setOnClickListener(this);
        heating.setOnClickListener(this);
        cooling.setOnClickListener(this);
        settings.setOnClickListener(this);

        // Getting reference to database and adding listener
        //mDatabase.addListenerForSingleValueEvent(singleEventListener);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        loadLightState();
    }


    public static void main(String[] args) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_light:
                changeLightState();
                // TODO: 5/29/18 implement function changeLightState
                break;
            case R.id.imageButton_heating:
                break;
            case R.id.imageButton_cooling:
                break;
            case R.id.imageButton_settings:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    // TODO: 22.05.2018 реализовать загрузку состояния кнопки или реализовать одно активити с изменяющимися layout


    private void saveLightState() {


        mDatabase.child("lightState").setValue(lightState);
        //Toast.makeText(this, "Light state saved", Toast.LENGTH_SHORT).show();
    }

    private void loadLightState() {
        // TODO: 6/3/18 fix bug with loading false state and turning false after returning from settings activity


        mDatabase.child("lightState").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Boolean value = dataSnapshot.getValue(Boolean.class);
                    lightState = value;
                    Toast.makeText(Main.this, "Value assigned" + value + " " + lightState, Toast.LENGTH_SHORT).show();
                    //Log.w("TAG", "Light state =" + lightState);

                    if (lightState) {
                        light.setImageResource(R.drawable.light_bulb_on);
                        Toast.makeText(Main.this, "Light set on", Toast.LENGTH_SHORT).show();
                    } else {
                        light.setImageResource(R.drawable.light_bulb_off);
                        Toast.makeText(Main.this, "Light set off", Toast.LENGTH_SHORT).show();
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Toast.makeText(this, "Light state loaded", Toast.LENGTH_SHORT).show();
    }

    private void changeLightState() {
        if (lightState){
            light.setImageResource(R.drawable.light_bulb_off);
            Toast.makeText(this, "Light was on, now is off", Toast.LENGTH_SHORT).show();
            lightState = false;
        } else if (!lightState) {
            light.setImageResource(R.drawable.light_bulb_on);
            Toast.makeText(this, "Light was off, now is on", Toast.LENGTH_SHORT).show();
            lightState = true;
        }
        mDatabase.child("lightState").setValue(lightState);
    }


    @Override
    protected void onStop() {
        super.onStop();
        saveLightState();
    }



}




