<template>
  <page-header-wrapper>
    <a-card style="margin-top: 24px" :bordered="false" title="模板列表">
      <s-table
        ref="table"
        size="default"
        rowKey="templateId"
        :pageSize="10000"
        :columns="columns"
        :data="loadData"
        showPagination="auto"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="ops" slot-scope="text, record">
          <a-button size="small" @click="download(record.templateFile)" >下载</a-button>
          <a
            :href="record.templateFile"
            :download="record.templateFile.split('/')[record.templateFile.split('/').length-1]"
            title="下载"
            mce_href="#">下载</a>
        </span>
      </s-table>
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { STable } from '@/components'
import { getContractTemplateList } from '@/api/contract'
import { success, errorMessage } from '@/utils/helper/responseHelper'
import jsonp from 'jsonp'
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '模板名称',
    dataIndex: 'templateName',
    scopedSlots: { customRender: 'templateName' }
  },
  {
    title: '操作',
    dataIndex: 'ops',
    scopedSlots: { customRender: 'ops' }
  }
]
// const statusMap = {
//   0: {
//     status: 'default',
//     text: '关闭'
//   },
//   1: {
//     status: 'processing',
//     text: '运行中'
//   },
//   2: {
//     status: 'success',
//     text: '已上线'
//   },
//   3: {
//     status: 'error',
//     text: '异常'
//   }
// }
export default {
  name: 'CustomContractList',
  components: {
    STable
  },
  data () {
    this.columns = columns
    return {
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        return getContractTemplateList()
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: 999,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询缴税明细失败，请稍后再试'
              })
            }
            setTimeout(() => {
              this.tradeflowLoading = false
            }, 600)
            return []
          })
          .catch((error) => {
            setTimeout(() => {
              this.tradeflowLoading = false
            }, 600)
            this.$notification.error({
              message: '查询缴税明细失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  methods: {
      download (url1) {
 const url = url1 // data:项目中获取的数据，包含文件url以及文件名等相关参数
    const fileName = ''
    // 先测试一下能不能跨域成功
    jsonp(url, null, (err, data) => {
        if (err) {
            console.error(err.message)
        } else {
            console.log(data)
        }
    })
      }
  },
  created () {
    // this.getCustomInfo()
  },
  activated () {
      this.$refs.table.refresh(true)
  }
}
</script>
