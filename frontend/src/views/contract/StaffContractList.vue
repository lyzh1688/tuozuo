<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-skeleton :loading="infoLoading" active title>
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="10" :sm="8">
              <a-form-item label="公司名称">
                <a-select
                  show-search
                  :value="queryParam.companyId"
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
                  @click="()=>{queryParam= {
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
    <a-card style="margin-top: 24px" :bordered="false">
      <s-table
        ref="table"
        size="default"
        rowKey="contractId"
        :pageSize="20"
        :columns="columns"
        :data="loadData"
        showPagination="auto"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="ops" slot-scope="text, record">
          <a-button size="small" @click="handleUpdate(record)" :loading="confirmLoading">审核</a-button>
          <a-button size="small" @click="handleDetail(record)" :loading="confirmLoading">详情</a-button>
        </span>
      </s-table>
    </a-card>
    <contractForm
      ref="contractForm"
      :clearUpload="clearUpload"
      :visible="contractVisible"
      :loading="confirmLoading"
      :model="contractMdl"
      :isUpdate="isupdate"
      :isShowOnly="true"
      @cancel="handleCancel"
      @ok="handleOk"
    >
      <template v-slot:extraInfo>
        <a-row>
          <a-col :span="12">
            <a-form-item label="合同状态" key="合同状态">
              <a-select
                :disabled="!isupdate"
                style="width:200px;"
                v-decorator="['contractStatus', {rules: [{required: true, message: '请输入合同状态！'}], validateTrigger: 'blur'}]"
                placeholder="请选择"
              >
                <a-select-option
                  v-for=" taxItem in contractStatus"
                  :key="taxItem.id"
                >{{ taxItem.name }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="备注">
              <a-textarea
                :disabled="!isupdate"
                v-decorator="['remark', {rules: [{required: true, message: '请输入备注！'}], validateTrigger: 'blur'} ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </template>
    </contractForm>
  </page-header-wrapper>
</template>
<script>
import { STable } from '@/components'
import { getContractList, confirmContrac, getContracDetail } from '@/api/contract'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { dictQuery, fuzzyQueryCompany } from '../../api/company'
import contractForm from './form/ContractForm'
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '甲方名称',
    dataIndex: 'companyPartyAName',
    scopedSlots: { customRender: 'companyPartyAName' }
  },
  {
    title: '乙方名称',
    dataIndex: 'companyPartyBName',
    scopedSlots: { customRender: 'companyPartyBName' }
  },
  {
    title: '合同名称',
    dataIndex: 'contractName',
    scopedSlots: { customRender: 'contractName' }
  },
  {
    title: '合同金额',
    dataIndex: 'contractAmount',
    scopedSlots: { customRender: 'contractAmount' }
  },
  {
    title: '已开发票金额',
    dataIndex: 'invoiceAmount',
    scopedSlots: { customRender: 'invoiceAmount' }
  },
  {
    title: '合同状态',
    dataIndex: 'contractStatus',
    scopedSlots: { customRender: 'contractStatus' }
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
  name: 'StaffContractList',
  components: {
    STable,
    contractForm
  },
  data () {
    this.columns = columns
    return {
      fuzzyCompanyList: [],
      infoLoading: false,
      queryParam: {
        companyId: ''
      },
      isShowOnly: false,
      isupdate: false,
      clearUpload: false,
      contractVisible: false,
      confirmLoading: false,
      contractMdl: {},
      contractStatus: [],
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        return getContractList(requestParameters.companyId, requestParameters.pageNo, requestParameters.pageSize)
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.contracts
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询合同列表失败，请稍后再试'
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
              message: '查询合同列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  methods: {
      fetchContracDetail (record) {
      this.isShowOnly = true
      this.confirmLoading = true
      getContracDetail(record.contractId)
        .then(response => {
          const result = response
          if (success(result)) {
            this.contractMdl = {
              ...result.data,
              contractId: record.contractId
            }
            console.log('this.contractMdl ', this.contractMdl)
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '获取合同详情失败'
            })
          }
        })
        .catch(error => {
          this.$notification.error({
            message: '获取合同详情失败',
            description: error
          })
          this.confirmLoading = false
        })
    },
    handleOk () {
      if (this.isShowOnly) {
        const form = this.$refs.contractForm.form
        this.clearUpload = !this.clearUpload
        form.resetFields() // 清理表单数据（可不做）
        this.$refs.table.refresh(true)
        this.contractVisible = false
        return
      }
      const form = this.$refs.contractForm.form
      form.validateFields((errors, values) => {
        if (!errors) {
            const tmpkey = values.companyPartyBName.split(':')
            values['companyId'] = tmpkey[0]
            values.companyPartyBName = tmpkey[1]
          this.confirmLoading = true
          confirmContrac(values, values.customId)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '修改成功'
                  })
                  const form = this.$refs.contractForm.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.contractVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '修改失败'
                  })
                }
                if (needLogin(result)) {
                  this.contractVisible = false
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
        }
      })
    },
     async handleDetail (record) {
         this.isupdate = false
           this.contractVisible = true
      await this.fetchContracDetail(record)
    },
    handleCancel () {
      this.contractVisible = false

      // const form = this.$refs.createModal.form
      // form.resetFields() // 清理表单数据（可不做）
    },
    async handleUpdate (record) {
      await this.fetchContracDetail(record)

      this.isupdate = true
      this.contractVisible = true
      this.isShowOnly = false
    },
    handleDefault (value) {
      return value === undefined || value === null ? '暂无数据' : String(value)
    },
    getContractStatus () {
      dictQuery('contractStatus')
        .then((Response) => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            this.contractStatus = result.data
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
    },
    handleCustomSearch (value) {
      fetch(value, (data) => (this.fuzzyCompanyList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      this.queryParam.companyId = value
      fetch(value, (data) => (this.fuzzyCompanyList = data))
    }
  },
  created () {
    this.getContractStatus()
  },
  activated () {
      this.$refs.table.refresh(true)
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
