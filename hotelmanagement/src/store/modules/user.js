import { login, getInfo } from "@/api/login";
import { getToken, setToken, removeToken } from "@/utils/auth";
const config = require("../../../config/index");
const getDefaultState = () => {
  return {
    token: getToken(),
    name: "",
    avatar: "",
    id: {},
    permissions: [],
    service: config.dev.service
  }
}

const state = getDefaultState();

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_NAME: (state, name) => {
    state.name = name;
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar;
  },
  SET_ID: (state, id) => {
    state.id = id;
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions;
  }
};

const actions = {
  login({ commit }, userInfo) {
    const { username, password } = userInfo;
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password })
        .then(response => {
          const { data } = response;
          commit("SET_TOKEN", data);
          setToken(data);
          resolve();
        })
        .catch(error => {
          reject(error);
        });
    });
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token)
        .then(response => {
          const { data } = response;
          if (!data) {
            reject("Verification failed, please Login again.");
          }
          const { username, avatar, id, permissions } = data;
          commit("SET_ID", id);
          commit("SET_NAME", username);
          commit("SET_AVATAR", avatar);
          commit("SET_PERMISSIONS", permissions);

          resolve(data);
        })
        .catch(error => {
          reject(error);
        });
    });
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      commit("SET_TOKEN", "");
      removeToken();
      window.location.reload();
      Object.assign(state, getDefaultState());
      resolve();
    });
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit("SET_TOKEN", "");
      removeToken();
      resolve();
    });
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions
};
