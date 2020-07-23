<template>
  <myinfo :customId="customId" />
</template>
<script>
import myinfo from './MyInfo'
export default {
  name: 'CustomInfo',
  components: {
    myinfo
  },
  data: function () {
    return {
      customId: ''
    }
  },
  activated () {
    console.log(this.$route.params.customId)
    if (this.$route.params.customId !== undefined) {
      this.customId = ''
      this.customId = this.$route.params.customId
    }
  }
}
</script>
