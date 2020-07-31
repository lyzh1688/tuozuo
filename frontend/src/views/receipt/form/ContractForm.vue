<template>
  <a-modal
    :title="model && model.receiptId!=undefined&&model.receiptId!==''?'修改开票':'新建开票申请'"
    :width="740"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout" key="CommonCompanyInfoForm">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-row>
          <a-col :span="12">
            <a-form-item v-show="false" label="开票Id" key="开票Id">
              <a-input
                :disabled="isShowOnly"
                v-decorator="['invoiceId', { validateTrigger: 'blur'}]"
              />
            </a-form-item>
            <a-form-item label="已审核合同" key="已审核合同">
              <a-select
                :disabled="isShowOnly"
                show-search
                v-decorator="['contractId', {rules: [{required: true, message: '请选择已审核合同！'}], validateTrigger: 'blur'}]"
                placeholder="input search text"
                style="width: 200px"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleCustomSearch2"
                @change="handleCustomChange2"
              >
                <a-select-option v-for="d in fuzzyContractList" :key="d.value">{{ d.text }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="公司名称" key="公司名称">
              <a-select
                :disabled="isShowOnly"
                show-search
                v-decorator="['companyId', {rules: [{required: true, message: '请选择公司名称！'}], validateTrigger: 'blur'}]"
                placeholder="input search text"
                style="width: 200px"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleCustomSearch"
                @change="handleCustomChange"
              >
                <a-select-option v-for="d in fuzzyCompanyList" :key="d.value">{{ d.text }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="发票类型" key="发票类型">
              <a-select
                :disabled="isShowOnly"
                style="width:200px;"
                v-decorator="['invoiceType', {rules: [{required: true, message: '请输入发票类型！'}], validateTrigger: 'blur'}]"
                placeholder="请选择"
              >
                <a-select-option
                  v-for=" taxItem in invoicePatternList"
                  :key="taxItem.id"
                >{{ taxItem.name }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="本次开票金额(元)" key="本次开票金额">
              <a-input-number
                :min="0"
                style="width:200px;"
                :disabled="isShowOnly"
                v-decorator="['invoiceAmount', {rules: [{required: true, message: '请输入本次开票金额！'}], validateTrigger: 'blur'}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="收款金额(元)" key="收款金额">
              <a-input-number
                :min="0"
                style="width:200px;"
                :disabled="isShowOnly"
                v-decorator="['recvAmount', {rules: [{required: true, message: '请输入收款金额！'}], validateTrigger: 'blur'}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="收款日期" key="收款日期">
              <a-date-picker
                :disabled="isShowOnly"
                valueFormat="YYYY-MM-DD"
                format="YYYY-MM-DD"
                v-decorator="['recvDate', {rules: [{required: true, message: '请输入收款日期！'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="委托书" key="委托书">
              <a-button
                type="text"
                v-if="model&&typeof model.authLetterFile === 'string'"
                @click="()=>{jumpToFile(model.authLetterFile)}"
              >已上传的文件</a-button>
              <a-upload
                :beforeUpload="beforeUpload"
                v-show="!isShowOnly"
                name="authLetterFile"
                :file-list="authLetterFileList"
                list-type="picture-card"
                v-decorator="['authLetterFile', {rules: [{required: true, message: '请输入委托书！'}], validateTrigger: 'blur'}]"
                :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
                @change="handleauthLetterFileChange"
              >
                <a-button v-show="!isShowOnly" :v-if="showUpload">
                  <a-icon :v-if="showUpload" type="upload" />上传
                </a-button>
              </a-upload>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="银行流水" key="银行流水">
              <a-button
                type="text"
                v-if="model&&typeof model.bankFlowFile === 'string'"
                @click="()=>{jumpToFile(model.bankFlowFile)}"
              >已上传的文件</a-button>
              <a-upload
                :beforeUpload="beforeUpload"
                v-show="!isShowOnly"
                name="bankFlowFile"
                :file-list="bankFlowFileList"
                list-type="picture-card"
                v-decorator="['bankFlowFile', {rules: [{required: true, message: '请输入银行流水！'}], validateTrigger: 'blur'}]"
                :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
                @change="handlebankFlowFileChange"
              >
                <a-button v-show="!isShowOnly" :v-if="showUpload">
                  <a-icon :v-if="showUpload" type="upload" />上传
                </a-button>
              </a-upload>
            </a-form-item>
          </a-col>
        </a-row>
        <slot name="extraInfo"></slot>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { dictQuery, fuzzyQueryCompany } from '@/api/company'
import { success, errorMessage } from '@/utils/helper/responseHelper'
import { fuzzyQueryContract } from '@/api/contract'
let timeout
let currentValue

function fetch (value, callback) {
  if (timeout) {
    clearTimeout(timeout)
    timeout = null
  }
  currentValue = value

  function fake () {
    fuzzyQueryCompany(value, 20, false).then((d) => {
      if (currentValue === value) {
        const result = d.data
        const data = []
        result.forEach((r) => {
          data.push({
            value: r['id'],
            text: r['name']
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

  timeout = setTimeout(fake, 300)
}
let timeout2
let currentValue2

function fetch2 (value, callback) {
  if (timeout2) {
    clearTimeout(timeout2)
    timeout2 = null
  }
  currentValue2 = value

  function fake () {
    fuzzyQueryContract('1', value, 20).then((d) => {
      if (currentValue2 === value) {
        const result = d.data
        const data = []
        result.forEach((r) => {
          data.push({
            value: r['id'],
            text: r['name']
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

  timeout2 = setTimeout(fake, 300)
}
// 表单字段
const fields = [
  'invoiceId',
  'companyId',
  'contractId',
  'invoiceType',
  'invoiceAmount',
  'recvAmount',
  'bankFlowFile',
  'authLetterFile',
  'invoiceStatus',
  'deliveryId',
  'recvDate',
  'invoiceContent',
  'remark',
  'invoiceATaxRate'
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
      default: () => {}
    },
    customTypemap: {
      type: Array,
      default: () => []
    },
    isShowOnly: {
      type: Boolean,
      default: false
    },
    isUpdate: {
      type: Boolean,
      default: false
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
        sm: { span: 10 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      }
    }
    return {
      fuzzyCompanyList: [],
      fuzzyContractList: [],
      showUpload: true,
      invoicePatternList: [],
      taxList: [],
      bankFlowFileList: [],
      authLetterFileList: [],
      // city: citiesHepler[0].label,
      cityIndex: 0,
      areaIndex: 0,
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this)
    }
  },
  created () {
    this.getDict('invoiceType').then((response) => {
      this.invoicePatternList = response
    })
    console.log('custom modal created')

    // 防止表单未注册
    fields.forEach((v) => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
      fetch2(this.model.contractName, (data) => (this.fuzzyContractList = data))
      fetch(this.model.companyName, (data) => (this.fuzzyCompanyList = data))
    })
  },
  methods: {
    jumpToFile (link) {
      window.open(link, '_blank')
    },
    handleCustomSearch (value) {
      fetch(value, (data) => (this.fuzzyCompanyList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      fetch(value, (data) => (this.fuzzyCompanyList = data))
    },
    handleCustomSearch2 (value) {
      fetch2(value, (data) => (this.fuzzyContractList = data))
    },
    handleCustomChange2 (value) {
      // console.log(value)
      fetch2(value, (data) => (this.fuzzyContractList = data))
    },
    handlebankFlowFileChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.bankFlowFileList = fileList
      console.log(info.file.originFileObj)
      //   this.fileList = [info]
    },
    handleauthLetterFileChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.authLetterFileList = fileList
      console.log(info.file.originFileObj)
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
        dictQuery(keyword).then((Response) => {
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
        })
      })
    }
  },
  watch: {
    clearUpload: function (newVal, oldVal) {
      this.bankFlowFileList = []
      this.authLetterFileList = []
    }
  }
}
</script>
