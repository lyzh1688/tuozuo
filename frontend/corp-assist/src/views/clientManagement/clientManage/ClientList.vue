<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-skeleton :loading="infoLoading" active title>
        <a-row>
          <a-col :sm="8" :xs="24">
            <span style="padding:5px;">客户名称</span>
            <a-input
              style="width:80%;"
              size="small"
              v-model="queryParam.clientName"
              placeholder="客户名称"
              @keypress.enter="refresh = !refresh"
            />
          </a-col>
          <a-col :span="8">
            <span style="padding:5px;">标签名称</span>
            <a-input
              style="width:80%;"
              size="small"
              v-model="queryParam.tagName"
              placeholder="标签名称"
              @keypress.enter="refresh = !refresh"
            />
          </a-col>
          <a-button type="dashed" size="small" @click="handleAdd()">新增客户</a-button>
          <a-button type="primary" size="small" @click="refresh = !refresh">查询</a-button>
        </a-row>
      </a-skeleton>
    </a-card>
    <a-card style="margin-top: 24px" :bordered="false" title="客户列表">
      <s-table
        ref="table"
        size="default"
        rowKey="clientId"
        :pageSize="20"
        :columns="columns"
        :data="loadData"
        showPagination="true"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="clientName" slot-scope="text, record">
          <a-button type=" dashed" size="small" @click="handleDetail(record)">{{ text }}</a-button>
        </span>
        <span slot="clientGender" slot-scope="text">{{ genderMap[text] }}</span>
        <span slot="tags" slot-scope="text, record">{{
          record.tags == null || record.tags == '' ? '' : record.tags.map(v => v.tagName).join(',')
        }}</span>
        <span slot="ops" slot-scope="text, record">
          <a-button type="primary" size="small" @click="handleUpdate(record)">修改</a-button>
          <a-divider type="vertical" />
          <a-button type="primary" size="small" @click="handleUpdateTag(record)">标签</a-button>
          <a-divider type="vertical" />
          <a-button type="danger" size="small" @click="handleDelete(record)">删除</a-button>
        </span>
      </s-table>
    </a-card>
    <clientform
      :title="formTitle"
      ref="tagForm"
      :visible="showMdl"
      :loading="confirmLoading"
      :model="clientMdl"
      :isUpdate="isupdate"
      :isShowOnly="isShowOnly"
      @cancel="handleCancel"
      @ok="handleOk"
    >
      <template v-slot:other v-if="isShowOnly">

        <a-form-item label="标签">
          <a-tag v-for="item in clientMdl.tags" :key="item.tagId">{{ item.tagName }}</a-tag>
        </a-form-item>
        <a-form-item label="操作人">
          <a-textarea
            :disabled="isShowOnly"
            v-decorator="['operatorId', { validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="操作人id">
          <a-textarea
            :disabled="isShowOnly"
            v-decorator="['operator', { validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="来源">
          <a-textarea
            :disabled="isShowOnly"
            v-decorator="['source', { validateTrigger: 'blur'}]"
          />
        </a-form-item>
      </template>
    </clientform>
    <modifytagdrawer
      ref="modifytagdrawer"
      :visible="drawerVisible"
      :projectId="queryParam.projectID"
      :staffInProjectList="inProjectMap"
      @onclose="drawerVisible = false"
      @refreshlist="refresh=!refresh"
    ></modifytagdrawer>
  </page-header-wrapper>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { STable } from '@/components'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { getClients, deleteClient, addClient, modifyClient, getClientDetail } from '@/api/client'
