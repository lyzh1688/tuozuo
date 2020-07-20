const tagsView = {
  state: {
    fullPathList: [],
      pages: [],
      cachedPages: [],
      activeKey: '',
      newTabIndex: 0
  },
  mutations: {
    SET_ACTIVE_KEY: (state, key) => {
      // console.log('setActiveKey2', key)
      state.activeKey = key
    },
    ADD_FULLPATH_LIST: (state, key) => {
      // console.log(state.fullPathList, key)
      const currentIndex = state.fullPathList.indexOf(key)
      if (currentIndex < 0) { state.fullPathList.push(key) }
    },
    ADD_PAGES: (state, key) => {
      // console.log(state.fullPathList, key)
      const currentIndex = state.fullPathList.indexOf(key.fullPath)
      if (currentIndex < 0) { state.pages.push(key) }
      if (!key.meta.noCache) {
        state.cachedPages.push(key)
      }
    },
    REMOVE_FULLPATH_LIST: (state, key) => {
      state.pages = state.pages.filter(page => page.fullPath !== key)
      state.fullPathList = state.fullPathList.filter(path => path !== key)
      state.cachedPages = state.cachedPages.filter(path => path !== key)
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
