import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    deviceInfo: []
  },
  mutations: {
    updateDeviceInfo(state, payload) {
      state.deviceInfo.push({
        xginfo: payload.xginfo,
        yginfo: payload.yginfo,
        zginfo: payload.zginfo,
        xainfo: payload.xainfo,
        yainfo: payload.yainfo,
        zainfo: payload.zainfo,
        xsinfo: payload.xsinfo,
        ysinfo: payload.ysinfo,
        zsinfo: payload.zsinfo
      })
    }
  },
  actions: {
  },
  modules: {
  }
})
