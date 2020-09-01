import request from '@/utils/request'
const apiList = {
  fuzzyProject: process.env.VUE_APP_SYSTEM_URL + '/v1/project',
  projectList: process.env.VUE_APP_SYSTEM_URL + '/v1/project/list',
  newProject: process.env.VUE_APP_SYSTEM_URL + '/v1/project',
  projectDetail: process.env.VUE_APP_SYSTEM_URL + '/v1/project/detail/',
  projectModify: process.env.VUE_APP_SYSTEM_URL + '/v1/project/',
  projectDone: process.env.VUE_APP_SYSTEM_URL + '/v1/project/'
}
/**
 * 模糊查询项目列表
 * @param {String} projectName 项目名称
 * @param {String} queryCnt 查询数量
 * @param {Boolean} all 是否全量查询，用于项目tab
 */
export function fuzzyQueryProject (projectName, queryCnt, all) {
    return request({
      url: apiList.fuzzyProject + '?projectName=' + projectName + '&queryCnt=' + queryCnt + '&all=' + all,
      method: 'GET'
    })
  }
/**
 * 获取项目列表
 * @param {String} projectId 项目Id
 * @param {String} industryType 行业类型
 * @param {String} budget 金额预算
 * @param {String} requirementStatus 需求状态
 * @param {String} pageNo 当前页码
 * @param {String} pageSize 分页大小
 */
export function getProjectList (projectId, industryType, budget, requirementStatus, pageNo, pageSize) {
  return request({
    url: apiList.projectList + '?projectId=' + projectId + '&industryType=' + industryType + '&budget=' + budget + '&requirementStatus=' + requirementStatus + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
    method: 'GET'
  })
}
/**
 * 新增项目
 * @param {Object} form 表单数据
 * companyName	是	String	公司名称
 * industryType	是	String	行业分类
 * releaseDate	是	String	发布时间
 * projectCycle	是	number	项目周期
 * staffNum	是	number	项目人员
 * province	是	String	省份
 * city	是	String	城市
 * district	是	String	区域
 * isResident	是	String	是否驻场	0，否，1，是
 * contactName	是	String	联系人
 * contact	是	String	联系电话
 * projectFile	是	MultipartFile	项目资料
 * desc	是	String	项目描述
 */
export function addNewProject (form) {
    return request({
        url: apiList.newProject,
        method: 'POST',
        data: form
      })
}
/**
 * 查询项目详情
 * @param {String} projectId 项目id
 */
export function getProjectDetail (projectId) {
    return request({
        url: apiList.projectDetail + '?projectId=' + projectId,
        method: 'GET'
    })
}
/**
 * 更像项目信息
 * @param {Object} form 更新表单
 * companyName	是	String	公司名称
 * industryType	是	String	行业分类
 * releaseDate	是	String	发布时间	发布时间早于今天不能修改
 * projectCycle	是	String	项目周期
 * staffNum	是	number	项目人员
 * province	是	String	省份
 * city	是	String	城市
 * district	是	String	区域
 * isResident	是	String	是否驻场	0，否，1，是
 * contactName	是	String	联系人
 * contact	是	String	联系电话
 * projectFile	是	MultipartFile	项目资料
 * desc	是	String	项目描述
 */
export function updateProject (form) {
    return request({
        url: apiList.projectModify,
        method: 'PUT',
        data: form
      })
}
