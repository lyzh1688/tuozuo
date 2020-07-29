<template>
  <a-modal
    :title="model && model.contractId!=undefined&&model.contractId!==''?'修改合同':'新建合同申请'"
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
            <a-form-item v-show="false" label="合同Id" key="合同Id">
              <a-input
                :disabled="isShowOnly"
                v-decorator="['contractId', { validateTrigger: 'blur'}]"
              />
            </a-form-item>
            <a-form-item label="合同名称" key="合同名称">
              <a-input
                :disabled="isShowOnly"
                v-decorator="['contractName', {rules: [{required: true, message: '请输入合同名称！'}], validateTrigger: 'blur'}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="甲方名称" key="甲方名称">
              <a-input
                :disabled="isShowOnly"
                v-decorator="['companyPartyAName', {rules: [{required: true, message: '请输入甲方名称！'}], validateTrigger: 'blur'}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="乙方名称" key="乙方名称">
              <a-select
                :disabled="isShowOnly"
                show-search
                v-decorator="['companyPartyBName', {rules: [{required: true, message: '请选择乙方名称！'}], validateTrigger: 'blur'}]"
                placeholder="input search text"
                style="width: 200px"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleCustomSearch"
                @change="handleCustomChange"
              >
                <a-select-option v-for="d in fuzzyCompanyList" :key="d.value+':'+d.text">{{ d.text }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="开票金额类型" key="开票金额类型">
              <a-select
                :disabled="isShowOnly"
                style="width:200px;"
                v-decorator="['invoicePattern', {rules: [{required: true, message: '请输入开票金额类型！'}], validateTrigger: 'blur'}]"
                placeholder="请选择"
              >
                <a-select-option
                  v-for=" taxItem in invoicePatternList"
                  :key="taxItem.id"
                >{{ taxItem.name }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="合同总金额(元)" key="合同总金额">
              <a-input-number
                :min="0"
                style="width:200px;"
                :disabled="isShowOnly"
                v-decorator="['contractAmount', {rules: [{required: true, message: '请输入公司名称！'}], validateTrigger: 'blur'}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="合同文件" key="合同文件">
              <a-button
                type="text"
                v-if="model&&typeof model.contractFile === 'string'"
                @click="()=>{jumpToFile(model.contractFile)}"
              >已上传的文件</a-button>
              <a-upload
                v-show="!isShowOnly"
                name="contractFile"
                :file-list="contractFileList"
                list-type="picture-card"
                v-decorator="['contractFile', {rules: [{required: true, message: '请输入合同文件！'}], validateTrigger: 'blur'}]"
                :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
                @change="handlecontractFileChange"
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
// 表单字段
const fields = [
  'contractId',
  'contractName',
  'companyPartyAName',
  'companyPartyBName',
  'invoicePattern',
  'contractAmount',
  'contractFile',
  'contractStatus',
  'remark'
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
      showUpload: true,
      invoicePatternList: [],
      taxList: [],
      contractFileList: [],
      // city: citiesHepler[0].label,
      cityIndex: 0,
      areaIndex: 0,
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this)
    }
  },
  created () {
    this.getDict('invoicePattern').then((response) => {
      this.invoicePatternList = response
    })
    console.log('custom modal created')

    // 防止表单未注册
    fields.forEach((v) => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
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
    handlecontractFileChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.contractFileList = fileList
      console.log(info.file.originFileObj)
      //   this.fileList = [info]
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
      this.contractFileList = []
    }
  }
}
</script>
