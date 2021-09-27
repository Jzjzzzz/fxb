import request from '@/utils/request'
export default {
  //分页查询公告列表
  listPage(page,limit,searchObj) {
    return request({
      url: `/admin/core/message/listPage/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //根据id删除公告
  removeById(id){
    return request({
      url: `/admin/core/message/removeById/${id}`,
      method: 'delete'
    })
  },
  //新增公告
  save(noticeMessage) {
    return request({
      url: `/admin/core/message/save`,
      method: 'post',
      data: noticeMessage
    })
  },
  //根据id查询公告
  getById(id) {
    return request({
      url: `/admin/core/message/getById/${id}`,
      method: 'get'
    })
  },
  //修改公告
  updateById(noticeMessage) {
    return request({
      url: `/admin/core/message/update`,
      method: 'put',
      data: noticeMessage
    })
  }

}
