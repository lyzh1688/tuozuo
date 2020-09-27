<template>
  <a-modal
    title="审核工资发放申请"
    :width="900"
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
    style="text-align: center"
  >
    <a-spin :spinning="loading">
      <a-form :form="form">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item label="工资id" v-show="false">
          <span>{{ model.paymentId }}</span>
        </a-form-item>
        <a-row>
          <a-col :span="6">
            <a-form-item label="公司名称">
              <span>{{ model.companyName }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="项目名称">
              <span>{{ model.projectName }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="转账凭证" key="转账凭证">
              <a-button
                type="text"
                @click="
                  () => {
                    jumpToFile(model.transferVoucher)
                  }
                "
              >已上传的文件</a-button
              >
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="6">
            <a-form-item label="总金额">
              <span>{{ model.totalWages }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="发放月份">
              <span>{{ model.month }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="发放日期">
              <span>{{ model.releaseDate }}</span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="48">
            <a-form-item>
              <div style="text-align: center">
                <a-button
                  type="primary"
                  @click="salaryListVisible=true
                  "
                >工资清单</a-button
                >
              </div>
            </a-form-item>
            <a-form-item v-show="false">
              <a-textarea v-decorator="['paymentId']" />
            </a-form-item>
            <a-form-item label="备注">
              <a-textarea
                v-decorator="[
                  'remark',
                  { rules: [{ required: true, message: '请输入备注！' }], validateTrigger: 'blur' },
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="48">
            <a-form-item>
              <div style="display: flex; flex-wrap: nowrap; flex-flow: row; justify-content: space-between">
                <div style="margin-inline-end: 70px; margin-inline-start: 70px">
                  <a-button
                    type="primary"
                    @click="
                      () => {
                        $emit('pass')
                      }
                    "
                  >审核通过</a-button
                  >
                </div>
                <div style="margin-inline-end: 70px; margin-inline-start: 70px">
                  <a-button
                    type="danger"
                    @click="
                      () => {
                        $emit('reject')
                      }
                    "
                  >审核不通过</a-button
                  >
                </div>
              </div>
            </a-form-item>
          </a-col>
        </a-row>
        <slot name="other"></slot>
      </a-form>
    </a-spin>
    <salarystafflist
      ref="salarystafflist"
      :visible="salaryListVisible"
      :projectId="model.projectId"
      :paymentId="model.paymentId"
      :companyId="model.companyId"
      :period="model.month"
      @ok="salaryListVisible = false"
      @cancel="salaryListVisible = false"
    ></salarystafflist>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import salarystafflist from './SalaryStaffList'
// 表单字段
const fields = [
  'companyId',
  'companyName',
  'projectId',
  'projectName',
  'totalWages',
  'releaseDate',
  'paymentId',
  'month',
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
  components: {
      salarystafflist
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
      projectFileList: [],
      provinceList: [],
      cityList: [],
      districtList: [],
      genderMap: {},
      cityIndex: 0,
      areaIndex: 0,
      salaryListVisible: false,
      industryTypeList: [],
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this)
    }
  },
  watch: {
    visible (value) {
      if (value) {
      }
    }
  },
  created () {
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
    }
  }
}
</script>
