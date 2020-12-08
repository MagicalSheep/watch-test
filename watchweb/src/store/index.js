import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    deviceInfo: [],
    devices: new Map()
  },
  mutations: {
    updateDeviceInfo(state, payload) {
      var info = JSON.parse(payload)
      state.devices.set(info['id'], info)
      state.deviceInfo = Array.from(state.devices.values())
    },
    addDevice(state, payload) {
      state.devices.set(payload, { "id": payload })
      state.deviceInfo = Array.from(state.devices.values())
    },
    delDevice(state, payload) {
      state.devices.delete(payload)
      state.deviceInfo = Array.from(state.devices.values())
    }
  },
  actions: {
  },
  modules: {
  }
})
