import {default as api} from '@/utils/api'

/**
 * 获取所有的的用户列表
 */
export function getList(params) {
  return api({
    url: "/member/list",
    method: "get",
    params: params
  });
}

export function getAllRoles() {
  return api({
    url: "/member/getAllRoles",
    method: "get"
  })
}

export function verifyUsername(params) {
  return api({
    url: "/member/verifyUsername",
    method: "get",
    params: params
  })
}

export function verifyPhone(params) {
  return api({
    url: "/member/verifyPhone",
    method: "get",
    params: params
  })
}

export function createUser(params) {
  return api({
    url: "/member/createUser",
    method: "post",
    data: params
  })
}

export function updateUser(params) {
  return api({
    url: "/member/updateUser",
    method: "post",
    data: params
  })
}
