const getters = {
  isMobile: state => state.app.isMobile,
  lang: state => state.app.lang,
  theme: state => state.app.theme,
  color: state => state.app.color,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  nickname: state => state.user.name,
  welcome: state => state.user.welcome,
  roles: state => state.user.roles,
  userInfo: state => state.user.info,
  addRouters: state => state.permission.addRouters,
  multiTab: state => state.app.multiTab,
  activeKey: state => state.tagsView.activeKey,
  pages: state => state.tagsView.pages,
  cachedPages: state => state.tagsView.cachedPages,
  fullPathList: state => state.tagsView.fullPathList,
  appExcludList: state => state.tagsView.appExcludList,
  lastUser: state => state.user.lastUser
}

export default getters
