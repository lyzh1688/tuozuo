<template>
  <a-drawer
    width="640"
    title="历史工资单"
    placement="right"
    :closable="false"
    :visible="visible"
    @close="() => { $emit('onClose') }"
  >
    <a-spin :spinning="infoLoading">
      <a-form >
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-row>
          <a-col :md="7" :sm="8">
            <a-form-item label="项目名称">
              <a-select
                show-search
                :value="queryParam.projectId"
                placeholder="请输入项目名称"
                style="width: 200px"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleCustomSearch"
                @change="handleCustomChange"
              >
                <a-select-option v-for="d in fuzzyCompanyList" :key="d.value">{{ d.text }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item label="开始日期">
              <a-date-picker
                valueFormat="YYYYMMDD"
                format="YYYY-MM-DD"
                v-model="queryParam.beginDate"
              />
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item label="结束日期">
              <a-date-picker
                valueFormat="YYYYMMDD"
                format="YYYY-MM-DD"
                v-model="queryParam.endDate"
              />
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item >
              <a-button type="primary" size="small" @click="refresh=!refresh">查询</a-button>
              <a-button
                size="small"
                type="primary"
                @click="()=>{queryParam= {
                  projectId: '',
                  beginDate: '',
                  endDate: '',
                  pageNo: 1,
                  pageSize: 20
                }}"
              >重置</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item >
          <s-table
            ref="salaryTable"
            size="default"
            rowKey="no"
            :pageSize="20"
            :columns="salaryColumns"
            :data="salaryData"
            showPagination="true"
          >
            <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
          </s-table>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-drawer>
</template>

<script>
import { STable } from '@/components'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { fuzzyQueryProject } from '@/api/projects'
import { getSalaryList } from '@/api/humanResource'
// 表单字段
const columns = [
  {
    title: '员工姓名',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '姓名',
    dataIndex: 'staffName',
    scopedSlots: { customRender: 'staffName' }
  },
  {
    title: '项目名称',
    dataIndex: 'projectName',
    scopedSlots: { customRender: 'projectName' }
  },
  {
    title: '工资',
    dataIndex: 'salary',
    scopedSlots: { customRender: 'salary' }
  },
  {
    title: '发放时间',
    dataIndex: 'releaseDate',
    scopedSlots: { customRender: 'releaseDate' }
  },
  {
    title: '银行卡号',
    dataIndex: 'bankCard',
    scopedSlots: { customRender: 'bankCard' }
  }
]
let timeout
let currentValue

function fetch (value, callback) {
  if (timeout) {
    clearTimeout(timeout)
    timeout = null
  }
  currentValue = value

  function fake () {
    fuzzyQueryProject(value, 20, false).then((d) => {
      if (currentValue === value) {
        const result = d.data
        const data = []
        result.forEach((r) => {
          data.push({
            value: r['id'],
            text: r['name']
          })
        })
        data.push({
          value: '',
          text: ''
        })
        callback(data)
      }
    })
  }

  timeout = setTimeout(fake, 300)
}
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
    staffId: {
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
      fuzzyProjectList: [],
      salaryData: parameter => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        this.infoLoading = true
        return getSalaryList(this.projectId, this.staffId, requestParameters.beginDate, requestParameters.endDate, requestParameters.pageNo, requestParameters.pageSize)
          .then(Response => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.payment
              setTimeout(() => {
              this.infoLoading = false
            }, 600)
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
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
  },
  methods: {
       handleCustomSearch (value) {
      fetch(value, (data) => (this.fuzzyProjectList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      this.queryParam.companyId = value
      fetch(value, (data) => (this.fuzzyProjectList = data))
    }
  },
  watch: {
    refresh: function (newVal, oldVal) {
          this.$refs.salaryTable.refresh(true)
    }
  }
}
</script>
