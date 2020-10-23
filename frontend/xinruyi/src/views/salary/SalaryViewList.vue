<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="8" v-if="isStaff">
            <a-form-item label="公司名称">
              <a-select
                show-search
                v-model="queryParam1.companyId"
                placeholder="请输入项目名称"
                style="width: 200px"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleCustomSearch2"
                @change="handleCustomChange2"
              >
                <a-select-option v-for="d in fuzzyCompanyList" :key="d.value">{{ d.text }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item label="项目名称">
              <a-select
                show-search
                v-model="queryParam1.projectId"
                placeholder="请输入项目名称"
                style="width: 200px"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleCustomSearch"
                @change="handleCustomChange"
              >
                <a-select-option v-for="d in fuzzyProjectList" :key="d.value">{{ d.text }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item label="发放状态">
              <a-select style="width: 200px" v-model="queryParam1.status" placeholder="请选择">
                <a-select-option v-for="projectStatusItem in paymentStatusList" :key="projectStatusItem.id">{{
                  projectStatusItem.name
                }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item>
              <a-button
                :disabled="projectListLoading"
                type="primary"
                size="small"
                @click="$refs.table.refresh(true)"
              >查询</a-button
              >
              <a-button
                :disabled="projectListLoading"
                size="small"
                type="primary"
                @click="
                  () => {
                    queryParam1 = {
                      companyId: '',
                      projectId: '',
                      status: '',
                      pageNo: 1,
                      pageSize: 20,
                    }
                  }
                "
              >重置</a-button
              >
            </a-form-item>
          </a-col>
        </a-row>
        <a-row v-if="!isStaff">
          <a-col :md="12" :sm="24">
            <a-form-item>
              <a-button size="small" @click="handleAdd">工资发放申请</a-button>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <s-table
        ref="table"
        size="default"
        rowKey="projectId"
        :pageSize="10"
        :columns="columns"
        :data="loadData"
        showPagination="true"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="releaseStatus" slot-scope="text">{{ paymentStatusMap[text] }}</span>
        <span slot="payVoucher" slot-scope="text">
          <a-button
            type="text"
            v-if="text && text !== ''"
            @click="
              () => {
                jumpToFile(text)
              }
            "
          >上传的文件</a-button
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
          >上传的文件</a-button
          ></span
        >
        <span slot="ops" slot-scope="text, record">
          <a-button
            v-if="!isStaff && record.releaseStatus == '5'"
            size="small"
            type="primary"
            @click="handleUpdate(record)"
            :loading="confirmLoading"
          >修改</a-button
          >
          <a-button
            v-if="isStaff && record.releaseStatus == '1'"
            size="small"
            type="primary"
            @click="handleVerify(record)"
            :loading="confirmLoading"
          >审核</a-button
          >
          <a-button
            v-if="!isStaff && record.releaseStatus == '2'"
            size="small"
            type="primary"
            @click="handleConfirm(record)"
            :loading="confirmLoading"
          >审核</a-button
          >
          <a-button
            v-if="isStaff && record.releaseStatus == '3'"
            size="small"
            type="primary"
            @click="handleRelease(record)"
            :loading="confirmLoading"
          >审核</a-button
          >
          <a-button size="small" @click="handleDetail(record)" :loading="confirmLoading">详情</a-button>
          <a-button size="small" @click="handleHistory(record)" :loading="confirmLoading">发放历史</a-button>
        </span>
      </s-table>
      <uploadfileform
        :title="formTitle"
        ref="uploadfileform"
        :clearUpload="clearUpload"
        :visible="salaryVisible"
        :loading="confirmLoading"
        :model="salaryMdl"
        :isUpdate="isupdate"
        :isShowOnly="isShowOnly"
        @cancel="handleCancel"
        @ok="handleOk"
      >
      </uploadfileform>
      <salarydetailwithlist
        :title="formTitle"
        ref="salarydetailwithlist"
        :visible="detailVisible"
        :loading="confirmLoading"
        :model="detailMdl"
        :isUpdate="isupdate"
        :isShowOnly="isShowOnly"
        @cancel="detailVisible = false"
        @ok="detailVisible = false"
      >
        <template v-slot:other v-if="!isShowOnly">
          <a-row>
            <a-col :span="48">
              <a-form-item>
                <div style="display: flex; flex-wrap: nowrap; flex-flow: row; justify-content: space-between">
                  <div style="margin-inline-end: 70px; margin-inline-start: 70px">
                    <a-button type="primary" @click="handleConfirmOK()">确认通过</a-button>
                  </div>
                  <div style="margin-inline-end: 70px; margin-inline-start: 70px">
                    <a-button type="danger" @click="handleConfirmReject()">确认不通过</a-button>
                  </div>
                </div>
              </a-form-item>
            </a-col>
          </a-row>
        </template>
      </salarydetailwithlist>
      <verifysalary
        ref="verifysalary"
        :visible="verifyVisible"
        :loading="confirmLoading"
        :model="detailMdl"
        @cancel="verifyVisible = false"
        @ok="verifyVisible = false"
        @pass="handleVerifyOK()"
        @reject="handleVerifyReject()"
      >
      </verifysalary>
      <salaryhislist
        title="工资发放历史"
        ref="salaryhislist"
        :visible="historyVisible"
        :loading="confirmLoading"
        :model="detailMdl"
        @cancel="historyVisible = false"
        @ok="historyVisible = false"
      >
      </salaryhislist>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { STable } from '@/components'
import { fuzzyQueryProject } from '@/api/projects'
import { fuzzyQueryCompany } from '@/api/company'
import { getSalaryViewList, uploadFile, modifyFile, doVerifySalary, doConfirmSalary } from '@/api/salary'
import { getCommonDict } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import uploadfileform from './forms/uploadFileForm'
import salarydetailwithlist from './forms/SalaryDetailWithList'
import verifysalary from './forms/VerifySalary'
import salaryhislist from './forms/SalaryHisList'
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
    dataIndex: 'releaseStatus',
    scopedSlots: { customRender: 'releaseStatus' }
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
    fuzzyQueryProject(value, 20, false).then((d) => {
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

  function fake2 () {
    fuzzyQueryCompany(value, 20, false).then((d) => {
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

  timeout2 = setTimeout(fake2, 300)
}
export default {
  name: 'SalaryViewList',
  data () {
    this.columns = columns
    return {
      clearUpload: false,
      salaryVisible: false,
      confirmLoading: false,
      salaryMdl: {},
      isupdate: false,
      isShowOnly: false,
      detailVisible: false,
      verifyVisible: false,
      historyVisible: false,
      detailMdl: {},
      formTitle: '',
      queryParam1: {
        companyId: '',
        projectId: '',
        status: ''
      },
      projectListLoading: false,
      fuzzyProjectList: [],
      fuzzyCompanyList: [],
      paymentStatusList: [],
      paymentStatusMap: {},
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam1)
        console.log('loadData request parameters:', requestParameters)
        return getSalaryViewList(
          requestParameters.companyId,
          requestParameters.projectId,
          requestParameters.status,
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.funds
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
  components: {
    STable,
    uploadfileform,
    salarydetailwithlist,
    verifysalary,
    salaryhislist
  },
  activated () {
    // console.log('isStaff', this.isStaff)
    fetch('', (data) => (this.fuzzyProjectList = data))
    fetch2('', (data) => (this.fuzzyCompanyList = data))
    this.getDict('paymentStatus').then((response) => {
      this.paymentStatusList = response
      this.paymentStatusMap = {}
      for (const i of response) {
        this.paymentStatusMap[i.id] = i.name
      }
    })
  },
  computed: {
    userInfo () {
      return this.$store.getters.userInfo
    },
    isStaff () {
      for (const i of this.$store.getters.roles.permissionList) {
        if (i.includes('staff')) {
          return true
        }
      }
      return false
    }
  },
  methods: {
    handleVerifyOK () {
      const form = this.$refs.verifysalary.form
      form.validateFields((errors, values) => {
        if (!errors) {
          console.log('values', values)

          Modal.confirm({
            title: '审核通过',
            content: '是否确认取审核通过？',
            onOk: () => {
              this.sendVerifyResult(values.paymentId, '1', values.remark)
            },
            onCancel () {}
          })
        }
      })
    },
    handleVerifyReject () {
      const form = this.$refs.verifysalary.form
      form.validateFields((errors, values) => {
        if (!errors) {
          Modal.confirm({
            title: '审核未通过',
            content: '是否确认不通过审核？该操作将需要客户重新修改审核后再次发起！',
            onOk: () => {
              this.sendVerifyResult(values.paymentId, '0', values.remark)
            },
            onCancel () {}
          })
        }
      })
    },
    handleConfirmOK () {
      const form = this.$refs.salarydetailwithlist.form
      form.validateFields((errors, values) => {
        if (!errors) {
          console.log('values', values)

          Modal.confirm({
            title: '审核通过',
            content: '是否确认取审核通过？通过后将进入工资待发放流程！',
            onOk: () => {
              this.sendConfirmResult(values.paymentId, '1')
            },
            onCancel () {}
          })
        }
      })
    },
    handleConfirmReject () {
      const form = this.$refs.salarydetailwithlist.form
      form.validateFields((errors, values) => {
        if (!errors) {
          Modal.confirm({
            title: '确认未通过',
            content: '是否确认不通过审核？该操作将需要您重新修改或者发起工资发放申请再继续流程！',
            onOk: () => {
              this.sendConfirmResult(values.paymentId, '0')
            },
            onCancel () {}
          })
        }
      })
    },
    sendVerifyResult (id, status, remark) {
      this.confirmLoading = true
      doVerifySalary(id, status, remark)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.$notification.success({
              message: '发送审核成功'
            })
            this.verifyVisible = false
            this.$refs.table.refresh(true)
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '发送审核失败'
            })
          }
          if (needLogin(result)) {
            this.verifyVisible = false
            this.confirmLoading = false
          }
        })
        .catch((error) => {
          this.$notification.error({
            message: '发送审核失败',
            description: error
          })
          this.confirmLoading = false
        })
    },
    sendConfirmResult (id, status) {
      this.confirmLoading = true
      doConfirmSalary(id, status)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.$notification.success({
              message: '发送确认成功'
            })
            this.detailVisible = false
            this.$refs.table.refresh(true)
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '发送确认失败'
            })
          }
          if (needLogin(result)) {
            this.detailVisible = false
            this.confirmLoading = false
          }
        })
        .catch((error) => {
          this.$notification.error({
            message: '发送确认失败',
            description: error
          })
          this.confirmLoading = false
        })
    },
    jumpToFile (link) {
      window.open(link, '_blank')
    },
    handleDetail (record) {
      this.detailMdl = record
      this.isShowOnly = true
      this.detailVisible = true
    },
    handleHistory (record) {
      this.detailMdl = record
      this.isShowOnly = true
      this.historyVisible = true
    },
    handleConfirm (record) {
      this.detailMdl = record
      this.isShowOnly = false
      this.detailVisible = true
    },
    // fetchProjectDetail (record) {
    //   this.formTitle = '项目详情'
    //   this.isShowOnly = true
    //   this.confirmLoading = true
    //   getProjectDetail(record.projectId)
    //     .then((response) => {
    //       const result = response
    //       if (success(result)) {
    //         this.salaryMdl = {
    //           ...result.data,
    //           projectId: record.projectId
    //         }
    //         this.salaryVisible = true
    //         this.confirmLoading = false
    //       } else {
    //         this.confirmLoading = false
    //         this.$notification.error({
    //           message: errorMessage(result),
    //           description: '获取项目详情失败'
    //         })
    //       }
    //     })
    //     .catch((error) => {
    //       this.$notification.error({
    //         message: '获取项目详情失败',
    //         description: error
    //       })
    //       this.confirmLoading = false
    //     })
    // },
    handleOk () {
      if (this.isShowOnly) {
        const form = this.$refs.uploadfileform.form
        this.clearUpload = !this.clearUpload
        form.resetFields() // 清理表单数据（可不做）
        this.$refs.table.refresh(true)
        this.salaryVisible = false
        return
      }
      const form = this.$refs.uploadfileform.form
      form.validateFields((errors, values) => {
        if (!errors) {
      console.log(values)
          this.confirmLoading = true
          if (this.isupdate && this.salaryMdl.releaseStatus === '5') {
            modifyFile(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '修改成功'
                  })
                  const form = this.$refs.uploadfileform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.salaryVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '修改失败'
                  })
                }
                if (needLogin(result)) {
                  this.salaryVisible = false
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
            uploadFile(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '执行成功'
                  })
                  const form = this.$refs.uploadfileform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.salaryVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '执行失败。请稍后再试'
                  })
                }
                if (needLogin(result)) {
                  this.salaryVisible = false
                  this.confirmLoading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '执行失败。请稍后再试',
                  description: error
                })
                this.confirmLoading = false
              })
          }
        }
      })
    },
    handleAdd () {
      this.formTitle = '新建工资发放申请'
      this.salaryMdl = { companyId: this.userInfo.name }
      const form = this.$refs.uploadfileform.form
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
      this.isupdate = false
      this.isShowOnly = false
      this.salaryVisible = true
    },
    handleRelease (record) {
      this.formTitle = '工资发放'
      this.salaryMdl = {
        companyId: record.companyId,
        paymentId: record.paymentId,
        projectName: record.projectName,
        projectId: record.projectId,
        companyName: record.companyName,
        amount: record.totalWages,
        month: record.month,
        releaseStatus: record.releaseStatus
      }
      const form = this.$refs.uploadfileform.form
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
      this.isupdate = true
      this.isShowOnly = false
      this.salaryVisible = true
    },
    async handleUpdate (record) {
      this.salaryMdl = {
        companyId: record.companyId,
        paymentId: record.paymentId,
        projectName: record.projectName,
        projectId: record.projectId,
        companyName: record.companyName,
        amount: record.totalWages,
        month: record.month,
        voucher: record.transferVoucher,
        releaseStatus: record.releaseStatus
      }
      const form = this.$refs.uploadfileform.form
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
      this.formTitle = '修改工资发放申请'
      this.isupdate = true
      this.salaryVisible = true
      this.isShowOnly = false
    },
    handleVerify (record) {
      this.detailMdl = record
      this.verifyVisible = true
    },
    handleCancel () {
      if (!this.isShowOnly) {
        Modal.confirm({
          title: '取消操作',
          content: '是否确认取消操作？所做的修改将会丢失！',
          onOk: () => {
            this.salaryVisible = false
          },
          onCancel () {}
        })
      } else {
        this.salaryVisible = false
      }
    },
    handleCustomSearch (value) {
      fetch(value, (data) => (this.fuzzyProjectList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      this.queryParam1.projectId = value
      fetch(value, (data) => (this.fuzzyProjectList = data))
    },
    handleCustomSearch2 (value) {
      fetch(value, (data) => (this.fuzzyCompanyList = data))
    },
    handleCustomChange2 (value) {
      // console.log(value)
      this.queryParam1.companyId = value
      fetch(value, (data) => (this.fuzzyCompanyList = data))
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

<style>
.text {
  flex-wrap: nowrap;
  flex-flow: row;
  justify-content: space-between;
  margin-inline-end: 10px;
  margin-inline-start: 10px;
  text-align: inherit;
}
</style>
