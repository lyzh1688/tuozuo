import request from '@/utils/request'
const ContractApi = {
    taxSummary: process.env.VUE_APP_SYSTEM_URL + '/v1/tax/statistics'
}
/**
registerArea	否	String	注册园区
customId	否	String	客户Id
areaCode	否	String	地区代码
areaLevel	否	String	地区级别
beginDate	否	String	统计开始时间 yyyy-MM-dd
endDate	否	String	统计结束时间 yyyy-MM-dd
pageNo	是	number	当前页码
pageSize	是	number	页数
 */
export function getTaxSummary (registerArea, customId, areaCode, areaLevel, beginDate, endDate, pageNo, pageSize) {
    return request({
        url: ContractApi.taxSummary + '?registerArea=' + registerArea + '&customId=' + customId + '&areaCode=' + areaCode + '&areaLevel=' + areaLevel + '&beginDate=' + beginDate + '&endDate=' + endDate + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        method: 'get'
    })
}
