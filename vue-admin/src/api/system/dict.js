import request from '@/utils/request'
export default {
  //查询顶级字典列表
  listByTop(page,limit,searchObj) {
    return request({
      url: `/admin/core/dict/listByTop/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //查询子节点字典列表
  listByParentId(page,limit,parentId,searchObj) {
    return request({
      url: `/admin/core/dict/listByParentId/${page}/${limit}/${parentId}`,
      method: 'post',
      data: searchObj
    })
  },
  //根据id删除节点
  removeByIdTop(id){
    return request({
      url: `/admin/core/dict/removeByIdTop/${id}`,
      method: 'delete'
    })
  },
  //新增顶级节点
  saveTop(dict) {
    return request({
      url: `/admin/core/dict/saveTop`,
      method: 'post',
      data: dict
    })
  },
  //新增子节点
  saveSun(parentId,dict) {
    return request({
      url: `/admin/core/dict/saveSun/${parentId}`,
      method: 'post',
      data: dict
    })
  },
  //根据字典id查询字典
  getById(id) {
    return request({
      url: `/admin/core/dict/getById/${id}`,
      method: 'get'
    })
  },
  updateById(dict) {
    return request({
      url: `/admin/core/dict/update`,
      method: 'put',
      data: dict
    })
  },
  //根据父类id清空redis缓存
  emptyDictByParentId(parentId) {
    return request({
      url: `/admin/core/dict/emptyDictByParentId/${parentId}`,
      method: 'get'
    })
  },

}
