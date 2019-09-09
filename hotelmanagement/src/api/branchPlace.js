import request from "@/utils/request";

export function fetchList(query) {
  return request({
    url: "/branch-place/list",
    method: "get",
    params: query
  });
}

export function create(data) {
  return request({
    url: "/branch-place/create",
    method: "post",
    data
  });
}

export function update(data) {
  return request({
    url: "/branch-place/update",
    method: "put",
    data
  });
}

export function remove(data) {
  return request({
    url: "/branch-place/delete",
    method: "put",
    params: data
  });
}

export function listBranchPlace() {
  return request({
    url: "/branch-place/list-branch-place",
    method: "get"
  });
}

export function listBranchPlaceByLevel(data) {
  return request({
    url: "/branch-place/list-branch-place-by-level",
    method: "get",
    params: data
  });
}