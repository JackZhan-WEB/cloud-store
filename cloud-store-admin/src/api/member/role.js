import {default as api} from '@/utils/api'


export function getAllRoles() {
  return api({
    url: "/role/getAllRoles",
    method: "get"
  })
}
