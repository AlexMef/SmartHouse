package com.alexmef.smarthouse;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.alexmef.smarthouse.R.color.colorAccent;
import static com.alexmef.smarthouse.R.color.colorPrimary;
import static com.alexmef.smarthouse.R.color.colorWhite;

public class Settings extends AppCompatActivity implements OnClickListener {

    private TextView editIP;
    private Button connect;
    private Button graphs;

    SharedPreferences sharedPreferences;

    private final String CONNECTION_STATE = "CONNECTION_STATE";
    private final String IP_ADRESS_STATE = "IP_ADRESS_STATE";


    private String ipAdressState = "";
    private boolean connectionState = false;

    private final boolean OFF = false;
    private final boolean ON = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings);

        editIP = findViewById(R.id.editIP);
        connect = findViewById(R.id.buttonConnect);
        graphs = findViewById(R.id.buttonGraphs);

        connect.setOnClickListener(this);
        graphs.setOnClickListener(this);

        connect.setTextColor(getResources().getColor(colorWhite));
        loadConnectionState();

        // TODO Autoconnect if connection state = true
    }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonConnect:
                    changeConnectionState();



                    // TODO: 24.05.2018 Реализовать подключение к серверу (подключение/отключение)
                    break;
                case R.id.buttonGraphs:
                    // TODO: 24.05.2018 Реализовать вывод графиков с показаниями датчиков
                    break;
                    default:
                        break;

            }
        }

        private void saveConnectionState(){
            sharedPreferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPreferences.edit();
            ed.putBoolean(CONNECTION_STATE, connectionState);
            ed.putString(IP_ADRESS_STATE, ipAdressState);
            ed.apply();
            Toast.makeText(this, "Connection state saved", Toast.LENGTH_SHORT).show();
        }

        private void loadConnectionState() {
            sharedPreferences = getPreferences(MODE_PRIVATE);
            ipAdressState = sharedPreferences.getString(IP_ADRESS_STATE, ipAdressState);
            editIP.setText(ipAdressState);
            connectionState = sharedPreferences.getBoolean(CONNECTION_STATE, connectionState);

            Toast.makeText(this, "Connection state loaded", Toast.LENGTH_SHORT).show();
            if (!connectionState){
                connect.setText(R.string.connection_off);
                connect.setBackgroundColor(getResources().getColor(colorAccent));
                Toast.makeText(this, ipAdressState + " is disconnected, connection state " + connectionState, Toast.LENGTH_SHORT).show();
            } else {
                connect.setText(R.string.connection_on);
                connect.setBackgroundColor(getResources().getColor(colorPrimary));
                Toast.makeText(this, ipAdressState + " is connected, connection state " + connectionState, Toast.LENGTH_SHORT).show();
            }
        }

        private void changeConnectionState(){
            ipAdressState = String.valueOf(editIP.getText());
            if (!connectionState){
                connect.setText(R.string.connection_on);
                connect.setBackgroundColor(getResources().getColor(colorPrimary));
                Toast.makeText(this, ipAdressState + " was disconnected, now is connected", Toast.LENGTH_SHORT).show();
                connectionState = true;

            } else {
                connect.setText(R.string.connection_off);
                connect.setBackgroundColor(getResources().getColor(colorAccent));
                Toast.makeText(this, ipAdressState + "was connected, now is disconnected", Toast.LENGTH_SHORT).show();
                connectionState = false;
            }
        }

    @Override
    protected void onStop() {
        super.onStop();
        saveConnectionState();
    }
}

