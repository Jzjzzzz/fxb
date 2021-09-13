import request from '@/utils/request'
export default {
    getTestRecordList(page,limit,userId){
        return request({
          url: `/front/core/testRecords/getTestRecordList/${page}/${limit}/${userId}`,
          method: 'post',
        })
    },
    getTestTopicListById(paperId){
      return request({
        url: `/front/core/testTopic/getTestTopicListById/${paperId}`,
        method: 'get',
      })
  },

  
}