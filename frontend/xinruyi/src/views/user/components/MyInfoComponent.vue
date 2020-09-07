<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-skeleton :loading="infoLoading" active title>
        <slot name="freshButton"></slot>
        <a-row>
          <a-col :sm="8" :xs="24">
            <userInfo title="公司名称" :value="handleDefault(customInfo.companyName)" :bordered="true" />
          </a-col>
          <a-col :sm="8" :xs="24">
            <userInfo
              title="联系方式"
              :value="handleDefault(customInfo.contact)"
              :bordered="true"
            />
          </a-col>
          <a-col :sm="8" :xs="24">
            <userInfo title="所在城市" >
              <template v-slot:value>
                <areashow :provinceCode="customInfo.province" :cityCode="customInfo.city" :districtCode="customInfo.district"/>
              </template>
            </userInfo>
          </a-col>
          <a-col :sm="8" :xs="24">
            <userInfo title="行业类型" :value="handleDefault(industryTypeMap[customInfo.industryType])" :bordered="true" />
          </a-col>
          <a-col :sm="8" :xs="24">
            <userInfo title="认证状态" :value="handleDefault(statusMap[customInfo.authStatus])" />
          </a-col>

        </a-row>
      </a-skeleton>
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { getCompanyInfo } from '@/api/company'
import { getCommonDict } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import UserInfo from './Info'
import areashow from './AreaShow'
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
    UserInfo,
    areashow
  },
  data () {
    return {
      customInfo: {},
      infoLoading: false,
      queryParam: {},
      industryTypeMap: {},
      statusMap: {}
    }
  },
  methods: {
    getDict (keyword) {
      return new Promise((resolve, reject) => {
        getCommonDict(keyword).then((Response) => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            resolve(result.data)
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询字典失败'
            })
          }
          if (needLogin(result)) {
            this.visible = false
          }
        })
      })
    },
    handleDefault (value) {
      return value === undefined || value === null ? '暂无数据' : String(value)
    },
    getCompanyInfo () {
      this.infoLoading = true
      getCompanyInfo()
        .then(Response => {
          const result = Response
          // console.log('getCompanyInfo', result)
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
    }

  },

  created () {
    // this.getCompanyInfo()
this.getDict('industryType').then((response) => {
      this.industryTypeMap = {}
      for (const i of response) {
        this.industryTypeMap[i.id] = i.name
      }
    })
    this.getDict('companyStatus').then((response) => {
      this.statusMap = {}
      for (const i of response) {
        this.statusMap[i.id] = i.name
      }
    })
  },
  activated () {
//     if (this.innerUpdate) {
// this.getCompanyInfo()
//      this.$refs.table.refresh(true)
// }
  },
  watch: {
    customId: function (newVal, oldVal) {
      if (newVal !== '') {
        this.currentCustomId = newVal // newVal即是chartData
        this.getCompanyInfo()
          // this.$refs.table.refresh(true)
      }
    },
    refresh: function (newVal, oldVal) {
        this.getCompanyInfo()
          // this.$refs.table.refresh(true)
    }
  }
}
</script>
