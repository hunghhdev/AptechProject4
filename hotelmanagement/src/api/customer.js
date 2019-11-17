import request from "@/utils/request";

export function findES(data) {
  return request({
    url: "/customer/find",
    method: "get",
    params: data
  });
}

export function create(data) {
  return request({
    url: "/customer/create",
    method: "post",
    data
  });
}

export function list(data) {
  return request({
    url: "/customer/list",
    method: "get",
    params: data
  });
}

export function update(data) {
  return request({
    url: "/customer/update",
    method: "put",
    data
  });
}

export function remove(data) {
  return request({
    url: "/customer/delete",
    method: "put",
    params: data
  });
}
