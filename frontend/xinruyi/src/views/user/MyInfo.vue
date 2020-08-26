<template>
  <myinfo :customId="customId" :refresh="refresh" >
    <template v-slot:freshButton>
      <a-button type="primary" size="small" @click="refresh=!refresh">刷新</a-button>
    </template>
  </myinfo>
</template>
<script>
import myinfo from './components/MyInfoComponent'
import { mapState } from 'vuex'

export default {
  name: 'MyInfo',
  components: {
    myinfo
  },
  data: function () {
    return {
      customId: '',
      refresh: false
    }
  },
  computed: {
    ...mapState({
      username: state => state.user.name
    })
  },
  activated () {
      this.refresh = !this.refresh
   this.customId = String(this.username)
  }
}
</script>
