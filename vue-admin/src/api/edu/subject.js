import request from '@/utils/request'
export default {
  //分页查询科目列表
  listPage(page,limit,searchObj) {
    return request({
      url: `/admin/core/subject/listPage/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //根据id删除科目
  removeById(id){
    return request({
      url: `/admin/core/subject/removeById/${id}`,
      method: 'delete'
    })
  },
  //新增科目
  save(subject) {
    return request({
      url: `/admin/core/subject/save`,
      method: 'post',
      data: subject
    })
  },
  //根据id查询科目
  getById(id) {
    return request({
      url: `/admin/core/subject/getById/${id}`,
      method: 'get'
    })
  },
  //修改科目
  updateById(subject) {
    return request({
      url: `/admin/core/subject/update`,
      method: 'put',
      data: subject
    })
  }

}
