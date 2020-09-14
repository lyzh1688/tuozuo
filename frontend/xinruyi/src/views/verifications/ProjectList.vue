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
                placeholder="请输入公司名称"
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
            <a-form-item label="行业类型">
              <a-select
                v-model="queryParam1.industryType"
                style="width: 200px"
              >
                <a-select-option
                  v-for="province in industryTypeList"
                  :key="province.id"
                >{{ province.name }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item label="项目状态">
              <a-select style="width:200px;" v-model="queryParam1.projectStatus" placeholder="请选择">
                <a-select-option
                  v-for=" projectStatusItem in projectStatusList"
                  :key="projectStatusItem.id"
                >{{ projectStatusItem.name }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="10" :sm="8">
            <a-form-item label="申请时间">
              <a-date-picker
                valueFormat="YYYYMMDD"
                format="YYYY-MM-DD"
                v-model="queryParam1.beginDate" />
              <a-date-picker
                valueFormat="YYYYMMDD"
                format="YYYY-MM-DD"
                v-model="queryParam1.endDate" />
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
                  projectStatus: '',
                  beginDate: '',
                  endDate: '',
                  companyId: '',
                  projectId: '',
                  industryType:'',
                  pageNo: 1,
                  pageSize: 20
                }}"
              >重置</a-button>
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
        :showPagination="true"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="projectStatus" slot-scope="text">{{ projectStatusMap[text] }}</span>
        <span slot="industryType" slot-scope="text">{{ industryTypeMap[text] }}</span>
        <span slot="ops" slot-scope="text, record">
          <a-button
            :disabled="record.projectStatus!='1'&&record.projectStatus!='6'"
            size="small"
            type="primary"
            @click="handleverify(record)"
            :loading="confirmLoading"
          >审核</a-button>
          <a-button
            size="small"
            @click="fetchProjectDetail(record)"
            :loading="confirmLoading"
          >详情</a-button>
        </span>
      </s-table>
      <projectform
        :title="formTitle"
        ref="projectform"
        :clearUpload="clearUpload"
        :visible="projectVisible"
        :loading="confirmLoading"
        :model="projectMdl"
        :isShowOnly="isShowOnly"
        @cancel="handleCancel"
        @ok="handleOk"
      >
        <template v-slot:other v-if="isShowOnly">
          <a-form-item label="服务费率">
            <a-input-number
              :disabled="isShowOnly&&!isverify||verifyType !== '1'"
              :min="0"
              v-decorator="['fee', {rules: [{required: true, message: '请输入服务费率！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item label="审核结果" v-if="isShowOnly&&isverify">
            <a-select
              :disabled="isShowOnly&&!isverify"
              style="width:200px;"
              v-decorator="['status', {rules: [{required: true, message: '请选择状态！'}], validateTrigger: 'blur'}]"
              placeholder="请选择">
              <a-select-option value="1">审核成功</a-select-option>
              <a-select-option value="0">审核失败</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="项目状态" v-if="isShowOnly&&!isverify">
            <a-select
              :disabled="isShowOnly&&!isverify"
              style="width:200px;"
              v-decorator="['status', {rules: [{required: true, message: '请选择状态！'}], validateTrigger: 'blur'}]"
              placeholder="请选择">
              <a-select-option
                v-for=" projectStatusItem in projectStatusList"
                :key="projectStatusItem.id"
              >{{ projectStatusItem.name }}</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="备注">
            <a-textarea
              :disabled="isShowOnly&&!isverify"
              v-decorator="['remark', {rules: [{required: true, message: '请输备注！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
        </template>
      </projectform>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { STable } from '@/components'
