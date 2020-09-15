<template>
  <page-header-wrapper>
    <a-card style="margin-top: 24px" :bordered="false" class="card-container">
      <a-tabs
        :style="{ height: '700px' }"
        ref="tabsa"
        :default-active-key="projectList.length>0?projectList[0].id:''"
        tab-position="left"
        @tabClick="fetchProlectStaffList"
      >
        <a-tab-pane class="tab-pane-self" v-for="item in projectList" :key="item.id">
          <template v-slot:tab>
            <span style="text-align:left;display:block;">{{ item.name }}</span>
          </template>
          <a-skeleton :loading="infoLoading" active title>
            <a-row>
              <a-col :sm="8" :xs="24">
                <span>状态:{{ projectStatusMap[item.status] }}</span>
                <a-button type="dashed" size="small" :disabled="item.status!='3'&&item.status!='5'" @click="handleProjectStaffOpsDrawer()">新增人员</a-button>
                <a-button type="primary" size="small" @click="refresh=!refresh">刷新</a-button>
              </a-col>
            </a-row>
          </a-skeleton>
          <s-table
            :ref="item.id"
            id="1"
            size="default"
            rowKey="id"
            :pageSize="20"
            :columns="columns"
            :data="loadData"
            showPagination="true"
          >
            <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
            <span slot="name" slot-scope="text, record">
              <a-button type=" dashed" size="small" @click="handleDetail(record)">{{ text }}</a-button>
            </span>
            <span slot="gender" slot-scope="text">{{ genderMap[text] }}</span>
            <span slot="status" slot-scope="text">
              <a-tag
                :color="text=='0'?'gray':text=='1'?'orange':'green'"
              >{{ staffStatusMap[text] }}</a-tag>
            </span>
            <span slot="ops" slot-scope="text, record">
              <a-button
                v-if="item.status=='3'||item.status=='5'"
                :disabled="record.status!=='2'"
                type="primary"
                size="small"
                shape="circle"
                icon="edit"
                @click="handleUpdate(record)"></a-button>
              <a-button
                v-if="item.status=='3'||item.status=='5'"
                :disabled="record.status!=='2'"
                type="danger"
                size="small"
                shape="circle"
                icon="delete"
                @click="handleDelete(record)"></a-button>
              <a-button type="dashed" size="small" @click="handleSalaryList(record)">工资单</a-button>
            </span>
          </s-table>
        </a-tab-pane>
      </a-tabs>
    </a-card>
    <staffDetail
      formTitle="人员详情"
      ref="staffDetailForm"
      :visible="staffDetailVisible"
      :loading="confirmLoading"
      :model="staffDetailMdl"
      :isShowOnly="isShowOnly"
      @cancel="staffDetailVisible=false"
      @ok="staffDetailVisible=false"
    ></staffDetail>
    <salarylistdrawer
      ref="salaryDrawer"
      :staffId="picStaffId"
      :visible="salaryVisible"
      :projectId="queryParam.projectID"
      @onclose="salaryVisible = false"
    ></salarylistdrawer>
    <projectstaffopsdrawer
      ref="ProjectStaffOpsDrawer"
      :visible="projectStaffOpsDrawerVisible"
      :projectId="queryParam.projectID"
      :staffInProjectList="inProjectMap"
      @onclose="projectStaffOpsDrawerVisible = false"
      @refreshlist="refresh=!refresh"
    ></projectstaffopsdrawer>
    <projectstaffopssubdrawer
      ref="projectstaffopssubdrawer"
      :subvisible="projectstaffopssubdrawerVisible"
      :projectId="queryParam.projectID"
      :model="staffMdl"
      :isUpdate="true"
      :drawerTitle="drawerTitle"
      @onclose="projectstaffopssubdrawerVisible = false"
      @refreshlist="refresh=!refresh"
    ></projectstaffopssubdrawer>
  </page-header-wrapper>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { fuzzyQueryProject } from '@/api/projects'
