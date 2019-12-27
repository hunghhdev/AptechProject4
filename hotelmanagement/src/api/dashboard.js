import request from "@/utils/request";

export function using() {
  return request({
    url: "/dashboard/using",
    method: "get",
  });
}

export function booking() {
  return request({
    url: "/dashboard/booking",
    method: "get",
  });
}

export function customer() {
  return request({
    url: "/dashboard/customer",
    method: "get",
  });
}

export function count() {
  return request({
    url: "/dashboard/count",
    method: "get",
  });
}
