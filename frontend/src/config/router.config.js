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
   // forms
  //  {
  //   path: '/form',
  //   redirect: '/form/base-form',
  //   component: BasicLayout,
  //   meta: { title: '表单页', icon: 'form', permission: ['admin'] },
  //   children: [
  //     {
  //       path: '/form/base-form',
  //       name: 'BaseForm',
  //       component: () => import('@/views/form/basicForm'),
  //       meta: { title: '基础表单', keepAlive: true, permission: ['admin'] }
  //     },
  //     {
  //       path: '/form/step-form',
  //       name: 'StepForm',
  //       component: () => import('@/views/form/stepForm/StepForm'),
  //       meta: { title: '分步表单', keepAlive: true, permission: ['admin'] }
  //     },
  //     {
  //       path: '/form/advanced-form',
  //       name: 'AdvanceForm',
  //       component: () => import('@/views/form/advancedForm/AdvancedForm'),
  //       meta: { title: '高级表单', keepAlive: true, permission: ['admin'] }
  //     }
  //   ]
  // },

  //     // list

  //     {
  //       path: '/list',
  //       name: 'list',
  //       component: BasicLayout,
  //       redirect: '/list/table-list',
  //       meta: { title: '列表页', icon: 'table', permission: ['admin'] },
  //       children: [
  //         {
  //           path: '/list/table-list/:pageNo([1-9]\\d*)?',
  //           name: 'TableList',
  //           component: () => import('@/views/list/TableList'),
  //           meta: { title: '查询表格', keepAlive: true, permission: ['admin'] }
  //         },
  //         {
  //           path: '/asdasd/list/basic-listasd',
  //           name: 'BasicList',
  //           hidden: true,
  //           component: () => import('@/views/list/BasicList'),
  //           meta: { title: '标准列表asd', keepAlive: true, permission: ['admin'] }
  //         },
  //         {
  //           path: '/list/card',
  //           name: 'CardList',
  //           component: () => import('@/views/list/CardList'),
  //           meta: { title: '卡片列表', keepAlive: true, permission: ['admin'] }
  //         },
  //         {
  //           path: '/list/search',
  //           name: 'SearchList',
  //           component: () => import('@/views/list/search/SearchLayout'),
  //           redirect: '/list/search/article',
  //           meta: { title: '搜索列表', keepAlive: true, permission: ['admin'] },
  //           children: [
  //             {
  //               path: '/list/search/article',
  //               name: 'SearchArticles',
  //               component: () => import('../views/list/search/Article'),
  //               meta: { title: '搜索列表（文章）', keepAlive: true, permission: ['admin'] }
  //             },
  //             {
  //               path: '/list/search/project',
  //               name: 'SearchProjects',
  //               component: () => import('../views/list/search/Projects'),
  //               meta: { title: '搜索列表（项目）', keepAlive: true, permission: ['admin'] }
  //             },
  //             {
  //               path: '/list/search/application',
  //               name: 'SearchApplications',
  //               component: () => import('../views/list/search/Applications'),
  //               meta: { title: '搜索列表（应用）', keepAlive: true, permission: ['admin'] }
  //             }
  //           ]
  //         }
  //       ]
  //     },
  //      // profile
  //      {
  //       path: '/profile',
  //       name: 'profile',
  //       component: BasicLayout,
  //       redirect: '/profile/basic',
  //       meta: { title: '详情页', icon: 'profile', permission: ['admin'] },
  //       children: [
  //         {
  //           path: '/profile/basic',
  //           name: 'ProfileBasic',
  //           component: () => import('@/views/profile/basic'),
  //           meta: { title: '基础详情页', permission: ['admin'] }
  //         },
  //         {
  //           path: '/profile/advanced',
  //           name: 'ProfileAdvanced',
  //           component: () => import('@/views/profile/advanced/Advanced'),
  //           meta: { title: '高级详情页', permission: ['admin'] }
  //         }
  //       ]
  //     },
  //      // result
  //      {
  //       path: '/result',
  //       name: 'result',
  //       component: BasicLayout,
  //       redirect: '/result/success',
  //       meta: { title: '结果页', icon: 'check-circle-o', permission: ['admin'] },
  //       children: [
  //         {
  //           path: '/result/success',
  //           name: 'ResultSuccess',
  //           component: () => import(/* webpackChunkName: "result" */ '@/views/result/Success'),
  //           meta: { title: '成功', keepAlive: false, hiddenHeaderContent: true, permission: ['admin'] }
  //         },
  //         {
  //           path: '/result/fail',
  //           name: 'ResultFail',
  //           component: () => import(/* webpackChunkName: "result" */ '@/views/result/Error'),
  //           meta: { title: '失败', keepAlive: false, hiddenHeaderContent: true, permission: ['admin'] }
  //         }
  //       ]
  //     },
  //     // Exception
  //     {
  //       path: '/exception',
  //       name: 'exception',
  //       component: BasicLayout,
  //       redirect: '/exception/403',
  //       meta: { title: '异常页', icon: 'warning', permission: ['admin'] },
  //       children: [
  //         {
  //           path: '/exception/403',
  //           name: 'Exception403',
  //           component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/403'),
  //           meta: { title: '403', permission: ['admin'] }
  //         },
  //         {
  //           path: '/exception/404',
  //           name: 'Exception404',
  //           component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404'),
  //           meta: { title: '404', permission: ['admin'] }
  //         },
  //         {
  //           path: '/exception/500',
  //           name: 'Exception500',
  //           component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/500'),
  //           meta: { title: '500', permission: ['admin'] }
  //         }
  //       ]
  //     },
  //      // account
  //      {
  //       path: '/account',
  //       component: BasicLayout,
  //       redirect: '/account/center',
  //       name: 'account',
  //       meta: { title: '个人页', icon: 'user', keepAlive: true, permission: ['admin'] },
  //       children: [
  //         {
  //           path: '/account/center',
  //           name: 'center',
  //           component: () => import('@/views/account/center'),
  //           meta: { title: '个人中心', keepAlive: true, permission: ['admin'] }
  //         },
  //         {
  //           path: '/account/settings',
  //           name: 'settings',
  //           component: () => import('@/views/account/settings/Index'),
  //           meta: { title: '个人设置', hideHeader: true, permission: ['admin'] },
  //           redirect: '/account/settings/base',
  //           hideChildrenInMenu: true,
  //           children: [
  //             {
  //               path: '/account/settings/base',
  //               name: 'BaseSettings',
  //               component: () => import('@/views/account/settings/BaseSetting'),
  //               meta: { title: '基本设置', hidden: true, permission: ['admin'] }
  //             },
  //             {
  //               path: '/account/settings/security',
  //               name: 'SecuritySettings',
  //               component: () => import('@/views/account/settings/Security'),
  //               meta: { title: '安全设置', hidden: true, keepAlive: true, permission: ['admin'] }
  //             },
  //             {
  //               path: '/account/settings/custom',
  //               name: 'CustomSettings',
  //               component: () => import('@/views/account/settings/Custom'),
  //               meta: { title: '个性化设置', hidden: true, keepAlive: true, permission: ['admin'] }
  //             },
  //             {
  //               path: '/account/settings/binding',
  //               name: 'BindingSettings',
  //               component: () => import('@/views/account/settings/Binding'),
  //               meta: { title: '账户绑定', hidden: true, keepAlive: true, permission: ['admin'] }
  //             },
  //             {
  //               path: '/account/settings/notification',
  //               name: 'NotificationSettings',
  //               component: () => import('@/views/account/settings/Notification'),
  //               meta: { title: '新消息通知', hidden: true, keepAlive: true, permission: ['admin'] }
  //             }
  //           ]
  //         }
  //       ]
  //     },
  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    hidden: true,
    meta: { title: 'home' },
    redirect: '/dashboard/workplace',
    children: [

      // other
      /*
      {
        path: '/other',
        name: 'otherPage',
        component: PageView,
        meta: { title: '其他组件', icon: 'slack', permission: [ 'dashboard' ] },
        redirect: '/other/icon-selector',
        children: [
          {
            path: '/other/icon-selector',
            name: 'TestIconSelect',
            component: () => import('@/views/other/IconSelectorView'),
            meta: { title: 'IconSelector', icon: 'tool', keepAlive: true, permission: [ 'dashboard' ] }
          },
          {
            path: '/other/list',
            component: RouteView,
            meta: { title: '业务布局', icon: 'layout', permission: [ 'support' ] },
            redirect: '/other/list/tree-list',
            children: [
              {
                path: '/other/list/tree-list',
                name: 'TreeList',
                component: () => import('@/views/other/TreeList'),
                meta: { title: '树目录表格', keepAlive: true }
              },
              {
                path: '/other/list/edit-table',
                name: 'EditList',
                component: () => import('@/views/other/TableInnerEditList'),
                meta: { title: '内联编辑表格', keepAlive: true }
              },
              {
                path: '/other/list/user-list',
                name: 'UserList',
                component: () => import('@/views/other/UserList'),
                meta: { title: '用户列表', keepAlive: true }
              },
              {
                path: '/other/list/role-list',
                name: 'RoleList',
                component: () => import('@/views/other/RoleList'),
                meta: { title: '角色列表', keepAlive: true }
              },
              {
                path: '/other/list/system-role',
                name: 'SystemRole',
                component: () => import('@/views/role/RoleList'),
                meta: { title: '角色列表2', keepAlive: true }
              },
              {
                path: '/other/list/permission-list',
                name: 'PermissionList',
                component: () => import('@/views/other/PermissionList'),
                meta: { title: '权限列表', keepAlive: true }
              }
            ]
          }
        ]
      }
      */
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
