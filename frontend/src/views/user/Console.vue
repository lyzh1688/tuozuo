<template>
  <page-header-wrapper>
    <a-card style="margin-top: 24px" :bordered="false">
      <a-skeleton :loading="companyListLoading" active title>
        <a-tabs
          :default-active-key="companyList.length>0?companyList[0].id:''"
          tab-position="left"
          @tabClick="fetchCompanyInfo"
        >
          <a-tab-pane v-for="item in companyList" :key="item.id" :tab="item.name">
            <a-skeleton :loading="infoLoading" active title>
              <a-row >
                <a-col :sm="12" :xs="24">
                  <userInfo
                    title="总开票数"
                    :value="handleDefault(companyInfo.totalInvoiceNum)"
                    :bordered="true"
                  />
                </a-col>
                <a-col :sm="12" :xs="24">
                  <userInfo title="已开票数" :value="handleDefault(companyInfo.invoicedNum)" />
                </a-col>
                <a-col :sm="12" :xs="24">
                  <userInfo
                    title="税金总额"
                    :value="handleDefault(companyInfo.totalInvoiceAmt)"
                    :bordered="true"
                  />
                </a-col>
                <a-col :sm="12" :xs="24">
                  <userInfo title="剩余免费快递次数" :value="handleDefault(companyInfo.freeDeliveryCnt)" />
                </a-col>
                <a-col :sm="12" :xs="24">
                  <userInfo
                    title="服务开始"
                    :value="handleDefault(companyInfo.beginDate)"
                    :bordered="true"
                  />
                </a-col>
                <a-col :sm="12" :xs="24">
                  <userInfo title="服务结束" :value="handleDefault(companyInfo.endDate)" />
                </a-col>
              </a-row>
            </a-skeleton>
          </a-tab-pane>
        </a-tabs>
      </a-skeleton>
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { fuzzyQueryCompany, getCompanyInfo } from '@/api/company'
import { success, errorMessage } from '@/utils/helper/responseHelper'
import { mapState } from 'vuex'
import UserInfo from './components/Info'
export default {
  name: 'Console',
  components: {
    UserInfo
  },
  data () {
    return {
      companyInfo: {},
      infoLoading: false,
      companyListLoading: false,
      pageSize: 10,
      pageNo: 1,
      totalNumber: 0,
      companyList: []
    }
  },
  methods: {
    handleDefault (value) {
      return value === undefined || value === null ? '暂无数据' : String(value)
    },
    fetchCompanyList () {
      this.companyListLoading = true
      fuzzyQueryCompany('', 20, true)
        .then(Response => {
          const result = Response
          if (success(result)) {
            this.companyList = result.data
            if (this.companyList.length > 0) {
              this.fetchCompanyInfo(this.companyList[0].id)
            }
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询个独公司列表失败'
            })
          }
          setTimeout(() => {
            this.companyListLoading = false
          }, 600)
        })
        .catch(error => {
          this.companyListLoading = false
          this.$notification.error({
            message: '查询个独公司列表失败',
            description: error
          })
        })
    },
    fetchCompanyInfo (companyId) {
      this.companyInfo = []
      this.infoLoading = true
      getCompanyInfo(companyId)
        .then(Response => {
          const result = Response
          console.log('getCompanyInfo', result)
          if (success(result)) {
            this.companyInfo = result.data.companyInfo
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询公司信息失败'
            })
          }
          setTimeout(() => {
            this.infoLoading = false
          }, 600)
        })
        .catch(error => {
          this.infoLoading = false
          this.$notification.error({
            message: '查询公司信息失败',
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
    // this.fetchCompanyList()
  },
  activated () {
    this.fetchCompanyList()
  }
}
</script>
