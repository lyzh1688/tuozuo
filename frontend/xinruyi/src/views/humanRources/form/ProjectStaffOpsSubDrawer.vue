<template>
  <a-drawer
    height="320"
    :title="drawerTitle"
    placement="top"
    :closable="false"
    :visible="subvisible"
    @close="handleCancel"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="false" label="人员id">
          <a-input
            v-decorator="['staffId', {rules: [{validateTrigger: 'blur'}
            ], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item v-show="false" label="项目id">
          <a-input
            v-decorator="['projectId', {rules: [{validateTrigger: 'blur'}
            ], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-col :md="8" :sm="8">
          <a-form-item label="工资">
            <a-input-number
              :min="0"
              v-decorator="['salary', {rules: [{required: true, message: '请输入工资！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
        </a-col>
        <a-col :md="8" :sm="8">
          <a-form-item label="入团时间">
            <a-date-picker
              :disabled="isUpdate"
              valueFormat="YYYYMMDD"
              format="YYYY-MM-DD"
              v-decorator="['enterDate', {rules: [{required: true, message: '请输入入团时间！'}], validateTrigger: ['change', 'blur']}]"
            />
          </a-form-item>
        </a-col>
        <a-col :md="8" :sm="8">
          <a-form-item label="离团时间">
            <a-date-picker
              valueFormat="YYYYMMDD"
              format="YYYY-MM-DD"
              v-decorator="['quitDate', {rules: [{required: true, message: '请输入离团时间！'}, { validator: handleQuitDate, trigger: 'blur'}], validateTrigger: ['change', 'blur']}]"
            />
          </a-form-item>
        </a-col>

      </a-form>
      <div
        :style="{
          width: '100%',
          borderTop: '1px solid #e8e8e8',
          padding: '10px 16px',
          textAlign: 'right',
          background: '#fff',
          borderRadius: '0 0 4px 4px',
        }"
      >
        <a-button style="marginRight: 8px" @click="handleCancel">取消</a-button>
        <a-button type="primary" @click="handleOk">提交</a-button>
      </div>
    </a-spin>
  </a-drawer>
</template>

<script>
import { Modal } from 'ant-design-vue'
import pick from 'lodash.pick'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { addProjectStaff, updateProjectStaff } from '@/api/humanResource'
const fields = ['staffId', 'projectId', 'salary', 'enterDate', 'quitDate']

export default {
  props: {
    subvisible: {
      type: Boolean,
      required: true
    },
    loading: {
      type: Boolean,
      default: () => false
    },
    model: {
      type: Object,
      default: () => null
    },
    drawerTitle: {
      type: String,
      default: () => ''
    },
    isUpdate: {
      type: Boolean,
      default: () => false
    }
  },
  components: {},
  data () {
    this.formLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      }
    }
    return {
      // city: citiesHepler[0].label,
      salaryList: [],
      staffListLoading: false,
      form: this.$form.createForm(this),
      staffList: []
    }
  },
  created () {
    fields.forEach((v) => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
    })
  },
  methods: {
      handleQuitDate (rule, value, callback) {
          if (new Date().getFullYear() - new Date(String(value).substring(0, 4) + '-' + String(value).substring(4, 6)).getFullYear() >= 0) {
              if (new Date().getMonth() - new Date(String(value).substring(0, 4) + '-' + String(value).substring(4, 6)).getMonth() >= 0) {
              callback(new Error('离团日期不能等于小于本月'))
              } else {
                  callback()
              }
          } else {
          callback()
          }
      },
    handleCancel () {
      Modal.confirm({
        title: '取消操作',
        content: '是否确认取消操作？所做的修改将会丢失！',
        onOk: () => {
          this.$emit('onclose')
        },
        onCancel () {}
      })
    },
    handleOk () {
      const form = this.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.loading = true
          if (this.isUpdate) {
            updateProjectStaff(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '修改成功'
                  })
                  const form = this.form
                  form.resetFields() // 清理表单数据（可不做）
                  this.$emit('onclose')
                  this.$emit('refreshlist')
                  this.loading = false
                } else {
                  this.loading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '修改失败'
                  })
                }
                if (needLogin(result)) {
                  this.$emit('onclose')
                  this.loading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '修改失败。请稍后再试',
                  description: error
                })
                this.loading = false
              })
          } else {
            addProjectStaff(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '添加成功'
                  })
                  const form = this.form
                  form.resetFields() // 清理表单数据（可不做）
                  this.$emit('onclose')
                  this.$emit('refreshlist')
                  this.loading = false
                } else {
                  this.loading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '添加失败'
                  })
                }
                if (needLogin(result)) {
                  this.$emit('onclose')
                  this.loading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '添加失败。请稍后再试',
                  description: error
                })
                this.loading = false
              })
          }
        }
      })
    }
  },
  watch: {}
}
</script>
