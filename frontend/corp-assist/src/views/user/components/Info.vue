<template>
  <div class="header-info">
    <span>{{ title }}</span>
    <p><a-avatar class="avatar" v-if="logo!==''" shape="square" :src="logo" />{{ value }}</p>
    <slot name="value"/>
    <em v-if="bordered" />
  </div>
</template>

<script>
export default {
  name: 'UserInfo',
  props: {
    title: {
      type: String,
      default: ''
    },
    value: {
      type: String,
      default: ''
    },
    logo: {
      type: String,
      default: ''
    },
    bordered: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style lang="less" scoped>
@import "~ant-design-vue/es/style/themes/default.less";

.header-info {
  position: relative;
  height: 10vh;
  text-align: center;
  & > span {
    display: inline-block;
    margin-bottom: 4px;
    color: @text-color-secondary;
    font-size: @font-size-base;
    line-height: 22px;
  }
  & > p {
    margin: 0;
    color: @heading-color;
    font-size: 24px;
    line-height: 32px;
  }
  & > span {
    margin: 0;
    color: @heading-color;
    font-size: 18px;
    line-height: 32px;
  }
  & > em {
    position: absolute;
    top: 0;
    right: 0;
    width: 1px;
    height: 56px;
    background-color: @border-color-split;
  }
}

</style>
