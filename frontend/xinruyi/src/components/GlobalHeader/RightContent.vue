<template>
  <div :class="wrpCls">
    <avatar-dropdown :menu="showMenu" :current-user="currentUser" :class="prefixCls" />
  </div>
</template>

<script>
import AvatarDropdown from './AvatarDropdown'
import MultiTab from '@/components/MultiTab'

export default {
  name: 'RightContent',
  components: {
    AvatarDropdown,
    MultiTab
  },
  props: {
    prefixCls: {
      type: String,
      default: 'ant-pro-global-header-index-action'
    },
    prefixClsTab: {
      type: String,
      default: 'ant-pro-global-header-index-muti-tab'
    },
    isMobile: {
      type: Boolean,
      default: () => false
    },
    topMenu: {
      type: Boolean,
      required: true
    },
    theme: {
      type: String,
      required: true
    },
    username: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      showMenu: true,
      currentUser: {}
    }
  },
  computed: {
    wrpCls () {
      return {
        'ant-pro-global-header-index-right': true,
        [`ant-pro-global-header-index-${(this.isMobile || !this.topMenu) ? 'light' : this.theme}`]: true
      }
    }
  },
  mounted () {
    setTimeout(() => {
      this.currentUser = {
        name: this.username
      }
    }, 1500)
  }
}
</script>
