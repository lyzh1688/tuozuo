<template>
  <a-modal
    :title="model && model.id > 0?'修改客户':'新建客户'"
    :width="740"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="model && model.id > 0" label="客户账户">
          <a-input v-decorator="['customId', { initialValue: 0 }]" disabled />
        </a-form-item>
        <a-form-item label="客户名称">
          <a-input v-decorator="['customName', {rules: [{required: true, message: '请输入客户名称！'}], validateTrigger: 'blur'}]" />
        </a-form-item>
        <a-form-item label="客户密码">
          <a-input-password
            :visibilityToggle="model && model.id > 0"
            type="lock"
            v-decorator="['customPswd', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}], validateTrigger: 'blur'}]" />
        </a-form-item>
        <a-form-item label="所在城市">
          <a-select
            v-decorator="['province', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            style="width: 120px"
            @change="handleChane">
            <a-select-option v-for="province in citiesHepler" :key="province.label" >
              {{ province.label }}
            </a-select-option>
          </a-select>
          <a-select
            v-decorator="['area', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            style="width: 120px"
            @change="handleChane2">
            <a-select-option v-for="cityItem in citiesHepler[areaIndex].children" :key="cityItem.label">
              {{ cityItem.label }}
            </a-select-option>
          </a-select>
          <a-select
            v-decorator="['ctiy', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            style="width: 120px">
            <a-select-option v-for="cityItem in citiesHepler[areaIndex].children[cityIndex].children" :key="cityItem.label">
              {{ cityItem.label }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="客户类型">
          <a-select
            v-decorator="['customType', {rules: [{required: true, message: '请选择用户类型！'}], validateTrigger: 'blur'}]"
            placeholder="请选择">
            <a-select-option v-for="customtypeItem in customTypemap" :key="customtypeItem.id">
              {{ customtypeItem.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="客户联系方式">
          <a-input
            v-decorator="['customContact', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}], validateTrigger: 'blur'}]" />
        </a-form-item>
        <a-form-item label="已缴纳服务费用">
          <a-select
            v-decorator="['hasPaid', {rules: [{required: true, message: '请选择支付状态！'}], validateTrigger: 'blur'}]"
            placeholder="请选择">
            <a-select-option value="1">已支付</a-select-option>
            <a-select-option value="0">未支付</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import citiesHepler from '@/utils/helper/citiesHelper'
// 表单字段
const fields = ['description', 'id']

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
    customTypemap: {
      type: Array,
      default: () => []
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
      citiesHepler: citiesHepler,
      // city: citiesHepler[0].label,
      cityIndex: 0,
      areaIndex: 0,
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this)
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
  methods: {
handleChane (index) {
  for (const i in citiesHepler) {
    if (citiesHepler[i].label === index) {
      this.areaIndex = i
    }
  }
},
handleChane2 (index) {
  for (const i in citiesHepler[this.areaIndex].children) {
    if (citiesHepler[this.areaIndex].children[i].label === index) {
      this.cityIndex = i
    }
  }
}
    }
}
</script>
