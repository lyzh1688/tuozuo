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
        <a-form-item v-show="false" label="客户Id">
          <a-input v-decorator="['clientId', { validateTrigger: 'blur' }]" />
        </a-form-item>
        <a-form-item label="公司名称">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'corpId',
              { rules: [{ required: false, message: '请输入公司名称！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item>
        <a-form-item label="客户名称">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'clientName',
              { rules: [{ required: true, message: '请输入客户名称！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item>
        <a-form-item label="客户电话">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'clientNumber',
              { rules: [{ required: true, message: '请输入客户电话！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item>
        <a-form-item label="客户性别">
          <a-select
            :disabled="isShowOnly"
            v-decorator="['clientGender', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            @change="handleChane"
          >
            <a-select-option
              v-for="item in genderList"
              :key="item.code"
            >{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="联系人名称">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'contact',
              { rules: [{ required: true, message: '请输入联系人名称！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item>
        <a-form-item label="联系人电话">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'contactNumber',
              { rules: [{ required: true, message: '请输入联系人电话！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item>
        <a-form-item label="联系人性别">
          <a-select
            :disabled="isShowOnly"
            v-decorator="['contactGender', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            @change="handleChane2"
          >
            <a-select-option
              v-for="item in genderList"
              :key="item.code"
            >{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="阿里旺旺号">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'alitalk',
              { rules: [{ required: false, message: '请输入阿里旺旺号！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item >
        <a-form-item v-show="false" label="操作人">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'operatorId',
              { rules: [{ required: false, message: '请输入阿里旺旺号！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item >
        <a-form-item v-show="false" label="操作人id">
          <a-input
            :disabled="isShowOnly"
            v-decorator="[
              'operator',
              { rules: [{ required: false, message: '请输入阿里旺旺号！' }], validateTrigger: 'blur' }
            ]"
          />
        </a-form-item >
        <a-form-item v-show="false" label="来源">
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
  'clientId', //	String	客户ID
  'userId', //	是	String	客户微信ID
  'corpId', //	是	String	企业ID	关联企业
  'clientName', //	是	String	客户名称
  'clientNumber', //	是	String	客户电话
  'clientGender', //	是	String	客户性别
  'contact', //	否	String	联系人姓名
  'contactNumber', //	否	String	联系人电话
  'contactGender', //	否	String	联系人性别
  'alitalk', //	否	String	阿里旺旺号
  'operatorId', //	否	String	创建人ID
  'operator', //	否	String	创建人
  'source', //	否	String	来源	PC/Applet
  'tags' //	否	String	来源	PC/Applet
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
