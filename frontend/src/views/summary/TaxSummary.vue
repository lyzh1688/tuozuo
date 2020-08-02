<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-skeleton :loading="infoLoading" active title>
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :span="8">
              <a-form-item label="地区">
                <a-select
                  v-model="areaForm.province"
                  style="width: 120px"
                  @change="handleChane"
                >
                  <a-select-option
                    v-for="province in provinceList"
                    :key="province.areaCode"
                  >{{ province.areaName }}</a-select-option>
                </a-select>
                <a-select
                  v-model="areaForm.city"
                  style="width: 120px"
                  @change="handleChane2"
                >
                  <a-select-option
                    v-for="cityItem in cityList"
                    :key="cityItem.areaCode"
                  >{{ cityItem.areaName }}</a-select-option>
                </a-select>
                <a-select
                  v-model="areaForm.district"
                  style="width: 120px"
                >
                  <a-select-option
                    v-for="cityItem in districtList"
                    :key="cityItem.areaCode"
                  >{{ cityItem.areaName }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="8">
              <a-form-item label="开始日期">
                <a-date-picker
                  valueFormat="YYYY-MM-DD"
                  format="YYYY-MM-DD"
                  v-model="queryParam.beginDate"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="8">
              <a-form-item label="结束日期">
                <a-date-picker
                  valueFormat="YYYY-MM-DD"
                  format="YYYY-MM-DD"
                  v-model="queryParam.endDate"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="8">
              <a-form-item label="客户名称">
                <a-select
                  show-search
                  v-model="queryParam.customId"
                  placeholder="请输入客户名称"
                  style="width: 200px"
                  :default-active-first-option="false"
                  :show-arrow="false"
                  :filter-option="false"
                  :not-found-content="null"
                  @search="handleCustomSearch"
                  @change="handleCustomChange"
                >
                  <a-select-option v-for="d in fuzzyCustomList" :key="d.value">{{ d.text }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item label="注册园区" key="注册园区">
                <a-select
                  :disabled="isShowOnly"
                  style="width:200px;"
                  v-model="queryParam.registerArea"
                  placeholder="请选择"
                >
                  <a-select-option
                    v-for=" taxItem in registerArea"
                    :key="taxItem.id"
                  >{{ taxItem.name }}</a-select-option>
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
                                 registerArea: '',
                                 customId: '',
                                 areaCode: '',
                                 areaLevel: '',
                                 beginDate: '',
                                 endDate: '',
                                 pageNo: 1,
                                 pageSize: 20
                               }
                               areaForm= {
                                 province: '',
                                 city: '',
                                 district: ''
                               }}"
                >重置</a-button>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-skeleton>
    </a-card>
    <a-card style="margin-top: 24px" :bordered="false">
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
import { getTaxSummary } from '@/api/summary'
import { fuzzyQueryCustom, dictQuery, getAreaCode } from '@/api/company'
import { success, errorMessage } from '@/utils/helper/responseHelper'
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '客户名称',
    dataIndex: 'customName',
    scopedSlots: { customRender: 'customName' }
  },
  {
    title: '公司名称',
    dataIndex: 'companyName',
    scopedSlots: { customRender: 'companyName' }
  },
  {
    title: '增值税',
    dataIndex: 'valueAddedTax',
    scopedSlots: { customRender: 'valueAddedTax' }
  },
  {
    title: '个税',
    dataIndex: 'incomeTax',
    scopedSlots: { customRender: 'incomeTax' }
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
    fuzzyQueryCustom(value, 20, true).then((d) => {
      if (currentValue === value) {
        const result = d.data
        const data = []
        result.forEach((r) => {
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
  name: 'TaxSummary',
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
      fuzzyCustomList: [],
      registerArea: [],
      provinceList: [],
      cityList: [],
      districtList: [],
      taxList: [],
      cityIndex: 0,
      areaIndex: 0,
      infoLoading: false,
      areaForm: {
        province: '',
        city: '',
        district: ''
      },
      queryParam: {
        registerArea: '',
        customId: '',
        areaCode: '',
        areaLevel: '',
        beginDate: '',
        endDate: ''
      },
      eventMap: {},
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        if (this.areaForm.district !== '') {
          requestParameters.areaLevel = 'district'
          requestParameters.areaCode = this.areaForm.district
        } else if (this.areaForm.city !== '') {
          requestParameters.areaLevel = 'city'
          requestParameters.areaCode = this.areaForm.city
        } else if (this.areaForm.province !== '') {
          requestParameters.areaLevel = 'province'
          requestParameters.areaCode = this.areaForm.province
        }
        console.log('loadData request parameters:', requestParameters)
        return getTaxSummary(
          requestParameters.registerArea,
          requestParameters.customId,
          requestParameters.areaCode,
          requestParameters.areaLevel,
          requestParameters.beginDate,
          requestParameters.endDate,
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then((Response) => {
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
    handleDefault (value) {
      return value === undefined || value === null ? '暂无数据' : String(value)
    },
    handleCustomSearch (value) {
      fetch(value, (data) => (this.fuzzyCustomList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      this.queryParam.companyId = value
      fetch(value, (data) => (this.fuzzyCustomList = data))
    },
    getDict (keyword) {
      return new Promise((resolve, reject) => {
        dictQuery(keyword).then((Response) => {
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
        })
      })
    },
    getAreaCode (level, code) {
      return new Promise((resolve, reject) => {
        getAreaCode(level, code).then((Response) => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            resolve(result.data)
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询区域信息失败'
            })
          }
        })
      })
    },
    async handleChane (index) {
      await this.getAreaCode('city', index).then((response) => {
        this.cityList = response
      })
      this.areaForm.city = ''
      this.areaForm.district = ''
    },
    async handleChane2 (index) {
      await this.getAreaCode('district', index).then((response) => {
        this.districtList = response
      })
      this.areaForm.district = ''
    }
  },
  created () {
    this.getAreaCode('province', '').then((response) => {
      this.provinceList = response
    })
    this.getDict('registerArea').then((response) => {
      this.registerArea = response
    })
    // this.getCustomInfo()
  },
  activated () {
    if (this.innerUpdate) {
      this.$refs.table.refresh(true)
    }
  },
  watch: {
    customId: function (newVal, oldVal) {
      if (newVal !== '') {
        this.currentCustomId = newVal // newVal即是chartData
        this.getCustomInfo()
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
