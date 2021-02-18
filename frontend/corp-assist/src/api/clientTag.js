import request from '@/utils/request'
const tagsApi = {
  addClientTagURL: process.env.VUE_APP_SYSTEM_URL + '/v1/tag',
  deleteClientTagURL: process.env.VUE_APP_SYSTEM_URL + '/v1/tag/',
  modifyClientTagURL: process.env.VUE_APP_SYSTEM_URL + '/v1/tag/',
  getClientTagsURL: process.env.VUE_APP_SYSTEM_URL + '/v1/tag',
  fuzzygetClientTagsURL: process.env.VUE_APP_SYSTEM_URL + '/v1/tag/info'
}
/**
 * 获取标签列表
 * @param {String} tagName 标签名称
 * @param {Number} pageNo 页码
 * @param {Number} pageSize 分页大小
 */
export function getClientTags (tagName, pageNo, pageSize) {
  return request({
    url: tagsApi.getClientTagsURL + '?tagName=' + tagName + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
    method: 'get'
  })
}
/**
 * 添加标签
 * @param {Object} form 标签表单信息
 * tagName	是	String	标签名称
 */
export function addClientTag (form) {
  return request({
    url: tagsApi.addClientTagURL,
    method: 'POST',
    data: form
  })
}
/**
 * 删除标签
 * @param {String} tagId 标签ID
 */
export function deleteClientTag (tagId) {
  return request({
    url: tagsApi.deleteClientTagURL + tagId,
    method: 'DELETE'
  })
}
/**
 * 标签名称模糊搜索
 * @param {String} tagName 标签名称
 * @param {String} count 查询条数
 */
export function fuzzygetClientTags (tagName, count) {
  return request({
    url: tagsApi.fuzzygetClientTagsURL + '?tagName=' + tagName + '&queryCnt=' + count,
    method: 'get'
  })
}
/**
 * 修改标签
 * @param {Object} form 标签表单信息
 * tagId 是	String	标签Id
 * tagName	是	String	标签名称
 */
export function modifyClientTag (form) {
  return request({
    url: tagsApi.modifyClientTagURL + form.tagId,
    method: 'PUT',
    data: form
  })
}
