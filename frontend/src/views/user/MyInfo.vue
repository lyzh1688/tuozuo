<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-skeleton :loading="infoLoading" active title>
        <a-row >
          <a-col :sm="8" :xs="24">
            <userInfo title="客户姓名" :value="handleDefault(customInfo.customName)" :bordered="true" />
          </a-col>
          <a-col :sm="8" :xs="24">
            <userInfo title="联系方式" :value="handleDefault(customInfo.customContact)" :bordered="true" />
          </a-col>
          <a-col :sm="8" :xs="24">
            <userInfo title="所在城市" :value="handleDefault(customInfo.province)" />
          </a-col>
          <a-col :sm="12" :xs="24">
            <userInfo title="账户余额" :value="handleDefault(customInfo.balance)" :bordered="true" />
          </a-col>
          <a-col :sm="12" :xs="24">
            <userInfo title="总服务费用" :value="handleDefault(customInfo.totalServerCharge)" />
          </a-col>
        </a-row>
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
import { getCustomInfo, getTradeflow, dictQuery } from '@/api/company'
import { success, errorMessage } from '@/utils/helper/responseHelper'
import { mapState } from 'vuex'
import UserInfo from './components/Info'
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '日期',
    dataIndex: 'tradeDate',
    scopedSlots: { customRender: 'tradeDate' }
  },
  {
    title: '事件',
    dataIndex: 'event',
    scopedSlots: { customRender: 'event' }
  },
  {
    title: '金额',
    dataIndex: 'amount',
    customRender: record => record + '元'
  },
  {
    title: '余额',
    dataIndex: 'balance',
    customRender: record => record + '元'
  },
  {
    title: '备注',
    dataIndex: 'remark',
    scopedSlots: { customRender: 'remark' }
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
  name: 'MyInfo',
  components: {
    STable,
    UserInfo
  },
  data () {
    this.columns = columns
    return {
      customInfo: {},
      infoLoading: false,
      queryParam: {},
      eventMap: {},
      loadData: parameter => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        return getTradeflow(this.username, requestParameters.pageNo, requestParameters.pageSize)
          .then(Response => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = []
              for (const i in result.data.tradeflow) {
                ans.push({ ...result.data.tradeflow[i], key: i })
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
                description: '查询客户信息失败，请稍后再试'
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
              message: '查询客户信息失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  methods: {
    handleDefault (value) {
      return value === undefined ? '暂无数据' : String(value)
    },
    getCustomInfo () {
      this.infoLoading = true
      getCustomInfo(this.username)
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
            message: '查询客户信息失败，请稍后再试',
            description: error
          })
        })
    },
    getEventDict () {
      dictQuery('event')
        .then(Response => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            this.eventMap = {}
            for (const i of result.data) {
              this.eventMap[i.id] = i.name
            }
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '事件字典失败'
            })
          }
          setTimeout(() => {
            this.infoLoading = false
          }, 600)
        })
        .catch(error => {
          this.infoLoading = false
          this.$notification.error({
            message: '事件字典失败',
            description: error
          })
        })
    }
  },
  computed: {
    ...mapState({
      username: state => state.user.name
    }),
    rowSelection () {
      return {
        selectedRowKeys: this.selectedRowKeys,
        onChange: this.onSelectChange
      }
    }
  },
  created () {
    this.getCustomInfo()
    this.getEventDict()
  }
}
</script>
