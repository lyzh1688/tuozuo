<template>
  <page-header-wrapper>
    <a-card :bordered="false" title="我的公司列表">
      <a-form layout="inline">
        <a-row>
          <a-col :md="12" :sm="24">
            <a-form-item>
              <a-button
                type="primary"
                size="small"
                @click="()=>{$refs.table.refresh(true)}">查询</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :md="12" :sm="24">
            <a-form-item>
              <a-button
                size="small"
                @click="()=>{$refs.table.refresh(true)}">创建公司申请</a-button>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <s-table
        ref="table"
        size="default"
        rowKey="customId"
        :pageSize="20"
        :columns="columns"
        :data="loadData"
        showPagination="auto"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="customType" slot-scope="text">{{ customTypeMap[text] }}</span>
        <span slot="ops" slot-scope="text, record">
          <a-button size="small" @click="handleUpdate(record)" :disabled="record.registerStatus==='2'" :loading="confirmLoading">修改</a-button>
        </span>
        <a slot="customName" slot-scope="text,record" @click="toCustomInfo(record)">{{ text }}</a>
      </s-table>
    </a-card>
  </page-header-wrapper>
</template>
<script>
import { STable } from '@/components'
import { getCompanyList, dictQuery, addCompany, updateCompany } from '@/api/company'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { mapState } from 'vuex'
import md5 from 'md5'
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '公司名称',
    dataIndex: 'companyName',
    scopedSlots: { customRender: 'companyName' }
  },

  {
    title: '业务状态',
    dataIndex: 'companyStatus',
    scopedSlots: { customRender: 'companyStatus' }
  },
  {
    title: '开始服务时间',
    dataIndex: 'beginDate',
    scopedSlots: { customRender: 'beginDate' }
  },
  {
    title: '结束服务时间',
    dataIndex: 'endDate',
    scopedSlots: { customRender: 'endDate' }
  },
   {
    title: '公司类型',
    dataIndex: 'companyType',
    scopedSlots: { customRender: 'companyType' }
  },
   {
    title: '综合税率',
    dataIndex: 'tax',
    scopedSlots: { customRender: 'tax' }
  },
  {
    title: '操作',
    dataIndex: 'ops',
    scopedSlots: { customRender: 'ops' }
  }
]
// const statusMap = {
//   0: {
//     status: 'default',
//     text: '关闭'
//   },
//   1: {
//     status: 'processing',
//     text: '运行中'
//   },
//   2: {
//     status: 'success',
//     text: '已上线'
//   },
//   3: {
//     status: 'error',
//     text: '异常'
//   }
// }
export default {
  name: 'MycompanyList',
  components: {
    STable
  },
  data () {
    this.columns = columns
    return {
      clearUpload: false,
      customMdl: null,
      customOpsVisible: false,
      confirmLoading: false,
      customInfo: {},
      bizStatus: [],
      bizStatusMap: {},
      fuzzyCustomList: [],
      infoLoading: false,
      isupdate: false,
      customTypeMap: {},
      customTypList: [],
      queryParam1: {
        companyStatus: '',
        pageNo: 1,
        pageSize: 20
      },
      queryParam2: {
        companyStatus: '',
        pageNo: 1,
        pageSize: 20
      },
      loadData: parameter => {
        const requestParameters = Object.assign({}, parameter, this.queryParam1)
        console.log('loadData request parameters:', requestParameters)
        return getCompanyList(
          requestParameters.companyStatus,
          '3',
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then(Response => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.companies
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询公司列表失败，请稍后再试'
              })
            }
            setTimeout(() => {
              this.tradeflowLoading = false
            }, 600)
            return []
          })
          .catch(error => {
            setTimeout(() => {
              this.tradeflowLoading = false
            }, 600)
            this.$notification.error({
              message: '查询公司列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  methods: {
    handleops (record) {
      this.fundOpsVisible = true
      this.fundMdl = { ...record }
    },
    handleOk () {
      const form = this.$refs.CustomInfoForm.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          values['province'] = values['province'] + '-' + values['area'] + '-' + values['city']
          values['customPswd'] = md5(values['customPswd'])
          delete values.ctiy
          delete values.area
          if (this.isupdate) {
            updateCompany(values, values.customId)
              .then(response => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '修改成功'
                  })
                  const form = this.$refs.CustomInfoForm.form
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.customOpsVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '修改失败'
                  })
                }
              })
              .catch(error => {
                this.$notification.error({
                  message: '修改失败。请稍后再试',
                  description: error
                })
                this.confirmLoading = false
              })
          } else {
            addCompany(values)
              .then(response => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '新增成功'
                  })
                  const form = this.$refs.CustomInfoForm.form
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.customOpsVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '新增失败。请稍后再试'
                  })
                }
                if (needLogin(result)) {
                  this.customOpsVisible = false
                  this.confirmLoading = false
                }
              })
              .catch(error => {
                this.$notification.error({
                  message: '新增失败。请稍后再试',
                  description: error
                })
                this.confirmLoading = false
              })
          }
        }
      })
    },
    handleCancel () {
      this.customOpsVisible = false
      this.fundOpsVisible = false

      // const form = this.$refs.createModal.form
      // form.resetFields() // 清理表单数据（可不做）
    },
    handleAdd () {
      this.isupdate = false
      this.customOpsVisible = true
    },
    handleUpdate (record) {
      this.isupdate = true
      this.customOpsVisible = true
      this.customMdl = { ...record }
      const province = this.customMdl.province.split('-')
      if (province.length === 3) {
        this.customMdl.province = province[0]
        this.customMdl['area'] = province[1]
        this.customMdl['city'] = province[2]
      } else {
        this.customMdl.province = ''
      }
      console.log(this.customMdl, record)
    },
    toCustomInfo (value) {
      this.$router.push({ name: 'CustomInfo', params: { customId: value.customId } })
    },
    handleCustomSearch (value) {
      fetch(value, data => (this.fuzzyCustomList = data))
    },
    handleCustomChange (value) {
      // console.log(value)
      this.queryParam.customName = value
      fetch(value, data => (this.fuzzyCustomList = data))
    },
    handleDefault (value) {
      return value === undefined ? '暂无数据' : String(value)
    },
    getDictBizStatus () {
      dictQuery('bizStatus')
        .then(Response => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            this.bizStatus = result.data
            this.bizStatus.push({ id: '', name: '不限制' })
            this.bizStatusMap = {}
            for (const i of result.data) {
              this.bizStatusMap[i.id] = i.name
            }
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询字典失败'
            })
          }
          setTimeout(() => {
            this.infoLoading = false
          }, 600)
        })
        .catch(error => {
          this.infoLoading = false
          this.$notification.error({
            message: '查询字典失败',
            description: error
          })
        })
    }
  },
  computed: {
    ...mapState({
      username: state => state.user.name
    }),
    rowSelection () {
      return {
        selectedRowKeys: this.selectedRowKeys,
        onChange: this.onSelectChange
      }
    }
  },
  // created () {
  //   this.getDict()
  // },
  activated () {
    this.$refs.table.refresh(true)
    this.getDictBizStatus()
  }
}
</script>
