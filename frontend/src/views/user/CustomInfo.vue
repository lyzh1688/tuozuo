<template>
  <Myinfo :customId="customId" />
</template>
<script>
import MyInfo from './MyInfo'
export default {
  name: 'CustomInfo',
  data: function () {
    return {
      customId: ''
    }
  },
  components: {
    MyInfo
  },
  created () {
      if (this.$route.params.customId !== undefined) {
          this.customId = this.$route.params.customId
      }
  }
}
</script>
