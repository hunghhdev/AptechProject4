import request from "@/utils/request";

export function fetchList(query) {
  return request({
    url: "/room/list",
    method: "get",
    params: query
  });
}

export function create(data) {
  return request({
    url: "/room/create",
    method: "post",
    data
  });
}

export function update(data) {
  return request({
    url: "/room/update",
    method: "put",
    data
  });
}

export function remove(data) {
  return request({
    url: "/room/delete",
    method: "put",
    params: data
  });
}
