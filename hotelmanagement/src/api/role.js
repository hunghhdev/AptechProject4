import request from "@/utils/request";

export function fetchList(query) {
  return request({
    url: "/role/list",
    method: "get",
    params: query
  });
}

export function create(data) {
  return request({
    url: "/role/create",
    method: "post",
    data
  });
}

export function update(data) {
  return request({
    url: "/role/update",
    method: "put",
    data
  });
}

export function remove(data) {
  return request({
    url: "/role/delete",
    method: "put",
    params: data
  });
}

export function permissions() {
  return request({
    url: "/role/permissions",
    method: "get"
  });
}

export function roles() {
  return request({
    url: "/role/roles",
    method: "get"
  });
}

