import storage from 'store'
import { login, logout } from '@/api/login'
import { success } from '@/utils/helper/responseHelper'
import { ACCESS_TOKEN, USER_NAME, AUTHORITY, LAST_USER } from '@/store/mutation-types'
import { welcome } from '@/utils/util'
const user = {
  state: {
    lastUser: '',
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_LAST_USER: (state, name) => {
      state.lastUser = name
      storage.set(LAST_USER, name, 7 * 24 * 60 * 60 * 1000)
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    }
  },

  actions: {
    // 登录
    Login ({ commit, dispatch }, userInfo) {
      return new Promise((resolve, reject) => {
        const username = userInfo.username
        login(userInfo).then(response => {
          const result = response
          // result['isRefresh'] = false
          const lastUser = storage.get(USER_NAME)
          commit('SET_LAST_USER', storage.get(USER_NAME))
          if (success(result)) {
            storage.set(ACCESS_TOKEN, result.data.accessToken, 7 * 24 * 60 * 60 * 1000)
            storage.set(USER_NAME, username, 7 * 24 * 60 * 60 * 1000)
            storage.set(AUTHORITY, result.data.authority, 7 * 24 * 60 * 60 * 1000)
            commit('SET_TOKEN', result.accessToken)
            commit('SET_ROLES', [])
            if (lastUser !== username) {
              // dispatch('setAppExculdeList', ['UserLayout', 'BasicLayout'])
              commit('TAB_RESET')
              commit('SET_ACTIVE_KEY', '/')
              // result['isRefresh'] = true
            }
            resolve(result)
          } else {
            commit('SET_TOKEN', '')
            commit('SET_ROLES', [])
            reject(result)
          }
        }).catch(error => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          reject(error)
        })
      })
    },
    UpadtelastUser ({ commit, dispatch }, userInfo) {
      return new Promise((resolve, reject) => {
        commit('SET_LAST_USER', userInfo)
        resolve()
      })
    },
    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        // getInfo().then(response => {
        //   const result = response.result

        //   if (result.role && result.role.permissions.length > 0) {
        //     const role = result.role
        //     role.permissions = result.role.permissions
        //     role.permissions.map(per => {
        //       if (per.actionEntitySet != null && per.actionEntitySet.length > 0) {
        //         const action = per.actionEntitySet.map(action => { return action.action })
        //         per.actionList = action
        //       }
        //     })
        //     role.permissionList = role.permissions.map(permission => { return permission.permissionId })
        //     commit('SET_ROLES', result.role)
        //     commit('SET_INFO', result)
        //   } else {
        //     reject(new Error('getInfo: roles must be a non-null array !'))
        //   }

        //   commit('SET_NAME', { name: result.name, welcome: welcome() })
        //   commit('SET_AVATAR', result.avatar)

        //   resolve(response)
        // }).catch(error => {
        //   reject(error)
        // })
        if (storage.get(AUTHORITY) != null) {
          const role = {
            permissionList: [storage.get(AUTHORITY)]
          }
          commit('SET_ROLES', role)
          commit('SET_INFO', {
            name: storage.get(USER_NAME)
          })
        } else {
          reject(new Error('getInfo: roles must be a non-null array !'))
        }
        commit('SET_LAST_USER', storage.get(LAST_USER))
        commit('SET_NAME', { name: storage.get(USER_NAME), welcome: welcome() })
        commit('SET_AVATAR', '')

        resolve({
          result: {
            role: {
              permissionList: [storage.get(AUTHORITY)]
            }
          }
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          storage.remove(ACCESS_TOKEN)
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          storage.remove(ACCESS_TOKEN)
        })
      })
    }

  }
}

export default user
