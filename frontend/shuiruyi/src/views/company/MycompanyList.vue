<template>
  <page-header-wrapper>
    <a-card :bordered="false" title="我的公司列表">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="10" :sm="8">
            <a-form-item label="公司名称">
              <a-select
                show-search
                v-model="queryParam1.companyId"
                placeholder="请输入公司名称"
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
                @click="()=>{ queryParam1= {
                  companyId: '',
                  companyStatus: '',
                  pageNo: 1,
                  pageSize: 20
                }}"
              >重置</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :md="12" :sm="24">
            <a-form-item>
              <a-button size="small" @click="handleAdd">创建公司申请</a-button>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <s-table
        ref="table"
        size="default"
        rowKey="companyId"
        :pageSize="20"
        :columns="columns"
        :data="loadData"
        showPagination="true"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="customType" slot-scope="text">{{ customTypeMap[text] }}</span>
        <span slot="ops" slot-scope="text, record">
          <a-button
            size="small"
            @click="handleUpdate(record)"
            :disabled="record.registerStatus==='2'"
            :loading="confirmLoading"
          >修改</a-button>
          <a-button size="small" @click="fetchCompanyDetail(record)" :loading="confirmLoading">详情</a-button>
        </span>
        <a slot="customName" slot-scope="text,record" @click="toCustomInfo(record)">{{ text }}</a>
      </s-table>
      <companyInfoform
        :title="formTitle"
        ref="companyInfoform"
        :clearUpload="clearUpload"
        :visible="companyVisible"
        :loading="confirmLoading"
        :model="companyMdl"
        :isUpdate="isupdate"
        :isShowOnly="isShowOnly"
        @cancel="handleCancel"
        @ok="handleOk"
      >
        <template v-slot:company-extra v-if="isShowOnly">
          <a-row>
            <a-col :span="12">
              <a-form-item label="总开票" key="总开票">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['totalInvoiceNum', {rules: [{required: true, message: '请输入总开票！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="已开票" key="已开票">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['invoicedNum', {rules: [{required: true, message: '请输入已开票！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="12">
              <a-form-item label="税金总额" key="税金总额">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['totalInvoiceAmt', {rules: [{required: true, message: '请输入税金总额！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="免费快递余额" key="免费快递余额">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['freeDeliveryCnt', {rules: [{required: true, message: '请输入剩余免费快递次数！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="12">
              <a-form-item label="业务状态" key="业务状态">
                <a-select
                  :disabled="isShowOnly"
                  style="width:200px;"
                  v-decorator="['companyStatus', {rules: [{required: true, message: '请输入业务状态！'}], validateTrigger: 'blur'}]"
                  placeholder="请选择"
                >
                  <a-select-option
                    v-for=" typeItem in bizStatus"
                    :key="typeItem.id"
                  >{{ typeItem.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="包含注销服务" key="包含注销服务">
                <a-select
                  :disabled="isShowOnly"
                  v-decorator="['includeCancel', {rules: [{required: true, message: '请输入是否包含注销服务！'}], validateTrigger: 'blur'}]"
                  placeholder="请选择"
                >
                  <a-select-option value="1">是</a-select-option>
                  <a-select-option value="0">否</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="12">
              <a-form-item label="服务开始" key="服务开始">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['beginDate', { rules: [{required: true, message: '请输入服务开始！'}],validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="服务结束" key="服务结束">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['endDate', {rules: [{required: true, message: '请输入服务结束！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="12">
              <a-form-item label="注册园区" key="注册园区">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['registerArea', {rules: [{required: true, message: '请输入注册园区！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="返税比例" key="返税比例">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['rebateTaxRate', {rules: [{required: true, message: '请输入返税比例！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </template>
      </companyInfoform>
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { STable } from '@/components'
import { getCompanyList, dictQuery, addCompany, updateCompany, getCompanyInfo, fuzzyQueryCompany } from '@/api/company'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { mapState } from 'vuex'
import companyInfoform from './forms/CommonCompanyInfo'
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
    title: '业务状态',
    dataIndex: 'companyStatus',
    scopedSlots: { customRender: 'companyStatus' }
  },
  {
    title: '开始服务时间',
    dataIndex: 'beginDate',
    scopedSlots: { customRender: 'beginDate' }
  },
  {
    title: '结束服务时间',
    dataIndex: 'endDate',
    scopedSlots: { customRender: 'endDate' }
  },
  {
    title: '公司类型',
    dataIndex: 'companyType',
    scopedSlots: { customRender: 'companyType' }
  },
  {
    title: '综合税率',
    dataIndex: 'tax',
    scopedSlots: { customRender: 'tax' }
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

let timeout
let currentValue

function fetch (value, callback) {
  if (timeout) {
    clearTimeout(timeout)
    timeout = null
  }
  currentValue = value

  function fake () {
    fuzzyQueryCompany(value, 20, false).then((d) => {
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
  name: 'MycompanyList',
  components: {
    STable,
    companyInfoform
  },
  data () {
    this.columns = columns
    return {
      fuzzyCompanyList: [],
      formTitle: '',
      clearUpload: false,
      isShowOnly: false,
      companyMdl: {},
      companyVisible: false,
      confirmLoading: false,
      bizStatus: [],
      bizStatusMap: {},
      fuzzyCustomList: [],
      infoLoading: false,
      isupdate: false,
      customTypeMap: {},
      customTypList: [],
      queryParam1: {
        companyId: '',
        companyStatus: '',
        pageNo: 1,
        pageSize: 20
      },
      queryParam2: {
        companyStatus: '',
        pageNo: 1,
        pageSize: 20
      },
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam1)
        console.log('loadData request parameters:', requestParameters)
        return getCompanyList(
          requestParameters.companyStatus,
          '3',
          requestParameters.pageNo,
          requestParameters.pageSize,
          requestParameters.companyId
        )
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.companies
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询公司列表失败，请稍后再试'
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
              message: '查询公司列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  methods: {
    fetchCompanyDetail (record) {
      this.formTitle = '公司详情'
      this.isShowOnly = true
      this.confirmLoading = true
      getCompanyInfo(record.companyId)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.companyMdl = {
              ...result.data.bossInfo,
              ...result.data.cfoInfo,
              ...result.data.companyInfo,
              companyId: record.companyId
            }
            console.log('this.companyMdl ', this.companyMdl)
            this.companyVisible = true
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '获取公司详情失败'
            })
          }
        })
        .catch((error) => {
          this.$notification.error({
            message: '获取公司详情失败',
            description: error
          })
          this.confirmLoading = false
        })
    },
    handleOk () {
      if (this.isShowOnly) {
        const form = this.$refs.companyInfoform.form
        this.clearUpload = !this.clearUpload
        form.resetFields() // 清理表单数据（可不做）
        this.$refs.table.refresh(true)
        this.companyVisible = false
        return
      }
      const form = this.$refs.companyInfoform.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          delete values.ctiy
          delete values.area
          if (this.isupdate) {
            updateCompany(values, values.customId)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '修改成功'
                  })
                  const form = this.$refs.companyInfoform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.companyVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '修改失败'
                  })
                }
                if (needLogin(result)) {
                  this.companyVisible = false
                  this.confirmLoading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '修改失败。请稍后再试',
                  description: error
                })
                this.confirmLoading = false
              })
          } else {
            addCompany(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '新增成功'
                  })
                  const form = this.$refs.companyInfoform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.companyVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '新增失败。请稍后再试'
                  })
                }
                if (needLogin(result)) {
                  this.companyVisible = false
                  this.confirmLoading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '新增失败。请稍后再试',
                  description: error
                })
                this.confirmLoading = false
              })
          }
        }
      })
    },
    handleCancel () {
      this.customOpsVisible = false
      this.companyVisible = false

      // const form = this.$refs.createModal.form
      // form.resetFields() // 清理表单数据（可不做）
    },
    handleCustomSearch (value) {
      fetch(value, (data) => (this.fuzzyCompanyList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      this.queryParam1.companyId = value
      fetch(value, (data) => (this.fuzzyCompanyList = data))
    },
    handleAdd () {
      // this.companyMdl['companyId'] = ''
      // this.companyMdl['bossIdPicUp'] = null
      // this.companyMdl['bossIdPicBack'] = null
      // this.companyMdl['cfoIdPicUp'] = null
      // this.companyMdl['cfoIdPicBack'] = null
      this.formTitle = '新建公司申请'
      this.companyMdl = {}
      const form = this.$refs.companyInfoform.form
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
      this.isupdate = false
      this.isShowOnly = false
      this.companyVisible = true
    },
    async handleUpdate (record) {
      await this.fetchCompanyDetail(record)
      this.formTitle = '修改公司'
      this.isupdate = true
      this.companyVisible = true
      this.isShowOnly = false
    },
    getDictBizStatus () {
      dictQuery('bizStatus')
        .then((Response) => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            this.bizStatus = result.data
            this.bizStatus.push({ id: '', name: '不限制' })
            this.bizStatusMap = {}
            for (const i of result.data) {
              this.bizStatusMap[i.id] = i.name
            }
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询字典失败'
            })
          }
          setTimeout(() => {
            this.infoLoading = false
          }, 600)
        })
        .catch((error) => {
          this.infoLoading = false
          this.$notification.error({
            message: '查询字典失败',
            description: error
          })
        })
    }
  },
  computed: {
    ...mapState({
      username: (state) => state.user.name
    })
  },
  created () {
    fetch('', (data) => (this.fuzzyCompanyList = data))
  },
  activated () {
    this.$refs.table.refresh(true)
    this.getDictBizStatus()
  }
}
</script>
