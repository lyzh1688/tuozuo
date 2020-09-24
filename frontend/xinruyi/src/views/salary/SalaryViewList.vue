<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="8">
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
              <a-select style="width:200px;" v-model="queryParam1.status" placeholder="请选择">
                <a-select-option
                  v-for=" projectStatusItem in paymentStatusList"
                  :key="projectStatusItem.id"
                >{{ projectStatusItem.name }}</a-select-option>
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
              >查询</a-button>
              <a-button
                :disabled="projectListLoading"
                size="small"
                type="primary"
                @click="()=>{ queryParam1= {
                  companyId: '',
                  projectId: '',
                  status:'',
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
              <a-button size="small" @click="handleAdd">工资发放申请</a-button>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <s-table
        ref="table"
        size="default"
        rowKey="projectId"
        :pageSize="20"
        :columns="columns"
        :data="loadData"
        showPagination="true"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="releaseStatus" slot-scope="text">{{ paymentStatusMap[text] }}</span>
        <span slot="payVoucher" slot-scope="text"> <a-button
          type="text"
          v-if="text&&text!==''"
          @click="()=>{jumpToFile(text)}"
        >上传的文件</a-button></span>
        <span slot="transferVoucher" slot-scope="text"> <a-button
          v-if="text&&text!==''"
          type="text"
          @click="()=>{jumpToFile(text)}"
        >上传的文件</a-button></span>
        <span slot="ops" slot-scope="text, record">
          <a-button
            :disabled="record.releaseStatus!=='4'"
            size="small"
            type="primary"
            @click="handleUpdate(record)"
            :loading="confirmLoading"
          >修改</a-button>
          <a-button size="small" @click="fetchProjectDetail(record)" :loading="confirmLoading">详情</a-button>
        </span>
      </s-table>
      <!-- <projectform
        :title="formTitle"
        ref="projectform"
        :clearUpload="clearUpload"
        :visible="projectVisible"
        :loading="confirmLoading"
        :model="projectMdl"
        :isUpdate="isupdate"
        :isShowOnly="isShowOnly"
        @cancel="handleCancel"
        @ok="handleOk"
      >
        <template v-slot:other v-if="isShowOnly">
          <a-form-item label="备注">
            <a-textarea
              :disabled="isShowOnly"
              v-decorator="['remark', { validateTrigger: 'blur'}]"
            />
          </a-form-item>
        </template>
      </projectform> -->
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { STable } from '@/components'
import {
  fuzzyQueryProject
} from '@/api/projects'
import {
  fuzzyQueryCompany
} from '@/api/company'
import {
  getSalaryViewList
} from '@/api/salary'
import { getCommonDict } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
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
      projectVisible: false,
      confirmLoading: false,
      projectMdl: {},
      isupdate: false,
      isShowOnly: false,
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
    STable
  },
  created () {
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
  methods: {
      jumpToFile (link) {
      window.open(link, '_blank')
    },
    // fetchProjectDetail (record) {
    //   this.formTitle = '项目详情'
    //   this.isShowOnly = true
    //   this.confirmLoading = true
    //   getProjectDetail(record.projectId)
    //     .then((response) => {
    //       const result = response
    //       if (success(result)) {
    //         this.projectMdl = {
    //           ...result.data,
    //           projectId: record.projectId
    //         }
    //         this.projectVisible = true
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
    // handleOk () {
    //   if (this.isShowOnly) {
    //     const form = this.$refs.projectform.form
    //     this.clearUpload = !this.clearUpload
    //     form.resetFields() // 清理表单数据（可不做）
    //     this.$refs.table.refresh(true)
    //     this.projectVisible = false
    //     return
    //   }
    //   const form = this.$refs.projectform.form
    //   form.validateFields((errors, values) => {
    //     if (!errors) {
    //       this.confirmLoading = true
    //       if (this.isupdate) {
    //         updateProject(values)
    //           .then((response) => {
    //             const result = response
    //             if (success(result)) {
    //               this.$notification.success({
    //                 message: '修改成功'
    //               })
    //               const form = this.$refs.projectform.form
    //               this.clearUpload = !this.clearUpload
    //               form.resetFields() // 清理表单数据（可不做）
    //               this.$refs.table.refresh(true)
    //               this.projectVisible = false
    //               this.confirmLoading = false
    //             } else {
    //               this.confirmLoading = false
    //               this.$notification.error({
    //                 message: errorMessage(result),
    //                 description: '修改失败'
    //               })
    //             }
    //             if (needLogin(result)) {
    //               this.projectVisible = false
    //               this.confirmLoading = false
    //             }
    //           })
    //           .catch((error) => {
    //             this.$notification.error({
    //               message: '修改失败。请稍后再试',
    //               description: error
    //             })
    //             this.confirmLoading = false
    //           })
    //       } else {
    //         addNewProject(values)
    //           .then((response) => {
    //             const result = response
    //             if (success(result)) {
    //               this.$notification.success({
    //                 message: '新增成功'
    //               })
    //               const form = this.$refs.projectform.form
    //               this.clearUpload = !this.clearUpload
    //               form.resetFields() // 清理表单数据（可不做）
    //               this.$refs.table.refresh(true)
    //               this.projectVisible = false
    //               this.confirmLoading = false
    //             } else {
    //               this.confirmLoading = false
    //               this.$notification.error({
    //                 message: errorMessage(result),
    //                 description: '新增失败。请稍后再试'
    //               })
    //             }
    //             if (needLogin(result)) {
    //               this.projectVisible = false
    //               this.confirmLoading = false
    //             }
    //           })
    //           .catch((error) => {
    //             this.$notification.error({
    //               message: '新增失败。请稍后再试',
    //               description: error
    //             })
    //             this.confirmLoading = false
    //           })
    //       }
    //     }
    //   })
    // },
    handleAdd () {
      this.formTitle = '新建项目'
      this.projectMdl = {}
      const form = this.$refs.projectform.form
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
      this.isupdate = false
      this.isShowOnly = false
      this.projectVisible = true
    },
    async handleUpdate (record) {
      await this.fetchProjectDetail(record)
      this.formTitle = '修改项目'
      this.isupdate = true
      this.projectVisible = true
      this.isShowOnly = false
    },
    handleCancel () {
      if (!this.isShowOnly) {
        Modal.confirm({
          title: '取消操作',
          content: '是否确认取消操作？所做的修改将会丢失！',
          onOk: () => {
            this.projectVisible = false
          },
          onCancel () {}
        })
      } else {
        this.projectVisible = false
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
</style>
