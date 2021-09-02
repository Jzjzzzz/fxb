import request from '@/utils/request'
export default {
  getListBanner() {
    return request({
      url: `/front/core/config/getSlideshow`,
      method: 'get'
    })
  }
}