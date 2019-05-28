package com.example.deepakkumar.bagtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import DistanceCalculator.DistanceCalculator;
import helpers.MqttHelper;

public class HomeActivity extends AppCompatActivity {

    MqttHelper mqttHelper;

    TextView dataReceived;
    TextView dataReceived1;
    TextView dataReceived2;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button maps = (Button) findViewById(R.id.maps);
        maps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                Intent i = new Intent(HomeActivity.this,MapsActivity.class);
                startActivity(i);
            }
        });


        Button buzzer = (Button) findViewById(R.id.buzzer);
        buzzer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                Intent i = new Intent(HomeActivity.this,BuzzerActivity.class);
                startActivity(i);
            }
        });




        /* This code will receive data from the cloudMqtt */
        dataReceived = (TextView) findViewById(R.id.dataReceived);
        startMqtt();
    }

    private void startMqtt(){
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug",mqttMessage.toString());
                try {
                    //Receiving an string on a textField named datareceived
                    dataReceived.setText(mqttMessage.toString());
                    String[] receivedStream = mqttMessage.toString().split(",");
                    Double latt1 = Double.parseDouble(receivedStream[0]);
                    Double long1 = Double.parseDouble(receivedStream[1]);

                    dataReceived1.setText(receivedStream[0]);
                    dataReceived2.setText(receivedStream[1]);


                    // Calculation for distance
                    DistanceCalculator d = new DistanceCalculator();
                    double r = d.DistanceCalculator(latt1, long1, 12.000008, 13.000009);
                }
                catch (Exception  ex1){
                    dataReceived.setText(mqttMessage.toString());
                    dataReceived1.setText(ex1.toString());
                    dataReceived2.setText("Error!");
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

}
