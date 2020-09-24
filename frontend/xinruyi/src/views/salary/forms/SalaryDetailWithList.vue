<template>
  <a-modal
    :title="title"
    :width="740"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item label="工资id" v-show="false">
          <span v-decorator="['paymentId']"></span>
        </a-form-item>
        <a-form-item label="公司名称">
          <span v-decorator="['companyName']"></span>
        </a-form-item>
        <a-form-item label="项目名称">
          <span v-decorator="['projectName']"></span>
        </a-form-item>
        <a-form-item label="总金额">
          <span v-decorator="['totalWages']"></span>
        </a-form-item>
        <a-form-item label="发放月份">
          <span v-decorator="['releaseDate']"></span>
        </a-form-item>
        <a-form-item label="发放列表">
          <s-table
            ref="table"
            size="default"
            rowKey="staffId"
            :pageSize="20"
            :columns="columns"
            :data="loadData"
            :showPagination="true"
            :scroll="{ y: 340 }"
          >
            <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
          </s-table>
        </a-form-item>
        <slot name="other"></slot>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { STable } from '@/components'
import { getDetailsalaryList } from '@/api/salary'
import { success, errorMessage } from '@/utils/helper/responseHelper'
// 表单字段
const fields = ['companyId', 'companyName', 'projectId', 'projectName', 'totalWages', 'releaseDate', 'paymentId']
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
    title: '性别',
    dataIndex: 'gender',
    scopedSlots: { customRender: 'gender' }
  },
  {
    title: '银行卡号',
    dataIndex: 'bankCard',
    scopedSlots: { customRender: 'bankCard' }
  },
  {
    title: '工资',
    dataIndex: 'salary',
    scopedSlots: { customRender: 'salary' }
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
    STable
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
      showUpload: true,
      projectFileList: [],
      provinceList: [],
      cityList: [],
      districtList: [],
      cityIndex: 0,
      areaIndex: 0,
      industryTypeList: [],
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this),
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam1)
        console.log('loadData request parameters:', requestParameters)
        return getDetailsalaryList(
          requestParameters.companyId,
          requestParameters.projectId,
          requestParameters.status,
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.funds
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询工资列表失败，请稍后再试'
              })
            }
            setTimeout(() => {
              this.projectListLoading = false
            }, 600)
            return []
          })
          .catch((error) => {
            setTimeout(() => {
              this.projectListLoading = false
            }, 600)
            this.$notification.error({
              message: '查询工资列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  watch: {},
  created () {
    console.log('custom modal created')
    // 防止表单未注册
    fields.forEach((v) => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
    })
  },
  methods: {}
}
</script>
