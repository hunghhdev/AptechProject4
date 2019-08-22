import axios from "axios";
import { MessageBox, Message } from "element-ui";
import store from "@/store";
import { getToken } from "@/utils/auth";
const config = require("../../config/index");

const service = axios.create({
  baseURL: config.dev.service + "/api",
  timeout: 20000
});

service.interceptors.request.use(
  config => {
    // do something before request is sent
    if (store.getters.token) {
      config.headers["authorization"] = getToken();
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */
  response => {
    const res = response.data;
    if (res.status !== "SUCCESS") {
      Message({
        message: res.message || "Error",
        type: "error",
        duration: 5 * 1000
      });
      if (res.data === 20000) {
        // to re-login
        store.dispatch("user/logout").then(() => {
          setTimeout(function() {
            location.reload();
          }, 1000);
        });
      }

      return Promise.reject(new Error(res.message || "Error"));
    } else {
      return res;
    }
  },
  error => {
    Message({
      message: error.message,
      type: "error",
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;
