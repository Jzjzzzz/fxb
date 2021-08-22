import request from '@/utils/request'
export default {
  
  //新增试卷
  save(paper) {
    return request({
      url: `/admin/core/paper/savePaper`,
      method: 'post',
      data: paper
    })
  },
  //分页查询试卷列表
  listPage(page,limit,searchObj) {
    return request({
      url: `/admin/core/paper/listPage/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //根据id删除试卷
  removeById(id){
    return request({
      url: `/admin/core/paper/removeById/${id}`,
      method: 'delete'
    })
  },
  //根据id查询试卷
  getById(id) {
    return request({
      url: `/admin/core/paper/getById/${id}`,
      method: 'get'
    })
  },
  //修改试卷
  updateById(paperSaveVo) {
    return request({
      url: `/admin/core/paper/updatePaperById`,
      method: 'put',
      data: paperSaveVo
    })
  }
}
