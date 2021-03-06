<template>
  <a-modal
    :title="title"
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
              <s-table
                ref="table"
                size="default"
                rowKey="staffId"
                :pageSize="1000"
                :columns="columns"
                :data="loadData"
                :showPagination="true"
                :scroll="{ y: 440 }"
              >
                <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
                <span slot="gender" slot-scope="text">{{ genderMap[text] }}</span>
              </s-table>
            </a-form-item>
          </a-col>
        </a-row>
        <slot name="other"></slot>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { STable } from '@/components'
import { getDetailsalaryList } from '@/api/salary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { getCommonDict } from '@/api/dictionary'
// 表单字段
const fields = [
  'companyId',
  'companyName',
  'projectId',
  'projectName',
  'totalWages',
  'releaseDate',
  'paymentId',
  'month'
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
    this.columns = columns
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
      industryTypeList: [],
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this),
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam1)
        console.log('loadData request parameters:', requestParameters)
        return getDetailsalaryList(
          this.model.paymentId,
          this.model.projectId,
          '',
          '',
          '',
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then((Response) => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.staffs
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
  watch: {
    visible (value) {
      if (value) {
        this.$refs.table.refresh(true)
      }
    }
  },
  created () {
    console.log('custom modal created')
    // 防止表单未注册
    fields.forEach((v) => this.form.getFieldDecorator(v))
    this.getDict('gender').then((response) => {
      this.genderMap = {}
      for (const i of response) {
        this.genderMap[i.id] = i.name
      }
    })
    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
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
    }
  }
}
</script>
