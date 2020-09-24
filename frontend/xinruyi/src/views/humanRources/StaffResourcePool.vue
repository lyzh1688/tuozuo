<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-skeleton :loading="infoLoading" active title>
        <a-row>
          <a-col :sm="8" :xs="24">
            <a-button type="dashed" size="small" @click="handleAdd()">新增人员</a-button>
            <a-button type="primary" size="small" @click="refresh=!refresh">刷新</a-button>
          </a-col>
        </a-row>
      </a-skeleton>
    </a-card>
    <a-card style="margin-top: 24px" :bordered="false" title="人力资源池列表">
      <s-table
        ref="table"
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
        <span slot="ops" slot-scope="text, record">
          <a-button type="primary" size="small" @click="handleUpdate(record)">修改</a-button>
          <a-divider type="vertical" />
          <a-button type="danger" size="small" @click="handleDelete(record)">删除</a-button>
          <a-divider type="vertical" />
          <a-button type="dashed" size="small" @click="handleSalaryList(record)">历史工资单</a-button>
        </span>
      </s-table>
    </a-card>
    <staffDetail
      :formTitle="formTitle"
      ref="staffDetailForm"
      :visible="staffDetailVisible"
      :loading="confirmLoading"
      :model="staffDetailMdl"
      :isUpdate="isupdate"
      :isShowOnly="isShowOnly"
      @cancel="handleCancel"
      @ok="handleOk"
    ></staffDetail>
    <SalaryListDrawer
      ref="salaryDrawer"
      :staffId="picStaffId"
      :visible="salaryVisible"
      :refresh="refreshSalary"
      @onclose="handleSalaryClose"
    ></SalaryListDrawer>
  </page-header-wrapper>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { STable } from '@/components'
import SalaryListDrawer from './form/SalaryListDrawer'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { getStaffList, addStaff, updateStaff, getStaffDetail, deleteStaff } from '@/api/humanResource'
import { getCommonDict } from '@/api/dictionary'
import staffDetail from './form/StaffDetails'
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
    title: '银行卡号',
    dataIndex: 'bankCard',
    scopedSlots: { customRender: 'bankCard' }
  },
  {
    title: '联系方式',
    dataIndex: 'contact',
    scopedSlots: { customRender: 'contact' }
  },
  {
    title: '操作',
    dataIndex: 'ops',
    scopedSlots: { customRender: 'ops' }
  }
]
export default {
  name: 'SalaryViewList',
  data () {
    this.columns = columns
    return {
      picStaffId: '',
      staffDetailVisible: false,
      salaryVisible: false,
      confirmLoading: false,
      staffDetailMdl: {},
      isupdate: false,
      isShowOnly: false,
      genderMap: {},
      refresh: false,
      queryParam: {},
      infoLoading: false,
      formTitle: '',
      refreshSalary: false,
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        this.infoLoading = true
        return getStaffList(requestParameters.pageNo, requestParameters.pageSize)
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.staffs
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
                description: '查询资源池列表失败，请稍后再试'
              })
              setTimeout(() => {
                this.infoLoading = false
              }, 600)
            }
          })
          .catch((error) => {
            setTimeout(() => {
              this.infoLoading = false
            }, 600)
            this.$notification.error({
              message: '查询资源池列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  methods: {
    handleSalaryList (record) {
      this.salaryVisible = true
      this.picStaffId = record.id
    },
    handleCancel () {
      if (!this.isShowOnly) {
        Modal.confirm({
          title: '取消操作',
          content: '是否确认取消操作？所做的修改将会丢失！',
          onOk: () => {
            this.staffDetailVisible = false
          },
          onCancel () {}
        })
      } else {
        this.staffDetailVisible = false
      }
    },
    handleSalaryClose () {
      this.salaryVisible = false
    },
    handleDelete (record) {
      Modal.confirm({
        title: '删除人员',
        content: '是否确认删除？该操作不可逆！',
        onOk: () => {
          this.confirmLoading = true
          deleteStaff(record.id)
            .then((response) => {
              const result = response
              if (success(result)) {
                this.$notification.success({
                  message: errorMessage(result),
                  description: '删除成功'
                })
                this.$refs.table.refresh(true)
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
    handleOk () {
      if (this.isShowOnly) {
        const form = this.$refs.staffDetailForm.form
        form.resetFields() // 清理表单数据（可不做）
        this.staffDetailVisible = false
        return
      }
      const form = this.$refs.staffDetailForm.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          if (this.isupdate) {
            updateStaff(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '修改成功'
                  })
                  const form = this.$refs.staffDetailForm.form
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.staffDetailVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '修改失败'
                  })
                }
                if (needLogin(result)) {
                  this.staffDetailVisible = false
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
            addStaff(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '添加成功'
                  })
                  const form = this.$refs.staffDetailForm.form
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.staffDetailVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '添加失败'
                  })
                }
                if (needLogin(result)) {
                  this.staffDetailVisible = false
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
      this.staffDetailVisible = true
      await this.getDetail(record)
    },
    handleAdd () {
      // const tmp = { ...this.receiptMdl }
      // tmp['receiptId'] = ''
      // tmp['authLetterFile	'] = null
      // tmp['bankFlowFile	'] = null
      this.formTitle = '新增人员'
      this.staffDetailMdl = {}
      const form = this.$refs.staffDetailForm.form
      form.resetFields() // 清理表单数据（可不做）
      this.isupdate = false
      this.staffDetailVisible = true
      this.isShowOnly = false
    },
    async handleUpdate (record) {
      await this.getDetail(record)
      this.formTitle = '修改人员详情'
      this.isupdate = true
      this.staffDetailVisible = true
      this.isShowOnly = false
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
    }
  },
  created () {
    this.getDict('gender').then((response) => {
      this.genderMap = {}
      for (const i of response) {
        this.genderMap[i.id] = i.name
      }
    })
  },
  activated () {
    this.$refs.table.refresh(true)
  },
  components: {
    STable,
    staffDetail,
    SalaryListDrawer
  },
  watch: {
    refresh: function (newVal, oldVal) {
      this.$refs.table.refresh(true)
    }
  }
}
</script>

<style>
</style>
