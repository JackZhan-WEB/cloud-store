/**
 *
 * =================================
 *       系统平台-菜单路由配置
 * =================================
 *
 */

import _import from '@/router/import'

/* 权限管理*/
const permission = [
  {
    'name': '/system/permission/permissionMgr.html',
    'path': '/system/permission/permissionMgr',
    desc: '权限管理',
    'component': _import('system/permission/userPermission/index'),
    meta: {isTab: true}
  }, {
    'name': '/system/permission/roleManager.html',
    'path': '/system/permission/roleManager',
    desc: '角色管理',
    'component': _import('system/permission/roleManager/index'),
    meta: {isTab: true}
  }
]


export default [
  ...permission
]
