import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import {getToken} from '@/utils/auth'
import store from '../store'

//Axios.defaults.baseURL = "/apis/v1"
// axios.defaults.baseURL = process.env.API_ROOT;
const service = axios.create({
  baseURL: process.env.API_ROOT, // api的base_url
  timeout: 15000                  // 请求超时时间2
});
// request拦截器
service.interceptors.request.use(config => {
  let token = getToken();
  if (token) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
    config.headers.token = `${token}`;
  }
  // if (config.url.indexOf(url) === -1) {
  //   config.url = url + config.url;/*拼接完整请求路径*/
  // }
  return config;
}, error => {
  // Do something with request error
  console.error(error); // for debug
  Promise.reject(error)
});
// respone拦截器
service.interceptors.response.use(response => {
    const res = response.data;
    if (res.code === 1) {
      Message({
        message: res.msg,
        type: 'success',
        duration: 3 * 1000
      });
      return res;
    }else if (res.code === 3) {
      Message({
        showClose: true,
        message: res.msg,
        type: 'error',
        duration: 500,
        onClose: () => {
          store.dispatch('FedLogOut').then(() => {
            location.reload()// 为了重新实例化vue-router对象 避免bug
          })
        }
      });
      return Promise.reject("未登录")
    } else if (res.code === 8888) {
      //不做全局处理的code
      return res;
    } else {
      Message({
        message: res.msg,
        type: 'error',
        duration: 3 * 1000
      });
      return Promise.reject(res)
    }
  },
  error => {
    console.error('err' + error);// for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 3 * 1000
    });
    return Promise.reject(error)
  }
);
export default service

