import request from '@/utils/request'
const clientApi = {
  addClientGroupURL: process.env.VUE_APP_SYSTEM_URL + '/v1/group',
  deleteClientGroupURL: process.env.VUE_APP_SYSTEM_URL + '/v1/group/',
  modifyClientGroupURL: process.env.VUE_APP_SYSTEM_URL + '/v1/group/',
  getClientGroupsURL: process.env.VUE_APP_SYSTEM_URL + '/v1/group',
  addGroupClientsURL: process.env.VUE_APP_SYSTEM_URL + '/v1/group/client/relation',
  getClientGroupDetailURL: process.env.VUE_APP_SYSTEM_URL + '/v1/group/detail/',
}
/**
 * 获取客户群列表
 * @param {String} tagName 标签名称
 * @param {String} groupName 客户群名称
 * @param {Number} pageNo 页码
 * @param {Number} pageSize 分页大小
 */
export function getClientGroups (tagName,groupName,pageNo,pageSize) {
  return request({
    url: clientApi.getClientGroupsURL +'?tagName='+tagName+'&groupName='+groupName+'&pageNo='+pageNo+'&pageSize='+pageSize,
    method: 'get'
  })
}
/**
 * 添加客户群
 * @param {Object} form 客户群表单信息
 * chatId	是	String	企业微信群ID	
groupName	是	String	群名称	
groupNotice	是	String	群通知	
operatorId	否	String	创建人ID	
operator	否	String	创建人	
source	否	String	来源	PC/Applet
 */
export function addClientGroup (form) {
  return request({
    url: clientApi.addClientGroupURL,
    method: 'POST',
    data: form
  })
}
/**
 * 删除客户群
 * @param {String} groupId 客户群ID
 */
export function deleteClientGroup (groupId) {
  return request({
    url: clientApi.deleteClientGroupURL + groupId,
    method: 'DELETE'
  })
}
/**
 * 获取客户群详情
 * @param {String} groupId 客户群名称
 */
export function getClientGroupDetail (groupId) {
  return request({
    url: clientApi.getClientGroupDetailURL + groupId,
    method: 'get'
  })
}
/**
 * 修改客户群
 * @param {Object} form 客户表单信息
 * groupId 是	String	客户群Id
 * chatId	是	String	企业微信群ID	
groupName	否	String	群名称	
groupNotice	否	String	群通知	
operatorId	否	String	创建人ID	
operator	否	String	创建人	
source	否	String	来源	PC/Applet
 */
export function modifyClientGroup (form) {
  return request({
    url: clientApi.modifyClientGroupURL + form.groupId,
    method: 'PUT',
    data: form
  })
}
/**
 * 客户群添加客户
 * @param {object} form 客户id 
 * groupId 客户id 
 * clients 标签ids 
 */
export function addGroupClients (form) {
    return request({
      url: clientApi.addGroupClientsURL,
      method: 'POST',
      data: form
    })
  }
