package com.alexmef.smarthouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.content.SharedPreferences.*;

public class Main extends AppCompatActivity implements View.OnClickListener {

    boolean bootLight = false;
    ImageButton light;
    ImageButton heating;
    ImageButton cooling;
    ImageButton settings;

    SharedPreferences sPref;

    String LIGHT_STATE = "LIGHT_STATE";




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

        loadLightState();
    }


    public static void main(String[] args) {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_light:
                changeLightState();
                //TODO Implement function change light state
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
        sPref = getPreferences(MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putBoolean(LIGHT_STATE, bootLight);
        ed.apply();
        Toast.makeText(this, "Light state saved", Toast.LENGTH_SHORT).show();
    }

    private void loadLightState() {
        sPref = getPreferences(MODE_PRIVATE);
        Boolean savedLightState = sPref.getBoolean(LIGHT_STATE, bootLight);
        bootLight = savedLightState;

        if (!bootLight){
            light.setImageResource(R.drawable.light_bulb_off);
        } else if (bootLight) {
            light.setImageResource(R.drawable.light_bulb_on);
        }
        Toast.makeText(this, "Light state loaded", Toast.LENGTH_SHORT).show();
    }

    private void changeLightState() {
        if (!bootLight){
            light.setImageResource(R.drawable.light_bulb_on);
            Toast.makeText(this, "Light was off, now is on", Toast.LENGTH_SHORT).show();
            bootLight = true;
        } else if (bootLight) {
            light.setImageResource(R.drawable.light_bulb_off);
            Toast.makeText(this, "Light was on, now is off", Toast.LENGTH_SHORT).show();
            bootLight = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveLightState();
    }

}
