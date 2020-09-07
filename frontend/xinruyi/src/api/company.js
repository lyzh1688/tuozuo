import request from '@/utils/request'
const CompanyApi = {
  companyInfo: process.env.VUE_APP_SYSTEM_URL + '/v1/company/profile',
  companyAuthentication: process.env.VUE_APP_SYSTEM_URL + '/v1/company/authentication',
  companyDetail: process.env.VUE_APP_SYSTEM_URL + '/v1/company/detail/',
  companyFuzzy: process.env.VUE_APP_SYSTEM_URL + '/v1/company',
  companyupdate: process.env.VUE_APP_SYSTEM_URL + '/v1/company'
}
/**
 * 获取公司基本信息
 */
export function getCompanyInfo () {
  return request({
    url: CompanyApi.companyInfo,
    method: 'get'
  })
}
/**
 * 发起认证申请
 * @param {Object} form 认证申请表单信息
 * companyName	是	String	企业名称
businessLicense	是	MultipartFile	营业执照
bossName	是	String	法人姓名
bossId	是	String	法人身份证号
bossIdPicUp	是	MultipartFile	法人身份证正面照片
bossIdPicBack	是	MultipartFile	法人身份证背面照片
companyAccount	是	String	企业对公账号
companyAccountBank	是	String	企业对公账号所在银行
contactName	是	String	联系人姓名
contact	是	String	联系人电话
 */
export function addCompanyAuthentication (form) {
  const formData = new FormData()
  for (const i in form) {
    if (i.includes('File')) {
      formData.append(i, form[i].file.originFileObj)
    } else {
      formData.append(i, form[i])
    }
  }
  return request({
    url: CompanyApi.companyAuthentication,
    method: 'POST',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data;charset=UTF-8'
    }
  })
}
/**
 * 获取公司详情
 * @param {String} companyId 公司ID
 */
export function getCompanyDetail (companyId) {
  return request({
    url: CompanyApi.companyDetail + companyId,
    method: 'get'
  })
}
/**
 * 公司名称模糊搜索
 * @param {String} companyName 公司名称
 * @param {String} count 查询条数
 */
export function fuzzyQueryCompany (companyName, count) {
  return request({
    url: CompanyApi.companyFuzzy + '?companyName=' + companyName + '&queryCnt=' + count,
    method: 'get'
  })
}
/**
 * 修改认证信息
 * @param {Object} form 修改公司认证表单信息
 * companyName	否	String	企业名称
businessLicense	否	MultipartFile	营业执照
bossName	否	String	法人姓名
bossId	否	String	法人身份证号
bossIdPicUp	否	MultipartFile	法人身份证正面照片
bossIdPicBack	否	MultipartFile	法人身份证背面照片
companyAccount	否	String	企业对公账号
companyAccountBank	否	String	企业对公账号所在银行
contactName	否	String	联系人姓名
contact	否	String	联系人电话
 */
export function updateCompany (form) {
  const formData = new FormData()
  for (const i in form) {
    if (i.includes('File')) {
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
    url: CompanyApi.companyupdate + form.companyId,
    method: 'PUT',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data;charset=UTF-8'
    }
  })
}
