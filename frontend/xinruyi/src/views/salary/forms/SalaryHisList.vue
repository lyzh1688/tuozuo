<template>
  <a-modal
    :title="title"
    :width="1100"
    :visible="visible"
    :confirmLoading="loading"
    @ok="
      () => {
        $emit('ok')
      }
    "
    @cancel="
      () => {
        $emit('cancel')
      }
    "
  >
    <a-spin :spinning="loading">
      <a-form :form="form">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-row>
          <a-col :span="6">
            <a-form-item label="公司名称">
              <span>{{ model.companyName }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="项目名称">
              <span>{{ model.projectName }}</span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :md="8" :sm="8">
            <a-form-item label="开始月份">
              <a-month-picker
                valueFormat="YYYYMM"
                format="YYYY-MM"
                v-model="queryParam.beginMonth"
              />
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item label="结束月份">
              <a-month-picker
                valueFormat="YYYYMM"
                format="YYYY-MM"
                v-model="queryParam.endMonth"
              />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item>
              <a-button
                :disabled="projectListLoading"
                type="primary"
                size="small"
                @click="$refs.table.refresh(true)"
              >查询</a-button>
              <a-button
                :disabled="projectListLoading"
                size="small"
                type="primary"
                @click="()=>{ queryParam= {
                  beginMonth: '',
                  endMonth:'',
                  pageNo: 1,
                  pageSize: 20
                }}"
              >重置</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="48">
            <a-form-item>
              <s-table
                ref="table"
                size="default"
                rowKey="staffId"
                :pageSize="10"
                :columns="columns"
                :data="loadData"
                :showPagination="true"
                :scroll="{ y: 440 }"
              >
                <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
                <span slot="status" slot-scope="text">{{ paymentStatusMap[text] }}</span>
                <span slot="payVoucher" slot-scope="text">
                  <a-button
                    type="text"
                    v-if="text && text !== ''"
                    @click="
                      () => {
                        jumpToFile(text)
                      }
                    "
                  >查看</a-button
                  ></span
                >
                <span slot="transferVoucher" slot-scope="text">
                  <a-button
                    v-if="text && text !== ''"
                    type="text"
                    @click="
                      () => {
                        jumpToFile(text)
                      }
                    "
                  >查看</a-button
                  ></span
                >
                <span slot="ops" slot-scope="text,record">
                  <a-button type="dashed" size="small" @click="handleSalaryList(record)">详情</a-button>
                </span>
              </s-table>
            </a-form-item>
          </a-col>
        </a-row>
        <slot name="other"></slot>
      </a-form>
    </a-spin>
    <salarylistdrawer
      ref="salaryDrawer"
      :paymentId="paymentId"
      :visible="salaryVisible"
      :projectId="model.projectId"
      @onclose="salaryVisible = false"
    ></salarylistdrawer>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { STable } from '@/components'
import { getHistorySalaryList } from '@/api/salary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { getCommonDict } from '@/api/dictionary'
import salarylistdrawer from './SalaryListDrawer'
// 表单字段
const fields = [
  'companyId',
  'companyName',
  'projectId',
  'projectName',
  'totalWages',
  'releaseDate',
  'paymentId',
  'month'
]
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '公司名称',
    dataIndex: 'companyName',
    scopedSlots: { customRender: 'companyName' }
  },
  {
    title: '项目名称',
    dataIndex: 'projectName',
    scopedSlots: { customRender: 'projectName' }
  },
  {
    title: '总工资',
    dataIndex: 'totalWages',
    scopedSlots: { customRender: 'totalWages' }
  },
  {
    title: '最近工资月份',
    dataIndex: 'month',
    scopedSlots: { customRender: 'month' }
  },
  {
    title: '最近发放日期',
    dataIndex: 'releaseDate',
    scopedSlots: { customRender: 'releaseDate' }
  },
  {
    title: '转账凭证',
    dataIndex: 'transferVoucher',
    scopedSlots: { customRender: 'transferVoucher' }
  },
  {
    title: '发放凭证',
    dataIndex: 'payVoucher',
    scopedSlots: { customRender: 'payVoucher' }
  },
  {
    title: '发放状态',
    dataIndex: 'status',
    scopedSlots: { customRender: 'status' }
  },
  {
    title: '备注',
    dataIndex: 'remark',
    scopedSlots: { customRender: 'remark' }
  },
  {
    title: '操作',
    dataIndex: 'ops',
    scopedSlots: { customRender: 'ops' }
  }
]
export default {
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    loading: {
      type: Boolean,
      default: () => false
    },
    model: {
      type: Object,
      default: () => null
    },
    isShowOnly: {
      type: Boolean,
      default: false
    },
    isUpdate: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    clearUpload: {
      type: Boolean,
      default: false
    }
  },
  components: {
    STable,
    salarylistdrawer
  },
  data () {
    this.columns = columns
    return {
      // city: citiesHepler[0].label,
      showUpload: true,
      projectFileList: [],
      provinceList: [],
      cityList: [],
      salaryVisible: false,
      projectListLoading: false,
      districtList: [],
      paymentStatusMap: {},
      genderMap: {},
      cityIndex: 0,
      areaIndex: 0,
      paymentId: '',
      queryParam: {
          endMonth: '',
          beginMonth: ''
      },
      industryTypeList: [],
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this),
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        return getHistorySalaryList(
          this.model.companyId,
          this.model.projectId,
          requestParameters.beginMonth,
          requestParameters.endMonth,
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.salaries
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询工资列表失败，请稍后再试'
              })
            }
            setTimeout(() => {
              this.projectListLoading = false
            }, 600)
            return []
          })
          .catch((error) => {
            setTimeout(() => {
              this.projectListLoading = false
            }, 600)
            this.$notification.error({
              message: '查询工资列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  watch: {
    visible (value) {
      if (value) {
        this.$refs.table.refresh(true)
      }
    }
  },
  created () {
    console.log('custom modal created')
    // 防止表单未注册
    fields.forEach((v) => this.form.getFieldDecorator(v))
     this.getDict('paymentStatus').then((response) => {
      this.paymentStatusList = response
      this.paymentStatusMap = {}
      for (const i of response) {
        this.paymentStatusMap[i.id] = i.name
      }
    })
    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
    })
  },
  methods: {
      handleSalaryList (record) {
      this.salaryVisible = true
      this.paymentId = record.paymentId
    },
    jumpToFile (link) {
      window.open(link, '_blank')
    },
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
    }
  }
}
</script>
