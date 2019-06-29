import {default as api} from '@/utils/api'


export function getAllRoles() {
  return api({
    url: "/role/getAllRoles",
    method: "get"
  })
}

/**
 * 获取所有的角色列表分页
 */
export function getList(params) {
  return api({
    url: "/role/list",
    method: "get",
    params: params
  });
}

/**
 * 创建角色
 */
export function createRole(params) {
  return api({
    url: "/role/createRole",
    method: "post",
    data: params
  });
}
/**
 * 创建角色
 */
export function updateRole(params) {
  return api({
    url: "/role/updateRole",
    method: "post",
    data: params
  });
}
/**
 * 创建角色
 */
export function removeRole(params) {
  return api({
    url: "/role/removeRole",
    method: "post",
    data: params
  });
}
