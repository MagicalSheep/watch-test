import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import store from './store'
var WebClient = require('./mqtt/client.js')
var host = 'ws://platform.magicalsheep.cn'
var port = '8083'

Vue.config.productionTip = false
Vue.use(ElementUI)

var client = new WebClient(host, port)
// Register event listener
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
      store.commit('updateDeviceInfo', message)
      break;
    case 'connect':
      store.commit('addDevice', message)
      break;
    case 'disconnect':
      store.commit('delDevice', message)
      break;
    default:
      break;
  }
})
// Connect to the broker
client.connect();

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

