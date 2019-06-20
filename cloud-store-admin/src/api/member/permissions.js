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
 * 加载权限
 */
export function loadPerms() {
  return api({
    url: "/permissions/loadPerms",
    method: "post"
  })
}
