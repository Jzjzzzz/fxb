import request from '@/utils/request'
export default {
    getTestRecordList(page,limit,userId){
        return request({
          url: `/front/core/testRecords/getTestRecordList/${page}/${limit}/${userId}`,
          method: 'post'
        })
    },
    getTestTopicListById(paperId){
      return request({
        url: `/front/core/testTopic/getTestTopicListById/${paperId}`,
        method: 'get'
      })
  },
  //收藏错题
  collectionError(topicId){
    return request({
      url: `/front/core/testTopic/collectionError/${topicId}`,
      method:'put'
    })
  },
   //错题列表
   getCollectionList(userId){
    return request({
      url: `/front/core/testTopic/getCollectionList/${userId}`,
      method:'get'
    })
  },
  //取消收藏
  cancelCollection(topicId){
    return request({
      url: `/front/core/testTopic/cancelCollection/${topicId}`,
      method:'put'
    })
  },


  
}