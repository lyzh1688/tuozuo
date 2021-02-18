// eslint-disable-next-line
import { UserLayout, BasicLayout, BlankLayout } from '@/layouts'
import { bxAnaalyse } from '@/core/icons'

// const RouteView = {
//   name: 'RouteView',
//   render: (h) => h('router-view')
// }

export const asyncRouterMap = [

  {
    path: '/clientManagement',
    name: 'ClientManagement',
    redirect: '/dashboard/workplace',
    component: BasicLayout,
    meta: { title: '客户管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'corpAssist.staff.normal', 'corpAssist.staff.visitor'] },
    children: [
      {
        path: '/clientManagement/tagList',
        name: 'TagList',
        component: resolve => require(['@/views/clientManagement/tagManagement/TagList'], resolve),
        meta: { title: '标签管理', keepAlive: true, permission: ['admin', 'corpAssist.staff.normal'] }
      },
      {
        path: '/clientManagement/myInfo',
        name: 'MyInfo',
        component: resolve => require(['@/views/user/MyInfo'], resolve),
        // component: () => import('@/views/user/MyInfo'),
        meta: { title: '我的', keepAlive: true, permission: ['admin', 'corpAssist.staff.normal', 'corpAssist.staff.visitor'] }
      }
    ]
  },
  {
    path: '/humanResourceService',
    name: 'humanResourceService',
    redirect: '/humanResourceService/resourcePool',
    component: BasicLayout,
    meta: { title: '人员管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'corpAssist.staff.normal'] },
    children: [
      {
        path: '/humanResourceService/resourcePool',
        name: 'HumanResourcePool',
        component: resolve => require(['@/views/humanRources/StaffResourcePool'], resolve),
        // component: () => import('@/views/user/staffList'),
        meta: { title: '人力资源池', keepAlive: true, permission: ['admin', 'corpAssist.staff.normal'] }
      },
      {
        path: '/humanResourceService/projectStaffList',
        name: 'ProjectStaff',
        component: resolve => require(['@/views/humanRources/ProjectStaff'], resolve),
        // component: () => import('@/views/user/staffInfo'),
        meta: { title: '项目人员管理', keepAlive: true, permission: ['admin', 'corpAssist.staff.normal'] }
      }
    ]
  },
  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    hidden: true,
    meta: { title: 'home' },
    redirect: '/dashboard/workplace',
    children: [

    ]
  },
  {
    path: '*', redirect: '/404', hidden: true
  }
]

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
    // dashboard
    {
      path: '/dashboard',
      name: 'dashboard',
      redirect: '/dashboard/workplace',
      component: BasicLayout,
      meta: { title: '仪表盘', keepAlive: true, icon: bxAnaalyse },
      children: [
        {
          path: '/dashboard/analysis/:pageNo([1-9]\\d*)?',
          name: 'Analysis',
          component: resolve => require(['@/views/dashboard/Analysis'], resolve),
          // component: () => import('@/views/dashboard/Analysis'),
          meta: { title: '分析页', keepAlive: false, permission: ['admin'] }
        },
        {
          path: '/dashboard/workplace',
          name: 'Workplace',
          component: resolve => require(['@/views/dashboard/Workplace'], resolve),
          // component: () => import('@/views/dashboard/Workplace'),
          meta: { title: '首页', keepAlive: true, noCache: true }
        }
      ]
    },
  {
    path: '/user',
    component: UserLayout,
    redirect: '/login',
    hidden: true,
    meta: { title: 'userLayout' },
    children: [
      {
        path: '/login',
        name: 'login',
        component: resolve => require(['@/views/user/Login'], resolve),
        // component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login'),
        meta: { title: '登陆', hidden: true, noCache: true }
      }
    ]
  },

  {
    path: '/404',
    component: resolve => require(['@/views/exception/404'], resolve)
    // component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')

  }

]
