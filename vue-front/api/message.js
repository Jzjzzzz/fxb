import request from '@/utils/request'
export default {
  
 
  getPageList(page, limit) {
    return request({
      url: `/front/core/message/getFrontMessageList/${page}/${limit}`,
      method: 'post'   
    })
  },
  //根据id查询公告
  getById(id) {
    return request({
      url: `/admin/core/message/getById/${id}`,
      method: 'get'
    })
  },
 
  
}