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
        <a-form-item v-show="false" label="公司id">
          <a-input v-decorator="['companyId', { validateTrigger: 'blur' }]" />
        </a-form-item>
        <a-form-item v-show="false" label="工资id">
          <a-input v-decorator="['paymentId', { validateTrigger: 'blur' }]" />
        </a-form-item>
        <a-form-item label="公司名称" v-show="isUpdate">
          <a-input :disabled="true" v-decorator="['companyName']" />
        </a-form-item>
        <a-form-item label="项目名称">
          <a-select
            :disabled="isUpdate"
            show-search
            placeholder="请输入项目名称"
            style="width: 200px"
            :default-active-first-option="false"
            :show-arrow="false"
            :filter-option="false"
            :not-found-content="null"
            v-decorator="[
              'projectId',
              { rules: [{ required: true, message: '请输入项目名称！' }], validateTrigger: 'blur' },
            ]"
            @search="handleProjectSearch"
            @change="handleProjectChange"
          >
            <a-select-option v-for="d in fuzzyProjectList" :key="d.value">{{ d.text }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="总金额">
          <a-input-number
            :disabled="isUpdate && model.releaseStatus !== '5'"
            :min="0"
            v-decorator="[
              'amount',
              { rules: [{ required: true, message: '请输入预算金额！' }], validateTrigger: 'blur' },
            ]"
          />
        </a-form-item>
        <a-form-item label="发放月份">
          <a-month-picker
            :disabled="isUpdate && model.releaseStatus !== '5'"
            valueFormat="YYYYMM"
            format="YYYY-MM"
            v-decorator="[
              'month',
              { rules: [{ required: true, message: '请输入项目开始时间！' }], validateTrigger: ['change', 'blur'] },
            ]"
          />
        </a-form-item>
        <a-form-item label="实际发放日期" v-if="isUpdate && model.releaseStatus !== '5'">
          <a-date-picker
            :disabled="isShowOnly"
            valueFormat="YYYYMMDD"
            format="YYYY-MM-DD"
            v-decorator="[
              'payDate',
              { rules: [{ required: true, message: '请输入工资发放日期时间！' }], validateTrigger: ['change', 'blur'] },
            ]"
          />
        </a-form-item>
        <a-form-item label="凭证文件" key="凭证文件">
          <a-button
            type="text"
            v-if="model && typeof model.voucher === 'string'"
            @click="
              () => {
                jumpToFile(model.voucher)
              }
            "
          >已上传的文件</a-button
          >
          <a-upload
            :beforeUpload="beforeUpload"
            v-show="!isShowOnly"
            name="projectFile"
            :file-list="voucherFileList"
            list-type="picture-card"
            v-decorator="[
              'voucher',
              { rules: [{ required: true, message: '请输入项目文件！' }], validateTrigger: 'blur' },
            ]"
            :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
            @change="handlevoucherFileChange"
          >
            <a-button v-show="!isShowOnly" :v-if="showUpload">
              <a-icon :v-if="showUpload" type="upload" />上传
            </a-button>
          </a-upload>
        </a-form-item>
        <slot name="other"></slot>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
// import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { fuzzyQueryProject } from '@/api/projects'
// 表单字段
const fields = [
  'paymentId',
  'projectName',
  'projectId',
  'companyId',
  'companyName',
  'amount',
  'month',
  'payDate',
  'voucher'
]
let timeout
let currentValue

function fetch (value, callback) {
  if (timeout) {
    clearTimeout(timeout)
    timeout = null
  }
  currentValue = value

  function fake () {
    fuzzyQueryProject(value, 20, false).then((d) => {
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
      voucherFileList: [],
      fuzzyProjectList: [],
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
  watch: {
    clearUpload: function (newVal, oldVal) {
      this.voucherFileList = []
    },
    visible (value) {
      if (value) {
        fetch(this.model.companyName ? this.model.companyName : '', (data) => (this.fuzzyProjectList = data))
      }
    }
  },
  created () {
    console.log('custom modal created')
    fetch(this.model.companyName ? this.model.companyName : '', (data) => (this.fuzzyProjectList = data))
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
    handleProjectSearch (value) {
      fetch(value, (data) => (this.fuzzyProjectList = data))
    },
    handleProjectChange (value) {
      // console.log(value)
      this.form.setFieldsValue({ projectId: value })
      fetch(value, (data) => (this.fuzzyProjectList = data))
    },
    handlevoucherFileChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.voucherFileList = fileList
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
    }
  }
}
</script>
