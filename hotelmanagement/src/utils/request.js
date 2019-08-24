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
    if (store.getters.token) {
      config.headers["authorization"] = getToken();
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

service.interceptors.response.use(
  response => {
    const res = response.data;
    if (res.status !== "SUCCESS") {
      Message({
        message: res.message || "Error",
        type: "error",
        duration: 5 * 1000
      });
      if (res.status === "TIMEOUT") {
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
