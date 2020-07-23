<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-skeleton :loading="infoLoading" active title>
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="10" :sm="8">
              <a-form-item label="客户名称">
                <a-select
                  show-search
                  :value="queryParam.customName"
                  placeholder="input search text"
                  style="width: 200px"
                  :default-active-first-option="false"
                  :show-arrow="false"
                  :filter-option="false"
                  :not-found-content="null"
                  @search="handleCustomSearch"
                  @change="handleCustomChange"
                >
                  <a-select-option v-for="d in fuzzyCustomList" :key="d.text">{{ d.text }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="8">
              <a-form-item label="是否完成支付">
                <a-select v-model="queryParam.hasPaid" placeholder="请选择" default-value>
                  <a-select-option value="1">已支付</a-select-option>
                  <a-select-option value="0">未支付</a-select-option>
                  <a-select-option value>不限制</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item>
                <a-button type="primary" size="small" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button
                  size="small"
                  type="primary"
                  @click="()=>{queryParam= {
                    hasPaid: '',
                    customName: '',
                    pageNo: 1,
                    pageSize: 20
                  }}"
                >重置</a-button>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item>
                <a-button :loading="confirmLoading" size="small" @click="handleAdd">新增客户</a-button>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-skeleton>
    </a-card>
    <a-card style="margin-top: 24px" :bordered="false" title="客户列表">
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
          <a-button size="small" @click="handleUpdate(record)" :loading="confirmLoading">修改</a-button>
          <a-button size="small" @click="handleops(record)" :loading="confirmLoading">余额变动</a-button>
        </span>
        <a slot="customName" slot-scope="text,record" @click="toCustomInfo(record)">{{ text }}</a>
      </s-table>
    </a-card>
    <custominfoform
      ref="CustomInfoForm"
      :visible="customOpsVisible"
      :loading="confirmLoading"
      :model="customMdl"
      :customTypemap="customTypList"
      @cancel="handleCancel"
      @ok="handleOk"
    />
    <fundoperation
      ref="fundoperation"
      :visible="fundOpsVisible"
      :loading="confirmLoading"
      :model="fundMdl"
      @cancel="handleCancel"
      @ok="handleFundOpsOk"
    />
  </page-header-wrapper>
</template>
<script>
import { STable } from '@/components'
import { getCustomList, dictQuery, fuzzyQueryCustom, addCustom, updateCustom, tradeOpration } from '@/api/company'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { mapState } from 'vuex'
import custominfoform from './forms/CustomInfoForm'
import fundoperation from './forms/FundOperation'
import md5 from 'md5'
const columns = [
  {
    title: '编号',
    scopedSlots: { customRender: 'no' }
  },
  {
    title: '客户名称',
    dataIndex: 'customName',
    scopedSlots: { customRender: 'customName' }
  },
  {
    title: '是否完成支付',
    dataIndex: 'hasPaid',
    customRender: text => {
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
    title: '客户类型',
    dataIndex: 'customType',
    scopedSlots: { customRender: 'customType' }
  },
  {
    title: '最后更新时间',
    dataIndex: 'updateDate',
    scopedSlots: { customRender: 'updateDate' }
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
let timeout
let currentValue

function fetch (value, callback) {
  if (timeout) {
    clearTimeout(timeout)
    timeout = null
  }
  currentValue = value

  function fake () {
    fuzzyQueryCustom(value, 20).then(d => {
      if (currentValue === value) {
        const result = d.data
        const data = []
        result.forEach(r => {
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
  name: 'CustomList',
  components: {
    STable,
    custominfoform,
    fundoperation
  },
  data () {
    this.columns = columns
    return {
      fundOpsVisible: false,
      fundMdl: null,
      customMdl: null,
      customOpsVisible: false,
      confirmLoading: false,
      customInfo: {},
      fuzzyCustomList: [],
      infoLoading: false,
      isupdate: false,
      customTypeMap: {},
      customTypList: [],
      queryParam: {
        hasPaid: '',
        customName: '',
        pageNo: 1,
        pageSize: 20
      },
      loadData: parameter => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        return getCustomList(
          requestParameters.customName,
          requestParameters.hasPaid,
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then(Response => {
            const result = Response
            // console.log('getTradeflow', result)
            if (success(result)) {
              const ans = result.data.customers
              return {
                pageSize: requestParameters.pageSize,
                pageNo: requestParameters.pageNo,
                totalCount: result.data.total,
                data: ans
              }
            } else {
              this.$notification.error({
                message: errorMessage(result),
                description: '查询客户信息失败，请稍后再试'
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
              message: '查询客户信息失败，请稍后再试',
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
    handleFundOpsOk () {
      const form = this.$refs.fundoperation.form
      form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          tradeOpration(values)
            .then(response => {
              const result = response
              if (success(result)) {
                this.$notification.success({
                  message: '余额修改成功'
                })
                const form = this.$refs.fundoperation.form
                form.resetFields() // 清理表单数据（可不做）
                this.$refs.table.refresh(true)
                this.fundOpsVisible = false
                this.confirmLoading = false
              } else {
                this.confirmLoading = false
                this.$notification.error({
                  message: errorMessage(result),
                  description: '余额修改成功'
                })
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
            updateCustom(values, values.customId)
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
            addCustom(values)
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
    getDict () {
      dictQuery('customType')
        .then(Response => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            this.customTypList = result.data
            this.customTypeMap = {}
            for (const i of result.data) {
              this.customTypeMap[i.id] = i.name
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
    this.getDict()
  }
}
</script>
