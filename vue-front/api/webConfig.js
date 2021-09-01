import request from '@/utils/request'

export default {

    getWebConfigRedis() {
    return request({
      url: `/front/core/config/getWebConfigRedis`,
      method: 'get'
    })
  }

}