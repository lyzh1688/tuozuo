<template>
  <a-modal
    title="项目加入审核"
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
        <a-form-item v-show="false" label="注册Id">
          <a-input v-decorator="['registerId', { validateTrigger: 'blur' }]" />
        </a-form-item>
        <a-row>
          <a-col :span="12">
            <a-form-item label="姓名">
              <a-input :disabled="true" v-decorator="['name', { validateTrigger: 'blur' }]" />
            </a-form-item>
          </a-col>
          <a-row>
            <a-col :span="12">
              <a-form-item label="身份证号">
                <a-input :disabled="true" v-decorator="['idNumber', { validateTrigger: 'blur' }]" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="联系方式">
              <a-input :disabled="true" v-decorator="['contact', { validateTrigger: 'blur' }]" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="身份证正面">
              <a-button
                type="text"
                v-if="model&&typeof model.idPicUp === 'string'"
                @click="()=>{jumpToFile(model.idPicUp)}"
              >已上传的文件</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="身份证反面">
              <a-button
                type="text"
                v-if="model&&typeof model.idPicBack === 'string'"
                @click="()=>{jumpToFile(model.idPicBack)}"
              >已上传的文件</a-button>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="录像">
              <a-button
                type="text"
                v-if="model&&typeof model.video === 'string'"
                @click="()=>{jumpToFile(model.video)}"
              >已上传的文件</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="手写签名">
              <a-button
                type="text"
                v-if="model&&typeof model.signPic === 'string'"
                @click="()=>{jumpToFile(model.signPic)}"
              >已上传的文件</a-button>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="审核结果">
              <a-select
                v-decorator="[
                  'result',
                  { rules: [{ required: true, message: '请选择支付状态！' }], validateTrigger: 'blur' },
                ]"
                placeholder="请选择"
              >
                <a-select-option value="1">审核通过</a-select-option>
                <a-select-option value="0">审核未通过</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="备注">
          <a-textarea
            v-decorator="['remark', { rules: [{ required: true, message: '请输备注！' }], validateTrigger: 'blur' }]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { success, errorMessage } from '@/utils/helper/responseHelper'
import { getProjectStaffList } from '@/api/humanResource'
// 表单字段
const fields = [
  'name',
  'contact',
  'idNumber',
  'idPicUp',
  'idPicBack',
  'video',
  'signPic',
  'result',
  'remark',
  'registerId'
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
    title: {
      type: String,
      default: ''
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
      industryTypeList: [],
      cityIndex: 0,
      areaIndex: 0,
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this),
      loadData: (parameter) => {
        return getProjectStaffList(this.model.projectId, 1, 1000)
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.staffs
              return {
                pageSize: 1000,
                pageNo: 1,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询项目人员失败，请稍后再试'
              })
            }
          })
          .catch((error) => {
            this.$notification.error({
              message: '查询项目人员失败，请稍后再试',
              description: error
            })
          })
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
  },
  components: {
  }
}
</script>
