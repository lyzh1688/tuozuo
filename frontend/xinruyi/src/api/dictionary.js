import request from '@/utils/request'
const apiList = {
  bankDict: process.env.VUE_APP_SYSTEM_URL + '/v1/search/bank',
  areaDict: process.env.VUE_APP_SYSTEM_URL + '/v1/search/area',
  commonDict: process.env.VUE_APP_SYSTEM_URL + '/v1/dict/'
}
/**
 * 查询通用字典
 * @param {String} group 字典类型
 */
export function getCommonDict (group) {
    return request({
      url: apiList.commonDict + group,
      method: 'GET'
    })
  }
/**
 *
 * @param {String}} areaLevel 地区等级
 * @param {String} areaCode 区域代码
 */
export function getAreaCode (areaLevel, areaCode) {
  return request({
    url: apiList.areaDict + '?areaLevel=' + areaLevel + '&areaCode=' + areaCode,
    method: 'GET'
  })
}
/**
 * @param {String} bankCode 银行code，查银行code时不传
 */
export function getBankDict (bankCode) {
  return request({
    url: apiList.bankDict + '?bankCode=' + bankCode,
    method: 'GET'
  })
}
