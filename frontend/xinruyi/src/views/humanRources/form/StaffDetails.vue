<template>
  <a-modal
    :title="formTitle"
    :width="740"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="false" label="人员id">
          <a-input
            v-decorator="['id', {rules: [{validateTrigger: 'blur'}
            ], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="姓名">
          <a-input
            :disabled="isShowOnly||isUpdate"
            v-decorator="['name', {rules: [{required: true, message: '请输入人员姓名！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="性别">
          <a-select
            :disabled="isShowOnly"
            v-decorator="['gender', {rules: [{required: true, message: '请选择用户性别！'}], validateTrigger: 'blur'}]"
            placeholder="请选择"
          >
            <a-select-option
              v-for="genderItem in genderList"
              :key="genderItem.id"
            >{{ genderItem.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="身份证号">
          <a-input
            :disabled="isShowOnly||isUpdate"
            v-decorator="['idNo', {rules: [{required: true, message: '请输入人员身份证号！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="银行卡号">
          <a-input
            :disabled="isShowOnly"
            v-decorator="['bankCard', {rules: [{required: true, message: '请输入人员银行卡号！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="银行">
          <a-select
            :disabled="isShowOnly"
            v-decorator="['bank', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            @change="handleChane"
          >
            <a-select-option
              v-for="bankItem in bankList"
              :key="bankItem.bankCode"
            >{{ bankItem.bankName }}</a-select-option>
          </a-select>
          <a-select
            :disabled="isShowOnly"
            show-search
            v-decorator="['accntBank', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            placeholder="请输入支行名称"
            style="width: 200px"
            :default-active-first-option="false"
            :show-arrow="false"
            :filter-option="false"
            :not-found-content="null"
            @search="handleCustomSearch"
            @change="handleCustomChange"
          >
            <a-select-option v-for="d in bankAreaList" :key="d.value">{{ d.text }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { getBankDict, getCommonDict } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
// 表单字段
const fields = ['id', 'name', 'idNo', 'gender', 'bankCard', 'bank', 'accntBank']
let timeout
let currentValue

function fetch (code, value, callback) {
  if (timeout) {
    clearTimeout(timeout)
    timeout = null
  }
  currentValue = value

  function fake () {
    if (code !== '') {
      getBankDict(code, value, 20).then((d) => {
        if (currentValue === value) {
          const result = d.data
          const data = []
          result.forEach((r) => {
            data.push({
              value: r['bankCode'],
              text: r['bankName']
            })
          })
          data.push({
            value: '',
            text: ''
          })
          callback(data)
        }
      })
    }
  }

  timeout = setTimeout(fake, 300)
}
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
    formTitle: {
      type: String,
      default: () => ''
    },
    isShowOnly: {
      type: Boolean,
      default: false
    },
    isUpdate: {
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
      // city: citiesHepler[0].label,
      bankList: [],
      genderList: [],
      bankAreaList: [],
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this)
    }
  },
  created () {
    console.log('custom modal created')
    this.getBankDict().then((response) => {
      this.bankList = response
    })
    this.getGenderDict('').then((response) => {
      this.genderList = response
    })
    // 防止表单未注册
    fields.forEach((v) => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
      if (this.model.bank !== '') {
        fetch(this.model.bank, this.model.bankBranchName, (data) => (this.bankAreaList = data))
      }
    })
  },
  methods: {
    handleCustomSearch (value) {
      fetch(this.form.getFieldValue('bank'), value, (data) => (this.bankAreaList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      this.form.accntBank = value
      fetch(this.form.getFieldValue('bank'), value, (data) => (this.bankAreaList = data))
    },
    getBankDict () {
      return new Promise((resolve, reject) => {
        getBankDict('', '', '').then((Response) => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            resolve(result.data)
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询字典信息失败'
            })
          }
          if (needLogin(result)) {
            this.visible = false
          }
        })
      })
    },
    getGenderDict () {
      return new Promise((resolve, reject) => {
        getCommonDict('gender').then((Response) => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            resolve(result.data)
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询字典信息失败'
            })
          }
          if (needLogin(result)) {
            this.visible = false
          }
        })
      })
    },
    handleChane (index) {
      fetch(index, '', (data) => (this.bankAreaList = data))
      if (this.model.bank !== index) {
        this.form.setFieldsValue({ accntBank: '' })
      }
    },
    handleChane2 (index) {
      //   await this.getBankDict(index).then((response) => {
      //     this.bankAreaList = response
      //   })
    }
  }
}
</script>
