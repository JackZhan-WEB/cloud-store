import {asyncRouterMap, constantRouterMap} from '@/router/index'
import store from "../index";
import {Message} from 'element-ui'

/**
 * 判断用户是否拥有此菜单
 * @param menus
 * @param route
 */
function hasPermission(menus, route) {
  if (route.menu) {
    /*
    * 如果这个路由有menu属性,就需要判断用户是否拥有此menu权限
    */
    return menus.indexOf(route.menu) > -1;
  } else {
    return true
  }
}

/**
 * 递归过滤异步路由表，返回符合用户菜单权限的路由表
 * @param asyncRouterMap
 * @param menus
 */
function filterAsyncRouter(asyncRouterMap, menus) {
  return asyncRouterMap.filter(route => {
    //filter,js语法里数组的过滤筛选方法
    if (hasPermission(menus, route)) {
      if (route.children && route.children.length) {
        //如果这个路由下面还有下一级的话,就递归调用
        route.children = filterAsyncRouter(route.children, menus);
        //如果过滤一圈后,没有子元素了,这个父级菜单就也不显示了
        return (route.children && route.children.length)
      }
      return true
    }
    return false
  });
}

const permission = {
  state: {
    routers: constantRouterMap, //本用户所有的路由,包括了固定的路由和下面的addRouters
    addRouters: [] //本用户的角色赋予的新增的动态路由
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers;
      state.routers = constantRouterMap.concat(routers) //将固定路由和新增路由进行合并, 成为本用户最终的全部路由信息
    }
  },
  actions: {
    GenerateRoutes({commit}, userPermission) {
      //生成路由
      return new Promise(resolve => {
        //roles是后台传过来的角色数组,比如['管理员','文章']
        const roles = userPermission.roles;
        if(roles.length === 0){
          Message({
            showClose: true,
            message: '该账号未绑定角色，联系管理员绑定角色后再尝试登陆！',
            type: 'error',
            duration: 3000,
            onClose: () => {
              store.dispatch('FedLogOut').then(() => {
                location.reload()// 为了重新实例化vue-router对象 避免bug
              })
            }
          });
          return Promise.reject("该账号未绑定角色")
        }
        const menus = userPermission.menus;
        let adminFlag = false;
        //声明 该角色可用的路由
        let accessedRouters;
        roles.some(function(item,index) {
          //已经进入判断为管理员就跳出循环
          if(adminFlag){
            return true;
          }
          if (item.name === 'admin') {
            //如果角色里包含'管理员',那么所有的路由都可以用
            //其实管理员也拥有全部菜单,这里主要是利用角色判断,节省加载时间
            adminFlag = true;
            accessedRouters = asyncRouterMap;
          }
        });
        if(!adminFlag){
          //否则需要通过以下方法来筛选出本角色可用的路由
          accessedRouters = filterAsyncRouter(asyncRouterMap, menus)
        }
        //执行设置路由的方法
        commit('SET_ROUTERS', accessedRouters);
        resolve()
      })
    }
  }
};
export default permission
