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
    meta: { title: '财务服务', keepAlive: true, icon: 'account-book', permission: ['admin', 'shuiruyi.custom.normal'] },
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
        meta: { title: '我的', keepAlive: true, permission: ['admin', 'shuiruyi.custom.normal'] }
      },
      {
        path: '/financialServices/InvoiceStatistics',
        name: 'InvoiceStatistics',
        component: resolve => require(['@/views/user/InvoiceStatistics'], resolve),
        // component: () => import('@/views/user/MyInfo'),
        meta: { title: '开票统计', keepAlive: true, permission: ['admin', 'shuiruyi.custom.normal'] }
      },
      {
        path: '/financialServices/InvoiceList',
        name: 'InvoiceList',
        component: resolve => require(['@/views/user/InvoiceList'], resolve),
        // component: () => import('@/views/user/MyInfo'),
        meta: { title: '缴税统计', keepAlive: true, permission: ['admin', 'shuiruyi.custom.normal'] }
      }
    ]
  },
  {
    path: '/customService',
    name: 'CustomService',
    redirect: '/customService/customList',
    component: BasicLayout,
    meta: { title: '客户管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'shuiruyi.staff.normal'] },
    children: [
      {
        path: '/customService/customList',
        name: 'CustomList',
        component: resolve => require(['@/views/user/CustomList'], resolve),
        // component: () => import('@/views/user/CustomList'),
        meta: { title: '客户列表', keepAlive: true, permission: ['admin', 'shuiruyi.staff.normal'] }
      },
      {
        path: '/customService/customInfo',
        name: 'CustomInfo',
        hidden: true,
        component: resolve => require(['@/views/user/CustomInfo'], resolve),
        // component: () => import('@/views/user/CustomInfo'),
        meta: { title: '客户详情', keepAlive: true, permission: ['admin', 'shuiruyi.staff.normal'] }
      }
    ]
  },
  {
    path: '/customcompanyControl',
    name: 'customcompanyControl',
    redirect: '/customcompanyControl/MycompanyList',
    component: BasicLayout,
    meta: { title: '公司管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'shuiruyi.custom.normal'] },
    children: [
      {
        path: '/customcompanyControl/MycompanyList',
        name: 'MycompanyList',
        component: resolve => require(['@/views/company/MycompanyList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '公司列表', keepAlive: true, permission: ['admin', 'shuiruyi.custom.normal'] }
      }
    ]
  },
  {
    path: '/customContractControl',
    name: 'customContractControl',
    redirect: '/customContractControl/CustomContractList',
    component: BasicLayout,
    meta: { title: '合同管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'shuiruyi.custom.normal'] },
    children: [
      {
        path: '/customContractControl/CustomContractList',
        name: 'CustomContractList',
        component: resolve => require(['@/views/contract/CustomContractList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '合同列表', keepAlive: true, permission: ['admin', 'shuiruyi.custom.normal'] }
      },
      {
        path: '/customContractControl/TemplateList',
        name: 'TemplateList',
        component: resolve => require(['@/views/contract/TemplateList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '模板列表', keepAlive: false, permission: ['admin', 'shuiruyi.custom.normal'] }
      }
    ]
  },
  {
    path: '/customReceiptControl',
    name: 'customReceiptControl',
    redirect: '/customReceiptControl/CustomReceiptList',
    component: BasicLayout,
    meta: { title: '开票管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'shuiruyi.custom.normal'] },
    children: [
      {
        path: '/customReceiptControl/CustomReceiptList',
        name: 'customReceiptControl',
        component: resolve => require(['@/views/receipt/CustomReceiptList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '开票列表', keepAlive: true, permission: ['admin', 'shuiruyi.custom.normal'] }
      }
    ]
  },
  {
    path: '/staffcompanyControl',
    name: 'staffcompanyControl',
    redirect: '/staffcompanyControl/CompanyList',
    component: BasicLayout,
    meta: { title: '公司管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'shuiruyi.staff.normal'] },
    children: [
      {
        path: '/staffcompanyControl/CompanyList',
        name: 'CompanyList',
        component: resolve => require(['@/views/company/CompanyList'], resolve),
        // component: () => import('@/views/company/CompanyList'),
        meta: { title: '公司列表', keepAlive: true, permission: ['admin', 'shuiruyi.staff.normal'] }
      }
    ]
  },
  {
    path: '/staffContractControl',
    name: 'staffContractControl',
    redirect: '/staffContractControl/StaffContractList',
    component: BasicLayout,
    meta: { title: '合同管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'shuiruyi.staff.normal'] },
    children: [
      {
        path: '/staffContractControl/StaffContractList',
        name: 'StaffContractList',
        component: resolve => require(['@/views/contract/StaffContractList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '合同列表', keepAlive: true, permission: ['admin', 'shuiruyi.staff.normal'] }
      }
    ]
  },
  {
    path: '/staffReceiptControl',
    name: 'staffReceiptControl',
    redirect: '/staffReceiptControl/StaffCReceiptList',
    component: BasicLayout,
    meta: { title: '开票管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'shuiruyi.staff.normal'] },
    children: [
      {
        path: '/staffReceiptControl/StaffCReceiptList',
        name: 'StaffCReceiptList',
        component: resolve => require(['@/views/receipt/StaffCReceiptList'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '开票列表', keepAlive: true, permission: ['admin', 'shuiruyi.staff.normal'] }
      }
    ]
  },
  {
    path: '/staffSummaryControl',
    name: 'staffSummaryControl',
    redirect: '/staffSummaryControl/TaxSummary',
    component: BasicLayout,
    meta: { title: '统计管理', keepAlive: true, icon: 'account-book', permission: ['admin', 'shuiruyi.staff.normal'] },
    children: [
      {
        path: '/staffSummaryControl/TaxSummary',
        name: 'TaxSummary',
        component: resolve => require(['@/views/summary/TaxSummary'], resolve),
        // component: () => import('@/views/company/MycompanyList'),
        meta: { title: '纳税统计', keepAlive: true, permission: ['admin', 'shuiruyi.staff.normal'] }
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
