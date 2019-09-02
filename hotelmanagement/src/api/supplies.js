import request from "@/utils/request";

export function fetchList(query) {
  return request({
    url: "/supplies/list",
    method: "get",
    params: query
  });
}

export function create(data) {
  return request({
    url: "/supplies/create",
    method: "post",
    data
  });
}

export function update(data) {
  return request({
    url: "/supplies/update",
    method: "put",
    data
  });
}

export function remove(data) {
  return request({
    url: "/supplies/delete",
    method: "put",
    params: data
  });
}
