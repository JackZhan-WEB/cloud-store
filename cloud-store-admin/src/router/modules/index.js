/**
 *
 * 菜单配置出口
 *
 */
// import system from './system'
import system from './system'

import _import from '@/router/import'
// import Router from "vue-router/types/index";

const asyncRouterMap = {
  name: 'home',
  path: '/',
  desc: '首页',
  component: _import('layout/Layout'),
  redirect: {path: '/system/permission/roleManager'},
  children: [{
    'name': '/system/permission/roleManager.html',
    'path': '/system/permission/roleManager',
    desc: '首页面板',
    'component': _import('system/permission/roleManager/index'),
    meta: {isTab: true}
  },
    ...system,

  ]
};
export const constantRouterMap = [
  {path: '/login', component: _import('login/index'), hidden: true},
  {path: '/404', component: _import('404'), hidden: true}
];
// export default new Router({
//   // mode: 'history', //后端支持可开
//   scrollBehavior: () => ({y: 0}),
//   routes: constantRouterMap
// });

