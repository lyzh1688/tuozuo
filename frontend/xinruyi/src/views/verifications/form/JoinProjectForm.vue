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
        <a-form-item v-show="false" label="公司id">
          <a-input v-decorator="['companyId', { validateTrigger: 'blur' }]" />
        </a-form-item>
        <a-form-item v-show="false" label="事件Id">
          <a-input v-decorator="['eventId', { validateTrigger: 'blur' }]" />
        </a-form-item>
        <a-form-item v-show="false" label="项目id">
          <a-input v-decorator="['projectId', { validateTrigger: 'blur' }]" />
        </a-form-item>
        <a-form-item v-show="false" label="员工id">
          <a-input v-decorator="['staffId', { validateTrigger: 'blur' }]" />
        </a-form-item>
        <a-row>
          <a-col :span="12">
            <a-form-item label="企业名称">
              <a-input :disabled="true" v-decorator="['companyName', { validateTrigger: 'blur' }]" />
            </a-form-item>
          </a-col>
          <a-row>
            <a-col :span="12">
              <a-form-item label="项目名称">
                <a-input :disabled="true" v-decorator="['projectName', { validateTrigger: 'blur' }]" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="员工姓名">
              <a-input :disabled="true" v-decorator="['staffName', { validateTrigger: 'blur' }]" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="联系方式">
              <a-input :disabled="true" v-decorator="['contact', { validateTrigger: 'blur' }]" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="身份证号">
              <a-input :disabled="true" v-decorator="['idNo', { validateTrigger: 'blur' }]" />
            </a-form-item>
          </a-col>
        </a-row>
        <s-table
          ref="joinprojecttable"
          id="1"
          size="default"
          rowKey="id"
          :pageSize="20"
          :columns="columns"
          :data="loadData"
          :showPagination="true"
        >
          <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        </s-table>
        <a-row>
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
import { STable } from '@/components'
import { getProjectStaffList } from '@/api/humanResource'
// 表单字段
const fields = [
  'companyId',
  'companyName',
  'projectId',
  'projectName',
  'eventId',
  'staffId',
  'staffName',
  'contact',
  'idNo',
  'result',
  'remark'
]
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '姓名',
    dataIndex: 'name',
    scopedSlots: { customRender: 'name' }
  },
  {
    title: '身份证号',
    dataIndex: 'idNo',
    scopedSlots: { customRender: 'idNo' }
  },
  {
    title: '工资',
    dataIndex: 'salary',
    scopedSlots: { customRender: 'salary' }
  },
  {
    title: '是否在池',
    dataIndex: 'isValid',
    customRender: (text) => {
      if (text === '0') {
        return '否'
      }
      if (text === '1') {
        return '是'
      }
      return ''
    }
  },
  {
    title: '是否签约',
    dataIndex: 'isSigned',
    customRender: (text) => {
      if (text === '0') {
        return '否'
      }
      if (text === '1') {
        return '是'
      }
      return ''
    }
  },
  {
    title: '备注',
    dataIndex: 'remark',
    scopedSlots: { customRender: 'remark' }
  }
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
    this.columns = columns
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
        this.$refs.joinprojecttable.refresh(true)
    })
  },
  methods: {},
  components: {
    STable
  }
}
</script>
