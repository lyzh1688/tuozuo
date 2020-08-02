<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-skeleton :loading="infoLoading" active title>
        <a-form layout="inline">
          <a-row>
            <a-col :md="7" :sm="8">
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
            <a-col :md="7" :sm="8">
              <a-form-item label="合同名称">
                <a-select
                  show-search
                  :value="queryParam.contractId"
                  placeholder="请输入合同名称"
                  style="width: 200px"
                  :default-active-first-option="false"
                  :show-arrow="false"
                  :filter-option="false"
                  :not-found-content="null"
                  @search="handleCustomSearch2"
                  @change="handleCustomChange2"
                >
                  <a-select-option v-for="d in fuzzyContractList" :key="d.value">{{ d.text }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="7" :sm="8">
              <a-form-item label="开票状态" key="开票状态">
                <a-select v-model="queryParam.invoiceStatus" style="width:200px;" placeholder="请选择">
                  <a-select-option
                    v-for=" taxItem in receiptStatus"
                    :key="taxItem.id"
                  >{{ taxItem.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="3" :sm="8">
              <a-form-item>
                <a-button type="primary" size="small" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button
                  size="small"
                  type="primary"
                  @click="()=>{queryParam= {
                    companyId: '',
                    contractId: '',
                    invoiceStatus: '',
                    pageNo: 1,
                    pageSize: 20
                  }}"
                >重置</a-button>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="12">
              <a-form-item>
                <a-button size="small" @click="handleAdd">创建开票申请</a-button>
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
        rowKey="invoiceId"
        :pageSize="20"
        :columns="columns"
        :data="loadData"
        showPagination="auto"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="ops" slot-scope="text, record">
          <a-button :disabled="record.invoiceStatus!=='审核中'&&record.invoiceStatus!=='审核失败'" size="small" @click="handleUpdate(record)" :loading="confirmLoading">修改</a-button>
          <a-button size="small" @click="handleDetail(record)" :loading="confirmLoading">详情</a-button>
        </span>
      </s-table>
    </a-card>
    <contractForm
      ref="contractForm"
      :clearUpload="clearUpload"
      :visible="receiptVisible"
      :loading="confirmLoading"
      :model="receiptMdl"
      :isUpdate="isupdate"
      :isShowOnly="isShowOnly"
      @cancel="handleCancel"
      @ok="handleOk"
    ></contractForm>
  </page-header-wrapper>
</template>
<script>
import { STable } from '@/components'
import { getReceiptList, addReceipt, updateReceipt, getReceiptDetail } from '@/api/receipt'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { dictQuery, fuzzyQueryCompany } from '../../api/company'
import { fuzzyQueryContract } from '../../api/contract'
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
    title: '发票金额',
    dataIndex: 'invoiceAmount',
    scopedSlots: { customRender: 'invoiceAmount' }
  },
  {
    title: '快递单号',
    dataIndex: 'deliveryId',
    scopedSlots: { customRender: 'deliveryId' }
  },
  {
    title: '状态',
    dataIndex: 'invoiceStatus',
    scopedSlots: { customRender: 'invoiceStatus' }
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
let timeout2
let currentValue2

function fetch2 (value, callback) {
  if (timeout2) {
    clearTimeout(timeout2)
    timeout2 = null
  }
  currentValue2 = value

  function fake () {
    fuzzyQueryContract('', value, 20).then((d) => {
      if (currentValue2 === value) {
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

  timeout2 = setTimeout(fake, 300)
}
export default {
  name: 'CustomReceiptList',
  components: {
    STable,
    contractForm
  },
  data () {
    this.columns = columns
    return {
      fuzzyCompanyList: [],
      fuzzyContractList: [],
      infoLoading: false,
      queryParam: {
        companyId: '',
        contractId: '',
        invoiceStatus: ''
      },
      isShowOnly: false,
      isupdate: false,
      clearUpload: false,
      receiptVisible: false,
      confirmLoading: false,
      receiptMdl: {},
      receiptStatus: [],
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        return getReceiptList(
          requestParameters.companyId,
          requestParameters.contractId,
          requestParameters.invoiceStatus,
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.invoices
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询开票列表失败，请稍后再试'
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
              message: '查询开票列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  methods: {
    fetchReceiptDetail (record) {
      this.isShowOnly = true
      this.confirmLoading = true
      getReceiptDetail(record.invoiceId)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.receiptMdl = {
              ...result.data,
              invoiceId: record.invoiceId
            }
            console.log('this.receiptMdl ', this.receiptMdl)
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '获取开票详情失败'
            })
          }
        })
        .catch((error) => {
          this.$notification.error({
            message: '获取开票详情失败',
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
        this.receiptVisible = false
        return
      }
      const form = this.$refs.contractForm.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          if (this.isupdate) {
            updateReceipt(values)
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
                  this.receiptVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '修改失败'
                  })
                }
                if (needLogin(result)) {
                  this.receiptVisible = false
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
            addReceipt(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '添加成功'
                  })
                  const form = this.$refs.contractForm.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.receiptVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '添加失败'
                  })
                }
                if (needLogin(result)) {
                  this.receiptVisible = false
                  this.confirmLoading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '添加失败。请稍后再试',
                  description: error
                })
                this.confirmLoading = false
              })
          }
        }
      })
    },
    async handleDetail (record) {
      this.isupdate = false
      this.receiptVisible = true
      await this.fetchReceiptDetail(record)
    },
    handleAdd () {
      const tmp = { ...this.receiptMdl }
      tmp['receiptId'] = ''
      tmp['authLetterFile	'] = null
      tmp['bankFlowFile	'] = null
      this.receiptMdl = { ...tmp }
      this.isupdate = false
      this.receiptVisible = true
      this.isShowOnly = false
    },
    handleCancel () {
      this.receiptVisible = false

      // const form = this.$refs.createModal.form
      // form.resetFields() // 清理表单数据（可不做）
    },
    async handleUpdate (record) {
      await this.fetchReceiptDetail(record)

      this.isupdate = true
      this.receiptVisible = true
      this.isShowOnly = false
    },
    handleDefault (value) {
      return value === undefined || value === null ? '暂无数据' : String(value)
    },
    getContractStatus () {
      dictQuery('invoiceStatus')
        .then((Response) => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            this.receiptStatus = result.data
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
    },
    handleCustomSearch2 (value) {
      fetch2(value, (data) => (this.fuzzyContractList = data))
    },
    handleCustomChange2 (value) {
      // console.log(value)
      this.queryParam.contractId = value
      fetch2(value, (data) => (this.fuzzyContractList = data))
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
