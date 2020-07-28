<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-skeleton :loading="infoLoading" active title>
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="10" :sm="8">
              <a-form-item label="开始月份">
                <a-month-picker
                  valueFormat="YYYY-MM"
                  format="YYYY-MM"
                  v-model="queryParam.beginMonth"
                />
              </a-form-item>
            </a-col>
            <a-col :md="10" :sm="8">
              <a-form-item label="结束月份">
                <a-month-picker
                  valueFormat="YYYY-MM"
                  format="YYYY-MM"
                  v-model="queryParam.endMonth"
                />
              </a-form-item>
            </a-col>
            <a-col :md="10" :sm="8">
              <a-form-item label="公司名称">
                <a-select
                  show-search
                  :value="queryParam.companyId"
                  placeholder="input search text"
                  style="width: 200px"
                  :default-active-first-option="false"
                  :show-arrow="false"
                  :filter-option="false"
                  :not-found-content="null"
                  @search="handleCustomSearch"
                  @change="handleCustomChange"
                >
                  <a-select-option v-for="d in fuzzyCompanyList" :key="d.value">{{ d.text }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item>
                <a-button type="primary" size="small" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button
                  size="small"
                  type="primary"
                  @click="()=>{queryParam= {
                    beginMonth: '',
                    endMonth: '',
                    companyId: '',
                    pageNo: 1,
                    pageSize: 20
                  }}"
                >重置</a-button>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-skeleton>
    </a-card>
    <a-card style="margin-top: 24px" :bordered="false" title="服务费用明细">
      <s-table
        ref="table"
        size="default"
        rowKey="key"
        :pageSize="20"
        :columns="columns"
        :data="loadData"
        showPagination="auto"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="event" slot-scope="text">{{ eventMap[text] }}</span>
      </s-table>
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { STable } from '@/components'
import { fuzzyQueryCompany, getInvoiceStatistics } from '@/api/company'
import { success, errorMessage } from '@/utils/helper/responseHelper'
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '日期',
    dataIndex: 'invoiceDate',
    scopedSlots: { customRender: 'invoiceDate' }
  },
  {
    title: '公司名称',
    dataIndex: 'companyName',
    scopedSlots: { customRender: 'companyName' }
  },
  {
    title: '开票数量',
    dataIndex: 'invoiceCnt',
    scopedSlots: { customRender: 'invoiceCnt' }
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
let timeout
let currentValue

function fetch (value, callback) {
  if (timeout) {
    clearTimeout(timeout)
    timeout = null
  }
  currentValue = value

  function fake () {
    fuzzyQueryCompany(value, 20, false).then(d => {
      if (currentValue === value) {
        const result = d.data
        const data = []
        result.forEach(r => {
          data.push({
            value: r['id'],
            text: r['name']
          })
        })
        data.push({
          value: '',
          text: ''
        })
        callback(data)
      }
    })
  }

  timeout = setTimeout(fake, 300)
}
export default {
  name: 'InvoiceStatistics',
  props: {
    customId: {
      type: String,
      default: ''
    },
    refresh: {
      type: Boolean,
      default: false
    }
  },
  components: {
    STable
  },
  data () {
    this.columns = columns
    return {
      fuzzyCompanyList: [],
      infoLoading: false,
      queryParam: {
           beginMonth: '',
           endMonth: '',
           companyId: ''
      },
      eventMap: {},
      loadData: parameter => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        return getInvoiceStatistics(requestParameters.beginMonth, requestParameters.endMonth, requestParameters.companyId, requestParameters.pageNo, requestParameters.pageSize)
          .then(Response => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = []
              for (const i in result.data.statistics) {
                ans.push({ ...result.data.statistics[i], key: i })
              }
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询开票统计失败，请稍后再试'
              })
            }
            setTimeout(() => {
              this.tradeflowLoading = false
            }, 600)
            return []
          })
          .catch(error => {
            setTimeout(() => {
              this.tradeflowLoading = false
            }, 600)
            this.$notification.error({
              message: '查询开票统计失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  methods: {
    handleDefault (value) {
      return value === undefined || value === null ? '暂无数据' : String(value)
    },
    fuzzyQueryCompanyFuc () {
      this.infoLoading = true
      fuzzyQueryCompany(this.currentCustomId)
        .then(Response => {
          const result = Response
          // console.log('getCustomInfo', result)
          if (success(result)) {
            this.customInfo = result.data
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询客户信息失败，请稍后再试'
            })
          }
          setTimeout(() => {
            this.infoLoading = false
          }, 600)
        })
        .catch(error => {
          this.infoLoading = false
          this.$notification.error({
            message: '查询公司信息失败，请稍后再试',
            description: error
          })
        })
    },
     handleCustomSearch (value) {
      fetch(value, data => (this.fuzzyCompanyList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      this.queryParam.companyId = value
      fetch(value, data => (this.fuzzyCompanyList = data))
    } },
  created () {
    // this.getCustomInfo()
  },
  activated () {
    if (this.innerUpdate) {
this.getCustomInfo()
     this.$refs.table.refresh(true)
}
  },
  watch: {
    customId: function (newVal, oldVal) {
      if (newVal !== '') {
        this.currentCustomId = newVal // newVal即是chartData
          this.$refs.table.refresh(true)
      }
    },
    refresh: function (newVal, oldVal) {
        this.getCustomInfo()
          this.$refs.table.refresh(true)
    }
  }
}
</script>
