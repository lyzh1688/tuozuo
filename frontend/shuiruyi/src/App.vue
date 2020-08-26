<template>
  <a-config-provider :locale="locale">
    <div id="app">
      <keep-alive exclude="UserLayout">
        <router-view />
      </keep-alive>
    </div>
  </a-config-provider>
</template>

<script>
import { domTitle, setDocumentTitle } from '@/utils/domUtil'
import { i18nRender } from '@/locales'

export default {
  data () {
    return {
      // cacheList: ['UserLayout']
    }
  },
  computed: {
    locale () {
      // 只是为了切换语言时，更新标题
      const { title } = this.$route.meta
      title && setDocumentTitle(`${i18nRender(title)} - ${domTitle}`)

      return this.$i18n.getLocaleMessage(this.$store.getters.lang).antLocale
    }
    // cachedPages () {
    //   return this.$store.state.tagsView.appExcludList
    // }
  }
  // watch: {
  //   cachedPages: {
  //     handler: function (val, oldval) {
  //       console.log('excludeList', val)
  //       this.cacheList = []
  //       for (const i of this.$store.getters.appExcludList) {
  //         this.cacheList.push(i.name)
  //       }
  //     },
  //     deep: true // 对象内部的属性监听，也叫深度监听
  //   }
  // }
}
</script>
