import request from '@/utils/request'
const clientApi = {
  addClientURL: process.env.VUE_APP_SYSTEM_URL + '/v1/client',
  deleteClientURL: process.env.VUE_APP_SYSTEM_URL + '/v1/client/',
  modifyClientURL: process.env.VUE_APP_SYSTEM_URL + '/v1/client/',
  getClientsURL: process.env.VUE_APP_SYSTEM_URL + '/v1/client',
  addClientTagsURL: process.env.VUE_APP_SYSTEM_URL + '/v1/client/tag/relation',
  getClientDetailURL: process.env.VUE_APP_SYSTEM_URL + '/v1/client/detail/'
}
/**
 * 获取客户列表
 * @param {String} tagName 标签名称
 * @param {String} clientName 客户名称
 * @param {Number} pageNo 页码
 * @param {Number} pageSize 分页大小
 */
export function getClients (tagName, clientName, pageNo, pageSize) {
  return request({
    url: clientApi.getClientsURL + '?tagName=' + tagName + '&clientName=' + clientName + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
    method: 'get'
  })
}
/**
 * 添加客户
 * @param {Object} form 客户表单信息
 * userId	是	String	客户微信ID
corpId	是	String	企业ID	关联企业
clientName	是	String	客户名称
clientNumber	是	String	客户电话
clientGender	是	String	客户性别
contact	否	String	联系人姓名
contactNumber	否	String	联系人电话
contactGender	否	String	联系人性别
alitalk	否	String	阿里旺旺号
operatorId	否	String	创建人ID
operator	否	String	创建人
source	否	String	来源
 */
export function addClient (form) {
  return request({
    url: clientApi.addClientURL,
    method: 'POST',
    data: form
  })
}
/**
 * 删除客户
 * @param {String} tagId 客户ID
 */
export function deleteClient (clientId) {
  return request({
    url: clientApi.deleteClientURL + clientId,
    method: 'DELETE'
  })
}
/**
 * 获取客户详情
 * @param {String} clientId 客户名称
 */
export function getClientDetail (clientId) {
  return request({
    url: clientApi.getClientDetailURL + clientId + '?type=1',
    method: 'get'
  })
}
/**
 * 修改客户
 * @param {Object} form 客户表单信息
 * clientId 是	String	客户Id
 * userId	是	String	微信ID
corpId	是	String	企业ID	关联企业
clientName	是	String	客户名称
clientNumber	是	String	客户电话
clientGender	是	String	客户性别
contact	否	String	联系人姓名
contactNumber	否	String	联系人电话
contactGender	否	String	联系人性别
alitalk	否	String	阿里旺旺号
operatorId	否	String	创建人ID
operator	否	String	创建人
source	否	String	来源	PC/Applet
 */
export function modifyClient (form) {
  return request({
    url: clientApi.modifyClientURL + form.clientId,
    method: 'PUT',
    data: form
  })
}
/**
 * 客户添加标签
 * @param {object} form 客户id
 * clientId 客户id
 * tags 标签ids
 */
export function addClientTags (form) {
    return request({
      url: clientApi.addClientTagsURL,
      method: 'POST',
      data: form
    })
  }
