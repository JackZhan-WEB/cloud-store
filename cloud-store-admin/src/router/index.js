import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */
import Layout from '../views/layout/Layout'

const _import = require('./_import_' + process.env.NODE_ENV);
console.log(process.env.NODE_ENV,'NODE_ENV');
console.log(process.env.BASE_URL,'BASE_URL');
Vue.use(Router);
export const constantRouterMap = [
  {path: '/login', component: _import('login/index'), hidden: true},
  {path: '/404', component: _import('404'), hidden: true},
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{
      path: 'dashboard', component: _import('dashboard/index')
    }]
  }
];
export default new Router({
  mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/member',
    component: Layout,
    redirect: '/member/',
    name: '用户管理',
    meta: {title: '用户管理', icon: ''},
    children: [
      {
        path: '',
        name: '用户列表',
        component: _import('member/member'),
        meta: {title: '用户列表', icon: ''},
        menu: 'member'
      },
      {
        path: 'member',
        name: '空位',
        component: _import('member/member'),
        meta: {title: '空位', icon: ''},
        menu: 'member'
      },
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/',
    name: '系统管理',
    meta: {title: '系统管理', icon: ''},
    children: [
      {
        path: '',
        name: '角色管理',
        component: _import('system/role'),
        meta: {title: '角色管理', icon: ''},
        menu: 'role'
      },
      {
        path: 'permissions',
        name: '权限管理',
        component: _import('system/permissions'),
        meta: {title: '权限管理', icon: ''},
        menu: 'permissions'
      },
      {
        path: 'menu',
        name: '菜单管理',
        component: _import('system/menu'),
        meta: {title: '菜单管理', icon: ''},
        menu: 'menu'
      },
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
];
