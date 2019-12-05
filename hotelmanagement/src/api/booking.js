import request from "@/utils/request";

export function listEmpty(query) {
  return request({
    url: "/booking/list-empty",
    method: "get",
    params: query
  });
}

export function booking(data) {
  return request({
    url: "/booking/book",
    method: "post",
    data
  });
}

export function listBooked() {
  return request({
    url: "/booking/list-booked",
    method: "get",
  });
}
