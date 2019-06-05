import {getInfo, login, logout} from '@/api/login'
import {getToken, removeToken, setToken} from '@/utils/auth'
import {default as api} from '../../utils/api'
import store from '../../store'
import router from '../../router'

const member = {
  state: {
    username: "",
    memberId: "",
    avatar: 'https://www.gravatar.com/avatar/6560ed55e62396e40b34aac1e5041028',
    role: '',
    token: '',
    menus: [],
    permissions: [],
  },
  mutations: {
    SET_USER: (state, userInfo) => {
      state.username = userInfo.username;
      state.memberId = userInfo.id;
      state.role = userInfo.roles;
      state.menus = userInfo.menus;
      state.permissions = userInfo.permissions;
    },
    RESET_USER: (state) => {
      state.username = '';
      state.memberId = '';
      state.role = '';
      state.menus = [];
      state.permissions = [];
    }
  },
  actions: {
    // 登录
    Login({commit, state}, loginForm) {
      return new Promise((resolve, reject) => {
        api({
          url: "login/auth",
          method: "post",
          data: loginForm
        }).then(data => {
          if (data.code === 1) {
            //cookie中保存前端登录状态
            this.state.member.token = data.data;
            setToken(data.data);
          }
          resolve(data);
        }).catch(err => {
          reject(err)
        })
      })
    },
    // 获取用户信息
    GetInfo({commit, state}) {
      return new Promise((resolve, reject) => {
        console.log(getToken(), 'getToken');
        api({
          url: '/login/getInfo',
          method: 'post',
          headers: {'token': getToken()}
        }).then(data => {
          //储存用户信息
          commit('SET_USER', data.data);
          //cookie保存登录状态,仅靠vuex保存的话,页面刷新就会丢失登录状态
          // setToken();
          //生成路由
          let userPermission = data.data;
          store.dispatch('GenerateRoutes', userPermission).then(() => {
            //生成该用户的新路由json操作完毕之后,调用vue-router的动态新增路由方法,将新路由添加
            router.addRoutes(store.getters.addRouters)
          });
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    LogOut({commit}) {
      return new Promise((resolve) => {
        api({
          url: "login/logout",
          method: "post"
        }).then(data => {
          commit('RESET_USER');
          removeToken();
          resolve(data);
        }).catch(() => {
          commit('RESET_USER');
          removeToken()
        })
      })
    },
    // 前端 登出
    FedLogOut({commit}) {
      return new Promise(resolve => {
        commit('RESET_USER');
        removeToken();
        resolve()
      })
    }
  }
};
export default member
