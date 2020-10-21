import request from '@/utils/request'
const apiList = {
  projectEventList: process.env.VUE_APP_SYSTEM_URL + '/v1/project/event/list',
  companyEventList: process.env.VUE_APP_SYSTEM_URL + '/v1/company/event/list',
  allEventList: process.env.VUE_APP_SYSTEM_URL + '/v1/event/list',
  companyAuth: process.env.VUE_APP_SYSTEM_URL + '/v1/company/status/',
  companySpot: process.env.VUE_APP_SYSTEM_URL + '/v1/company/spot',
  projectRelease: process.env.VUE_APP_SYSTEM_URL + '/v1/project/release',
  decruitment: process.env.VUE_APP_SYSTEM_URL + '/v1/project/staff/decruitment',
  staffAuthentication: process.env.VUE_APP_SYSTEM_URL + '/v1/staff/authentication',
  staffCertification: process.env.VUE_APP_SYSTEM_URL + '/v1/staff/certification',
  projectConfirmation: process.env.VUE_APP_SYSTEM_URL + '/v1/project/confirmation',
  customJoinProject: process.env.VUE_APP_SYSTEM_URL + '/v1/staff/participation/',
  customIdentification: process.env.VUE_APP_SYSTEM_URL + '/v1/staff/identification/'

}
/**
 * 查询待办列表
 * @param {String} companyId 公司Id
 * @param {String} projectId 项目Id
 * @param {String} eventId 事件Id
 * @param {String} status 事件状态
 * @param {String} pageNo 页码
 * @param {String} pageSize 分页大小
 */
export function getAllEventList (companyId, projectId, eventId, status, pageNo, pageSize) {
    return request({
        url: apiList.allEventList + '?companyId=' + companyId + '&projectId=' + projectId + '&eventId=' + eventId + '&status=' + status + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        method: 'get'
    })
}
/**
 * 查询公司事件列表
 * @param {String} companyId 公司Id
 * @param {String} industryId 行业Id
 * @param {String} province 省份
 * @param {String} city 城市
 * @param {String} district 区域
 * @param {String} status 	企业申请状态	字典companyApplyStatus
 * @param {String} beginDate 开始日期	yyyyMMdd
 * @param {String} endDate 结束日期	yyyyMMdd
 * @param {String} pageNo 当前页码
 * @param {String} pageSize 页数
 */
export function getCompanyEventList (companyId, industryId, province, city, district, status, beginDate, endDate, pageNo, pageSize) {
    return request({
        url: apiList.companyEventList + '?companyId=' + companyId + '&industryId=' + industryId + '&province=' + province + '&city=' + city + '&district=' + district + '&status=' + status + '&beginDate=' + beginDate + '&endDate=' + endDate + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        method: 'get'
    })
}
/**
 * 获取项目事件列表
 * @param {String} companyId  公司Id
 * @param {String} projectId 项目Id
 * @param {String} status projectStatus
 * @param {String} beginDate 开始日期yyyyMMdd
 * @param {String} endDate 结束日期yyyyMMdd
 * @param {String} pageNo 当前页码
 * @param {String} pageSize 页数
 */
export function getProjectEventList (companyId, projectId, status, industryType, beginDate, endDate, pageNo, pageSize) {
    return request({
        url: apiList.projectEventList + '?companyId=' + companyId + '&projectId=' + projectId + '&status=' + status + '&industryType=' + industryType + '&beginDate=' + beginDate + '&endDate=' + endDate + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        method: 'get'
    })
}
/**
 * 进行公司认证审核
 * @param {Object} form 审核表单
 * status	是	String	审核状态	参考数据库状态
remark	是	String	备注
 */
export function doCompanyAuth (form) {
    return request({
        url: apiList.companyAuth + form.companyId,
        method: 'put',
        data: form
    })
}
/**
 * 进行公司入住审核
 * @param {Object} form 审核表单
 * registerId	是	String	注册Id
companyId	是	String	企业Id
password	是	String	登录密码
remark	是	String	备注
result	是	String	0，拒绝，1，入驻
 */
export function docompanySpot (form) {
    return request({
        url: apiList.companySpot,
        method: 'put',
        data: form
    })
}
/**
 * 进行项目发布审核
 * @param {Object} form 审核表单
 * projectId	是	String	项目ID
fee	是	number	服务费率	审核成功，费率必传
status	是	String	审核状态
remark	是	String	备注
 */
export function doprojectRelease (form) {
    return request({
        url: apiList.projectRelease,
        method: 'put',
        data: form
    })
}
/**
 * 进行项目裁员审核
 * @param {Object} form 审核表单
 * companyId	是	String	企业Id
projectId	是	String	项目Id
staffId	是	String	员工Id
payStatus	是	String	工资结算状态，0，未结算，1，结算
auditResult	是	String	审核结果，0，审核失败，1，审核成功
remark	是	String	备注
 */
export function dodecruitment (form) {
    return request({
        url: apiList.decruitment,
        method: 'put',
        data: form
    })
}
/**
 * 进行项目完成审核
 * @param {Object} form 审核表单
 * projectId	是	String	项目Id
status	String	审核状态
remark	是	String	备注
 */
export function doprojectConfirmation (form) {
    return request({
        url: apiList.projectConfirmation,
        method: 'put',
        data: form
    })
}
/**
 * 项目加入申请审核
 * @param {Object} form 审核表单
 * eventId	是	String	事件Id
status	String	审核状态
remark	是	String	备注
 */
export function doJoinProject (form) {
    return request({
        url: apiList.customJoinProject + form.eventId,
        method: 'put',
        data: form
    })
}
/**
 * 人员实名认证审核
 * @param {Object} form 审核表单
 * registerId	是	String	人员注册id
status	String	审核状态
remark	是	String	备注
 */
export function doCustomIdentification (form) {
    return request({
        url: apiList.customIdentification + form.registerId,
        method: 'put',
        data: form
    })
}
// /**
//  * 进行项目裁员审核
//  * @param {Object} form 审核表单
//  * companyId	否	String	公司Id
// projectId	否	String	项目Id
// staff	是	String		员工姓名
// contact	是	String		联系方式
// pageNo	是	number	当前页码
// pageSize	是	number	页数
//  */
// export function dostaffAuthentication (form) {
//     return request({
//         url: apiList.staffAuthentication,
//         method: 'put',
//         data: form
//     })
// }
