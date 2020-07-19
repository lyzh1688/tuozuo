import Mock from 'mockjs2'
import { builder, getBody, getQueryParameters } from '../util'
const ergouCustomInfo = (options) => {
    const body = getBody(options)
    console.log('ergouCustomInfo mock: body', body)
    return builder({
        'code': 0,
        'msg': 'OK',
        'data': {
            'customName': '蚂蚁金福',
            'customContact': '1388888888',
            'balance': 156374.378,
            'totalServerCharge': 12243.35,
            'province': '上海市,黄浦区'
        }
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
}
const adminCustomInfo = (options) => {
    const body = getBody(options)
    console.log('adminCustomInfo mock: body', body)
    return builder({
        'code': 0,
        'msg': 'OK',
        'data': {
            'customName': '蚂蚁金福admin',
            'customContact': '1388888888',
            'balance': 156374.378,
            'totalServerCharge': 12243.35,
            'province': '上海市,黄浦区'
        }
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
}
const adminTradeFlow = (options) => {
    const body = getQueryParameters(options)
    console.log('adminTradeFlow mock: body', body)
    return builder({
        'code': 0,
        'msg': 'OK',
        'data': {
            'tradeflow':
                [
                    {
                        'tradeDate': '2020-07-12',
                        'event': 'INVEST',
                        'amount': 1125.34,
                        'balance': 189457.12,
                        'remark': '打款给支付宝12234.23元'
                    }, {
                        'tradeDate': '2020-07-13',
                        'event': 'INVEST',
                        'amount': 1125.34,
                        'balance': 189457.12,
                        'remark': '打款给支付宝12234.23元'
                    }
                ],
            'total': 40
        }
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
}
const eventDict = (options) => {
    const body = getQueryParameters(options)
    console.log('eventDict mock: body', body)
    return builder({
            'code': 0,
            'msg': 'OK',
            'data':
                [
                    { 'id': 'INVEST', 'name': '充值' },
                    { 'id': 'CUT_PAYMENT', 'name': '扣款' }
                ]
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
}
const fuzzyQueryCompany = (options) => {
    const body = getQueryParameters(options)
    console.log(' fuzzyQueryCompany mock: body', body)
    if (body.showAll) {
        return builder({
                'code': 0,
                'msg': 'OK',
                'data':
                    [
                        {
                            'id': 'dfzq',
                            'name': '东方证券'
                        },
                        {
                            'id': 'xfzq',
                            'name': '西方证券'
                        },
                        {
                            'id': 'bfzq',
                            'name': '北方证券'
                        }
                    ]
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
    }
}
const dfzqCompanyInfo = (options) => {
    const body = getQueryParameters(options)
    console.log('dfzqCompanyInfo mock: body', body)
    return builder({
        'code': 0,
        'msg': 'OK',
        'data': {
                'companyInfo': {
                        'companyName': '公司名称',
                        'companyStatus': '注册完成',
                        'tax': 0.5,
                        'address': '2022-02-02',
                        'totalInvoiceNum': 5,
                        'invoicedNum': 3,
                        'totalInvoiceAmt': 156374.378,
                        'freeDeliveryCnt': 12,
                        'includeCancel': '1',
                        'beginDate': '2020-07-12 12:00:00',
                        'endDate': '2021-07-12 12:00:00'
                        },
                'bossInfo': {
                        'bossName': '投资人姓名',
                        'bossId': '投资人身份证',
                        'bossContact': '投资人联系方式',
                        'bossIdPicUp': 'http://119.3.19.171/photo/12344/xxx.jpg',
                        'bossIdPicBack': 'http://119.3.19.171/photo/12344/xxx.jpg'
                        },
                'cfoInfo': {
                        'cfoName': '财务负责人姓名',
                        'cfoId': '财务负责人身份证',
                        'cfoContact': '财务负责人联系方式',
                        'cfoIdPicUp': 'http://119.3.19.171/photo/12344/xxx.jpg',
                        'cfoIdPicBack': 'http://119.3.19.171/photo/12344/xxx.jpg'
                        }
            }
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
}
const xfzqCompanyInfo = (options) => {
    const body = getQueryParameters(options)
    console.log('dfzqCompanyInfo mock: body', body)
    return builder({
        'code': 0,
        'msg': 'OK',
        'data': {
                'companyInfo': {
                        'companyName': '公司名称',
                        'companyStatus': '注册完成',
                        'tax': 0.5,
                        'address': '2022-02-02',
                        'totalInvoiceNum': 22,
                        'invoicedNum': 11,
                        'totalInvoiceAmt': 22156374.378,
                        'freeDeliveryCnt': 55,
                        'includeCancel': '1',
                        'beginDate': '2020-07-14 12:00:00',
                        'endDate': '2021-07-15 12:00:00'
                        },
                'bossInfo': {
                        'bossName': '投资人姓名',
                        'bossId': '投资人身份证',
                        'bossContact': '投资人联系方式',
                        'bossIdPicUp': 'http://119.3.19.171/photo/12344/xxx.jpg',
                        'bossIdPicBack': 'http://119.3.19.171/photo/12344/xxx.jpg'
                        },
                'cfoInfo': {
                        'cfoName': '财务负责人姓名',
                        'cfoId': '财务负责人身份证',
                        'cfoContact': '财务负责人联系方式',
                        'cfoIdPicUp': 'http://119.3.19.171/photo/12344/xxx.jpg',
                        'cfoIdPicBack': 'http://119.3.19.171/photo/12344/xxx.jpg'
                        }
            }
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
}
const bfzqCompanyInfo = (options) => {
    const body = getQueryParameters(options)
    console.log('dfzqCompanyInfo mock: body', body)
    return builder({
        'code': 0,
        'msg': 'OK',
        'data': {
                'companyInfo': {
                        'companyName': '公司名称',
                        'companyStatus': '注册完成',
                        'tax': 0.5,
                        'address': '2022-02-02',
                        'totalInvoiceNum': 10,
                        'invoicedNum': 5,
                        'totalInvoiceAmt': 15611374.378,
                        'freeDeliveryCnt': 15,
                        'includeCancel': '1',
                        'beginDate': '2020-07-11 12:00:00',
                        'endDate': '2021-07-17 12:00:00'
                        },
                'bossInfo': {
                        'bossName': '投资人姓名',
                        'bossId': '投资人身份证',
                        'bossContact': '投资人联系方式',
                        'bossIdPicUp': 'http://119.3.19.171/photo/12344/xxx.jpg',
                        'bossIdPicBack': 'http://119.3.19.171/photo/12344/xxx.jpg'
                        },
                'cfoInfo': {
                        'cfoName': '财务负责人姓名',
                        'cfoId': '财务负责人身份证',
                        'cfoContact': '财务负责人联系方式',
                        'cfoIdPicUp': 'http://119.3.19.171/photo/12344/xxx.jpg',
                        'cfoIdPicBack': 'http://119.3.19.171/photo/12344/xxx.jpg'
                        }
            }
    }, '', 200, { 'Custom-Header': Mock.mock('@guid') })
}
Mock.mock(/\/custom\/detail\/ergou/, 'get', ergouCustomInfo)
Mock.mock(/\/custom\/detail\/admin/, 'get', adminCustomInfo)

Mock.mock(/\/custom\/tradeflow\/admin/, 'get', adminTradeFlow)
Mock.mock(/\/dict\/event/, 'get', eventDict)
Mock.mock(/\/company\/detail\/dfzq/, 'get', dfzqCompanyInfo)
Mock.mock(/\/company\/detail\/xfzq/, 'get', xfzqCompanyInfo)
Mock.mock(/\/company\/detail\/bfzq/, 'get', bfzqCompanyInfo)
Mock.mock(/\/company/, 'get', fuzzyQueryCompany)