import {
  fuzzyQueryProject,
  getProjectDetail
} from '@/api/projects'
import {
  fuzzyQueryCompany
} from '@/api/company'
import {
  getProjectEventList,
  doprojectRelease,
  doprojectConfirmation
} from '@/api/events'
import { getCommonDict } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import projectform from '@/views/projects/form/ProjectForm'
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
    title: '行业类型',
    dataIndex: 'industryType',
    scopedSlots: { customRender: 'industryType' }
  },
  {
    title: '申请日期',
    dataIndex: 'applyDate',
    scopedSlots: { customRender: 'applyDate' }
  },
  {
    title: '申请状态',
    dataIndex: 'projectStatus',
    scopedSlots: { customRender: 'projectStatus' }
  },
  {
    title: '服务费率',
    dataIndex: 'rate',
    scopedSlots: { customRender: 'rate' }
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
    fuzzyQueryCompany(value, 20).then((d) => {
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
  name: 'ProjectVerificationList',
  data () {
    this.columns = columns
    return {
      clearUpload: false,
      projectVisible: false,
      confirmLoading: false,
      projectMdl: {},
      isverify: false,
      verifyType: '',
      isShowOnly: false,
      industryTypeList: [],
      industryTypeMap: {},
      formTitle: '',
      queryParam1: {
        projectStatus: '',
        industryType: '',
        beginDate: '',
        endDate: '',
        companyId: '',
        projectId: ''
      },
      projectListLoading: false,
      fuzzyProjectList: [],
      fuzzyCompanyList: [],
      projectStatusList: [],
      projectStatusMap: {},
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam1)
        console.log('loadData request parameters:', requestParameters)
        return getProjectEventList(
          requestParameters.companyId,
          requestParameters.projectId,
          requestParameters.projectStatus,
          requestParameters.industryType,
          requestParameters.beginDate,
          requestParameters.endDate,
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.projects
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询项目列表失败，请稍后再试'
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
              message: '查询项目列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  components: {
    STable,
    projectform
  },
  created () {
    fetch('', (data) => (this.fuzzyProjectList = data))
    fetch2('', (data) => (this.fuzzyCompanyList = data))
    this.getDict('projectStatus').then((response) => {
      this.projectStatusList = response
      this.projectStatusMap = {}
      for (const i of response) {
        this.projectStatusMap[i.id] = i.name
      }
    })
     this.getDict('industryType').then((response) => {
      this.industryTypeList = response
      this.industryTypeMap = {}
      for (const i of response) {
        this.industryTypeMap[i.id] = i.name
      }
    })
  },
  methods: {
     fetchProjectDetail (record, mark) {
      this.formTitle = '项目详情'
      this.isShowOnly = true
      this.isverify = false
      this.confirmLoading = true
      getProjectDetail(record.projectId)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.projectMdl = {
              ...result.data,
              projectId: record.projectId,
              fee: record.rate,
              status: record.projectStatus
            }
            if (mark) {
                this.projectMdl.remark = ''
      this.projectMdl.status = ''
            }
            console.log(this.projectMdl)
            this.projectVisible = true
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '获取项目详情失败'
            })
          }
        })
        .catch((error) => {
          this.$notification.error({
            message: '获取项目详情失败',
            description: error
          })
          this.confirmLoading = false
        })
    },
    handleOk () {
      if (this.isShowOnly && !this.isverify) {
        const form = this.$refs.projectform.form
        this.clearUpload = !this.clearUpload
        form.resetFields() // 清理表单数据（可不做）
        this.$refs.table.refresh(true)
        this.projectVisible = false
        return
      }
      const form = this.$refs.projectform.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          console.log('verifyType', this.verifyType)
          if (this.verifyType === '1') {
 doprojectRelease(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '审核成功'
                  })
                  const form = this.$refs.projectform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.projectVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '审核失败'
                  })
                }
                if (needLogin(result)) {
                  this.projectVisible = false
                  this.confirmLoading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '审核失败。请稍后再试',
                  description: error
                })
                this.confirmLoading = false
              })
          } else if (this.verifyType === '6') {
              doprojectConfirmation(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '审核成功'
                  })
                  const form = this.$refs.projectform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.projectVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '审核失败'
                  })
                }
                if (needLogin(result)) {
                  this.projectVisible = false
                  this.confirmLoading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '审核失败。请稍后再试',
                  description: error
                })
                this.confirmLoading = false
              })
          }
        }
      })
    },
    handleAdd () {
      this.formTitle = '新建项目'
      this.projectMdl = {}
      const form = this.$refs.projectform.form
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
      this.isverify = false
      this.isShowOnly = false
      this.projectVisible = true
    },
   async handleverify (record) {
       const form = this.$refs.projectform.form
       this.verifyType = record.projectStatus
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
    this.fetchProjectDetail(record, true)
   if (record.projectStatus === '1') {
this.formTitle = '审核项目发布申请'
      } else {
          this.formTitle = '审核项目完成申请'
      }

      console.log(this.projectMdl)
      this.projectVisible = true
      this.isverify = true
      this.isShowOnly = true
    },
    handleCancel () {
        if (this.isverify) {
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
      fetch2(value, (data) => (this.fuzzyCompanyList = data))
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
