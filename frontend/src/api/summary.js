import request from '@/utils/request'
const ContractApi = {
    taxSummary: process.env.VUE_APP_SYSTEM_URL + '/v1/tax/statistics'
}
/**
registerArea	否	String	注册园区
customId	否	String	客户Id
area	否	String	地区
tax	否	String	税种
beginDate	否	String	统计开始时间 yyyy-MM-dd
endDate	否	String	统计结束时间 yyyy-MM-dd
pageNo	是	number	当前页码
pageSize	是	number	页数
 */
export function getTaxSummary (registerArea, customId, area, tax, beginDate, endDate, pageNo, pageSize) {
    return request({
        url: ContractApi.receiptDetail + '?registerArea=' + registerArea + '&customId=' + customId + '&area=' + area + '&tax=' + tax + '&beginDate=' + beginDate + '&endDate=' + endDate + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        method: 'get'
    })
}
