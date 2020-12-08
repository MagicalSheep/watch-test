package cn.magicalsheep.watchapp.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import cn.magicalsheep.watchapp.MainActivity;

public class DeviceClient extends BaseClient {

    public DeviceClient(String host, String port, String clientID) {
        super(host, port, clientID);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // ignored
    }

    @Override
    public void handleMessage(String topic, MqttMessage message) {
        switch (topic) {
            case "command":
                MainActivity.print("[Message] " + new String(message.getPayload()));
                // TODO: Execute the command
                break;
            default:
                break;
        }
    }

    @Override
    public void caughtException(Throwable e) {
        MainActivity.print("[Error] " + e.getMessage());
    }
}
