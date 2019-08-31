import request from "@/utils/request";

export function personnelLevel() {
  return request({
    url: "/personnel-level/list",
    method: "get"
  });
}
