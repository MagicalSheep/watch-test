const EventEmitter = require('events')
var mqtt = require('mqtt')

class WebClient extends EventEmitter {
    /**
     * New a web client
     * 
     * Event:
     *  1. connect - connected to the broker
     *  2. reconnect - try to reconnect to the broker
     *  3. offline - disconnected to the broker
     *  4. error(err) - throw exception
     *  5. success(msg) - work completed successfully
     *  6. subsuccess(granted) - return the array of successfully subscribed topic
     *  7. message(topic, message) - return topic and (string)message
     * @param {string} host The address of broker
     * @param {string} port The port of broker
     */
    constructor(host, port) {
        super()
        this.host = host
        this.port = port
    }

    /**
     * Connect to the broker
     */
    connect() {
        this.client = mqtt.connect(this.host + ':' + this.port + '/mqtt')
        var instance = this
        this.client.on("connect", function () {
            instance.emit("connect")
        })
        this.client.on("reconnect", function () {
            instance.emit("reconnect")
        })
        this.client.on("offline", function () {
            instance.emit("offline")
        })
        this.client.on("error", function (err) {
            instance.emit("error", err)
        })
        this.client.on("message", function (topic, message) {
            instance.emit("message", topic, message.toString())
        })
    }

    /**
     * Disconnect to the broker
     */
    disconnect() {
        if (this.client != null) {
            this.client.end()
        }
    }

    /**
     * Publish topic to the broker
     * @param {string} topic Topic
     * @param {string} message Message
     * @param {int} qos QoS level
     * @param {boolean} isRetain Retain flag
     */
    publish(topic, message, qos, isRetain) {
        var instance = this
        this.client.publish(topic, message, { qos: qos, retain: isRetain }, function (err) {
            if (err) {
                instance.emit("error", err)
            } else {
                instance.emit("success", "Publish topic successfully")
            }
        })
    }

    /**
     * Subscribe the topic
     * @param {string/array/object} topic Topic/Topics
     */
    subscribe(topic) {
        var instance = this
        this.client.subscribe(topic, function (err, granted) {
            if (err) {
                instance.emit("error", err)
            } else {
                instance.emit("success", "Subscribe topic successfully")
                instance.emit("subsuccess", granted)
            }
        })
    }
}

module.exports = WebClient