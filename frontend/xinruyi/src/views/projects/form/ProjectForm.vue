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
          v-show="false"
          label="项目Id"
        >
          <a-input
            v-decorator="['projectId', { validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="项目名称">
          <a-input
            v-decorator="['projectName', {rules: [{required: true, message: '请输入项目名称！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="行业类型">
          <a-select
            v-decorator="['industryType', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            style="width: 120px"
          >
            <a-select-option
              v-for="province in industryTypeList"
              :key="province.areaCode"
            >{{ province.areaName }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="项目开始时间">
          <a-date-picker
            :disabled="isUpdate"
            valueFormat="YYYYMMDD"
            format="YYYY-MM-DD"
            v-decorator="['releaseDate', {rules: [{required: true, message: '请输入项目开始时间！'}], validateTrigger: ['change', 'blur']}]"
          />
        </a-form-item>
        <a-form-item label="项目周期(月)">
          <a-input-number
            :min="0"
            v-decorator="['projectCycle', {rules: [{required: true, message: '请输入入团时间！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="项目人员">
          <a-input-number
            :min="0"
            v-decorator="['staffNum', {rules: [{required: true, message: '请输入人员！'}], validateTrigger: 'blur'}]"
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
        <a-form-item label="是否驻场">
          <a-select
            v-decorator="['isResident', {rules: [{required: true, message: '请选择！'}], validateTrigger: 'blur'}]"
            style="width: 120px"
          >
            <a-select-option
              value="0"
            >否</a-select-option>
            <a-select-option
              value="1"
            >是</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="联系人">
          <a-input
            v-decorator="['contactName', {rules: [{required: true, message: '请输入联系人！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="联系方式">
          <a-input
            v-decorator="['contact', {rules: [{required: true, message: '请输入联系方式！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <a-form-item label="项目文件" key="项目文件">
          <a-button
            type="text"
            v-if="model&&typeof model.bossIdPicUp === 'string'"
            @click="()=>{jumpToFile(model.bossIdPicUp)}"
          >已上传的文件</a-button>
          <a-upload
            :beforeUpload="beforeUpload"
            v-show="!isShowOnly"
            name="projectFile"
            :file-list="bossIdPicUpList"
            list-type="picture-card"
            v-decorator="['projectFile', {rules: [{required: true, message: '请输入项目文件！'}], validateTrigger: 'blur'}]"
            :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
            @change="handleBossIdPicUpChange"
          >
            <a-button v-show="!isShowOnly" :v-if="showUpload">
              <a-icon :v-if="showUpload" type="upload" />上传
            </a-button>
          </a-upload>
        </a-form-item>
        <a-form-item label="描述" >
          <a-textarea
            :disabled="isShowOnly"
            v-decorator="['desc', {rules: [{required: true, message: '请输描述！'}], validateTrigger: 'blur'}]"
          />
        </a-form-item>
        <slot name="other"></slot>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { getAreaCode, getCommonDict } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
// 表单字段
const fields = [
  'projectId',
  'projectName',
  'industryType',
  'releaseDate',
  'projectCycle',
  'staffNum',
  'province',
  'city',
  'district',
  'isResident',
'contactName',
'contact',
'projectFile',
'desc'
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
      industryTypeList: [],
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
    this.getDict('industryType').then((response) => {
      this.industryTypeList = response
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
