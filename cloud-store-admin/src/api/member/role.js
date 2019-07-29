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
 * 更新角色
 */
export function updateRole(params) {
  return api({
    url: "/role/updateRole",
    method: "post",
    data: params
  });
}
/**
 * 删除角色
 */
export function deleteRole(params) {
  return api({
    url: "/role/deleteRole/"+params,
    method: "delete",
  });
}
/**
 * 批量删除角色
 */
export function batchDelete(params) {
  return api({
    url: "/role/batchDelete",
    method: "post",
    data: params
  });
}
