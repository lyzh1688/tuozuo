<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <myinfo :customId="customId" :refresh="refresh" @mystatus="handlemystatus">
        <template v-slot:freshButton style="padding:5px;">
          <a-button type="primary" size="small" @click="refresh=!refresh">刷新</a-button>
          <a-divider type="vertical" />
          <a-button type="primary" size="small" @click="handleAdd">认证申请</a-button>
          <a-divider type="vertical" />
          <a-button type="primary" size="small" @click="handleUpdate">修改申请</a-button>
          <a-divider type="vertical" />
          <a-button type="primary" size="small" @click="fetchCompanyDetail">详情</a-button>
        </template>
      </myinfo>
      <companyauthentication
        :title="formTitle"
        ref="companyauthenticationform"
        :clearUpload="clearUpload"
        :visible="companyVisible"
        :loading="confirmLoading"
        :model="companyMdl"
        :isUpdate="isupdate"
        :isShowOnly="isShowOnly"
        @cancel="handleCancel"
        @ok="handleContractOk"
      >
        <template v-slot:other v-if="isShowOnly">
          <a-form-item label="备注">
            <a-textarea
              :disabled="isShowOnly"
              v-decorator="['remark', { validateTrigger: 'blur'}]"
            />
          </a-form-item>
        </template>
      </companyauthentication>
      <contract :visible="contractVisible" @cancel="handleContractCancel" @ok="handleOk" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { Modal } from 'ant-design-vue'
import myinfo from './components/MyInfoComponent'
import { getCompanyDetail, addCompanyAuthentication, updateCompany } from '@/api/company'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { mapState } from 'vuex'
import companyauthentication from './forms/CompanyAuthentication'
import contract from './components/contract'
export default {
  name: 'MyInfo',
  components: {
    myinfo,
    companyauthentication,
    contract
  },
  data: function () {
    return {
      customId: '',
      refresh: false,
      status: '',
      registerId: '',
      companyId: '',
      formTitle: '',
      contractVisible: false,
      clearUpload: false,
      companyVisible: false,
      confirmLoading: false,
      companyMdl: {},
      isupdate: false,
      isShowOnly: false
    }
  },
  computed: {
    ...mapState({
      username: (state) => state.user.name
    })
  },
  methods: {
    handlemystatus (value) {
      this.status = value.authStatus
      this.registerId = value.registerId
      this.companyId = value.companyId
    },
    fetchCompanyDetail () {
      this.formTitle = '公司详情'
      this.isShowOnly = true
      this.confirmLoading = true
      getCompanyDetail(this.companyId)
        .then((response) => {
          const result = response
          if (success(result)) {
            this.companyMdl = {
              ...result.data,
              registerId: this.registerId,
              companyId: this.companyId
            }
            this.companyVisible = true
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '获取公司详情失败'
            })
          }
        })
        .catch((error) => {
          this.$notification.error({
            message: '获取公司详情失败',
            description: error
          })
          this.confirmLoading = false
        })
    },
    handleContractOk () {
      const form = this.$refs.companyauthenticationform.form
      form.validateFields((errors, values) => {
        if (!errors) {
          if (this.isupdate || this.isShowOnly) {
            this.handleOk()
          } else {
            this.contractVisible = true
          }
        }
      })
    },
    handleContractCancel () {
      this.contractVisible = false
    },
    handleOk () {
      if (this.isShowOnly) {
        const form = this.$refs.companyauthenticationform.form
        this.clearUpload = !this.clearUpload
        form.resetFields() // 清理表单数据（可不做）
        this.companyVisible = false
        return
      }
      const form = this.$refs.companyauthenticationform.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          if (this.isupdate) {
            updateCompany(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '修改成功'
                  })
                  const form = this.$refs.companyauthenticationform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.companyVisible = false
                  this.confirmLoading = false
                  this.refresh = !this.refresh
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '修改失败'
                  })
                }
                if (needLogin(result)) {
                  this.companyVisible = false
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
            addCompanyAuthentication(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '新增成功'
                  })
                  const form = this.$refs.companyauthenticationform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.companyVisible = false
                  this.confirmLoading = false
                  this.refresh = !this.refresh
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '新增失败。请稍后再试'
                  })
                }
                if (needLogin(result)) {
                  this.companyVisible = false
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
      this.formTitle = '新建认证申请'
      this.companyMdl = {
        registerId: this.registerId,
        companyId: this.companyId
      }
      const form = this.$refs.companyauthenticationform.form
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
      this.isupdate = false
      this.isShowOnly = false
      this.companyVisible = true
    },
    async handleUpdate (record) {
      await this.fetchCompanyDetail(record)
      this.formTitle = '修改认证申请'
      this.isupdate = true
      this.companyVisible = true
      this.isShowOnly = false
    },
    handleCancel () {
      if (!this.isShowOnly) {
        Modal.confirm({
          title: '取消操作',
          content: '是否确认取消操作？所做的修改将会丢失！',
          onOk: () => {
            this.companyVisible = false
          },
          onCancel () {}
        })
      } else {
        this.companyVisible = false
      }
    }
  },
  activated () {
    this.refresh = !this.refresh
  }
}
</script>
