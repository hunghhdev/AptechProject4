import request from "@/utils/request";

export function list(data) {
  return request({
    url: "/user/list",
    method: "get",
    params: data
  });
}

export function create(data) {
  return request({
    url: "/user/create",
    method: "post",
    data
  });
}

export function update(data) {
  return request({
    url: "/user/update",
    method: "put",
    data
  });
}

export function remove(data) {
  return request({
    url: "/user/delete",
    method: "put",
    params: data
  });
}

export function uploadAvatar(data) {
  var formData = new FormData();
  formData.append("file", data);
  return request({
    url: "/user/uploadAvatar",
    method: "post",
    headers: {
      "content-type": "multipart/form-data"
    },
    data: formData
  });
}
