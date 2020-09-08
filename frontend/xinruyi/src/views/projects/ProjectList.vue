<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="10" :sm="8">
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
          <a-col :md="10" :sm="8">
            <a-form-item label="项目状态">
              <a-select style="width:200px;" v-model="queryParam1.projectStatus" placeholder="请选择">
                <a-select-option
                  v-for=" projectStatusItem in projectStatusList"
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
                  projectId: '',
                  projectStatus: '',
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
              <a-button size="small" @click="handleAdd">发布项目</a-button>
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
        <span slot="ops" slot-scope="text, record">
          <a-button
            :disabled="projectStatusMap[record.projectStatus].search('完成')>=0"
            size="small"
            type="primary"
            @click="handleUpdate(record)"
            :loading="confirmLoading"
          >修改</a-button>
          <a-button size="small" @click="fetchProjectDetail(record)" :loading="confirmLoading">详情</a-button>
          <a-button
            :disabled="projectStatusMap[record.projectStatus].search('完成')>=0"
            size="small"
            type="primary"
            @click="handleComplete(record)"
            :loading="confirmLoading"
          >完成</a-button>
        </span>
      </s-table>
      <projectform
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
          <a-form-item label="备注" >
            <a-textarea
              :disabled="isShowOnly"
              v-decorator="['remark', { validateTrigger: 'blur'}]"
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
import { fuzzyQueryProject, getProjectList, addNewProject, updateProject, getProjectDetail, completeProjects } from '@/api/projects'
import { getCommonDict } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import projectform from './form/ProjectForm'
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '项目名称',
    dataIndex: 'projectName',
    scopedSlots: { customRender: 'projectName' }
  },

  {
    title: '项目周期',
    dataIndex: 'projectCycle',
    scopedSlots: { customRender: 'projectCycle' }
  },
  {
    title: '预算金额',
    dataIndex: 'budget',
    scopedSlots: { customRender: 'budget' }
  },
  {
    title: '项目人数',
    dataIndex: 'staffNum',
    scopedSlots: { customRender: 'staffNum' }
  },
  {
    title: '开始时间',
    dataIndex: 'beginDate',
    scopedSlots: { customRender: 'beginDate' }
  },
  {
    title: '结束时间',
    dataIndex: 'endDate',
    scopedSlots: { customRender: 'endDate' }
  },
  {
    title: '项目状态',
    dataIndex: 'projectStatus',
    scopedSlots: { customRender: 'projectStatus' }
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
export default {
  name: 'ProjectList',
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
        projectStatus: '',
        projectId: ''
      },
      projectListLoading: false,
      fuzzyProjectList: [],
      projectStatusList: [],
      projectStatusMap: {},
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam1)
        console.log('loadData request parameters:', requestParameters)
        return getProjectList(
          requestParameters.projectId,
          '',
          '',
          requestParameters.projectStatus,
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
    this.getDict('projectStatus').then((response) => {
      this.projectStatusList = response
      this.projectStatusMap = {}
      for (const i of response) {
        this.projectStatusMap[i.id] = i.name
      }
    })
  },
  methods: {
    fetchProjectDetail (record) {
      this.formTitle = '公司详情'
      this.isShowOnly = true
      this.confirmLoading = true
      getProjectDetail(record.projectId)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.projectMdl = {
              ...result.data,
              projectId: record.projectId
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
      if (this.isShowOnly) {
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
          if (this.isupdate) {
            updateProject(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '修改成功'
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
                    description: '修改失败'
                  })
                }
                if (needLogin(result)) {
                  this.projectVisible = false
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
            addNewProject(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '新增成功'
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
                    description: '新增失败。请稍后再试'
                  })
                }
                if (needLogin(result)) {
                  this.projectVisible = false
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
    handleComplete (record) {
        this.confirmLoading = true
completeProjects(record.projectId)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.$notification.success({
              message: errorMessage(result),
              description: '发起项目完成审核成功'
            })
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '发起项目完成审核失败'
            })
          }
        })
        .catch((error) => {
          this.$notification.error({
            message: '发起项目完成审核失败',
            description: error
          })
          this.confirmLoading = false
        })
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
