import Vue from 'vue'
import Router from 'vue-router'
import { constantRouterMap } from '@/config/router.config'

// hack router push callback
const originalPush = Router.prototype.push
Router.prototype.push = function push (location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}
Vue.use(Router)

const createRouter = () => new Router({
  base: '/corp_assist_ui/', // 需要和打包路径一致，否则nginx刷新找不到。。。
  mode: 'history',
  routes: constantRouterMap
})
 const router = createRouter()
 export function resetAndRoutes (params) {
  router.matcher = new Router({
    base: '/corp_assist_ui/',
    mode: 'history',
    routes: constantRouterMap
  }).matcher
  router.addRoutes(params)
 }
 export default router
