<template>
  <a-drawer
    width="480"
    title="客户标签"
    placement="right"
    :closable="false"
    :visible="visible"
    :afterVisibleChange="visibleChange"
    @close="() => { $emit('onclose') }"
  >
    <a-spin :spinning="tagListLoading">
      <a-form>
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item>
          <a-transfer
            :data-source="tagList"
            show-search
            :filter-option="filterOption"
            :target-keys="targetKeys"
            :render="item => item.title"
            @change="handleChange"
            @search="handleSearch"
          />
        </a-form-item>
        <a-button
          type="primary"
          size="small"
          @click="handleSave()"
        >保存</a-button>
      </a-form>
    </a-spin>
  </a-drawer>
</template>

<script>
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import { fuzzygetClientTags } from '@/api/clientTag'
import { addClientTags } from '@/api/client'
export default {
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    clientTagList: {
      type: Array,
      default: () => []
    },
    clientId: {
      type: String,
      default: ''
    }
  },
  components: {
  },
  data () {
    return {
      // city: citiesHepler[0].label,
      salaryList: [],
      staffid: '',
      tagListLoading: false,
      projectstaffopssubdrawerVisible: false,
      staffMdl: {},
      drawerTitle: '',
      queryParam: {
        beginDate: '',
        endData: '',
        projectId: ''
      },
      targetKeys: [],
      tagList: []
    }
  },
  created () {},
  methods: {
    handleSave () {
      this.tagListLoading = true
      const form = {
        clientId: this.clientId,
        tags: this.targetKeys
      }
      addClientTags(form)
        .then((Response) => {
          const result = Response
          if (success(result)) {
            this.$emit('onclose')
            // console.log(this.clientTagList)
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '修改客户标签失败'
            })
          }
          if (needLogin(result)) {
            this.tagListLoading = false
            this.visible = false
          }
          setTimeout(() => {
            this.tagListLoading = false
          }, 600)
        })
        .catch((error) => {
          this.tagListLoading = false
          this.$notification.error({
            message: '修改客户标签失败',
            description: error
          })
        })
    },
    filterOption (inputValue, option) {
      return option.title.indexOf(inputValue) > -1
    },
    handleChange (targetKeys, direction, moveKeys) {
      // console.log(targetKeys, direction, moveKeys)
      this.targetKeys = targetKeys
    },
    handleSearch (dir, value) {
      console.log('search:', dir, value)
    },
    fetchTagList () {
      this.tagListLoading = true
      fuzzygetClientTags('', 9999)
        .then((Response) => {
          const result = Response
          if (success(result)) {
            this.tagList = result.data.map(v => {
              return {
          key: v.tagId,
          title: v.tagName
        }
            })
            // console.log(this.clientTagList)
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询标签列表失败'
            })
          }
          if (needLogin(result)) {
            this.tagListLoading = false
            this.visible = false
          }
          setTimeout(() => {
            this.tagListLoading = false
          }, 600)
        })
        .catch((error) => {
          this.tagListLoading = false
          this.$notification.error({
            message: '查询标签列表失败',
            description: error
          })
        })
    },
    visibleChange (data) {
      if (data) {
        this.targetKeys = this.clientTagList
        this.fetchTagList()
      }
    }
  },
  watch: {
      clientTagList (oldvalue, newvalue) {
          this.fetchTagList()
      }
  }
}
</script>
