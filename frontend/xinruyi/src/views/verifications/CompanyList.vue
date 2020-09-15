<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="8">
            <a-form-item label="公司名称">
              <a-select
                show-search
                v-model="queryParam1.companyId"
                placeholder="请输入公司名称"
                style="width: 200px"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleCustomSearch2"
                @change="handleCustomChange2"
              >
                <a-select-option v-for="d in fuzzyCompanyList" :key="d.value">{{ d.text }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item label="公司状态">
              <a-select style="width:200px;" v-model="queryParam1.companyStatus" placeholder="请选择">
                <a-select-option
                  v-for=" projectStatusItem in companyStatusList"
                  :key="projectStatusItem.id"
                >{{ projectStatusItem.name }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="8">
            <a-form-item label="行业类型">
              <a-select v-model="queryParam1.industryType" style="width: 200px">
                <a-select-option
                  v-for="province in industryTypeList"
                  :key="province.id"
                >{{ province.name }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="10">
            <a-form-item label="地区">
              <a-select v-model="queryParam1.province" style="width: 120px" @change="handleChane">
                <a-select-option
                  v-for="province in provinceList"
                  :key="province.areaCode"
                >{{ province.areaName }}</a-select-option>
              </a-select>
              <a-select v-model="queryParam1.city" style="width: 120px" @change="handleChane2">
                <a-select-option
                  v-for="cityItem in cityList"
                  :key="cityItem.areaCode"
                >{{ cityItem.areaName }}</a-select-option>
              </a-select>
              <a-select v-model="queryParam1.district" style="width: 120px">
                <a-select-option
                  v-for="cityItem in districtList"
                  :key="cityItem.areaCode"
                >{{ cityItem.areaName }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="10" :sm="8">
            <a-form-item label="申请时间">
              <a-date-picker
                valueFormat="YYYYMMDD"
                format="YYYY-MM-DD"
                v-model="queryParam1.beginDate"
              />
              <a-date-picker
                valueFormat="YYYYMMDD"
                format="YYYY-MM-DD"
                v-model="queryParam1.endDate"
              />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item>
              <a-button
                :disabled="projectListLoading"
                type="primary"
                size="small"
                @click="$refs.table.refresh(true)"
              >查询</a-button>
              <a-button
                :disabled="projectListLoading"
                size="small"
                type="primary"
                @click="()=>{ queryParam1= {
                  companyStatus: '',
                  industryType: '',
                  beginDate: '',
                  endDate: '',
                  companyId: '',
                  province: '',
                  city: '',
                  district: '',
                  pageNo: 1,
                  pageSize: 20
                }}"
              >重置</a-button>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <s-table
        ref="table"
        size="default"
        rowKey="projectId"
        :pageSize="20"
        :columns="columns"
        :data="loadData"
        :showPagination="true"
      >
        <span slot="no" slot-scope="text, record, index">{{ index + 1 }}</span>
        <span slot="status" slot-scope="text">{{ companyStatusMap[text] }}</span>
        <span slot="industryType" slot-scope="text">{{ industryTypeMap[text] }}</span>
        <span
          slot="area"
          slot-scope="text, record"
        >{{ record.provinceName }}-{{ record.cityName }}-{{ record.districtName }}</span>
        <span slot="ops" slot-scope="text, record">
          <a-button
            :disabled="record.status!='1'&&record.status!='4'"
            size="small"
            type="primary"
            @click="handleverify(record)"
            :loading="confirmLoading"
          >审核</a-button>
          <a-button
            :disabled="record.status=='1'||record.status=='3'"
            size="small"
            @click="fetchCompanyDetail(record)"
            :loading="confirmLoading"
          >详情</a-button>
        </span>
      </s-table>
      <companyform
        title="审核企业认证申请"
        ref="companyform"
        :clearUpload="clearUpload"
        :visible="companyVisible"
        :loading="confirmLoading"
        :model="companyMdl"
        :isShowOnly="isShowOnly"
        @cancel="handleCancel"
        @ok="handleOk"
      >
        <template v-slot:other v-if="isShowOnly">
          <a-form-item label="审核结果" v-if="isShowOnly&&isverify">
            <a-select
              :disabled="isShowOnly&&!isverify"
              style="width:200px;"
              v-decorator="['status', {rules: [{required: true, message: '请选择状态！'}], validateTrigger: 'blur'}]"
              placeholder="请选择"
            >
              <a-select-option value="1">审核成功</a-select-option>
              <a-select-option value="0">审核失败</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="公司状态" v-if="isShowOnly&&!isverify">
            <a-select
              :disabled="isShowOnly&&!isverify"
              style="width:200px;"
              v-decorator="['status', {rules: [{required: true, message: '请选择状态！'}], validateTrigger: 'blur'}]"
              placeholder="请选择"
            >
              <a-select-option
                v-for=" projectStatusItem in companyStatusList"
                :key="projectStatusItem.id"
              >{{ projectStatusItem.name }}</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="备注">
            <a-textarea
              :disabled="isShowOnly&&!isverify"
              v-decorator="['remark', {rules: [{required: true, message: '请输备注！'}], validateTrigger: 'blur'}]"
            />
          </a-form-item>
        </template>
      </companyform>
      <companyspotform
        title="审核企业入住申请"
        ref="companyspotform"
        :visible="companyspotformVisible"
        :loading="confirmLoading"
        :model="companyMdl"
        :isShowOnly="isShowOnly"
        @cancel="handleCancel"
        @ok="handleOk"
      ></companyspotform>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { Modal } from 'ant-design-vue'
import { STable } from '@/components'
import { fuzzyQueryCompany, getCompanyDetail } from '@/api/company'
import { getCompanyEventList, doCompanyAuth, docompanySpot } from '@/api/events'
import { getCommonDict, getAreaCode } from '@/api/dictionary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import companyform from '@/views/user/forms/CompanyAuthentication'
import companyspotform from './form/CompanySpotForm'
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
    title: '行业类型',
    dataIndex: 'industryType',
    scopedSlots: { customRender: 'industryType' }
  },
  {
    title: '地区',
    dataIndex: 'area',
    scopedSlots: { customRender: 'area' }
  },
  {
    title: '申请日期',
    dataIndex: 'applyDate',
    scopedSlots: { customRender: 'applyDate' }
  },
  {
    title: '申请状态',
    dataIndex: 'status',
    scopedSlots: { customRender: 'status' }
  },
  {
    title: '联系方式',
    dataIndex: 'contact',
    scopedSlots: { customRender: 'contact' }
  },
  {
    title: '备注',
    dataIndex: 'remark',
    scopedSlots: { customRender: 'remark' }
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

let timeout2
let currentValue2

function fetch2 (value, callback) {
  if (timeout2) {
    clearTimeout(timeout2)
    timeout2 = null
  }
  currentValue2 = value

  function fake2 () {
    fuzzyQueryCompany(value, 20).then((d) => {
      if (currentValue2 === value) {
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

  timeout2 = setTimeout(fake2, 300)
}
export default {
  name: 'CompanyVerificationList',
  data () {
    this.columns = columns
    return {
      clearUpload: false,
      companyVisible: false,
      confirmLoading: false,
      companyMdl: {},
      isverify: false,
      verifyType: '',
      isShowOnly: false,
      companyspotformVisible: false,
      formTitle: '',
      queryParam1: {
        companyStatus: '',
        industryType: '',
        beginDate: '',
        endDate: '',
        companyId: '',
        province: '',
        city: '',
        district: ''
      },
      projectListLoading: false,
      provinceList: [],
      cityList: [],
      districtList: [],
      fuzzyCompanyList: [],
      industryTypeList: [],
      industryTypeMap: {},
      companyStatusList: [],
      companyStatusMap: {},
      loadData: (parameter) => {
        const requestParameters = Object.assign({}, parameter, this.queryParam1)
        console.log('loadData request parameters:', requestParameters)
        return getCompanyEventList(
          requestParameters.companyId,
          requestParameters.industryType,
          requestParameters.province,
          requestParameters.city,
          requestParameters.district,
          requestParameters.companyStatus,
          requestParameters.beginDate,
          requestParameters.endDate,
          requestParameters.pageNo,
          requestParameters.pageSize
        )
          .then((Response) => {
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
                description: '查询项目列表失败，请稍后再试'
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
              message: '查询项目列表失败，请稍后再试',
              description: error
            })
          })
      }
    }
  },
  components: {
    STable,
    companyform,
    companyspotform
  },
  created () {
    fetch('', (data) => (this.fuzzyProjectList = data))
    fetch2('', (data) => (this.fuzzyCompanyList = data))
    this.getAreaCode('province', '').then((response) => {
      this.provinceList = response
    })
    this.getDict('companyStatus').then((response) => {
      this.companyStatusList = response
      this.companyStatusMap = {}
      for (const i of response) {
        this.companyStatusMap[i.id] = i.name
      }
    })
    this.getDict('industryType').then((response) => {
      this.industryTypeList = response
      this.industryTypeMap = {}
      for (const i of response) {
        this.industryTypeMap[i.id] = i.name
      }
    })
  },
  methods: {
    getAreaCode (level, code) {
      return new Promise((resolve, reject) => {
        getAreaCode(level, code).then((Response) => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
            resolve(result.data)
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询区域信息失败'
            })
          }
        })
      })
    },
    fetchCompanyDetail (record, mark) {
      this.formTitle = '公司详情'
      this.isShowOnly = true
      this.isverify = false
      this.confirmLoading = true
      getCompanyDetail(record.companyId)
        .then((response) => {
          const result = response
          if (success(result)) {
            if (record.status !== '1') {
              this.companyMdl = {
                ...result.data,
                status: record.status
              }
              if (mark) {
                this.companyMdl.remark = ''
                this.companyMdl.status = ''
              }
              this.companyVisible = true
            } else {
            }
            this.confirmLoading = false
          } else {
            this.confirmLoading = false
            this.$notification.error({
              message: errorMessage(result),
              description: '获取项目详情失败'
            })
          }
        })
        .catch((error) => {
          this.$notification.error({
            message: '获取项目详情失败',
            description: error
          })
          this.confirmLoading = false
        })
    },
    async handleChane (index) {
      await this.getAreaCode('city', index).then((response) => {
        this.cityList = response
      })
      this.queryParam1.city = ''
      this.queryParam1.district = ''
    },
    async handleChane2 (index) {
      await this.getAreaCode('district', index).then((response) => {
        this.districtList = response
      })
      this.queryParam1.district = ''
    },
    handleOk () {
      if (this.isShowOnly && !this.isverify) {
        const form = this.$refs.companyform.form
        this.clearUpload = !this.clearUpload
        form.resetFields() // 清理表单数据（可不做）
        const form2 = this.$refs.companyspotform.form
        this.clearUpload = !this.clearUpload
        form2.resetFields()
        this.$refs.table.refresh(true)
        this.companyVisible = false
        this.companyspotformVisible = false
        return
      }
      if (this.verifyType === '1') {
        const form = this.$refs.companyspotform.form
        form.validateFields((errors, values) => {
          if (!errors) {
            this.confirmLoading = true
            values['password'] = md5(values['password'])
            docompanySpot(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '审核成功'
                  })
                  const form = this.$refs.companyspotform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.companyspotformVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '审核失败'
                  })
                }
                if (needLogin(result)) {
                  this.companyspotformVisible = false
                  this.confirmLoading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '审核失败',
                  description: error
                })
                this.confirmLoading = false
              })
          }
        })
      } else {
        const form = this.$refs.companyform.form
        form.validateFields((errors, values) => {
          if (!errors) {
            this.confirmLoading = true
            doCompanyAuth(values)
              .then((response) => {
                const result = response
                if (success(result)) {
                  this.$notification.success({
                    message: '审核成功'
                  })
                  const form = this.$refs.companyform.form
                  this.clearUpload = !this.clearUpload
                  form.resetFields() // 清理表单数据（可不做）
                  this.$refs.table.refresh(true)
                  this.companyVisible = false
                  this.confirmLoading = false
                } else {
                  this.confirmLoading = false
                  this.$notification.error({
                    message: errorMessage(result),
                    description: '审核失败'
                  })
                }
                if (needLogin(result)) {
                  this.companyVisible = false
                  this.confirmLoading = false
                }
              })
              .catch((error) => {
                this.$notification.error({
                  message: '审核失败',
                  description: error
                })
                this.confirmLoading = false
              })
          }
        })
      }
    },
    handleAdd () {
      this.formTitle = '新建项目'
      this.companyMdl = {}
      const form = this.$refs.companyform.form
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
      this.isverify = false
      this.isShowOnly = false
      this.companyVisible = true
    },
    async handleverify (record) {
      this.verifyType = record.status
      const form = this.$refs.companyform.form
      this.clearUpload = !this.clearUpload
      form.resetFields() // 清理表单数据（可不做）
      const form2 = this.$refs.companyspotform.form
      form2.resetFields()
      if (record.status === '1') {
        this.companyMdl = {
          companyName: record.companyName,
          province: record.provinceName,
          city: record.cityName,
          district: record.districtName,
          industryType: record.industryType,
          registerId: JSON.parse(record.snapshot)['registerId']
        }
        this.companyspotformVisible = true
      } else {
        await this.fetchCompanyDetail(record, true)
        this.companyVisible = true
      }
      this.isverify = true
      this.isShowOnly = true
    },
    handleCancel () {
      if (this.isverify) {
        Modal.confirm({
          title: '取消操作',
          content: '是否确认取消操作？所做的修改将会丢失！',
          onOk: () => {
            this.companyVisible = false
            this.companyspotformVisible = false
          },
          onCancel () {}
        })
      } else {
        this.companyVisible = false
        this.companyspotformVisible = false
      }
    },
    handleCustomSearch2 (value) {
      fetch(value, (data) => (this.fuzzyCompanyList = data))
    },
    handleCustomChange2 (value) {
      // console.log(value)
      this.queryParam1.companyId = value
      fetch2(value, (data) => (this.fuzzyCompanyList = data))
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
  }
}
</script>

<style>
</style>
