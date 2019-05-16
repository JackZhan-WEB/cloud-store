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
