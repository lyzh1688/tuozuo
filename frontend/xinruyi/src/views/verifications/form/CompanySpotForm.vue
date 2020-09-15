<template>
  <a-modal
    :title="title"
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
          v-show="false"
          label="注册id"
        >
          <a-input
            v-decorator="['registerId', {validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-row>
          <a-col :span="12">
            <a-form-item label="企业名称">
              <a-input
                :disabled="true"
                v-decorator="['companyName', { validateTrigger: 'blur'}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="客户账户"
            >
              <a-input
                v-decorator="['companyId', {rules: [{required: true, message: '请输入客户账号！'},
                                                    { validator: checkData, trigger: 'blur'}
                ], validateTrigger: 'blur'}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="联系方式">
              <a-input
                :disabled="true"
                v-decorator="['contact', { validateTrigger: 'blur'}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="初始密码">
              <a-input-password
                type="lock"
                v-decorator="['password', {rules: [{required: true, message: '请输入初始密码！'}], validateTrigger: 'blur'}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="所在城市">
          <span>{{ model.province }}-{{ model.city }}-{{ model.district }}</span>
        </a-form-item>
        <a-form-item label="行业类型">
          <a-select
            :disabled="true"
            v-decorator="['industryType', { validateTrigger: 'blur'}]"
            placeholder="请选择"
          >
            <a-select-option
              v-for="customtypeItem in industryTypeList"
              :key="customtypeItem.id"
            >{{ customtypeItem.name }}</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item label="审核结果">
          <a-select
            v-decorator="['result', {rules: [{required: true, message: '请选择支付状态！'}], validateTrigger: 'blur'}]"
            placeholder="请选择"
          >
            <a-select-option value="1">审核通过</a-select-option>
            <a-select-option value="0">审核未通过</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea
            v-decorator="['remark', {rules: [{required: true, message: '请输备注！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { getCommonDict } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
// 表单字段
const fields = [
  'registerId',
  'companyId',
  'companyName',
  'password',
  'remark',
  'result',
  'contact',
  'industryType',
  'province',
  'city',
  'district'
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
   title: {
      type: String,
      default: ''
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
      industryTypeList: [],
      cityIndex: 0,
      areaIndex: 0,
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this)
    }
  },
  created () {
     this.getDict('industryType').then((response) => {
      this.industryTypeList = response
      this.industryTypeMap = {}
      for (const i of response) {
        this.industryTypeMap[i.id] = i.name
      }
    })
    // 防止表单未注册
    fields.forEach(v => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
    })
  },
  methods: {
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
    },
   checkData (rule, value, callback) {
      if (value) {
        if (/[\u4E00-\u9FA5]/g.test(value) || /^[0-9]+.?[0-9]*$/g.test(value)) {
          callback(new Error('只可输入字母、不能输入汉字!'))
        } else {
          callback()
        }
      }
      callback()
    }
  }
}
</script>
