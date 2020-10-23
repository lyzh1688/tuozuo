<template>
  <page-header-wrapper>
    <a-card :bordered="false" title="待处理事件">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="8" v-if="isStaff">
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
            <a-form-item label="事件类型">
              <a-select v-model="queryParam1.eventType" style="width: 200px">
                <a-select-option v-for="province in eventTypeList" :key="province.id">{{
                  province.name
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
                      eventType: '',
                      companyId: '',
                      projectId: '',
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
        <span slot="eventType" slot-scope="text">{{ eventTypeMap[text] }}</span>
        <span slot="ops" slot-scope="text, record">
          <a-button size="small" type="primary" @click="handleverify(record)" :loading="confirmLoading">审核</a-button>
          <!-- <a-button size="small" @click="fetchProjectDetail(record)" :loading="confirmLoading">详情</a-button> -->
        </span>
      </s-table>
    </a-card>
    <a-card :bordered="false" title="已完成事件">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="8" v-if="isStaff">
            <a-form-item label="公司名称">
              <a-select
                show-search
                v-model="queryParam2.companyId"
                placeholder="请输入公司名称"
                style="width: 200px"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleCustomSearch3"
                @change="handleCustomChange3"
              >
                <a-select-option v-for="d in fuzzyCompanyList2" :key="d.value">{{ d.text }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item label="项目名称">
              <a-select
                show-search
                v-model="queryParam2.projectId"
                placeholder="请输入项目名称"
                style="width: 200px"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleCustomSearch4"
                @change="handleCustomChange4"
              >
                <a-select-option v-for="d in fuzzyProjectList2" :key="d.value">{{ d.text }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item label="事件类型">
              <a-select v-model="queryParam2.eventType" style="width: 200px">
                <a-select-option v-for="province in eventTypeList" :key="province.id">{{
                  province.name
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
                @click="$refs.table2.refresh(true)"
              >查询</a-button
              >
              <a-button
                :disabled="projectListLoading"
                size="small"
                type="primary"
                @click="
                  () => {
                    queryParam2 = {
                      eventType: '',
                      companyId: '',
                      projectId: '',
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
      </a-form>
      <s-table
        ref="table2"
        size="default"
        rowKey="projectId"
        :pageSize="10"
        :columns="columns2"
        :data="loadData2"
        showPagination="true"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="eventType" slot-scope="text">{{ eventTypeMap[text] }}</span>
        <!-- <span slot="ops" slot-scope="text, record">
          <a-button size="small" @click="fetchProjectDetail(record)" :loading="confirmLoading">详情</a-button>
        </span>-->
      </s-table>
    </a-card>
    <projectform
      :title="formTitle"
      ref="projectform"
      :clearUpload="clearUpload"
      :visible="projectVisible"
      :loading="confirmLoading"
      :model="verificationMdl"
      :isShowOnly="isShowOnly"
      @cancel="handleCancel"
      @ok="handleOk"
    >
      <template v-slot:other v-if="isShowOnly">
        <a-form-item label="服务费率">
          <a-input-number
            :disabled="(isShowOnly && !isverify) || verifyType !== '3'"
            :min="0"
            v-decorator="['fee', { rules: [{ required: true, message: '请输入服务费率！' }], validateTrigger: 'blur' }]"
          />
        </a-form-item>
        <a-form-item label="审核结果" v-if="isShowOnly && isverify">
          <a-select
            :disabled="isShowOnly && !isverify"
            style="width: 200px"
            v-decorator="['status', { rules: [{ required: true, message: '请选择状态！' }], validateTrigger: 'blur' }]"
            placeholder="请选择"
          >
            <a-select-option value="1">审核成功</a-select-option>
            <a-select-option value="0">审核失败</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="项目状态" v-if="isShowOnly && !isverify">
          <a-select
            :disabled="isShowOnly && !isverify"
            style="width: 200px"
            v-decorator="['status', { rules: [{ required: true, message: '请选择状态！' }], validateTrigger: 'blur' }]"
            placeholder="请选择"
          >
            <a-select-option v-for="projectStatusItem in projectStatusList" :key="projectStatusItem.id">{{
              projectStatusItem.name
            }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea
            :disabled="isShowOnly && !isverify"
            v-decorator="['remark', { rules: [{ required: true, message: '请输备注！' }], validateTrigger: 'blur' }]"
          />
        </a-form-item>
      </template>
    </projectform>
    <companyform
      title="审核企业认证申请"
      ref="companyform"
      :clearUpload="clearUpload"
      :visible="companyVisible"
      :loading="confirmLoading"
      :model="verificationMdl"
      :isShowOnly="isShowOnly"
      @cancel="handleCancel"
      @ok="handleOk"
    >
      <template v-slot:other v-if="isShowOnly">
        <a-form-item label="审核结果" v-if="isShowOnly && isverify">
          <a-select
            :disabled="isShowOnly && !isverify"
            style="width: 200px"
            v-decorator="['status', { rules: [{ required: true, message: '请选择状态！' }], validateTrigger: 'blur' }]"
            placeholder="请选择"
          >
            <a-select-option value="1">审核成功</a-select-option>
            <a-select-option value="0">审核失败</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="公司状态" v-if="isShowOnly && !isverify">
          <a-select
            :disabled="isShowOnly && !isverify"
            style="width: 200px"
            v-decorator="['status', { rules: [{ required: true, message: '请选择状态！' }], validateTrigger: 'blur' }]"
            placeholder="请选择"
          >
            <a-select-option v-for="projectStatusItem in companyStatusList" :key="projectStatusItem.id">{{
              projectStatusItem.name
            }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea
            :disabled="isShowOnly && !isverify"
            v-decorator="['remark', { rules: [{ required: true, message: '请输备注！' }], validateTrigger: 'blur' }]"
          />
        </a-form-item>
      </template>
    </companyform>
    <companyspotform
      title="审核企业入住申请"
      ref="companyspotform"
      :visible="companyspotformVisible"
      :loading="confirmLoading"
      :model="verificationMdl"
      :isShowOnly="isShowOnly"
      @cancel="handleCancel"
      @ok="handleOk"
    ></companyspotform>
    <firestaffform
      title="审核企业入住申请"
      ref="firestaffform"
      :visible="fireStaffVisible"
      :loading="confirmLoading"
      :model="verificationMdl"
      :isShowOnly="isShowOnly"
      @cancel="handleCancel"
      @ok="handleOk"
    ></firestaffform>
    <joinprojectform
      ref="joinprojectform"
      :visible="joinprojectVisible"
      :loading="confirmLoading"
      :model="joinprojectMdl"
      :isShowOnly="isShowOnly"
      @cancel="handleCancel"
      @ok="handleOk"
    ></joinprojectform>
    <uploadfileform
      :title="formTitle"
      ref="uploadfileform"
      :clearUpload="clearUpload"
      :visible="salaryVisible"
      :loading="confirmLoading"
      :model="salaryMdl"
      :isUpdate="true"
      :isShowOnly="false"
      @cancel="handleCancel"
      @ok="handleSalaryRelease"
    >
    </uploadfileform>
    <verifysalary
      ref="salaryVerifysalary"
      :visible="salaryVerifyVisible"
      :loading="confirmLoading"
      :model="salaryDetailMdl"
      @cancel="salaryVerifyVisible = false"
      @ok="salaryVerifyVisible = false"
      @pass="handleVerifyOK()"
      @reject="handleVerifyReject()"
    >
    </verifysalary>
    <salarydetailwithlist
      :title="formTitle"
      ref="salarydetailwithlist"
      :visible="salaryDetailVisible"
      :loading="confirmLoading"
      :model="salaryDetailMdl"
      :isUpdate="isupdate"
      :isShowOnly="false"
      @cancel="salaryDetailVisible = false"
      @ok="salaryDetailVisible = false"
    >
      <template v-slot:other >
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
  </page-header-wrapper>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { STable } from '@/components'
import { fuzzyQueryProject, getProjectDetail } from '@/api/projects'
import { fuzzyQueryCompany, getCompanyDetail } from '@/api/company'
import {
  getAllEventList,
  doprojectRelease,
  doCompanyAuth,
  docompanySpot,
  doprojectConfirmation,
  dodecruitment,
  doJoinProject
} from '@/api/events'
import { getCommonDict } from '@/api/dictionary'
import { uploadFile, doVerifySalary, doConfirmSalary } from '@/api/salary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import projectform from '@/views/projects/form/ProjectForm'
import md5 from 'md5'
import firestaffform from './form/FireStaffForm'
import companyform from '@/views/user/forms/CompanyAuthentication'
import companyspotform from './form/CompanySpotForm'
import uploadfileform from '@/views/salary/forms/uploadFileForm'
import verifysalary from '@/views/salary/forms/VerifySalary'
import salarydetailwithlist from '@/views/salary/forms/SalaryDetailWithList'
import joinprojectform from './form/JoinProjectForm'
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
    title: '事件类型',
    dataIndex: 'eventType',
    scopedSlots: { customRender: 'eventType' }
  },
  {
    title: '申请日期',
    dataIndex: 'applyDate',
    scopedSlots: { customRender: 'applyDate' }
  },
  {
    title: '申请者',
    dataIndex: 'applicant',
    scopedSlots: { customRender: 'applicant' }
  },
  {
    title: '操作',
    dataIndex: 'ops',
    scopedSlots: { customRender: 'ops' }
  }
]
const columns2 = [
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
    title: '事件类型',
    dataIndex: 'eventType',
    scopedSlots: { customRender: 'eventType' }
  },
  {
    title: '申请日期',
    dataIndex: 'applyDate',
    scopedSlots: { customRender: 'applyDate' }
  },
  {
    title: '申请者',
    dataIndex: 'applicant',
    scopedSlots: { customRender: 'applicant' }
  },
  {
    title: '处理时间',
    dataIndex: 'finishDate',
    scopedSlots: { customRender: 'finishDate' }
  },
  {
    title: '审核状态',
    dataIndex: 'status',
    customRender: (text) => {
      if (text === '0') {
        return '审核未通过'
      }
      if (text === '1') {
        return '审核通过'
      }
      return ''
    }
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
  name: 'EventList',
  data () {
    this.columns = columns
    this.columns2 = columns2
    return {
      clearUpload: false,
      projectVisible: false,
      companyspotformVisible: false,
      fireStaffVisible: false,
      companyVisible: false,
      confirmLoading: false,
      salaryVisible: false,
      salaryMdl: {},
      salaryDetailMdl: {},
      joinprojectMdl: {},
      salaryDetailVisible: false,
      salaryVerifyVisible: false,
      joinprojectVisible: false,
      verificationMdl: {},
      verifyType: '',
      isverify: false,
      isupdate: false,
      isShowOnly: false,
      eventTypeList: [],
      eventTypeMap: {},
      industryTypeList: [],
      industryTypeMap: {},
      companyStatusList: [],
      companyStatusMap: {},
      formTitle: '',
      queryParam1: {
        eventType: '',
        companyId: '',
        projectId: ''
      },
      queryParam2: {
        eventType: '',
        companyId: '',
        projectId: ''
      },
      projectListLoading: false,
      fuzzyProjectList: [],
      fuzzyCompanyList: [],
      fuzzyProjectList2: [],
      fuzzyCompanyList2: [],
      projectStatusList: [],
      projectStatusMap: {},
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam1)
        console.log('loadData request parameters:', requestParameters)
        return getAllEventList(
          requestParameters.companyId,
          requestParameters.projectId,
          requestParameters.eventType,
          '0',
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.events
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
      },
      loadData2: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam2)
        console.log('loadData request parameters:', requestParameters)
        return getAllEventList(
          requestParameters.companyId,
          requestParameters.projectId,
          requestParameters.eventType,
          '1',
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.events
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
    projectform,
    firestaffform,
    companyform,
    companyspotform,
    uploadfileform,
    verifysalary,
    salarydetailwithlist,
    joinprojectform
  },
  activated () {
    fetch('', (data) => {
      this.fuzzyProjectList = data
      this.fuzzyProjectList2 = data
    })
    fetch2('', (data) => {
      this.fuzzyCompanyList = data
      this.fuzzyCompanyList2 = data
    })
    this.getDict('projectStatus').then((response) => {
      this.projectStatusList = response
      this.projectStatusMap = {}
      for (const i of response) {
        this.projectStatusMap[i.id] = i.name
      }
    })
    this.getDict('companyStatus').then((response) => {
      this.companyStatusList = response
      this.companyStatusMap = {}
      for (const i of response) {
        this.companyStatusMap[i.id] = i.name
      }
    })
    this.getDict('eventType').then((response) => {
      this.eventTypeList = response
      this.eventTypeMap = {}
      for (const i of response) {
        this.eventTypeMap[i.id] = i.name
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
   computed: {
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
    sendConfirmResult (id, status) {
      this.confirmLoading = true
      doConfirmSalary(id, status)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.$notification.success({
              message: '发送确认成功'
            })
            this.salaryDetailVisible = false
            this.$refs.table.refresh(true)
            this.$refs.table2.refresh(true)
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '发送确认失败'
            })
          }
          if (needLogin(result)) {
            this.salaryDetailVisible = false
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
    handleVerifyOK () {
      const form = this.$refs.salaryVerifysalary.form
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
      const form = this.$refs.salaryVerifysalary.form
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
    sendVerifyResult (id, status, remark) {
      this.confirmLoading = true
      doVerifySalary(id, status, remark)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.$notification.success({
              message: '发送审核成功'
            })
            this.salaryVerifyVisible = false
            this.confirmLoading = false
            this.$refs.table.refresh(true)
            this.$refs.table2.refresh(true)
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '发送审核失败'
            })
          }
          if (needLogin(result)) {
            this.salaryVerifyVisible = false
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
    fetchCompanyDetail (record, mark) {
      this.formTitle = '公司详情'
      this.isShowOnly = true
      this.isverify = false
      this.confirmLoading = true
      getCompanyDetail(JSON.parse(record.snapshot)['companyId'])
        .then((response) => {
          const result = response
          if (success(result)) {
            if (record.status !== '1') {
              this.verificationMdl = {
                ...result.data,
                status: record.status
              }
              if (mark) {
                this.verificationMdl.remark = ''
                this.verificationMdl.status = ''
              }
              this.companyVisible = true
            } else {
              this.verificationMdl = {
                companyName: record.companyName,
                province: record.provinceName,
                city: record.cityName,
                district: record.districtName,
                industryType: record.industryType,
                registerId: JSON.parse(record.snapshot)['registerId']
              }
            }
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
    fetchProjectDetail (record, mark) {
      this.formTitle = '项目详情'
      this.isShowOnly = true
      this.isverify = false
      this.confirmLoading = true
      getProjectDetail(JSON.parse(record.snapshot)['projectId'])
        .then((response) => {
          const result = response
          if (success(result)) {
            this.verificationMdl = {
              ...result.data,
              projectId: JSON.parse(record.snapshot)['projectId'],
              fee: record.rate,
              status: record.projectStatus
            }
            if (mark) {
              this.verificationMdl.remark = ''
              this.verificationMdl.status = ''
            }
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
        const form = this.$refs.companyform.form
        this.clearUpload = !this.clearUpload
        form.resetFields() // 清理表单数据（可不做）
        const form2 = this.$refs.companyspotform.form
        this.clearUpload = !this.clearUpload
        form2.resetFields()
        this.$refs.table.refresh(true)
        this.companyVisible = false
        this.companyspotformVisible = false
        const form3 = this.$refs.projectform.form
        this.clearUpload = !this.clearUpload
        form3.resetFields() // 清理表单数据（可不做）
        this.$refs.table.refresh(true)
        this.projectVisible = false
        const form4 = this.$refs.firestaffform.form
        this.clearUpload = !this.clearUpload
        form4.resetFields() // 清理表单数据（可不做）
        this.$refs.table.refresh(true)
        this.fireStaffVisible = false
        return
      }
      if (this.verifyType === '1') {
        const form = this.$refs.companyspotform.form
        form.validateFields((errors, values) => {
          if (!errors) {
            this.confirmLoading = true
            values['password'] = md5(values['password'])
            docompanySpot(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '审核成功'
                  })
                  const form = this.$refs.companyspotform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.$refs.table2.refresh(true)
                  this.companyspotformVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '审核失败'
                  })
                }
                if (needLogin(result)) {
                  this.companyspotformVisible = false
                  this.confirmLoading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '审核失败',
                  description: error
                })
                this.confirmLoading = false
              })
          }
        })
      }
      if (this.verifyType === '2') {
        const form = this.$refs.companyform.form
        form.validateFields((errors, values) => {
          if (!errors) {
            this.confirmLoading = true
            doCompanyAuth(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '审核成功'
                  })
                  const form = this.$refs.companyform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.$refs.table2.refresh(true)
                  this.companyVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '审核失败'
                  })
                }
                if (needLogin(result)) {
                  this.companyVisible = false
                  this.confirmLoading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '审核失败',
                  description: error
                })
                this.confirmLoading = false
              })
          }
        })
      }
      if (this.verifyType === '3') {
        const form = this.$refs.projectform.form
        form.validateFields((errors, values) => {
          if (!errors) {
            this.confirmLoading = true
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
                  this.$refs.table2.refresh(true)
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
        })
      }
      if (this.verifyType === '4') {
        const form = this.$refs.projectform.form
        form.validateFields((errors, values) => {
          if (!errors) {
            this.confirmLoading = true
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
                  this.$refs.table2.refresh(true)
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
        })
      }
      if (this.verifyType === '5') {
        const form = this.$refs.joinprojectform.form
        form.validateFields((errors, values) => {
          if (!errors) {
            this.confirmLoading = true
            doJoinProject(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '审核成功'
                  })
                  this.$refs.table.refresh(true)
                  this.$refs.table2.refresh(true)
                  this.joinprojectVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '审核失败'
                  })
                }
                if (needLogin(result)) {
                  this.joinprojectVisible = false
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
        })
      }
      if (this.verifyType === '7') {
        const form = this.$refs.firestaffform.form
        form.validateFields((errors, values) => {
          if (!errors) {
            this.confirmLoading = true
            dodecruitment(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '审核成功'
                  })
                  const form = this.$refs.firestaffform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.$refs.table2.refresh(true)
                  this.fireStaffVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '审核失败'
                  })
                }
                if (needLogin(result)) {
                  this.fireStaffVisible = false
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
        })
      }
    },
    async handleverify (record) {
      this.verifyType = record.eventType
      const form = this.$refs.companyform.form
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
      const form2 = this.$refs.companyspotform.form
      form2.resetFields()
      const form3 = this.$refs.projectform.form
      form3.resetFields() // 清理表单数据（可不做）
      const form4 = this.$refs.firestaffform.form
      form4.resetFields() // 清理表单数据（可不做）
      if (this.verifyType === '1') {
        const snapshotBody = JSON.parse(record.snapshot)
        this.verificationMdl = {
          companyName: record.companyName,
          province: snapshotBody.provinceName,
          city: snapshotBody.cityName,
          district: snapshotBody.districtName,
          industryType: snapshotBody.industryType,
          registerId: snapshotBody['registerId']
        }
        this.companyspotformVisible = true
      } else if (this.verifyType === '2') {
        await this.fetchCompanyDetail(record, true)
        this.companyVisible = true
      } else if (this.verifyType === '3') {
        await this.fetchProjectDetail(record)
        this.formTitle = '审核项目发布申请'
        this.projectVisible = true
      } else if (this.verifyType === '4') {
        await this.fetchProjectDetail(record)
        this.formTitle = '审核项目完成申请'
        this.projectVisible = true
      } else if (this.verifyType === '5') {
        this.joinprojectMdl = { ...JSON.parse(record.snapshot), eventId: record.eventId }
        this.joinprojectVisible = true
      } else if (this.verifyType === '7') {
        const snapshotBody = JSON.parse(record.snapshot)
        this.verificationMdl = {
          companyId: snapshotBody.companyId,
          companyName: snapshotBody.companyName,
          projectId: snapshotBody.projectId,
          projectName: snapshotBody.projectName,
          staffId: snapshotBody.staffId,
          staffName: snapshotBody.staffName,
          contact: snapshotBody.contact
        }
        this.fireStaffVisible = true
      } else if (this.verifyType === '8') {
        const snapshotBody = JSON.parse(record.snapshot)
        this.salaryDetailMdl = {
          transferVoucher: snapshotBody.transferVoucher,
          companyId: snapshotBody.company_id,
          paymentId: snapshotBody.payment_id,
          projectName: snapshotBody.project_name,
          projectId: snapshotBody.project_id,
          companyName: snapshotBody.company_name,
          totalWages: snapshotBody.amount,
          month: snapshotBody.month
        }
        this.salaryVerifyVisible = true
      } else if (this.verifyType === '9') {
        const snapshotBody = JSON.parse(record.snapshot)
        this.salaryDetailMdl = {
          transferVoucher: snapshotBody.transferVoucher,
          companyId: snapshotBody.company_id,
          paymentId: snapshotBody.payment_id,
          projectName: snapshotBody.project_name,
          projectId: snapshotBody.project_id,
          companyName: snapshotBody.company_name,
          totalWages: snapshotBody.amount,
          month: snapshotBody.month
        }
        this.salaryDetailVisible = true
      } else if (this.verifyType === '10') {
        const snapshotBody = JSON.parse(record.snapshot)
        this.formTitle = '工资发放'
        this.salaryMdl = {
          companyId: snapshotBody.company_id,
          paymentId: snapshotBody.payment_id,
          projectName: snapshotBody.project_name,
          projectId: snapshotBody.project_id,
          companyName: snapshotBody.company_name,
          amount: snapshotBody.amount,
          month: snapshotBody.month
        }
        const form = this.$refs.uploadfileform.form
        this.clearUpload = !this.clearUpload
        form.resetFields() // 清理表单数据（可不做）
        this.isupdate = true
        this.isShowOnly = false
        this.salaryVisible = true
      }

      this.isverify = true
      this.isShowOnly = true
    },
    handleSalaryRelease () {
      const form = this.$refs.uploadfileform.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
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
                this.$refs.table2.refresh(true)
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
      })
    },
    handleCancel () {
      if (this.isverify) {
        Modal.confirm({
          title: '取消操作',
          content: '是否确认取消操作？所做的修改将会丢失！',
          onOk: () => {
            this.projectVisible = false
            this.companyVisible = false
            this.companyspotformVisible = false
            this.fireStaffVisible = false
            this.salaryVisible = false
            this.joinprojectVisible = false
          },
          onCancel () {}
        })
      } else {
        this.projectVisible = false
        this.companyVisible = false
        this.companyspotformVisible = false
        this.fireStaffVisible = false
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
    handleCustomSearch4 (value) {
      fetch(value, (data) => (this.fuzzyProjectList2 = data))
    },
    handleCustomChange4 (value) {
      // console.log(value)
      this.queryParam2.projectId = value
      fetch(value, (data) => (this.fuzzyProjectList2 = data))
    },
    handleCustomSearch3 (value) {
      fetch(value, (data) => (this.fuzzyCompanyList2 = data))
    },
    handleCustomChange3 (value) {
      // console.log(value)
      this.queryParam2.companyId = value
      fetch2(value, (data) => (this.fuzzyCompanyList2 = data))
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