import clientform from './form/ClientForm'
import modifytagdrawer from './form/modifyTagDrawer'
import { mapState } from 'vuex'
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '客户名称',
    dataIndex: 'clientName',
    scopedSlots: { customRender: 'clientName' }
  },
  {
    title: '公司名称',
    dataIndex: 'corpName',
    scopedSlots: { customRender: 'corpName' }
  },
  {
    title: '客户电话',
    dataIndex: 'clientNumber',
    scopedSlots: { customRender: 'clientNumber' }
  },
  {
    title: '客户性别',
    dataIndex: 'clientGender',
    scopedSlots: { customRender: 'clientGender' }
  },
  {
    title: '标签',
    dataIndex: 'tags',
    scopedSlots: { customRender: 'tags' }
  },
  {
    title: '来源',
    dataIndex: 'source',
    scopedSlots: { customRender: 'source' }
  },
  {
    title: '操作',
    dataIndex: 'ops',
    scopedSlots: { customRender: 'ops' }
  }
]
export default {
  name: 'ClientList',
  data () {
    this.columns = columns
    return {
      confirmLoading: false,
      showMdl: false,
      clientMdl: {},
      isupdate: false,
      isShowOnly: false,
      refresh: false,
      drawerVisible: false,
      queryParam: {
        tagName: '',
        clientName: ''
      },
      genderMap: {
        1: '男',
        2: '女'
      },
      infoLoading: false,
      formTitle: '',
      loadData: parameter => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        this.infoLoading = true
        return getClients(
          requestParameters.tagName,
          requestParameters.clientName,
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then(Response => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.clients
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
                description: '查询标签列表失败，请稍后再试'
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
              message: '查询标签列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  computed: {
    ...mapState({
      username: state => state.user.name
    })
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
            this.showMdl = false
          },
          onCancel () {}
        })
      } else {
        this.showMdl = false
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
          deleteClient(record.clientId)
            .then(response => {
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
                this.showMdl = false
                this.confirmLoading = false
              }
            })
            .catch(error => {
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
        const form = this.$refs.tagForm.form
        form.resetFields() // 清理表单数据（可不做）
        this.showMdl = false
        return
      }
      const form = this.$refs.tagForm.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          if (this.isupdate) {
            modifyClient(values)
              .then(response => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '修改成功'
                  })
                  const form = this.$refs.tagForm.form
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.showMdl = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '修改失败'
                  })
                }
                if (needLogin(result)) {
                  this.showMdl = false
                  this.confirmLoading = false
                }
              })
              .catch(error => {
                this.$notification.error({
                  message: '修改失败。请稍后再试',
                  description: error
                })
                this.confirmLoading = false
              })
          } else {
            addClient(values)
              .then(response => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '添加成功'
                  })
                  const form = this.$refs.tagForm.form
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.showMdl = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '添加失败'
                  })
                }
                if (needLogin(result)) {
                  this.showMdl = false
                  this.confirmLoading = false
                }
              })
              .catch(error => {
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
      this.showMdl = true
      await this.getDetail(record)
    },
    handleAdd () {
      // const tmp = { ...this.receiptMdl }
      // tmp['receiptId'] = ''
      // tmp['authLetterFile	'] = null
      // tmp['bankFlowFile	'] = null
      this.formTitle = '新增客户'
      this.clientMdl = {
        operatorId: this.username,
        operator: this.username,
        source: 'PC'
      }
      const form = this.$refs.tagForm.form
      form.resetFields() // 清理表单数据（可不做）
      this.isupdate = false
      this.showMdl = true
      this.isShowOnly = false
    },
    async handleUpdate (record) {
      await this.getDetail(record)
      this.formTitle = '修改客户'
      this.isupdate = true
      this.showMdl = true
      this.isShowOnly = false
    },
    handleUpdateTag (record) {
      this.isupdate = true
      this.drawerVisible = true
      this.isShowOnly = false
    },
    getDetail (record) {
      this.formTitle = '客户详情'
      this.isShowOnly = true
      this.confirmLoading = true
      getClientDetail(record.clientId)
        .then(response => {
          const result = response
          if (success(result)) {
            this.clientMdl = {
              ...result.data,
              clientId: record.clientId
            }
            if (this.isupdate) {
              this.clientMdl.operatorId = this.username
              this.clientMdl.operator = this.username
            }

            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '获取客户详情失败'
            })
          }
          if (needLogin(result)) {
            this.showMdl = false
            this.confirmLoading = false
          }
        })
        .catch(error => {
          this.$notification.error({
            message: '获取客户详情失败',
            description: error
          })
          this.confirmLoading = false
        })
    }
    // getDict (keyword) {
    //   return new Promise((resolve, reject) => {
    //     getCommonDict(keyword).then((Response) => {
    //       const result = Response
    //       // console.log('dictQuery', result)
    //       if (success(result)) {
    //         resolve(result.data)
    //       } else {
    //         this.$notification.error({
    //           message: errorMessage(result),
    //           description: '查询字典失败'
    //         })
    //       }
    //       if (needLogin(result)) {
    //         this.visible = false
    //       }
    //     })
    //   })
    // }
  },
  activated () {
    // this.getDict('gender').then((response) => {
    //   this.genderMap = {}
    //   for (const i of response) {
    //     this.genderMap[i.id] = i.name
    //   }
    // })
    this.$refs.table.refresh(true)
  },
  // activated () {

  // },
  components: {
    STable,
    clientform,
    modifytagdrawer
  },
  watch: {
    refresh: function (newVal, oldVal) {
      this.$refs.table.refresh(true)
    }
  }
}
</script>

<style></style>
