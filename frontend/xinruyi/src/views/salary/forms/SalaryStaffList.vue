<template>
  <a-modal
    title="工资单详情"
    :width="900"
    :visible="visible"
    :confirmLoading="loading"
    @ok="handleOK"
    @cancel="handleCancel"
  >
    <a-spin :spinning="loading">
      <a-form :form="form">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-row>
          <a-col :span="6">
            <a-form-item label="公司名称">
              <a-button class="editable-add-btn" @click="handleAdd"> 新增 </a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="48">
            <a-form-item>
              <a-table bordered :data-source="dataSource" :columns="columns" rowKey="staffId">
                <template slot="salary" slot-scope="text, record">
                  <editablenumcolumn :text="text" @change="onCellChange(record.staffId, 'salary', $event)" />
                </template>
                <template slot="operation" slot-scope="text, record">
                  <a-popconfirm v-if="dataSource.length" title="Sure to delete?" @confirm="() => onDelete(record.staffId)">
                    <a href="javascript:;">删除</a>
                  </a-popconfirm>
                </template>
              </a-table>
            </a-form-item>
          </a-col>
        </a-row>
        <slot name="other"></slot>
      </a-form>
    </a-spin>
    <projectstaffopsdrawer
      ref="ProjectStaffOpsDrawer"
      :visible="projectStaffOpsDrawerVisible"
      :projectId="projectId"
      :staffInProjectList="staffIdMap"
      @onclose="projectStaffOpsDrawerVisible = false"
      @add="doAdd"
    ></projectstaffopsdrawer>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { Modal } from 'ant-design-vue'
import { getDetailsalaryList, domodifySalaryList } from '@/api/salary'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { getCommonDict } from '@/api/dictionary'
import projectstaffopsdrawer from './ProjectStaffOpsDrawer'
import editablenumcolumn from './editableNumColumn'
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
    title: '姓名',
    dataIndex: 'name',
    scopedSlots: { customRender: 'name' }
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
    title: '操作',
    dataIndex: 'operation',
    scopedSlots: { customRender: 'operation' }
  }

]
export default {
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    model: {
      type: Object,
      default: () => null
    },
    paymentId: {
      type: String,
      default: () => ''
    },
    projectId: {
      type: String,
      default: () => ''
    },
    companyId: {
      type: String,
      default: () => ''
    },
    period: {
      type: String,
      default: () => ''
    }
  },
  components: {
    editablenumcolumn,
    projectstaffopsdrawer
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
      loading: false,
      staffIdMap: {},
      projectStaffOpsDrawerVisible: false,
      genderMap: {},
      cityIndex: 0,
      areaIndex: 0,
      industryTypeList: [],
      // secondCity: citiesHepler[0].children[0].label,
      // secondCities: citiesHepler[0].children,
      form: this.$form.createForm(this),
      dataSource: []
    }
  },
  watch: {
    visible (value) {
      if (value) {
        this.getsalaryDetailList()
      }
    }
  },
  created () {
    console.log('custom modal created')
    // 防止表单未注册
    fields.forEach((v) => this.form.getFieldDecorator(v))
    this.getsalaryDetailList()
    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
    })
  },
  methods: {
    handleCancel () {
      Modal.confirm({
        title: '取消操作',
        content: '是否确认取消操作？所做的修改将会丢失！',
        onOk: () => {
          this.$emit('cancel')
        },
        onCancel () {}
      })
    },
    handleOK () {
      Modal.confirm({
        title: '确认提交',
        content: '是否确认提交？将会更新工资列表！',
        onOk: () => {
          const tmpBody = {
            companyId: this.companyId,
            projectId: this.projectId,
            period: this.period,
            paymentId: this.paymentId,
            staffSalary: []
          }
          for (const i of this.dataSource) {
            if (i.salary === '-1') {
              this.$message.error(i.name + '的工资未设置，为-1！请修改后再保存！')
              return
            }
            tmpBody.staffSalary.push({
              staffId: i.staffId,
              salary: i.salary
            })
          }
          domodifySalaryList(tmpBody)
            .then((Response) => {
              const result = Response
              // console.log('getTradeflow', result)
              if (success(result)) {
                this.$emit('ok')

                setTimeout(() => {
                  this.loading = false
                }, 600)
              } else {
                this.$notification.error({
                  message: errorMessage(result),
                  description: '保存失败！'
                })
                setTimeout(() => {
                  this.loading = false
                }, 600)
              }
              if (needLogin(result)) {
                this.visible = false
              }
            })
            .catch((error) => {
              setTimeout(() => {
                this.loading = false
              }, 600)
              this.$notification.error({
                message: '保存失败！',
                description: error
              })
            })
        },
        onCancel () {}
      })
    },
    getsalaryDetailList () {
      this.dataSource = []
      getDetailsalaryList(this.paymentId, this.projectId, '', '', '', 1, 1000)
        .then((Response) => {
          const result = Response
          // console.log('getTradeflow', result)
          if (success(result)) {
            this.dataSource = result.data.staffs
            this.staffIdMap = {}
            for (const i of this.dataSource) {
                this.staffIdMap[i.staffId] = i.staffId
            }
            setTimeout(() => {
              this.loading = false
            }, 600)
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询资源池列表失败，请稍后再试'
            })
            setTimeout(() => {
              this.loading = false
            }, 600)
          }
          if (needLogin(result)) {
            this.visible = false
          }
        })
        .catch((error) => {
          setTimeout(() => {
            this.loading = false
          }, 600)
          this.$notification.error({
            message: '查询工资列表失败，请稍后再试',
            description: error
          })
        })
    },
    onCellChange (key, dataIndex, value) {
      const dataSource = [...this.dataSource]
      const target = dataSource.find((item) => item.staffId === key)
      if (target) {
        target[dataIndex] = value
        this.dataSource = dataSource
      }
    },
    onDelete (key) {
      const dataSource = [...this.dataSource]
      this.dataSource = dataSource.filter((item) => item.staffId !== key)
    },
    handleAdd () {
      this.projectStaffOpsDrawerVisible = true
    },
    doAdd (record) {
      const { dataSource } = this
      const newData = {
        name: record.staffName,
        idNo: record.idNo,
        staffId: record.staffId,
        salary: -1
      }
      this.staffIdMap[record.staffId] = record.staffId
      this.dataSource = [...dataSource, newData]
      this.projectStaffOpsDrawerVisible = false
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
<style lang="less" scoped>

.editable-add-btn {
  margin-bottom: 8px;
}
</style>
