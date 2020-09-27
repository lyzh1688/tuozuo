<template>
  <a-drawer
    width="640"
    title="历史工资单"
    placement="right"
    :closable="false"
    :visible="visible"
    :afterVisibleChange="visibleChange"
    @close="() => { $emit('onclose') }"
  >
    <a-spin :spinning="infoLoading">
      <a-form >
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-row>
          <a-col :md="8" :sm="8">
            <a-form-item >
              <a-button type="primary" size="small" @click="refresh=!refresh">查询</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item >
          <s-table
            ref="salaryTable"
            size="default"
            rowKey="key"
            :pageSize="20"
            :columns="salaryColumns"
            :data="salaryData"
            showPagination="true"
          >
            <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
            <span slot="gender" slot-scope="text">{{ genderMap[text] }}</span>
          </s-table>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-drawer>
</template>

<script>
import { STable } from '@/components'
import { getCommonDict } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { getDetailsalaryList } from '@/api/salary'
// 表单字段
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
    title: '性别',
    dataIndex: 'gender',
    scopedSlots: { customRender: 'gender' }
  },
  {
    title: '工资',
    dataIndex: 'salary',
    scopedSlots: { customRender: 'salary' }
  },
  {
    title: '身份证',
    dataIndex: 'idNo',
    scopedSlots: { customRender: 'idNo' }
  },
  {
    title: '银行卡号',
    dataIndex: 'bankCard',
    scopedSlots: { customRender: 'bankCard' }
  }
]

export default {
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    refresh: {
         type: Boolean,
       default: () => false
    },
    paymentId: {
        type: String,
       default: () => ''
    },
    projectId: {
        type: String,
       default: () => ''
    }
  },
  components: {
      STable
  },
  data () {
      this.salaryColumns = columns
    return {
      // city: citiesHepler[0].label,
      salaryList: [],
      infoLoading: false,
      queryParam: {
          beginDate: '',
          endData: '',
          projectId: ''
      },
      genderMap: {},
      fuzzyProjectList: [],
      salaryData: parameter => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        if (this.projectId !== '') {
          requestParameters.projectId = this.projectId
        }
        if (this.paymentId !== '') {
          requestParameters.paymentId = this.paymentId
        }
        console.log('loadData request parameters:', requestParameters)
        this.infoLoading = true
        return getDetailsalaryList(requestParameters.paymentId, requestParameters.projectId, '', '', '', requestParameters.pageNo, requestParameters.pageSize)
          .then(Response => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.staffs
              setTimeout(() => {
              this.infoLoading = false
            }, 600)
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: Number.parseInt(result.data.total),
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询资源池列表失败，请稍后再试'
              })
               setTimeout(() => {
              this.infoLoading = false
            }, 600)
            }
            if (needLogin(result)) {
                  this.visible = false
                }
          })
          .catch(error => {
            setTimeout(() => {
              this.infoLoading = false
            }, 600)
            this.$notification.error({
              message: '查询资源池列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  created () {
       fetch('', (data) => (this.fuzzyProjectList = data))
       this.getDict('gender').then((response) => {
      this.genderMap = {}
      for (const i of response) {
        this.genderMap[i.id] = i.name
      }
    })
  },
  methods: {
      visibleChange (data) {
          if (data) {
              this.queryParam = {
                  projectId: '',
                  beginDate: '',
                  endDate: '',
                  pageNo: 1,
                  pageSize: 20
                }
              this.$refs.salaryTable.refresh(true)
          }
      },
       handleCustomSearch (value) {
      fetch(value, (data) => (this.fuzzyProjectList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      this.queryParam.projectId = value
      fetch(value, (data) => (this.fuzzyProjectList = data))
    },
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
  },
  watch: {
    refresh: function (newVal, oldVal) {
          this.$refs.salaryTable && this.$refs.salaryTable.refresh(true)
    }
  }
}
</script>
