package cn.magicalsheep.watchapp.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public abstract class BaseClient {
    private boolean isConnected = false;
    private String host;
    private String port;
    private String clientID;
    private MqttClient mqtt;
    MemoryPersistence persistence = new MemoryPersistence();

    public BaseClient(String host, String port, String clientID) {
        this.host = host;
        this.port = port;
        this.clientID = clientID;
        try {
            mqtt = new MqttClient(host + ":" + port, clientID, persistence);
            registerListener();
        } catch (MqttException e) {
            caughtException(e);
        }
    }

    /**
     * Register listener for MqttClient
     */
    private void registerListener() {
        mqtt.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                caughtException(cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                handleMessage(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
//                deliveryComplete(token);
            }
        });
    }

    /**
     * Connect to the broker
     */
    public void connect() {
        try {
            mqtt.connect();
            isConnected = true;
        } catch (MqttException e) {
            caughtException(e);
        }
    }

    /**
     * Connect to the broker
     *
     * @param mqttConnectOptions Connection options
     */
    public void connect(MqttConnectOptions mqttConnectOptions) {
        try {
            mqtt.connect(mqttConnectOptions);
            isConnected = true;
        } catch (MqttException e) {
            caughtException(e);
        }
    }

    /**
     * Disconnect to the broker
     */
    public void disconnect() {
        try {
            mqtt.disconnect();
            isConnected = false;
        } catch (MqttException e) {
            caughtException(e);
        }
    }

    /**
     * Close the client
     */
    public void close(){
        try {
            mqtt.close();
        } catch (MqttException e) {
            caughtException(e);
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Publish the topic to the broker
     *
     * @param topic      Topic
     * @param message    Message
     * @param qos        QoS level
     * @param isRetained Whether is a retained message or not
     */
    public void publish(String topic, String message, int qos, boolean isRetained) {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(isRetained);
        try {
            mqtt.publish(topic, mqttMessage);
        } catch (MqttException e) {
            caughtException(e);
        }
    }

    /**
     * Subscribe the topic
     *
     * @param topic Topic
     */
    public void subscribe(String topic) {
        try {
            mqtt.subscribe(topic);
        } catch (MqttException e) {
            caughtException(e);
        }
    }

    /**
     * Subscribe the topic
     *
     * @param topics Topics
     */
    public void subscribe(String[] topics) {
        try {
            mqtt.subscribe(topics);
        } catch (MqttException e) {
            caughtException(e);
        }
    }

    /**
     * Unknown function
     *
     * @param token Unknown yet
     */
    public abstract void deliveryComplete(IMqttDeliveryToken token);

    /**
     * The function of handling message
     *
     * @param topic   Topic
     * @param message Message
     */
    public abstract void handleMessage(String topic, MqttMessage message);

    /**
     * Handle the throwable
     *
     * @param e Throwable
     */
    public abstract void caughtException(Throwable e);
}
