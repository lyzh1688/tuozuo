import request from '@/utils/request'
const CompanyApi = {
  customInfo: '/v1/custom/detail/',
  customList: '/v1/custom/list', //
  addCustom: '/v1/custom', // post
  fuzzyQueryCustom: '/v1/custom', //
  fuzzyQueryCompany: '/v1/company', //
  updateCustom: '/v1/custom/', // put
  tradeflow: '/v1/custom/tradeflow/',
  companyInfo: '/v1/company/detail/',
  companyList: '/v1/company/list',
  addCompany: '/v1/company', // post
  updateCompany: '/v1/custom/', // put
  tradeOpration: '/v1/custom/trade', // put
  dictQuery: '/v1/dict/'// bizStatus,customType,registerStatus,event
}
/*
companyName	否	String	公司名称
queryCnt	否	String	查询条数，默认20
showAll	否	boolean	是否返回全部公司,true：是，false：否，此字段为控制台个独列表使用
*/
export function fuzzyQueryCompany (companyName, queryCnt, showAll) {
  return request({
    url: CompanyApi.fuzzyQueryCompany + '?companyName=' + companyName + '&queryCnt=' + queryCnt + '&showAll=' + showAll,
    method: 'GET'
  })
}
export function dictQuery (dictionName) {
  return request({
    url: CompanyApi.dictQuery + dictionName,
    method: 'GET'
  })
}
export function getCustomInfo (customId) {
  return request({
    url: CompanyApi.customInfo + customId,
    method: 'GET'
  })
}
/*
customName	否	String	客户名称
bizStatus	否	String	业务状态
pageNo	是	number	当前页码
pageSize	是	number	页数
*/
export function getCustomList (customName, bizStatus, pageNo, pageSize) {
  return request({
    url: CompanyApi.customList + '?customName=' + customName + '&bizStatus=' + bizStatus + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
    method: 'GET'
  })
}
/*
companyStatus	是	String	业务状态
registerStatus	否	String	注册状态，0：未注册，1：注册完成
pageNo	是	number	当前页码
pageSize	是	number	页数
*/
export function getCompanyList (companyStatus, registerStatus, pageNo, pageSize) {
  return request({
    url: CompanyApi.companyList + '?companyStatus=' + companyStatus + '&registerStatus=' + registerStatus + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
    method: 'GET'
  })
}
export function getCompanyInfo (companyId) {
  return request({
    url: CompanyApi.companyInfo + companyId,
    method: 'GET'
  })
}
export function getTradeflow (customId, pageNo, pageSize) {
  return request({
    url: CompanyApi.tradeflow + customId + '?pageNo=' + pageNo + '&pageSize=' + pageSize,
    method: 'GET'
  })
}
/*
customId	是	String	客户Id
customType	是	String	客户类型
province	是	String	所在城市
event	是	String	事件类型,INVEST:充值 CUT_PAYMENT:扣款
amount	是	number	金额
tradeSnapshot	否	MultiPartFile	凭证上传
*/
export function tradeOpration (params) {
  return request({
    url: CompanyApi.tradeOpration,
    method: 'PUT',
    data: params
  })
}
/*
customName	是	String	客户名称
customPswd	是	String	业务状态
province	是	String	所在城市
customType	是	String	客户类型，1:直营 2:代理商
customContact	是	String	客户联系方式
hasPaid	是	String	已缴纳服务费用 ，1：已支付 0：未支付
*/
export function addCustom (params) {
  return request({
    url: CompanyApi.addCustom,
    method: 'POST',
    data: params
  })
}
/*
customName	是	String	客户名称
customPswd	是	String	业务状态
province	是	String	所在城市
customType	是	String	客户类型，1:直营 2:代理商
customContact	是	String	客户联系方式
hasPaid	是	String	已缴纳服务费用 ，1：已支付 0：未支付
*/
export function updateCustom (params, customId) {
  return request({
    url: CompanyApi.updateCustom + customId,
    method: 'PUT',
    data: params
  })
}
