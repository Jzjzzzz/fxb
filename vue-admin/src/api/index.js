import request from '@/utils/request'
export default {
  
  //新增试卷
  getCount() {
    return request({
      url: `/admin/core/index/getCount`,
      method: 'get'
    })
  }
}
