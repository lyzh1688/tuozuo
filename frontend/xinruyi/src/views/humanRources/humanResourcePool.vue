<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-skeleton :loading="infoLoading" active title>
        <a-row>
          <a-col :sm="8" :xs="24">
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
        :showPagination="true"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="gender" slot-scope="text">{{ genderMap[text] }}</span>
        <span slot="ops" >
          <a-button type="primary" size="small" @click="refresh=!refresh">修改</a-button>
          <a-button type="danger" size="small" @click="refresh=!refresh">删除</a-button>
          <a-button type="dashed" size="small" @click="refresh=!refresh">历史工资单</a-button>
        </span>
      </s-table>
    </a-card>
    <staffDetail
      :title="formTitle"
      ref="staffDetailForm"
      :visible="staffDetailVisible"
      :loading="confirmLoading"
      :model="staffDetailMdl"
      :isUpdate="isupdate"
      :isShowOnly="isShowOnly"
      @cancel="handleCancel"
      @ok="handleOk"
    ></staffDetail>
  </page-header-wrapper>
</template>

<script>
import { STable } from '@/components'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { getStaffList, addStaff, updateStaff } from '@/api/humanResource'
import { getCommonDict } from '@/api/dictionary'
import staffDetail from './form/staffDetail'
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
    title: '操作',
    dataIndex: 'ops',
    scopedSlots: { customRender: 'ops' }
  }
]
export default {
    name: 'HumanResourcePool',
    data () {
        this.columns = columns
        return {
            staffDetailVisible: false,
            confirmLoading: false,
            staffDetailMdl: {},
            isupdate: false,
            isShowOnly: false,
            genderMap: {},
            refresh: false,
            queryParam: {},
            infoLoading: false,
            formTitle: '',
 loadData: parameter => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        this.infoLoading = true
        return getStaffList(requestParameters.pageNo, requestParameters.pageSize)
          .then(Response => {
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
          .catch(error => {
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
         handleCancel () {
      this.staffDetailVisible = false
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
    staffDetail
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
