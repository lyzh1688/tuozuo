<template>
  <div class="main">
    <a-form
      id="formLogin"
      class="user-layout-login"
      ref="formLogin"
      :form="form"
      @submit="handleSubmit"
    >
      <a-tabs
        :activeKey="customActiveKey"
        :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
        @change="handleTabClick"
      >
        <a-tab-pane key="tab1" tab="账号密码登录">
          <a-alert
            v-if="isLoginError"
            type="error"
            showIcon
            style="margin-bottom: 24px;"
            message="账户,密码或用户组错误"
          />
          <a-form-item>
            <a-input
              size="large"
              type="text"
              placeholder="账户"
              v-decorator="[
                'username',
                {rules: [{ required: true, message: '请输入帐户名' }, { validator: handleUsernameOrEmail }], validateTrigger: 'change'}
              ]"
            >
              <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }" />
            </a-input>
          </a-form-item>

          <a-form-item>
            <a-input-password
              size="large"
              placeholder="密码"
              v-decorator="[
                'password',
                {rules: [{ required: true, message: '请输入密码' }], validateTrigger: 'blur'}
              ]"
            >
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }" />
            </a-input-password>
          </a-form-item>
          <a-form-item>
            <a-select
              @change="handleSelectRoleGroup"
              v-decorator="[
                'roleGroup',
                {rules: [{ required: true, message: '请选择用户组' }], validateTrigger: 'change', initialValue:'custom'}
              ]"
            >
              <a-select-option value="custom">客户</a-select-option>
              <a-select-option value="staff">管理员</a-select-option>
            </a-select>
          </a-form-item>
        </a-tab-pane>
      </a-tabs>

      <!-- <a-form-item>
        <a-checkbox v-decorator="['rememberMe', { valuePropName: 'checked' }]">自动登录</a-checkbox>
      </a-form-item> -->

      <a-form-item style="margin-top:24px">
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="login-button"
          :loading="state.loginBtn"
          :disabled="state.loginBtn"
        >确定</a-button>
      </a-form-item>

      <div class="user-login-other">
        <span>其他登录方式</span>
        <a>
          <a-icon class="item-icon" type="wechat"></a-icon>
        </a>
      </div>
    </a-form>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import { timeFix } from '@/utils/util'
import { success, errorMessage, needLogin } from '@/utils/helper/responseHelper'
import RsaEncrypt from '@/components/RSAHelper/RsaEncrypt'
import { getPublicKey } from '@/api/login'
export default {
  name: 'Login',
  data () {
    return {
      customActiveKey: 'tab1',
      loginBtn: false,
      // login type: 0 email, 1 username, 2 telephone
      loginType: 0,
      isLoginError: false,
      requiredTwoStepCaptcha: false,
      stepCaptchaVisible: false,
      form: this.$form.createForm(this),
      state: {
        time: 60,
        loginBtn: false,
        // login type: 0 email, 1 username, 2 telephone
        loginType: 0,
        smsSendBtn: false
      }
    }
  },
  created () {},
  methods: {
    ...mapActions(['Login', 'Logout']),
    // handler
    handleSelectRoleGroup (value) {},
    handleUsernameOrEmail (rule, value, callback) {
      const { state } = this
      const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (regex.test(value)) {
        state.loginType = 0
      } else {
        state.loginType = 1
      }
      callback()
    },
    handleTabClick (key) {
      this.customActiveKey = key
      // this.form.resetFields()
    },
    handleSubmit (e) {
      e.preventDefault()
      const {
        form: { validateFields },
        state,
        Login
      } = this

      state.loginBtn = true

      const validateFieldsKey = ['username', 'password', 'roleGroup']

      validateFields(validateFieldsKey, { force: true }, (err, values) => {
        if (!err) {
          // console.log('login form', values)
          const loginParams = { ...values }
          // delete loginParams.username
          // loginParams[!state.loginType ? 'email' : 'username'] = values.username
          getPublicKey(loginParams.username, loginParams.roleGroup)
            .then(response => {
              const result = response
              if (success(result)) {
                loginParams.password = RsaEncrypt.rsaData(values.password, result.data.publicKey)
                // console.log(loginParams.password)
                Login(loginParams)
                  .then(res => this.loginSuccess(res))
                  .catch(err => this.requestFailed(err))
                  .finally(() => {
                    state.loginBtn = false
                  })
              } else {
                this.$notification.error({
                  message: errorMessage(result),
                  description: '登录失败。请稍后再试'
                })
              }
              setTimeout(() => {
                state.loginBtn = false
              }, 600)
            })
            .catch(error => {
              this.$notification.error({
                message: '登录失败。请稍后再试',
                description: error
              })
              setTimeout(() => {
                state.loginBtn = false
              }, 600)
            })
        } else {
          setTimeout(() => {
            state.loginBtn = false
          }, 600)
        }
      })
    },
    loginSuccess (res) {
      console.log(res)
      // check res.homePage define, set $router.push name res.homePage
      // Why not enter onComplete
      /*
      this.$router.push({ name: 'analysis' }, () => {
        console.log('onComplete')
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`
        })
      })
      */
     const toPath = this.$store.getters.activeKey == null || this.$store.getters.activeKey === '' ? '/' : this.$store.getters.activeKey
      // console.log('toPath', toPath, this.$store.getters.activeKey)
      // 处理切换用户登录
      // if (res.isRefresh) {
      //   console.log('res.isRefresh', res.isRefresh)
      //   this.$store.dispatch('setAppExculdeList', ['UserLayout'])
      //   toPath = '/'
      // }
      this.$router.push({ path: toPath })
      // 延迟 1 秒显示欢迎信息
      setTimeout(() => {
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`
        })
      }, 1000)
      this.isLoginError = false
    },
    requestFailed (err) {
      this.isLoginError = true
      if (!needLogin(err)) {
      this.$notification['error']({
        message: '错误',
        description: (((err.response || {}).data || {}).message || errorMessage(err)) || '请求出现错误，请稍后再试',
        duration: 4
      })
      }
    }
  }
}
</script>

<style lang="less" scoped>
.user-layout-login {
  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
