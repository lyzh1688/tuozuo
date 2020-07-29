import request from '@/utils/request'
const ContractApi = {
    customContractList: process.env.VUE_APP_SYSTEM_URL + '/v1/contract/list',
    addContract: process.env.VUE_APP_SYSTEM_URL + '/v1/contract', // post
    contractTemplateList: process.env.VUE_APP_SYSTEM_URL + '/v1/contract/template',
    fuzzyQueryContract: process.env.VUE_APP_SYSTEM_URL + '/v1/contract/contracts',
    updateContract: process.env.VUE_APP_SYSTEM_URL + '/v1/contract/', // put
    confirmContrac: process.env.VUE_APP_SYSTEM_URL + '/v1/contract/' // put
}
/**
 companyId	否	String	公司ID
pageNo	是	number	当前页码
pageSize	是	number	页数
 */
export function getContractList (companyId, pageNo, pageSize) {
    return request({
        url: ContractApi.customContractList + '?companyId=' + companyId + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
        method: 'get'
    })
}
/**
contractStatus	否	String	合同状态
contractName	否	String	合同名称
queryCnt	否	number	查询条数
 */
export function fuzzyQueryContract (contractStatus, contractName, queryCnt) {
    return request({
        url: ContractApi.fuzzyQueryContract + '?contractStatus=' + contractStatus + '&contractName=' + contractName + '&queryCnt=' + queryCnt,
        method: 'get'
    })
}
export function getContractTemplateList () {
    return request({
        url: ContractApi.contractTemplateList,
        method: 'get'
    })
}

export function addContract (params) {
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
        url: ContractApi.addContract,
        method: 'POST',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data;charset=UTF-8'
        }
    })
}
export function updateContract (params) {
    const formData = new FormData()
    for (const i in params) {
      if (i === 'contractId') {
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
        url: ContractApi.updateContract + params.contractId,
        method: 'PUT',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data;charset=UTF-8'
        }
      })
}
export function confirmContrac (params) {
      return request({
        url: ContractApi.confirmContrac + params.contractId,
        method: 'PUT',
        data: params
      })
}
