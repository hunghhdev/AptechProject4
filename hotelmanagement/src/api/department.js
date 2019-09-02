import request from "@/utils/request";

export function fetchList(query) {
  return request({
    url: "/department/list",
    method: "get",
    params: query
  });
}

export function create(data) {
  return request({
    url: "/department/create",
    method: "post",
    data
  });
}
