import request from '@/utils/request'
export default {
   //分页查询试卷列表
   listPage(page,limit,searchObj) {
    return request({
      url: `/admin/core/user/listPage/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //根据id删除用户
  removeById(id){
    return request({
      url: `/admin/core/user/removeById/${id}`,
      method: 'delete'
    })
  },
  //根据id查询用户
  getById(id) {
    return request({
      url: `/admin/core/user/getById/${id}`,
      method: 'get'
    })
  },
  //新增用户
  save(user) {
    return request({
      url: `/admin/core/user/save`,
      method: 'post',
      data: user
    })
  },
  //修改科目
  updateById(user) {
    return request({
      url: `/admin/core/user/update`,
      method: 'put',
      data: user
    })
  }
}
