<template>
  <a-drawer
    height="320"
    title="项目新增人员"
    placement="top"
    :closable="false"
    :visible="visible"
    :afterVisibleChange="visibleChange"
    @close="() => { $emit('onclose') }"
  >
    <a-spin :spinning="staffListLoading">
      <a-form>
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item>
          <a-list :grid="{ gutter: 16, column: 4 }" :data-source="staffList">
            <a-list-item slot="renderItem" slot-scope="item">
              <a-card :title="item.staffName">
                <span>身份证：{{ item.idNo }}</span>
                <a-divider type="vertical" />
                <a-button
                  type="primary"
                  size="small"
                  shape="circle"
                  icon="plus"
                  @click="handleAdd(item)"
                ></a-button>
              </a-card>
            </a-list-item>
          </a-list>
        </a-form-item>
      </a-form>
    </a-spin>
    <projectstaffopssubdrawer
      ref="projectstaffopssubdrawer"
      :subvisible="projectstaffopssubdrawerVisible"
      :projectId="projectId"
      :model="staffMdl"
      :staffId="staffid"
      :drawerTitle="drawerTitle"
      @onclose="projectstaffopssubdrawerVisible = false"
      @refreshlist="$emit('refreshlist')"
    ></projectstaffopssubdrawer>
  </a-drawer>
</template>

<script>
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { fuzzyQueryStaff } from '@/api/humanResource'
import projectstaffopssubdrawer from './ProjectStaffOpsSubDrawer'
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
    staffInProjectList: {
      type: Object,
      default: () => []
    },
    projectId: {
      type: String,
      default: () => ''
    }
  },
  components: {
    projectstaffopssubdrawer
  },
  data () {
    return {
      // city: citiesHepler[0].label,
      salaryList: [],
      staffid: '',
      staffListLoading: false,
      projectstaffopssubdrawerVisible: false,
      staffMdl: {},
      drawerTitle: '',
      queryParam: {
        beginDate: '',
        endData: '',
        projectId: ''
      },
      staffList: []
    }
  },
  created () {},
  methods: {
    handleAdd (record) {
      this.drawerTitle = '添加人员：' + record.staffName + ':' + record.idNo
      this.staffMdl = {
        staffId: record.staffId,
        projectId: this.projectId
      }
      const form = this.$refs.projectstaffopssubdrawer.form
      form.resetFields() // 清理表单数据（可不做）
      this.projectstaffopssubdrawerVisible = true
    },
    fetchProlectList () {
      this.staffListLoading = true
      fuzzyQueryStaff('', 20, true)
        .then((Response) => {
          const result = Response
          if (success(result)) {
            this.staffList = []
            // console.log(this.staffInProjectList)
            for (const i of result.data) {
              if (this.staffInProjectList[i.staffId] === undefined) {
                this.staffList.push(i)
              }
            }
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询人员列表失败'
            })
          }
          if (needLogin(result)) {
            this.staffListLoading = false
            this.visible = false
          }
          setTimeout(() => {
            this.staffListLoading = false
          }, 600)
        })
        .catch((error) => {
          this.staffListLoading = false
          this.$notification.error({
            message: '查询人员列表失败',
            description: error
          })
        })
    },
    visibleChange (data) {
      if (data) {
        this.fetchProlectList()
      }
    }
  },
  watch: {
      staffInProjectList (oldvalue, newvalue) {
          this.fetchProlectList()
      }
  }
}
</script>
