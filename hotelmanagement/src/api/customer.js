import request from "@/utils/request";

export function findES(data) {
    return request({
      url: "/customer/find",
      method: "get",
      params: data
    });
  }