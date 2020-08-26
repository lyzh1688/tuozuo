<template>
  <span>{{ area }}</span>
</template>
<script>
import { getAreaCode } from '@/api/company'
import { success, errorMessage } from '@/utils/helper/responseHelper'
export default {
  name: 'AreaShow',
  props: {
    provinceCode: {
      type: String,
      required: true,
      default: ''
    },
    cityCode: {
      type: String,
      default: ''
    },
    districtCode: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
        province: '',
        city: '',
        district: '',
      provinceList: {},
      cityList: {},
      districtList: {}
    }
  },
  computed: {
      area () {
          console.log(this.districtCode, this.districtList[this.districtCode])
          return (this.provinceList[this.provinceCode] === undefined ? '' : this.provinceList[this.provinceCode]) + '-' + (this.cityList[this.cityCode] === undefined ? '' : this.cityList[this.cityCode]) + '-' + (this.districtList[this.districtCode] === undefined ? '' : this.districtList[this.districtCode])
      }
  },
  watch: {
      provinceCode: function (value) {
           if (value && value !== '') {
        this.handleChane(value)
      }
      },
      cityCode: function (value) {
        if (value && value !== '') {
        this.handleChane2(value)
      }
      }

  },
  created () {
       this.getAreaCode('province', '').then((response) => {
      this.provinceList = response
    })
     if (this.provinceCode && this.provinceCode !== '') {
        this.getAreaCode('city', this.provinceCode).then((response) => {
        this.cityList = response
      })
      }
      if (this.cityCode && this.cityCode !== '') {
        this.getAreaCode('district', this.cityCode).then((response) => {
        this.districtList = response
      })
      }
  },
  methods: {
getAreaCode (level, code) {
      return new Promise((resolve, reject) => {
        getAreaCode(level, code).then((Response) => {
          const result = Response
          // console.log('dictQuery', result)
          if (success(result)) {
              const map = {}
              for (const i of result.data) {
                  map[i.areaCode] = i.areaName
              }
            resolve(map)
          } else {
            this.$notification.error({
              message: errorMessage(result),
              description: '查询区域信息失败'
            })
          }
        })
      })
    },
    async handleChane (index) {
      await this.getAreaCode('city', index).then((response) => {
        this.cityList = response
      })
    },
    async handleChane2 (index) {
      await this.getAreaCode('district', index).then((response) => {
        this.districtList = response
      })
    }
  }
}
</script>
