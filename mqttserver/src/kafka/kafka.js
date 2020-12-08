const { Kafka } = require('kafkajs')
const EventEmitter = require('events')

class KafkaWorker extends EventEmitter {

    /**
     * 
     * @param {string} clientId Client id
     * @param {array} brokers Array of brokers
     * @param {string} groupId Consumer group id
     */
    constructor(clientId, brokers, groupId) {
        super()
        const kafka = new Kafka({
            clientId: clientId,
            brokers: brokers
        })
        this.producer = kafka.producer()
        this.consumer = kafka.consumer({ groupId: groupId })
    }

    /**
     * Connect to the kafka broker
     */
    connect() {
        this.producer.connect()
        this.consumer.connect()
    }

    /**
     * Disconnect to the kafka broker
     */
    disconnect() {
        this.producer.disconnect()
        this.consumer.disconnect()
    }

    /**
     * Push data into kafka
     * @param {string} topic Topic
     * @param {array} messages (Array of object which has only one property called 'value')Messages
     */
    produce(topic, messages) {
        this.producer.send({
            topic: topic,
            messages: messages
        })
    }

    /**
     * Cousume data of topic
     * @param {string} topic Topic
     */
    consume(topic) {
        var instance = this
        consumer.subscribe({ topic: topic, fromBeginning: true })
        consumer.run({
            eachMessage: async ({ topic, partition, message }) => {
                instance.emit("message", message.value.toString())
            },
        })
    }
}

module.exports = KafkaWorker