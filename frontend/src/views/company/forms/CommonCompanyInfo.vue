<template>
  <a-modal
    :title="model && model.companyId!=undefined&&model.companyId!==''?'修改公司':'新建客户申请'"
    :width="740"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout" key="CommonCompanyInfoForm">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-card title="公司基本信息">
          <a-row>
            <a-col :span="12">
              <a-form-item v-show="false" label="公司Id" key="公司Id">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['companyId', { validateTrigger: 'blur'}]"
                />
              </a-form-item>
              <a-form-item label="公司名称" key="公司名称">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['companyName', {rules: [{required: true, message: '请输入公司名称！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="公司类型" key="公司类型">
                <a-select
                  :disabled="isShowOnly"
                  style="width:200px;"
                  v-decorator="['companyType', {rules: [{required: true, message: '请输入公司类型！'}], validateTrigger: 'blur'}]"
                  placeholder="请选择"
                >
                  <a-select-option
                    v-for=" typeItem in companyTypeList"
                    :key="typeItem.id"
                  >{{ typeItem.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="12">
              <a-form-item label="综合税率" key="综合税率">
                <a-select
                  :disabled="isShowOnly"
                  style="width:200px;"
                  v-decorator="['tax', {rules: [{required: true, message: '请输入公司类型！'}], validateTrigger: 'blur'}]"
                  placeholder="请选择"
                >
                  <a-select-option v-for=" taxItem in taxList" :key="taxItem.id">{{ taxItem.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="邮寄地址" key="邮寄地址">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['address', {rules: [{required: true, message: '请输入公司名称！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <slot name="company-extra"></slot>
        </a-card>
        <a-card title="投资人信息">
          <a-row>
            <a-col :span="12">
              <a-form-item label="姓名" key="姓名">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['bossName', {rules: [{required: true, message: '请输入投资人姓名！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="身份证号" key="身份证号">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['bossId', {rules: [{required: true, message: '请输入身份证号！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="12">
              <a-form-item label="联系电话" key="联系电话">
                <a-input-number
                  :min="0"
                  :disabled="isShowOnly"
                  style="width:200px;"
                  v-decorator="['bossContact', {rules: [{required: true, message: '请输入联系电话！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="12">
              <a-form-item label="身份证正面" key="身份证正面">
                <a-button
                  type="text"
                  v-if="model&&typeof model.bossIdPicUp === 'string'"
                  @click="()=>{jumpToFile(model.bossIdPicUp)}"
                >已上传的文件</a-button>
                <a-upload
                  v-show="!isShowOnly"
                  name="bossIdPicUp"
                  :file-list="bossIdPicUpList"
                  list-type="picture-card"
                  v-decorator="['bossIdPicUp', {rules: [{required: true, message: '请输入身份证正面！'}], validateTrigger: 'blur'}]"
                  :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
                  @change="handleBossIdPicUpChange"
                >
                  <a-button v-show="!isShowOnly" :v-if="showUpload">
                    <a-icon :v-if="showUpload" type="upload" />上传
                  </a-button>
                </a-upload>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="身份证反面" key="身份证反面">
                <a-button
                  type="text"
                  v-if="model&&typeof model.bossIdPicBack === 'string'"
                  @click="()=>{jumpToFile(model.bossIdPicBack)}"
                >已上传的文件</a-button>
                <a-upload
                  v-show="!isShowOnly"
                  name="bossIdPicBack"
                  :file-list="bossIdPicBackList"
                  list-type="picture-card"
                  v-decorator="['bossIdPicBack', {rules: [{required: true, message: '请输入身份证反面面！'}], validateTrigger: 'blur'}]"
                  :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
                  @change="handleBossIdPicBackChange"
                >
                  <a-button v-show="!isShowOnly" :v-if="showUpload">
                    <a-icon :v-if="showUpload" type="upload" />上传
                  </a-button>
                </a-upload>
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
        <a-card title="财务负责人信息">
          <a-row>
            <a-col :span="12">
              <a-form-item label="姓名" key="姓名">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['cfoName', {rules: [{required: true, message: '请输入投资人姓名！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="身份证号">
                <a-input
                  :disabled="isShowOnly"
                  v-decorator="['cfoId', {rules: [{required: true, message: '请输入身份证号！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="12">
              <a-form-item label="联系电话" key="联系电话">
                <a-input-number
                  :min="0"
                  :disabled="isShowOnly"
                  style="width:200px;"
                  v-decorator="['cfoContact', {rules: [{required: true, message: '请输入联系电话！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="12">
              <a-form-item label="身份证正面" key="身份证正面">
                <a-button
                  type="text"
                  v-if="model&&typeof model.cfoIdPicUp === 'string'"
                  @click="()=>{jumpToFile(model.cfoIdPicUp)}"
                >已上传的文件</a-button>
                <a-upload
                  v-show="!isShowOnly"
                  name="cfoIdPicUp"
                  :file-list="cfoIdPicUpList"
                  list-type="picture-card"
                  v-decorator="['cfoIdPicUp', {rules: [{required: true, message: '请输入身份证正面！'}], validateTrigger: 'blur'}]"
                  :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
                  @change="handleCfoIdPicUpChange"
                >
                  <a-button v-show="!isShowOnly" :v-if="showUpload">
                    <a-icon :v-if="showUpload" type="upload" />上传
                  </a-button>
                </a-upload>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="身份证反面" key="身份证反面">
                <a-button
                  type="text"
                  v-if="model&&typeof model.cfoIdPicBack === 'string'"
                  @click="()=>{jumpToFile(model.cfoIdPicBack)}"
                >已上传的文件</a-button>
                <a-upload
                  v-show="!isShowOnly"
                  name="cfoIdPicBack"
                  :file-list="cfoIdPicBackList"
                  list-type="picture-card"
                  v-decorator="['cfoIdPicBack', {rules: [{required: true, message: '请输入身份证反面面！'}], validateTrigger: 'blur'}]"
                  :showUploadList="{ showPreviewIcon: false, showRemoveIcon: true }"
                  @change="handleCfoIdPicBackChange"
                >
                  <a-button v-show="!isShowOnly" :v-if="showUpload">
                    <a-icon :v-if="showUpload" type="upload" />上传
                  </a-button>
                </a-upload>
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { dictQuery } from '@/api/company'
import { success, errorMessage } from '@/utils/helper/responseHelper'
// 表单字段
const fields = [
  'companyId',
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
      showUpload: true,
      companyTypeList: [],
      taxList: [],
      bossIdPicUpList: [],
      bossIdPicBackList: [],
      cfoIdPicBackList: [],
      cfoIdPicUpList: [],
      // city: citiesHepler[0].label,
      cityIndex: 0,
      areaIndex: 0,
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this)
    }
  },
  created () {
    this.getDict('companyType').then(response => {
      this.companyTypeList = response
    })
    this.getDict('tax').then(response => {
      this.taxList = response
    })
    console.log('custom modal created')

    // 防止表单未注册
    fields.forEach(v => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
    })
  },
  methods: {
    jumpToFile (link) {
      window.open(link, '_blank')
    },
    handleBossIdPicUpChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.bossIdPicUpList = fileList
      console.log(info.file.originFileObj)
      //   this.fileList = [info]
    },
    handleBossIdPicBackChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.bossIdPicBackList = fileList
      console.log(info.file.originFileObj)
      //   this.fileList = [info]
    },
    handleCfoIdPicUpChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.cfoIdPicUpList = fileList
      console.log(info.file.originFileObj)
      //   this.fileList = [info]
    },
    handleCfoIdPicBackChange (info) {
      let fileList = [...info.fileList]
      fileList = fileList.slice(-1)
      this.cfoIdPicBackList = fileList
      console.log(info.file.originFileObj)
      //   this.fileList = [info]
    },
    getDict (keyword) {
      return new Promise((resolve, reject) => {
        dictQuery(keyword).then(Response => {
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
      this.bossIdPicUpList = []
      this.bossIdPicBackList = []
      this.cfoIdPicBackList = []
      this.cfoIdPicUpList = []
    }
  }
}
</script>
