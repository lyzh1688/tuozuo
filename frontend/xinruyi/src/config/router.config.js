// eslint-disable-next-line
import { UserLayout, BasicLayout, BlankLayout } from '@/layouts'
import { bxAnaalyse } from '@/core/icons'

// const RouteView = {
//   name: 'RouteView',
//   render: (h) => h('router-view')
// }

export const asyncRouterMap = [

  {
    path: '/financialServices',
    name: 'FinancialServices',
    redirect: '/dashboard/workplace',
    component: BasicLayout,
    meta: { title: '财务服务', keepAlive: true, icon: 'account-book', permission: ['admin', 'xinruyi.custom.normal', 'xinruyi.custom.visitor'] },
    children: [
      {
        path: '/financialServices/console',
        name: 'Console',
        component: resolve => require(['@/views/user/Console'], resolve),
        meta: { title: '控制台', keepAlive: true, permission: ['admin', 'shuiruyi.custom.normal'] }
      },
      {
        path: '/financialServices/myInfo',
        name: 'MyInfo',
        component: resolve => require(['@/views/user/MyInfo'], resolve),
        // component: () => import('@/views/user/MyInfo'),
        meta: { title: '我的', keepAlive: true, permission: ['admin', 'xinruyi.custom.normal', 'xinruyi.custom.visitor'] }
      }
    ]
  },
  {
    path: '/humanResourceService',
    name: 'humanResourceService',
    redirect: '/humanResourceService/resourcePool',
    component: BasicLayout,
    meta: { title: '人员管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'xinruyi.custom.normal'] },
    children: [
      {
        path: '/humanResourceService/resourcePool',
        name: 'HumanResourcePool',
        component: resolve => require(['@/views/humanRources/StaffResourcePool'], resolve),
        // component: () => import('@/views/user/CustomList'),
        meta: { title: '人力资源池', keepAlive: true, permission: ['admin', 'xinruyi.custom.normal'] }
      },
      {
        path: '/humanResourceService/projectStaffList',
        name: 'ProjectStaff',
        component: resolve => require(['@/views/humanRources/ProjectStaff'], resolve),
        // component: () => import('@/views/user/CustomInfo'),
        meta: { title: '项目人员管理', keepAlive: true, permission: ['admin', 'xinruyi.custom.normal'] }
      }
    ]
  },
  {
    path: '/projectContorl',
    name: 'ProjectContorl',
    redirect: '/projectContorl/projectList',
    component: BasicLayout,
    meta: { title: '项目管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'xinruyi.custom.normal'] },
    children: [
      {
        path: '/projectContorl/projectList',
        name: 'ProjectList',
        component: resolve => require(['@/views/projects/ProjectList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '项目列表', keepAlive: true, permission: ['admin', 'xinruyi.custom.normal'] }
      }
    ]
  },
  {
    path: '/salaryControl',
    name: 'SalaryControl',
    redirect: '/salaryControl/SalaryViewList',
    component: BasicLayout,
    meta: { title: '资金管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'xinruyi.staff.normal', 'xinruyi.custom.normal'] },
    children: [
      {
        path: '/salaryControl/SalaryViewList',
        name: 'SalaryViewList',
        component: resolve => require(['@/views/salary/SalaryViewList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '待办列表', keepAlive: true, permission: ['admin', 'xinruyi.staff.normal', 'xinruyi.custom.normal'] }
      }
    ]
  },
  {
    path: '/eventControl',
    name: 'EventControl',
    redirect: '/eventControl/projectVerificationList',
    component: BasicLayout,
    meta: { title: '审核管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'xinruyi.staff.normal'] },
    children: [
      {
        path: '/eventControl/eventList',
        name: 'EventList',
        component: resolve => require(['@/views/verifications/EventList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '待办列表', keepAlive: true, permission: ['admin', 'xinruyi.staff.normal'] }
      },
      {
        path: '/eventControl/projectVerificationList',
        name: 'ProjectVerificationList',
        component: resolve => require(['@/views/verifications/ProjectList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '项目列表', keepAlive: true, permission: ['admin', 'xinruyi.staff.normal'] }
      },
      {
        path: '/eventControl/CompanyVerificationList',
        name: 'CompanyVerificationList',
        component: resolve => require(['@/views/verifications/CompanyList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '公司列表', keepAlive: true, permission: ['admin', 'xinruyi.staff.normal'] }
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
