import Mock from 'mockjs2'
import { builder, getBody, getQueryParameters } from '../util'
import RsaEncrypt from '@/components/RSAHelper/RsaEncrypt'

const roleGroup = ['staff', 'custom']
const username = ['ergou', 'sanpang', 'admin']
const systemId = ['xinruyi']
const accessToken = '1231231231'
// 强硬要求 ant.design 相同密码
// '21232f297a57a5a743894a0e4a801fc3',
// const password = ['8914de686ab28dc22f30d3d8e107ff6c', '21232f297a57a5a743894a0e4a801fc3'] // admin, ant.design
const password = ['admin', 'ant.design'] // admin, ant.design
const publicKey = 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ5lZ1t3QbKWiNwOrclbqtsY45ibVg38KxLJ+r2obGRI0gkwn1oA2hz3zB4qvH+rPLexVsDSOcdvS9TiZVw6NScCAwEAAQ=='
const privateKey = 'MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAnmVnW3dBspaI3A6tyVuq2xjjmJtWDfwrEsn6vahsZEjSCTCfWgDaHPfMHiq8f6s8t7FWwNI5x29L1OJlXDo1JwIDAQABAkBTlp3RtgCUlz5IKvLpNRfYIa1SBn+GR9IPS0moNq5kyPiK6UJ9tw8i4poXvWcSe7+i0mHQNRlt4O115SqHS4xxAiEAy1U1HEoRFDZsM3tcoMt5NNz/a4Fp5kC8U+hFgYpXXMkCIQDHbHzM9uvG7CaNDJlXIsTEocuXinjuiGBwwbab4iAqbwIhAJVbhaO+FEWQpHI8j5HdZK8cFtLVQQDNVIkd+NBq2jlhAiEAmHcn/yg04LPBUYs1XPoXB+JPHj/e7zdSzEI903YRc38CIEqKTV2/1jcGRj12769ZnWTUC+CchbTP8S+ynNXa47vP'
const login = (options) => {
  const body = getBody(options)
  console.log('mock: body', body)
  const passwordstr = RsaEncrypt.decodeData(body.password, privateKey)
  if (!username.includes(body.userId) || !password.includes(passwordstr)) {
    return builder({ isLogin: true }, '账户或密码错误', 401)
  }
  if (!roleGroup.includes(body.roleGroup)) {
    return builder({ isLogin: true }, '用户分组错误', 401)
  }
  if (!systemId.includes(body.systemId)) {
    return builder({ isLogin: true }, '系统信息出错', 401)
  }
  if (body.userId === 'admin') {
    return builder({
      'code': 0,
      'msg': 'OK',
      'data': {
        'accessToken': accessToken,
        'authority': 'admin'
    }
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
  }
  if (body.roleGroup === 'staff') {
    return builder({
      'code': 0,
      'msg': 'OK',
      'data': {
        'accessToken': accessToken,
        'authority': 'shuiruyi.staff.admin'
    }
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
  }
  if (body.roleGroup === 'custom') {
    return builder({
      'code': 0,
      'msg': 'OK',
      'data': {
        'accessToken': accessToken,
        'authority': 'shuiruyi.custom.normal'
    }
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
  }
}

const logout = () => {
  return builder({}, '[测试接口] 注销成功')
}

const getPublicKey = (options) => {
  const body = getQueryParameters(options)
  console.log('getPublicKey mock: params', body)
  if (!username.includes(body.userId)) {
    return builder({ isLogin: true }, '账户错误', 401)
  }
  if (!roleGroup.includes(body.roleGroup)) {
    return builder({ isLogin: true }, '用户分组错误', 401)
  }
  if (!systemId.includes(body.systemId)) {
    return builder({ isLogin: true }, '系统信息出错', 401)
  }
  return builder({
    'code': 0,
    'msg': 'OK',
    'data': {
      'publicKey': publicKey
  }
  }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
}

const smsCaptcha = () => {
  return builder({ captcha: Mock.mock('@integer(10000, 99999)') })
}

const twofactor = () => {
  return builder({ stepCode: Mock.mock('@integer(0, 1)') })
}

Mock.mock(/\/auth\/v1\/login/, 'post', login)
Mock.mock(/\/auth\/v1\/logout/, 'post', logout)
Mock.mock(/\/auth\/v1\/publicKey/, 'get', getPublicKey)
Mock.mock(/\/auth\/v1\/account\/sms/, 'post', smsCaptcha)
Mock.mock(/\/auth\/v1\/auth\/2step-code/, 'post', twofactor)
