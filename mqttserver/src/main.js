var mqtt = require('mqtt')
var MqttServer = require('./mqtt/client.js')
var KafkaWorker = require('./kafka/kafka.js')
var host = 'mqtt://platform.magicalsheep.cn'
var port = '1883'

function main() {
    var client = new MqttServer(host, port)
    var kafka = new KafkaWorker('mqttserver', ['localhost:9092'], 'device')
    // Register event listener
    kafka.on("message", function (message) {
        console.log("Receive message from kafka: " + message)
    })
    kafka.connect()
    client.on("connect", function () {
        console.log("Connected to the broker")
        // Subscribe the topic
        client.subscribe(['deviceInfo', 'connect', 'disconnect'])
    })
    client.on("reconnect", function () {
        console.log("Trying to reconnect...")
    })
    client.on("offline", function () {
        console.log("Disconnected to the broker")
    })
    client.on("error", function (err) {
        console.log("error: " + err)
    })
    client.on("success", function (msg) {
        console.log(msg)
    })
    client.on("subsuccess", function (granted) {
        for (var i in granted) {
            console.log("Successfully subcribe the topic " + granted[i].topic + " (QoS level: " + granted[i].qos + ")")
        }
    })
    client.on("message", function (topic, message) {
        console.log("Receive message: " + message + " (from topic: " + topic + ")")
        switch (topic) {
            case 'deviceInfo':
                // Push into Kafka
                kafka.produce('watchmsg', [{ value: message }])
                break;
            case 'connect':
                console.log('Device ' + message + ' has connected')
                break;
            case 'disconnect':
                console.log('Device ' + message + ' has disconnected')
                break;
            default:
                break;
        }
    })
    // Connect to the broker
    client.connect();
}

main()