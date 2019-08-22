import request from "@/utils/request";

export function login(data) {
  return request({
    url: "/login",
    method: "get",
    params: data
  });
}

export function getInfo(token) {
  return request({
    url: "/getInfo",
    method: "get",
    params: { token }
  });
}
