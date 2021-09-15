import request from '@/utils/request'
export default {
  //分页查询科目列表
  listPage(page,limit) {
    return request({
      url: `/admin/core/log/listPage/${page}/${limit}`,
      method: 'get',
    })
  },
  
}
