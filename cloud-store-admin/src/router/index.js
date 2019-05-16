import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */
import Layout from '../views/layout/Layout'
import {asyncRouterMap, constantRouterMap} from '@/router/modules/index'
const _import = require('./import/_import_' + process.env.NODE_ENV);
Vue.use(Router);


export default [
  asyncRouterMap,
  constantRouterMap
]
