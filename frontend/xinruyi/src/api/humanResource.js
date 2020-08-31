import request from '@/utils/request'
const CompanyApi = {
  staffList: process.env.VUE_APP_SYSTEM_URL + '/v1/staff/list',
  addStaff: process.env.VUE_APP_SYSTEM_URL + '/v1/staff',
  updateStaff: process.env.VUE_APP_SYSTEM_URL + '/v1/staff/',
  deleteStaff: process.env.VUE_APP_SYSTEM_URL + '/v1/staff/',
  salaryList: process.env.VUE_APP_SYSTEM_URL + '/v1/staff/salary',
  staffDetail: process.env.VUE_APP_SYSTEM_URL + '/v1/staff/detail/',
  staffFuzzyQuery: process.env.VUE_APP_SYSTEM_URL + '/v1/staff',
  projectStaffList: process.env.VUE_APP_SYSTEM_URL + '/v1/project/staff/',
  addprojectStaff: process.env.VUE_APP_SYSTEM_URL + '/v1/project/staff/',
  updateprojectStaff: process.env.VUE_APP_SYSTEM_URL + '/v1/project/staff/'
}
/**
 * 获取人力资源池列表
 * @param {String} pageNo 当前页码
 * @param {String} pageSize 分页大小
 */
export function getStaffList (pageNo, pageSize) {
  return request({
    url: CompanyApi.staffList + '?pageNo=' + pageNo + '&pageSize=' + pageSize,
    method: 'GET'
  })
}
/**
 * 新增人力
 * @param {Object} parameter 请求体
 * name	是	String	姓名
 * idNo	是	String	身份证
 * gender	是	String	性别
 * bankCard	是	String	银行卡
 * bank	是	String	银行名称	和开户行关联，单独建表
 * accntBank	是	String	开户行
 */
export function addStaff (parameter) {
    return request({
      url: CompanyApi.addStaff,
      method: 'POST',
      data: parameter
    })
  }
  /**
   * 修改人力
   * @param {object} parameter 请求体
   * gender	否	String	性别
   * bankCard	否	String	银行卡
   * bank	否	String	银行名称
   * accntBank	否	String	开户行
   */
  export function updateStaff (parameter) {
    return request({
      url: CompanyApi.updateStaff + parameter.id,
      method: 'PUT',
      data: parameter
    })
  }
  /**
   * 删除人力
   * @param {String} id 员工Id
   */
  export function deleteStaff (id) {
    return request({
      url: CompanyApi.deleteStaff + id,
      method: 'DELETE'
    })
  }
  /**
   * 获取人力详情
   * @param {String} id 员工Id
   */
  export function getStaffDetail (id) {
    return request({
      url: CompanyApi.staffDetail + id,
      method: 'GET'
    })
  }
  /**
   * 人力模糊查询
   * @param {String} staffName 人员名称
   * @param {String} queryCnt 查询条数
   */
  export function fuzzyQueryStaff (staffName, queryCnt) {
    return request({
      url: CompanyApi.staffFuzzyQuery + '?staffName=' + staffName + '&queryCnt=' + queryCnt,
      method: 'GET'
    })
  }
  /**
   * 获取历史工资单列表
   * @param {String} projectId 否	String	项目ID
   * @param {String} staffId 否	String	人员ID
   * @param {String} beginDate 否	String	开始时间	yyyyMMdd
   * @param {String} endDate 否	String	结束时间	yyyyMMdd
   * @param {String} pageNo 是	number	当前页码
   * @param {String} pageSize 是	number	页数
   */
  export function getSalaryList (projectId, staffId, beginDate, endDate, pageNo, pageSize) {
    return request({
      url: CompanyApi.salaryList + '?projectId=' + projectId + '&staffId=' + staffId + '&beginDate=' + beginDate + '&endDate=' + endDate + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
      method: 'GET'
    })
  }
  /**
   * 获取项目人员列表
   * @param {String} projectId 项目ID
   * @param {String} pageNo 当前页面
   * @param {String} pageSize 分页大小
   */
  export function getProjectStaffList (projectId, pageNo, pageSize) {
    return request({
      url: CompanyApi.projectStaffList + projectId + pageNo + '&pageSize=' + pageSize,
      method: 'GET'
    })
  }
  /**
   * 项目新增人员
   * @param {Object} parameter 输入表单
   * staffId	是	String	员工Id
   * salary	是	number	工资
   * gender	是	String	性别
   * enterDate	是	String	入团时间
   * quitDate	是	String	离团时间
   */
  export function addProjectStaff (parameter) {
    return request({
      url: CompanyApi.addprojectStaff + parameter.projectId,
      method: 'POST',
      data: parameter
    })
  }
/**
   * 项目人员修改
   * @param {Object} parameter 输入表单
   * projectId	否	String	项目Id
   * salary	否	number	工资
   * quitDate	否	String	离团时间	不能为当月
   */
  export function updateProjectStaff (parameter) {
    return request({
      url: CompanyApi.addprojectStaff + parameter.staffId,
      method: 'PUT',
      data: parameter
    })
  }
