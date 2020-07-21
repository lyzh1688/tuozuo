const tagsView = {
  state: {
    fullPathList: [],
    pages: [],
    cachedPages: [],
    activeKey: '',
    notInTablist: ['/login'],
    newTabIndex: 0,
    appExcludList: ['UserLayout']
  },
  mutations: {
    SET_APP_EXCLUDE_LIST: (state, key) => {
      console.log('SET_APP_EXCLUDE_LIST', key)
      state.appExcludList = key
    },
    SET_ACTIVE_KEY: (state, key) => {
      // console.log('setActiveKey2', key)
      let flag = true
      for (const i of state.notInTablist) {
        if (key.includes(i)) {
          flag = false
        }
      }
      if (flag) {
        state.activeKey = key
      }
    },
    RESET: (state) => {
      state = {
        fullPathList: [],
        pages: [],
        cachedPages: [],
        activeKey: '',
        notInTablist: ['/login'],
        newTabIndex: 0
      }
      console.log(state)
    },
    ADD_FULLPATH_LIST: (state, key) => {
      // console.log(state.fullPathList, key)
      const currentIndex = state.fullPathList.indexOf(key)
      let flag = true
      for (const i of state.notInTablist) {
        if (key.includes(i)) {
          flag = false
        }
      }
      // console.log('ADD_FULLPATH_LIST', key)
      if (currentIndex < 0 && flag) { state.fullPathList = [...state.fullPathList, key] }
    },
    ADD_PAGES: (state, key) => {
      // console.log(state.fullPathList, key)
      const currentIndex = state.fullPathList.indexOf(key.fullPath)
      let flag = true
      for (const i of state.notInTablist) {
        if (key.fullPath.includes(i)) {
          flag = false
        }
      }
      // console.log('key.meta.noCache', key.meta.noCache)
      if (currentIndex < 0 && flag) {
        state.pages = [...state.pages, key]
        // console.log('key.meta.noCache', key.meta.noCache)
        if (!key.meta.noCache || key.meta.noCache === undefined) {
          state.cachedPages = [...state.cachedPages, key]
        }
        // console.log('state.cachedPages', state.cachedPages)
      }
    },
    REMOVE_FULLPATH_LIST: (state, key) => {
      state.pages = [...state.pages.filter(page => page.fullPath !== key)]
      state.fullPathList = [...state.fullPathList.filter(path => path !== key)]
      state.cachedPages = [...state.cachedPages.filter(path => path.fullPath !== key)]
    },
    ADD_VISITED_VIEWS: (state, view) => {
      if (state.visitedViews.some(v => v.path === view.path)) return
      const tmp = (view.path).split('/').pop()
      // console.log('path' + view.path + view.query)
      state.visitedViews.push({
        name: view.name,
        fakeName: tmp,
        path: view.path,
        title: view.meta.title || 'no-name'
      })
      if (!view.meta.noCache) {
        state.cachedViews.push(view.name)
      }
    },
    DEL_VISITED_VIEWS: (state, view) => {
      for (const [i, v] of state.visitedViews.entries()) {
        if (v.path === view.path) {
          state.visitedViews.splice(i, 1)
          break
        }
      }
      for (const i of state.cachedViews) {
        // console.log('删除：' + i)
        // console.log('删除：' + view.name)
        if (i === view.name) {
          const index = state.cachedViews.indexOf(i)
          state.cachedViews.splice(index, 1)
          break
        }
      }
    },
    DEL_OTHERS_VIEWS: (state, view) => {
      for (const [i, v] of state.visitedViews.entries()) {
        if (v.path === view.path) {
          state.visitedViews = state.visitedViews.slice(i, i + 1)
          break
        }
      }
      for (const i of state.cachedViews) {
        if (i === view.name) {
          const index = state.cachedViews.indexOf(i)
          state.cachedViews = state.cachedViews.slice(index, i + 1)
          break
        }
      }
    },
    DEL_ALL_VIEWS: (state) => {
      state.visitedViews = []
      state.cachedViews = []
    },
    SET_ATCIVE_VIEW: (state, view) => {
      state.activeView = view.path
    }
  },
  actions: {
    setAppExculdeList ({ commit }, view) {
      commit('SET_APP_EXCLUDE_LIST', view)
    },
    resetTabViews ({ commit }) {
      commit('RESET')
    },
    setActiveKey ({ commit }, view) {
      commit('SET_ACTIVE_KEY', view)
    },
    addFullPathList ({ commit }, view) {
      commit('ADD_FULLPATH_LIST', view)
    },
    addPages ({ commit }, view) {
      commit('ADD_PAGES', view)
    },
    remove ({ commit }, view) {
      commit('REMOVE_FULLPATH_LIST', view)
    },
    addVisitedViews ({ commit }, view) {
      commit('ADD_VISITED_VIEWS', view)
    },
    delVisitedViews ({ commit, state }, view) {
      return new Promise((resolve) => {
        commit('DEL_VISITED_VIEWS', view)
        resolve([...state.visitedViews])
      })
    },
    delOthersViews ({ commit, state }, view) {
      return new Promise((resolve) => {
        commit('DEL_OTHERS_VIEWS', view)
        resolve([...state.visitedViews])
      })
    },
    delAllViews ({ commit, state }) {
      return new Promise((resolve) => {
        commit('DEL_ALL_VIEWS')
        resolve([...state.visitedViews])
      })
    },
    setActiveView ({ commit }, view) {
      commit('SET_ATCIVE_VIEW', view)
    }
  }
}

export default tagsView
