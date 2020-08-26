<template>
  <myinfo :customId="customId" :refresh="refresh">
    <template v-slot:freshButton>
      <a-button type="primary" size="small" @click="refresh=!refresh">刷新</a-button>
    </template>
  </myinfo>
</template>
<script>
import myinfo from './components/MyInfoComponent'
export default {
  name: 'CustomInfo',
  components: {
    myinfo
  },
  data: function () {
    return {
      customId: '',
      refresh: false
    }
  },
  activated () {
    this.refresh = !this.refresh
    if (this.$route.params.customId !== undefined) {
      this.customId = String(this.$route.params.customId)
    }
  }
}
</script>
