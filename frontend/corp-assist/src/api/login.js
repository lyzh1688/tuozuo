import request from '@/utils/request'
const systemId = 'corpAssist'
const userApi = {
  Login: '/auth/v1/login',
  Logout: '/auth/v1/logout',
  PublicKey: '/auth/v1/publicKey',
  ForgePassword: '/auth/forge-password',
  Register: '/auth/register',
  twoStepCode: '/auth/2step-code',
  SendSms: '/account/sms',
  SendSmsErr: '/account/sms_err',
  // get my info
  UserInfo: '/user/info',
  UserMenu: '/user/nav'
}

/**
 * login func
 * parameter: {
 *     username: '',
 *     password: '',
 *     remember_me: true,
 *     captcha: '12345'
 * }
 * @param parameter
 * @returns {*}
 */
export function login (parameter) {
  parameter['userId'] = parameter.username
  delete parameter.username
  parameter['systemId'] = systemId
  return request({
    url: userApi.Login,
    method: 'post',
    data: parameter,
    transformRequest: [function (data) {
      // Do whatever you want to transform the data
      let ret = ''
      for (const it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' }
  })
}
export function getPublicKey (userId, roleGroup) {
  return request({
    url: userApi.PublicKey + '?userId=' + userId + '&roleGroup=' + roleGroup + '&systemId=' + systemId,
    method: 'get'
  })
}
export function getSmsCaptcha (parameter) {
  return request({
    url: userApi.SendSms,
    method: 'post',
    data: parameter
  })
}

export function getInfo () {
  return request({
    url: userApi.UserInfo,
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getCurrentUserNav () {
  return request({
    url: userApi.UserMenu,
    method: 'get'
  })
}

export function logout () {
  return request({
    url: userApi.Logout,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

/**
 * get user 2step code open?
 * @param parameter {*}
 */
export function get2step (parameter) {
  return request({
    url: userApi.twoStepCode,
    method: 'post',
    data: parameter
  })
}
