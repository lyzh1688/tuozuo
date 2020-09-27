import request from '@/utils/request'
const apiList = {
  salaryviewList: process.env.VUE_APP_SYSTEM_URL + '/v1/fund/list',
  fileUpload: process.env.VUE_APP_SYSTEM_URL + '/v1/fund/voucher',
  historyList: process.env.VUE_APP_SYSTEM_URL + '/v1/fund/history/',
  modifyFileupload: process.env.VUE_APP_SYSTEM_URL + '/v1/fund/voucher/',
  confirmSalary: process.env.VUE_APP_SYSTEM_URL + '/v1/fund/salary/confirmation/',
  verifySalary: process.env.VUE_APP_SYSTEM_URL + '/v1/fund/payoff/',
  modifySalaryList: process.env.VUE_APP_SYSTEM_URL + '/v1/fund/detail/',
  salaryList: process.env.VUE_APP_SYSTEM_URL + '/v1/fund/salary/detail'
}
/**
 * 获取工资发放列表
 * @param {String} companyId 公司id
 * @param {String} projectId 项目id
 * @param {String} status 工资发放状态
 * @param {String} pageNo 查询页数
 * @param {String} pageSize 分页大小
 */
export function getSalaryViewList (companyId, projectId, status, pageNo, pageSize) {
    return request({
        url: apiList.salaryviewList + '?companyId=' + companyId + '&projectId=' + projectId + '&status=' + status + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        method: 'get'
    })
}
/**
 * 上传凭证
 * @param {Object} form 文件表单
 * paymentId	否	String	发放工资Id	管理员上传凭证携带
projectId	是	String	项目Id
amount	是	String	金额
month	是	String	月份
payDate	是	String	发放日期	yyyymmdd
voucher	是	multipartfile	凭证	根据用户判断存放字段
 */
export function uploadFile (form) {
    const formData = new FormData()
    for (const i in form) {
      if (i.includes('voucher')) {
        formData.append(i, form[i].file.originFileObj)
      } else {
        formData.append(i, form[i])
      }
    }
    return request({
        url: apiList.fileUpload,
        method: 'POST',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data;charset=UTF-8'
        }
    })
}
/**
 * 修改上传凭证
 * @param {Object} form 文件表单
 * paymentId	否	String	发放工资Id	管理员上传凭证携带
projectId	是	String	项目Id
amount	是	String	金额
month	是	String	月份
payDate	是	String	发放日期	yyyymmdd
voucher	是	multipartfile	凭证	根据用户判断存放字段
 */
export function modifyFile (form) {
    const formData = new FormData()
    for (const i in form) {
        if (i.includes('payDate')) {
            continue
        }
      if (i.includes('voucher')) {
        if (typeof form[i] === 'string') {
          // formData.append(i, null)
        } else {
          formData.append(i, form[i].file.originFileObj)
        }
      } else {
        formData.append(i, form[i])
      }
    }
    return request({
        url: apiList.modifyFileupload + form.paymentId,
        method: 'puT',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data;charset=UTF-8'
        }
    })
}
/**
 * 获取工资发放历史
 * @param {String} companyId 公司id
 * @param {String} projectId 项目id
 * @param {String} beginMonth 开始月份
 * @param {String} endMonth 结束月份
 * @param {String} pageNo 页码
 * @param {String} pageSize 分页大小
 */
export function getHistorySalaryList (companyId, projectId, beginMonth, endMonth, pageNo, pageSize) {
    return request({
        url: apiList.historyList + projectId + '?companyId=' + companyId + '&beginMonth=' + beginMonth + '&endMonth=' + endMonth + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        method: 'get'
    })
}
/**
 * 确认工资明细
 * @param {String} paymentId 工资单id
 * @param {String} status 审核状态
 */
export function doConfirmSalary (paymentId, status) {
    return request({
        url: apiList.confirmSalary + paymentId,
        data: { status: status },
        method: 'put'
    })
}
/**
 * 审核发放工资申请
 * @param {String} paymentId 工资单id
 * @param {String} status 审核状态
 * @param {String} remark 备注
 */
export function doVerifySalary (paymentId, status, remark) {
    return request({
        url: apiList.verifySalary + paymentId,
        data: { status: status,
            remark: remark },
        method: 'put'
    })
}
/**
 * 获取工资明细
 * @param {String} paymentId 工资单id
 * @param {String} projectId 项目id
 * @param {String} startDate 开始日期
 * @param {String} endDate 结束日期
 * @param {String} staffId 人员id
 * @param {String} pageNo 页码
 * @param {String} pageSize 分页大小
 */
export function getDetailsalaryList (paymentId, projectId, startDate, endDate, staffId, pageNo, pageSize) {
    return request({
        url: apiList.salaryList + '?paymentId=' + paymentId + '&projectId=' + projectId + '&startDate=' + startDate + '&endDate=' + endDate + '&staffId=' + staffId + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        method: 'get'
    })
}
/**
 * 修改工资名单
 * @param {Object} form 修改工资列表
 * companyId	是	String	公司Id
projectId	是	String	项目Id
period	是	String	月份
payDate	是	String	发放时间
paymentId	是	String	发放Id
List<StaffSalary>	是	list	员工工资信息
staffSalary

参数名	必选	说明	备注
staffId	是	String	工资Id
salary	number	String	工资
 */
export function domodifySalaryList (form) {
    return request({
        url: apiList.modifySalaryList + form.paymentId,
        data: form,
        method: 'PUT'
    })
}
