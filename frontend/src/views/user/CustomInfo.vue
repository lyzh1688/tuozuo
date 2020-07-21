<template>
  <Myinfo :customId="customId" />
</template>
<script>
import MyInfo from './MyInfo'
export default {
  name: 'CustomInfo',
  data: function () {
    return {
      customId: '111'
    }
  },
  components: {
    MyInfo
  },
  created () {
      if (this.$router.query.customId !== undefined) {
          this.customId = this.$router.query.customId
      }
  }
}
</script>
