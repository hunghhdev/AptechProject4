import request from "@/utils/request";

export function listEmpty(query) {
  return request({
    url: "/room/list",
    method: "get",
    params: query
  });
}
