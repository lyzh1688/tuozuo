<template>
  <a-modal
    title="余额变动"
    :width="740"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <a-form-item disabled label="客户账户">
          <a-input
            disabled
            v-decorator="['customId', {rules: [{required: true, message: '请输入客户账号！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="客户名称">
          <a-input
            disabled
            v-decorator="['customName', {rules: [{required: true, message: '请输入客户名称！'}], validateTrigger: 'blur'} ]"
          />
        </a-form-item>
        <a-form-item label="所在城市">
          <a-input
            disabled
            v-decorator="['province', {rules: [{required: true}], validateTrigger: 'blur'} ]"
          />
        </a-form-item>
        <a-form-item label="客户类型">
          <a-select
            disabled
            v-decorator="['customType', {rules: [{required: true, message: '请选择用户类型！'}], validateTrigger: 'blur'}]"
            placeholder="请选择"
          >
            <a-select-option
              v-for="customtypeItem in customTypeList"
              :key="customtypeItem.id"
            >{{ customtypeItem.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="操作类型">
          <a-select
            v-decorator="['event', {rules: [{required: true, message: '请选择操作类型！'}], validateTrigger: 'blur'}]"
            placeholder="请选择"
          >
            <a-select-option v-for="eventItem in eventList" :key="eventItem.id">{{ eventItem.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="金额">
          <a-input-number
            v-decorator="['amount', {rules: [{required: true, message: '请输入金额！'}], validateTrigger: 'blur'} ]"
          />
        </a-form-item>
        <a-form-item label="凭证文件">
          <a-upload
            name="tradeSnapshot"
            :file-list="fileList"
            list-type="picture-card"
            v-decorator="['tradeSnapshot', {rules: [{required: true, message: '请上传凭证！'}], validateTrigger: 'blur'} ]"
            :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
            @change="handleChange"
          >
            <a-button :v-if="showUpload">
              <a-icon :v-if="showUpload" type="upload" />上传
            </a-button>
            <!-- <img v-if="imageUrl" :src="imageUrl" alt="avatar" />
            <div v-else>
              <a-icon :type="loading ? 'loading' : 'plus'" />
              <div class="ant-upload-text">
                Upload
              </div>
            </div>-->
          </a-upload>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { dictQuery } from '@/api/company'
import { success, errorMessage } from '@/utils/helper/responseHelper'
// 表单字段
const fields = ['customId', 'customName', 'province', 'customType', 'event', 'amount', 'city', 'tradeSnapshot']

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
    customTypeList: {
      type: Array,
      default: () => []
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
      showUpload: true,
      fileList: [],
      eventList: [],
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
    this.getEventDict()
    // 防止表单未注册
    fields.forEach(v => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
    })
  },
  methods: {
    customRequest (info) {
      console.log(info)
      return info
    },
    handleChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.fileList = fileList
      console.log(info.file.originFileObj)
      //   this.fileList = [info]
    },
    getEventDict () {
      dictQuery('event')
        .then(Response => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            this.eventList = result.data
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '事件字典失败'
            })
          }
          setTimeout(() => {
            this.infoLoading = false
          }, 600)
        })
        .catch(error => {
          this.infoLoading = false
          this.$notification.error({
            message: '事件字典失败',
            description: error
          })
        })
    }
  },
  watch: {
    clearUpload: function (newVal, oldVal) {
      this.fileList = []
    }
  }
}
</script>
<style>
</style>
