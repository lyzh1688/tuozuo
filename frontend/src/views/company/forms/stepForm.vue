
<template>
  <a-modal
    :title="model && model.customId!=undefined&&model.customId!==''?'修改客户':'新建客户'"
    :width="740"
    :visible="visible"
    :confirmLoading="loading"
    :maskClosable="false"
    :footer="null"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-steps class="steps" :current="currentTab">
        <a-step title="进行余额操作" />
        <a-step title="修改公司状态" />
      </a-steps>
      <div class="content">
        <a-form :form="form" v-bind="formLayout">
          <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
          <a-form-item label="公司id" v-show="false">
            <a-input v-decorator="['companyId', { validateTrigger: 'blur'}]" />
          </a-form-item>
          <a-form-item label="关联流水">
            <a-input v-show="false" v-model="tradeFlow" v-decorator="['tradeFlow', { validateTrigger: 'blur'}]" />
            <a-button size="small" @click="handleops(record)" :loading="confirmLoading">余额变动</a-button>
          </a-form-item>
          <a-row>
            <a-col v-show="currentTab === 1" :span="12">
              <a-form-item label="免费快递余额" key="免费快递余额">
                <a-input
                  v-decorator="['freeDeliveryCnt', {rules: [{required: true, message: '请输入剩余免费快递次数！'}], validateTrigger: 'blur'}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col v-show="currentTab === 1" :span="12">
              <a-form-item label="业务状态" key="业务状态">
                <a-select
                  style="width:200px;"
                  v-decorator="['companyStatus', {rules: [{required: true, message: '请输入业务状态！'}], validateTrigger: 'blur'}]"
                  placeholder="请选择"
                >
                  <a-select-option
                    v-for=" typeItem in bizStatus"
                    :key="typeItem.id"
                  >{{ typeItem.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col v-show="currentTab === 1" :span="12">
              <a-form-item label="包含注销服务" key="包含注销服务">
                <a-select
                  :disabled="tradeFlow==null||tradeFlow==''||tradeFlow==undefined"
                  v-decorator="['includeCancel', {rules: [{required: true, message: '请输入是否包含注销服务！'}], validateTrigger: 'blur'}]"
                  placeholder="请选择"
                >
                  <a-select-option value="1">是</a-select-option>
                  <a-select-option value="0">否</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col v-show="currentTab === 1" :span="12">
              <a-form-item label="服务开始" key="服务开始">
                <a-date-picker
                  :disabled="tradeFlow==null||tradeFlow==''||tradeFlow==undefined"
                  valueFormat="YYYY-MM-DD"
                  format="YYYY-MM-DD"
                  v-decorator="['beginDate']"
                />
              </a-form-item>
            </a-col>
            <a-col v-show="currentTab === 1" :span="12">
              <a-form-item label="服务结束" key="服务结束">
                <a-date-picker
                  :disabled="tradeFlow==null||tradeFlow==''||tradeFlow==undefined"
                  valueFormat="YYYY-MM-DD"
                  format="YYYY-MM-DD"
                  v-decorator="['endDate']"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col v-show="currentTab === 1" :span="12">
              <a-form-item label="注册园区" key="注册园区">
                <a-select
                  style="width:200px;"
                  v-decorator="['registerArea', {rules: [{required: true, message: '请输入注册园区！'}], validateTrigger: 'blur'}]"
                  placeholder="请选择"
                >
                  <a-select-option
                    v-for=" typeItem in registerArea"
                    :key="typeItem.name"
                  >{{ typeItem.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col v-show="currentTab === 1" :span="12">
              <a-form-item label="返税比例" key="返税比例">
                <a-select
                  style="width:200px;"
                  v-decorator="['rebateTaxRate', {rules: [{required: true, message: '请输入返税比例！'}], validateTrigger: 'blur'}]"
                  placeholder="请选择"
                >
                  <a-select-option
                    v-for=" typeItem in rebateTaxRate"
                    :key="typeItem.id"
                  >{{ typeItem.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-form-item v-if="currentTab === 0" :wrapperCol="{span: 19, offset: 5}">
            <a-button
              :disabled="tradeFlow==null||tradeFlow==''||tradeFlow==undefined"
              type="primary"
              @click="nextStep"
            >下一步</a-button>
            <a-button type="primary" @click="nextStep">跳过</a-button>
          </a-form-item>
          <a-form-item v-if="currentTab === 1" :wrapperCol="{span: 19, offset: 5}">
            <a-button :loading="loading" type="primary" @click="finish">提交</a-button>
            <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
          </a-form-item>
        </a-form>
        <div v-if="currentTab === 0" class="step-form-style-desc">
          <h3>说明</h3>
          <h4>修改公司部分状态需要关联余额变动流水</h4>
          <p>需要修改服务时间，是否包含注销服务等状态前需要进行扣款操作，否则无法修改</p>
          <h4>无需修改相关状态则点击跳过</h4>
        </div>
      </div>
    </a-spin>
    <fundoperation
      ref="fundoperation"
      :clearUpload="clearUpload"
      :visible="fundOpsVisible"
      :loading="confirmLoading"
      :customTypeList="customType"
      :model="fundMdl"
      @cancel="handleCancel"
      @ok="handleFundOpsOk"
    />
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { dictQuery, tradeOpration, getCustomInfo } from '@/api/company'
import fundoperation from '@/views/user/forms/FundOperation'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
// 表单字段
const fields = [
  'companyId',
  'freeDeliveryCnt',
  'companyStatus',
  'includeCancel',
  'beginDate',
  'endDate',
  'tradeFlow',
  'registerArea',
  'rebateTaxRate'
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
    },
    resetStep: {
      type: Boolean,
      default: false
    }
  },
  components: {
    fundoperation
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
        customInfo: {},
        customType: [],
      confirmLoading: false,
      fundMdl: {},
      clearUpload: false,
      fundOpsVisible: false,
      tradeFlow: '',
      currentTab: 0,
      bizStatus: [],
      registerArea: [],
      rebateTaxRate: [],
      // city: citiesHepler[0].label,
      cityIndex: 0,
      areaIndex: 0,
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this)
    }
  },
  created () {
    this.getDict('bizStatus').then(response => {
      this.bizStatus = response
    })
    this.getDict('registerArea').then(response => {
      this.registerArea = response
    })
    this.getDict('rebateTaxRate').then(response => {
      this.rebateTaxRate = response
    })
     this.getDict('customType').then(response => {
      this.customType = response
    })
    console.log('custom modal created')

    // 防止表单未注册
    fields.forEach(v => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
    })
    this.$watch('resetStep', () => {
      this.currentTab = 0
    })
  },
  methods: {
      getCustomInfo (currentCustomId) {
      this.confirmLoading = true
      getCustomInfo(currentCustomId)
        .then(Response => {
          const result = Response
          // console.log('getCustomInfo', result)
          if (success(result)) {
             this.fundMdl = { ...result.data, customId: currentCustomId }
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询客户信息失败，请稍后再试'
            })
          }
          setTimeout(() => {
            this.confirmLoading = false
          }, 600)
        })
        .catch(error => {
          this.confirmLoading = false
          this.$notification.error({
            message: '查询客户信息失败，请稍后再试',
            description: error
          })
        })
    },
    handleCancel () {
      this.fundOpsVisible = false
    },
    async handleops () {
        await this.getCustomInfo(this.model.customId)
      this.fundOpsVisible = true
    },
    handleFundOpsOk () {
      const form = this.$refs.fundoperation.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          values.tradeFile = values.tradeFile.file.originFileObj
          tradeOpration(values)
            .then(response => {
              const result = response
              if (success(result)) {
                this.tradeFlow = result.data.tradeFlowId
                this.form.setFieldsValue({ tradeFlow: result.data.tradeFlowId })
                this.$notification.success({
                  message: '余额修改成功'
                })
                const form = this.$refs.fundoperation.form
                form.resetFields() // 清理表单数据（可不做）
                this.clearUpload = !this.clearUpload
                this.fundOpsVisible = false
                this.confirmLoading = false
              } else {
                this.confirmLoading = false
                this.$notification.error({
                  message: errorMessage(result),
                  description: '余额修改成功'
                })
              }
              if (needLogin(result)) {
                this.fundOpsVisible = false
                this.confirmLoading = false
              }
            })
            .catch(error => {
              this.$notification.error({
                message: '余额修改成功失败。请稍后再试',
                description: error
              })
              this.confirmLoading = false
            })
        }
      })
    },
    nextStep () {
      if (this.currentTab < 2) {
        this.currentTab += 1
      }
    },
    prevStep () {
      if (this.currentTab > 0) {
        this.currentTab -= 1
      }
    },
    finish () {
      this.$emit('ok')
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
  }
}
</script>
<style lang="less" scoped>
.step-form-style-desc {
  padding: 0 56px;
  color: rgba(0, 0, 0, 0.45);

  h3 {
    margin: 0 0 12px;
    color: rgba(0, 0, 0, 0.45);
    font-size: 16px;
    line-height: 32px;
  }

  h4 {
    margin: 0 0 4px;
    color: rgba(0, 0, 0, 0.45);
    font-size: 14px;
    line-height: 22px;
  }

  p {
    margin-top: 0;
    margin-bottom: 12px;
    line-height: 22px;
  }
}
</style>
