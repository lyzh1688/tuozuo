import request from '@/utils/request'
const ContractApi = {
    receiptList: process.env.VUE_APP_SYSTEM_URL + '/v1/invoice/list',
    addReceipt: process.env.VUE_APP_SYSTEM_URL + '/v1/invoice', // post
    receiptDetail: process.env.VUE_APP_SYSTEM_URL + '/v1/invoice/detail/',
    updateReceipt: process.env.VUE_APP_SYSTEM_URL + '/v1/invoice/', // put
    confirmReceipt: process.env.VUE_APP_SYSTEM_URL + 'v1/invoice/audit/' // put
}
/**
 receiptId	否	String	公司ID
pageNo	是	number	当前页码
pageSize	是	number	页数
 */
export function getReceiptDetail (receiptId) {
    return request({
        url: ContractApi.receiptDetail + receiptId,
        method: 'get'
    })
}
/**
companyId	否	String	公司ID
contractId	否	String	合同ID
invoiceStatus	否	String	发票状态	0：审核中，1：审核打款，2：审核失败，3：已开具发票
pageNo	是	number	当前页码
pageSize	是	number	页数
 */
export function getReceiptList (companyId, contractId, invoiceStatus, pageNo, pageSize) {
    return request({
        url: ContractApi.receiptList + '?companyId=' + companyId + '&pageNo=' + pageNo + '&pageSize=' + pageSize + '&contractId=' + contractId + '&invoiceStatus=' + invoiceStatus,
        method: 'get'
    })
}

export function addReceipt (params) {
    const formData = new FormData()
    for (const i in params) {
      if (i.includes('File')) {
        formData.append(i, params[i].file.originFileObj)
      } else {
        formData.append(i, params[i])
      }
    }
    console.log(formData, params)
    return request({
        url: ContractApi.addReceipt,
        method: 'POST',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data;charset=UTF-8'
        }
    })
}
export function updateReceipt (params) {
    const formData = new FormData()
    for (const i in params) {
      if (i === 'invoiceId') {
        continue
      }
      if (i.includes('File')) {
        if (typeof params[i] === 'string') {
          // formData.append(i, null)
        } else {
          formData.append(i, params[i].file.originFileObj)
        }
      } else {
        formData.append(i, params[i])
      }
    }
      return request({
        url: ContractApi.updateReceipt + params.invoiceId,
        method: 'PUT',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data;charset=UTF-8'
        }
      })
}
export function confirmReceipt (params) {
      return request({
        url: ContractApi.confirmReceipt + params.invoiceId,
        method: 'PUT',
        data: params
      })
}
