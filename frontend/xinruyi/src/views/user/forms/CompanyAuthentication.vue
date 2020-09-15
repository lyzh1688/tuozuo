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
        <a-form-item v-show="false" label="注册Id">
          <a-input v-decorator="['registerId', { validateTrigger: 'blur'}]" />
        </a-form-item>
        <a-form-item v-show="false" label="公司Id">
          <a-input v-decorator="['companyId', { validateTrigger: 'blur'}]" />
        </a-form-item>
        <a-form-item label="公司名称">
          <a-input
            :disabled="isShowOnly"
            v-decorator="['companyName', {rules: [{required: true, message: '请输入项目名称！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="营业执照证明" key="营业执照证明">
          <a-button
            type="text"
            v-if="model&&typeof model.businessLicense === 'string'"
            @click="()=>{jumpToFile(model.businessLicense)}"
          >已上传的文件</a-button>
          <a-upload
            :beforeUpload="beforeUpload"
            v-show="!isShowOnly"
            name="businessLicense"
            :file-list="businessLicenseList"
            list-type="picture-card"
            v-decorator="['businessLicense', {rules: [{required: true, message: '请输入营业执照证明！'}], validateTrigger: 'blur'}]"
            :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
            @change="handlebusinessLicenseChange"
          >
            <a-button v-show="!isShowOnly" :v-if="showUpload">
              <a-icon :v-if="showUpload" type="upload" />上传
            </a-button>
          </a-upload>
        </a-form-item>
        <a-form-item label="法人姓名">
          <a-input
            :disabled="isShowOnly"
            v-decorator="['bossName', {rules: [{required: true, message: '请输入法人姓名！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="法人身份证号">
          <a-input
            :disabled="isShowOnly"
            v-decorator="['bossId', {rules: [{required: true, message: '请输入法人身份证号！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="身份证正面" key="身份证正面">
          <a-button
            type="text"
            v-if="model&&typeof model.bossIdPicUp === 'string'"
            @click="()=>{jumpToFile(model.bossIdPicUp)}"
          >已上传的文件</a-button>
          <a-upload
            :beforeUpload="beforeUpload"
            v-show="!isShowOnly"
            name="bossIdPicUp"
            :file-list="bossIdPicUpList"
            list-type="picture-card"
            v-decorator="['bossIdPicUp', {rules: [{required: true, message: '请输入身份证正面！'}], validateTrigger: 'blur'}]"
            :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
            @change="handlebossIdPicUpChange"
          >
            <a-button v-show="!isShowOnly" :v-if="showUpload">
              <a-icon :v-if="showUpload" type="upload" />上传
            </a-button>
          </a-upload>
        </a-form-item>
        <a-form-item label="身份中反面" key="身份中反面">
          <a-button
            type="text"
            v-if="model&&typeof model.bossIdPicBack === 'string'"
            @click="()=>{jumpToFile(model.bossIdPicBack)}"
          >已上传的文件</a-button>
          <a-upload
            :beforeUpload="beforeUpload"
            v-show="!isShowOnly"
            name="bossIdPicBack"
            :file-list="bossIdPicBackList"
            list-type="picture-card"
            v-decorator="['bossIdPicBack', {rules: [{required: true, message: '请输入身份中反面！'}], validateTrigger: 'blur'}]"
            :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
            @change="handlebossIdPicBackChange"
          >
            <a-button v-show="!isShowOnly" :v-if="showUpload">
              <a-icon :v-if="showUpload" type="upload" />上传
            </a-button>
          </a-upload>
        </a-form-item>
        <a-form-item label="对公账户账号">
          <a-input-number
            :disabled="isShowOnly"
            :min="0"
            v-decorator="['companyAccount', {rules: [{required: true, message: '请输入对公账户账号！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="银行">
          <a-select
            :disabled="isShowOnly"
            v-decorator="['companyAccountBank', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
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
            v-decorator="['companyAccountBranchBank', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
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
        <a-form-item label="联系人姓名">
          <a-input
            :disabled="isShowOnly"
            v-decorator="['contactName', {rules: [{required: true, message: '请输入联系人姓名！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="联系方式">
          <a-input
            :disabled="isShowOnly"
            v-decorator="['contact', {rules: [{required: true, message: '请输入联系方式！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <slot name="other"></slot>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { getBankDict, getCommonDict } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
// 表单字段
const fields = [
  'registerId', // String	注册Id
  'companyId', // String	企业Id
  'companyName', // String	企业名称
  'businessLicense', //	String	营业执照
  'bossName', //	String	法人姓名
  'bossId', //	法人身份证号
  'bossIdPicUp', //	String	法人身份证正面照片
  'bossIdPicBack', //	String	法人身份证背面照片
  'companyAccount', //	String	企业对公账号
  'companyAccountBank', //	String	企业对公账号所在银行
  'companyAccountBranchBank', //	String	企业对公账号所在银行
  'contactName', //	String	联系人姓名
  'contact', //	String	联系人电话
  'status',
  'remark' //	String	备注
]
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
      // city: citiesHepler[0].label,
      showUpload: true,
      businessLicenseList: [],
      bossIdPicUpList: [],
      bossIdPicBackList: [],
      provinceList: [],
      bankAreaList: [],
      bankList: [],
      cityList: [],
      districtList: [],
      cityIndex: 0,
      areaIndex: 0,
      industryTypeList: [],
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
    // 防止表单未注册
    fields.forEach((v) => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
      if (this.model.companyAccountBank !== '') {
        fetch(this.model.companyAccountBank, this.model.bankBranchName, (data) => (this.bankAreaList = data))
      }
    })
  },
  watch: {
    clearUpload: function (newVal, oldVal) {
      this.businessLicenseList = []
      this.bossIdPicUpList = []
      this.bossIdPicBackList = []
    }
  },
  methods: {
     handleCustomSearch (value) {
      fetch(this.form.getFieldValue('companyAccountBank'), value, (data) => (this.bankAreaList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      this.form.accntBank = value
      fetch(this.form.getFieldValue('companyAccountBank'), value, (data) => (this.bankAreaList = data))
    },
    jumpToFile (link) {
      window.open(link, '_blank')
    },
    handlebusinessLicenseChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.businessLicenseList = fileList
      //   this.fileList = [info]
    },
    handlebossIdPicUpChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.bossIdPicUpList = fileList
      //   this.fileList = [info]
    },
    handlebossIdPicBackChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.bossIdPicBackList = fileList
      //   this.fileList = [info]
    },
    beforeUpload (file) {
      return new Promise((resolve, reject) => {
        console.log('beforeUpload', file)
        if (file.size / (1024 * 1024) > 30) {
          this.$notification.error({
            message: '文件大小不能超过30M'
          })
          reject(new Error('文件大小不能超过30M'))
        }
        resolve()
      })
    },
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
   handleChane (index) {
      fetch(index, '', (data) => (this.bankAreaList = data))
      if (this.model.companyAccountBank !== index) {
        this.form.setFieldsValue({ companyAccountBranchBank: '' })
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
