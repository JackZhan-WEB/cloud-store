import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import member from './modules/member'
import permission from './modules/permission'
import getters from './getters'

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    app,
    member,
    permission
  },
  getters
});

export default store
