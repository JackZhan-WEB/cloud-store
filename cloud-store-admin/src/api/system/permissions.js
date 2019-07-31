import {default as api} from '@/utils/api'

/**
 * 获取所有的的权限列表
 */
export function getList(params) {
  return api({
    url: "/permissions/list",
    method: "get",
    params: params
  });
}

/**
 * 获取所有的的可授权权限
 */
export function getPerms() {
  return api({
    url: "/permissions/getPerms",
    method: "get"
  });
}

/**
 * 加载权限
 */
export function loadPerms() {
  return api({
    url: "/permissions/loadPerms",
    method: "post"
  })
}

/**
 * 获取当前用户的权限
 */
export function getCheckPerms(params) {
  return api({
    url: "/permissions/getCheckPerms",
    method: "get",
    params: {"id": params}
  })
}
