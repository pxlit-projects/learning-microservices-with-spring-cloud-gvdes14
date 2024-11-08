// src/store.js (if using Vuex)
import { createStore } from 'vuex';

export default createStore({
  state: {
    user: {
      role: 'admin' // or 'user'
    }
  },
  getters: {
    isAdmin: state => state.user.role === 'admin'
  }
});