import { getProjectStaffList, getStaffDetail, delectStaffFromProject } from '@/api/humanResource'
import { getCommonDict } from '@/api/dictionary'
import { STable } from '@/components'
import staffDetail from './form/StaffDetails'
import salarylistdrawer from './form/SalaryListDrawer'
import projectstaffopsdrawer from './form/ProjectStaffOpsDrawer'
import projectstaffopssubdrawer from './form/ProjectStaffOpsSubDrawer'
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '姓名',
    dataIndex: 'name',
    scopedSlots: { customRender: 'name' }
  },
  {
    title: '身份证号',
    dataIndex: 'idNo',
    scopedSlots: { customRender: 'idNo' }
  },
  {
    title: '性别',
    dataIndex: 'gender',
    scopedSlots: { customRender: 'gender' }
  },
  {
    title: '工资',
    dataIndex: 'salary',
    scopedSlots: { customRender: 'salary' }
  },
  {
    title: '进团时间',
    dataIndex: 'beginDate',
    scopedSlots: { customRender: 'beginDate' }
  },
  {
    title: '离团时间',
    dataIndex: 'endDate',
    scopedSlots: { customRender: 'endDate' }
  },
  {
    title: '状态',
    dataIndex: 'status',
    scopedSlots: { customRender: 'status' }
  },
  {
    title: '是否在池',
    dataIndex: 'isValid',
    customRender: (text) => {
      if (text === '0') {
        return '否'
      }
      if (text === '1') {
        return '是'
      }
      return ''
    }
  },
  {
    title: '是否签约',
    dataIndex: 'isSigned',
    customRender: (text) => {
      if (text === '0') {
        return '否'
      }
      if (text === '1') {
        return '是'
      }
      return ''
    }
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
  name: 'ProjectStaff',
  data () {
    this.columns = columns
    return {
      projectList: [],
      projectStaffOpsDrawerVisible: false,
      projectstaffopssubdrawerVisible: false,
      projectListLoading: false,
      queryParam: {
        projectID: ''
      },
      projectStatusMap: '',
      staffMdl: {},
      picStaffId: '',
      currentTableId: '',
      salaryVisible: false,
      confirmLoading: false,
      staffDetailMdl: {},
      staffStatusMap: {},
      inProjectMap: {},
      genderMap: {},
      isShowOnly: false,
      drawerTitle: '',
      staffDetailVisible: false,
      refresh: false,
      infoLoading: false,
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        this.infoLoading = true
        if (requestParameters.projectID === '') {
          return {
            pageSize: 0,
            pageNo: 1,
            totalCount: 0,
            data: []
          }
        }
        return getProjectStaffList(requestParameters.projectID, requestParameters.pageNo, requestParameters.pageSize)
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.staffs
              this.inProjectMap = {}
              for (const i of ans) {
                this.inProjectMap[i.id] = i.name
              }
              setTimeout(() => {
                this.infoLoading = false
              }, 600)
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询项目人员失败，请稍后再试'
              })

              setTimeout(() => {
                this.infoLoading = false
              }, 600)
              return {
                pageSize: 0,
                pageNo: 1,
                totalCount: 0,
                data: []
              }
            }
          })
          .catch((error) => {
            setTimeout(() => {
              this.infoLoading = false
            }, 600)
            this.$notification.error({
              message: '查询项目人员失败，请稍后再试',
              description: error
            })
            return {
              pageSize: 0,
              pageNo: 1,
              totalCount: 0,
              data: []
            }
          })
      }
    }
  },
  components: {
    STable,
    staffDetail,
    salarylistdrawer,
    projectstaffopsdrawer,
    projectstaffopssubdrawer
  },
  created () {
      this.fetchProlectList()
    this.getDict('gender').then((response) => {
      this.genderMap = {}
      for (const i of response) {
        this.genderMap[i.id] = i.name
      }
    })
    this.getDict('projectStatus').then((response) => {
      this.projectStatusMap = {}
      for (const i of response) {
        this.projectStatusMap[i.id] = i.name
      }
    })
    this.getDict('staffStatus').then((response) => {
      this.staffStatusMap = {}
      for (const i of response) {
        this.staffStatusMap[i.id] = i.name
      }
    })
  },
  watch: {
    refresh (newVal, oldVal) {
      console.log('tabsa', this.$refs)
      this.$refs[this.currentTableId][0].refresh(true)
    }
  },
  async activated () {
//    await this.fetchProlectList()
   this.$nextTick(() => {
    this.$refs[this.currentTableId][0].refresh(true)
   })
  },
  methods: {
    handleUpdate (record) {
      this.drawerTitle = '修改项目人员：' + record.name + ':' + record.idNo
      const form = this.$refs.projectstaffopssubdrawer.form
      form.resetFields() // 清理表单数据（可不做）
      this.staffMdl = {
        staffId: record.id,
        projectId: this.currentTableId,
        salary: record.salary,
        enterDate: record.beginDate,
        quitDate: record.endDate
      }
      this.projectstaffopssubdrawerVisible = true
    },
    handleDelete (record) {
      Modal.confirm({
        title: '删除人员',
        content: '是否确认删除？该操作将发起审核！',
        onOk: () => {
          this.confirmLoading = true
          delectStaffFromProject(record.id, this.currentTableId)
            .then((response) => {
              const result = response
              if (success(result)) {
                this.$notification.success({
                  message: errorMessage(result),
                  description: '删除成功'
                })
                this.$refs[this.currentTableId][0].refresh(true)
                this.confirmLoading = false
              } else {
                this.confirmLoading = false
                this.$notification.error({
                  message: errorMessage(result),
                  description: '删除失败'
                })
              }
              if (needLogin(result)) {
                this.staffDetailVisible = false
                this.confirmLoading = false
              }
            })
            .catch((error) => {
              this.$notification.error({
                message: '删除失败',
                description: error
              })
              this.confirmLoading = false
            })
        },
        onCancel () {}
      })
    },
    handleSalaryList (record) {
      this.salaryVisible = true
      this.picStaffId = record.id
    },
    handleProjectStaffOpsDrawer (record) {
      this.projectStaffOpsDrawerVisible = true
    },
    async handleDetail (record) {
      this.staffDetailVisible = true
      await this.getDetail(record)
    },
    getDetail (record) {
      this.formTitle = '人员详情'
      this.isShowOnly = true
      this.confirmLoading = true
      getStaffDetail(record.id)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.staffDetailMdl = {
              ...result.data,
              id: record.id
            }
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '获取人员详情失败'
            })
          }
          if (needLogin(result)) {
            this.staffDetailVisible = false
            this.confirmLoading = false
          }
        })
        .catch((error) => {
          this.$notification.error({
            message: '获取人员详情失败',
            description: error
          })
          this.confirmLoading = false
        })
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
    },
    fetchProlectList () {
      this.projectListLoading = true
      fuzzyQueryProject('', 20, true)
        .then((Response) => {
          const result = Response
          if (success(result)) {
            this.projectList = result.data
            if (this.projectList.length > 0) {
              this.queryParam.projectID = this.projectList[0].id
              this.currentTableId = this.projectList[0].id
}
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询项目列表失败'
            })
          }
          if (needLogin(result)) {
            this.projectListLoading = false
          }
          setTimeout(() => {
              this.refresh = !this.refresh
            this.projectListLoading = false
          }, 600)
        })
        .catch((error) => {
          this.projectListLoading = false
          this.$notification.error({
            message: '查询项目列表失败',
            description: error
          })
        })
    },
    fetchProlectStaffList (projectId) {
      this.currentTableId = projectId
      this.queryParam.projectID = projectId
      if (this.$refs[this.currentTableId] === undefined) {
        setTimeout(() => {
          this.$refs[this.currentTableId][0].refresh(true)
        }, 600)
      } else {
        this.$refs[this.currentTableId][0].refresh(true)
      }
    }
  }
}
</script>

<style>
.a {
  background-color: gray;
}
</style>
