<template>
  <a-modal
    :title="title"
    :width="740"
    :visible="visible"
    :confirmLoading="loading"
    @ok="
      () => {
        $emit('ok')
      }
    "
    @cancel="
      () => {
        $emit('cancel')
      }
    "
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="false" label="客户群Id">
          <a-input v-decorator="['groupId', { validateTrigger: 'blur' }]" />
        </a-form-item>
        <a-form-item label="客户群名称">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'groupName',
              { rules: [{ required: true, message: '请输入客户名称！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item>
        <a-form-item label="群通知">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'groupNotice',
              { rules: [{ required: false, message: '请输入客户电话！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item>
        <a-form-item v-show="isShowOnly" label="操作人">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'operatorId',
              { rules: [{ required: false, message: '请输入阿里旺旺号！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item >
        <a-form-item v-show="isShowOnly" label="操作人id">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'operator',
              { rules: [{ required: false, message: '请输入阿里旺旺号！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item >
        <a-form-item v-show="isShowOnly" label="来源">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'source',
              { rules: [{ required: false, message: '请输入阿里旺旺号！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item>
        <slot name="other"></slot>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
// 表单字段
const fields = [
  'groupId', //	String	客户群ID
  'chatId', //	是	String	客户群微信ID
  'groupName', //	是	String	客户群名称
  'groupNotice', //	是	String	群通知
  'operatorId', //	否	String	创建人ID
  'operator', //	否	String	创建人
  'source', //	否	String	来源	PC/Applet
  'tags', //	否	String	来源	PC/Applet，
  'clients'
]
export default {
  props: {
    visible: {
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
    isShowOnly: {
      type: Boolean,
      default: false
    },
    isUpdate: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    clearUpload: {
      type: Boolean,
      default: false
    }
  },
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
      form: this.$form.createForm(this),
      genderList: [{
          code: '1',
          name: '男'
      }, {
          code: '2',
          name: '女'
      }]
    }
  },
  created () {
    console.log('custom modal created')
    // 防止表单未注册
    fields.forEach(v => this.form.getFieldDecorator(v))
    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
    })
  },
  watch: {},
  methods: {
        handleChane (index) {
            // console.log(index)
      this.form.setFieldsValue({ clientGender: index })
    },
      handleChane2 (index) {
      this.form.setFieldsValue({ contactGender: index })
    }
  }
}
</script>
