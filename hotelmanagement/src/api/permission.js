import request from "@/utils/request";

export function list(query) {
  return request({
    url: "/permission/list",
    method: "get",
    params: query
  });
}
