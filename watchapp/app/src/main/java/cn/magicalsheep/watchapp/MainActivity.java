package cn.magicalsheep.watchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import cn.magicalsheep.watchapp.model.DeviceInfo;
import cn.magicalsheep.watchapp.utils.Factory;

public class MainActivity extends AppCompatActivity {
    private SensorEvent[] infos = new SensorEvent[3];

    private boolean isSending = false;

    private Button connect;
    private Button send;
    private TextView console;
    private static MainActivity instance;

    /**
     * Print message to the console
     *
     * @param msg Message
     */
    public static void print(String msg) {
        instance.runOnUiThread(() -> {
            if (instance.console.getText().equals("")) instance.console.setText(msg);
            else instance.console.setText(String.format("%s\n%s", instance.console.getText(), msg));
        });
    }

    private void connect() {
        Factory.getDeviceClient().connect();
        if (Factory.getDeviceClient().isConnected()) {
            Factory.getDeviceClient().publish("connect", Factory.getUuid(), 1, false);
            // Tell mqttserver that the device has connected
            this.runOnUiThread(() -> {
                connect.setText(R.string.disconnect);
                send.setEnabled(true);
                connect.setBackgroundColor(getResources().getColor(R.color.red));
            });
        }
    }

    private void disconnect() {
        if (isSending) isSending = false; // Stop sending message
        Factory.getDeviceClient().publish("disconnect", Factory.getUuid(), 1, false);
        // Tell mqttserver that the device is going to disconnect
        Factory.getDeviceClient().disconnect();
        if (!Factory.getDeviceClient().isConnected()) {
            this.runOnUiThread(() -> {
                connect.setText(R.string.connect);
                send.setEnabled(false);
                send.setText(R.string.send);
                connect.setBackgroundColor(getResources().getColor(R.color.purple_500));
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        connect = findViewById(R.id.connect);
        send = findViewById(R.id.send);
        console = findViewById(R.id.console);

        Factory.initDeviceClient();

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // register listener for sensors and store the values every times the change event happen
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                infos[0] = event;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                infos[1] = event;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                infos[2] = event;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

        connect.setOnClickListener(v -> {
            // If disconnected, try to connect to the broker and subscribe the command topic
            // Else disconnect to the broker
            new Thread(() -> {
                if (!Factory.getDeviceClient().isConnected()) {
                    connect();
                    Factory.getDeviceClient().subscribe("command");
                } else {
                    disconnect();
                }
            }).start();
        });
        send.setOnClickListener(v -> {
            if (!isSending) {
                isSending = true;
                send.setText(R.string.stop_send);
                new Thread(() -> {
                    // send device info to the broker
                    while (isSending) {
                        try {
                            Gson gson = new Gson();
                            DeviceInfo deviceInfo = getDeviceInfo();
                            Factory.getDeviceClient().publish("deviceInfo", gson.toJson(deviceInfo), 1, false);
                            MainActivity.print("[Send] " + gson.toJson(deviceInfo));
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                isSending = false;
                send.setText(R.string.send);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        disconnect();
        Factory.getDeviceClient().close();
        super.onDestroy();
    }

    /**
     * 获取设备传感器信息
     *
     * @return DeviceInfo
     */
    private DeviceInfo getDeviceInfo() {
        return new DeviceInfo(Factory.getUuid(),
                infos[0].values[0], infos[0].values[1], infos[0].values[2],
                infos[1].values[0], infos[1].values[1], infos[1].values[2],
                infos[2].values[0], infos[2].values[1], infos[2].values[2]);
    }
}