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

export function listUsing() {
  return request({
    url: "/booking/list-using",
    method: "get"
  })
}

export function cancelBook(data) {
  return request({
    url: "/booking/cancel-book",
    method: "post",
    data
  })
}

export function checkIn(data) {
  return request({
    url: "/booking/check-in",
    method: "post",
    data
  })
}

export function getById(data) {
  return request({
    url: "/booking/get-by-id",
    method: "get",
    params: data
  })
}

export function checkOut(data) {
  return request({
    url: "/booking/check-out",
    method: "post",
    data
  })
}
