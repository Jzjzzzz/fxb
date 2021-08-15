import request from '@/utils/request'
export default {
  //字典数据
  getByParentId() {
    return request({
      url: `/admin/core/topic/getByParentId`,
      method: 'get'
    })
  },
  //学科列表
  listSubject(){
    return request({
      url: `/admin/core/subject/listSubject`,
      method: 'get'
    })
  },
  //新增单选题
  saveTopic(topic) {
    return request({
      url: `/admin/core/topic/saveTopic`,
      method: 'post',
      data: topic
    })
  },

  //列表数据
  listTopicPage(page,limit,searchObj){
    return request({
      url: `/admin/core/topic/listByTopic/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },

  //根据id删除题目
  removeById(id){
    return request({
      url: `/admin/core/topic/removeById/${id}`,
      method: 'delete'
    })
  },
  //根据id查询题目
  getById(id){
    return request({
      url: `/admin/core/topic/getById/${id}`,
      method: 'get'
    })
  },
  //根据id修改题目
  updateTopicById(topicSaveVo){
    return request({
      url: `/admin/core/topic/updateTopicById`,
      method: 'put',
      data : topicSaveVo
    })
  },
}
