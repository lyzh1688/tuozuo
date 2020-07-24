<template>
  <a-modal
    :title="model && model.customId!=undefined&&model.customId!==''?'修改客户':'新建客户'"
    :width="740"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-card title="公司基本信息">
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="公司名称"
          >
            <a-input
              v-decorator="['companyName', {rules: [{required: true, message: '请输入公司名称！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="公司类型"
          >
            <a-input
              v-decorator="['companyType', {rules: [{required: true, message: '请输入公司类型！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="综合税率"
          >
            <a-input
              v-decorator="['tax', {rules: [{required: true, message: '请输入公司类型！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="邮寄地址"
          >
            <a-input
              v-decorator="['address', {rules: [{required: true, message: '请输入公司名称！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <slot name="company-extra"></slot>
        </a-card>
        <a-card title="投资人信息">
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="投资人姓名"
          >
            <a-input
              v-decorator="['bossName', {rules: [{required: true, message: '请输入投资人姓名！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="身份证号"
          >
            <a-input
              v-decorator="['bossId', {rules: [{required: true, message: '请输入身份证号！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="联系电话"
          >
            <a-input
              v-decorator="['bossContact', {rules: [{required: true, message: '请输入联系电话！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="身份证正面"
          >
            <a-input
              v-decorator="['bossIdPicUp', {rules: [{required: true, message: '请输入身份证正面！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="身份证反面面"
          >
            <a-input
              v-decorator="['bossIdPicBack', {rules: [{required: true, message: '请输入身份证反面面！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
        </a-card>
        <a-card title="财务负责人信息">
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="财务负责人姓名"
          >
            <a-input
              v-decorator="['cfoName', {rules: [{required: true, message: '请输入投资人姓名！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="身份证号"
          >
            <a-input
              v-decorator="['cfoId', {rules: [{required: true, message: '请输入身份证号！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="联系电话"
          >
            <a-input
              v-decorator="['cfoContact', {rules: [{required: true, message: '请输入联系电话！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="身份证正面"
          >
            <a-input
              v-decorator="['cfoIdPicUp', {rules: [{required: true, message: '请输入身份证正面！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
          <a-form-item
            v-show="!(model && model.customId!=undefined&&model.customId!=='')"
            label="身份证反面面"
          >
            <a-input
              v-decorator="['cfoIdPicBack', {rules: [{required: true, message: '请输入身份证反面面！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
        </a-card>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import citiesHepler from '@/utils/helper/citiesHelper'
// 表单字段
const fields = [
'companyName',
'companyType',
'tax',
'address',
'totalInvoiceNum',
'invoicedNum',
'totalInvoiceAmt',
'freeDeliveryCnt',
'companyStatus',
'includeCancel',
'beginDate',
'endDate',
'tradeFlow',
'registerArea',
'rebateTaxRate',
'bossName',
'bossId',
'bossContact',
'bossIdPicUp',
'bossIdPicBack',
'cfoName',
'cfoId',
'cfoContact',
'cfoIdPicUp',
'cfoIdPicBack'
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
      if (this.model.province && this.model.province !== '') {
        this.handleChane(this.model.province)
      }
      if (this.model.area && this.model.area !== '') {
        this.handleChane(this.model.area)
      }
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
