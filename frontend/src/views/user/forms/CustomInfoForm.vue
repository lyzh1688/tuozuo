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
        <a-form-item
          v-show="!(model && model.customId!=undefined&&model.customId!=='')"
          label="客户账户"
        >
          <a-input
            v-decorator="['customId', {rules: [{required: true, message: '请输入客户账号！'},
                                               { validator: checkData, trigger: 'blur'}
            ], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="客户名称">
          <a-input
            v-decorator="['customName', {rules: [{required: true, message: '请输入客户名称！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="客户密码">
          <a-input-password
            :visibilityToggle="!(model && model.customId!=undefined&&model.customId!=='')"
            type="lock"
            v-decorator="['customPswd', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="所在城市">
          <a-select
            v-decorator="['province', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            style="width: 120px"
            @change="handleChane"
          >
            <a-select-option
              v-for="province in provinceList"
              :key="province.areaCode"
            >{{ province.areaName }}</a-select-option>
          </a-select>
          <a-select
            v-decorator="['city', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            style="width: 120px"
            @change="handleChane2"
          >
            <a-select-option
              v-for="cityItem in cityList"
              :key="cityItem.areaCode"
            >{{ cityItem.areaName }}</a-select-option>
          </a-select>
          <a-select
            v-decorator="['district', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            style="width: 120px"
          >
            <a-select-option
              v-for="cityItem in districtList"
              :key="cityItem.areaCode"
            >{{ cityItem.areaName }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="客户类型">
          <a-select
            v-decorator="['customType', {rules: [{required: true, message: '请选择用户类型！'}], validateTrigger: 'blur'}]"
            placeholder="请选择"
          >
            <a-select-option
              v-for="customtypeItem in customTypemap"
              :key="customtypeItem.id"
            >{{ customtypeItem.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="客户联系方式">
          <a-input
            v-decorator="['customContact', {rules: [{required: true, message: '请输入至少五个字符的规则描述！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="已缴纳服务费用">
          <a-select
            v-decorator="['hasPaid', {rules: [{required: true, message: '请选择支付状态！'}], validateTrigger: 'blur'}]"
            placeholder="请选择"
          >
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
import { getAreaCode } from '@/api/company'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
// 表单字段
const fields = [
  'customId',
  'customName',
  'customPswd',
  'province',
  'city',
  'district',
  'customType',
  'customContact',
  'hasPaid'
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
      // city: citiesHepler[0].label,
      provinceList: [],
      cityList: [],
      districtList: [],
      cityIndex: 0,
      areaIndex: 0,
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this)
    }
  },
  created () {
    console.log('custom modal created')
 this.getAreaCode('province', '').then((response) => {
      this.provinceList = response
    })
    // 防止表单未注册
    fields.forEach(v => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
      if (this.model.province && this.model.province !== '') {
        this.handleChane(this.model.province)
      }
      if (this.model.city && this.model.city !== '') {
        this.handleChane2(this.model.city)
      }
    })
  },
  methods: {
      checkData (rule, value, callback) {
      if (value) {
        if (/[\u4E00-\u9FA5]/g.test(value) || /^[0-9]+.?[0-9]*$/g.test(value)) {
          callback(new Error('只可输入字母、不能输入汉字!'))
        } else {
          callback()
        }
      }
      callback()
    },
     getAreaCode (level, code) {
      return new Promise((resolve, reject) => {
        getAreaCode(level, code).then((Response) => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            resolve(result.data)
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询区域信息失败'
            })
          }
           if (needLogin(result)) {
            this.visible = false
          }
        })
      })
    },
       async handleChane (index) {
      await this.getAreaCode('city', index).then((response) => {
        this.cityList = response
      })
      if (this.model.province !== index) {
 this.form.setFieldsValue({ city: '' })
       this.form.setFieldsValue({ district: '' })
      }
    },
    async handleChane2 (index) {
      await this.getAreaCode('district', index).then((response) => {
        this.districtList = response
      })
       if (this.model.city !== index) {
      this.form.setFieldsValue({ district: '' })
      }
    }
  }
}
</script>
