import request from '@/utils/request'
export default {
  
  getHotPaper(){
      return request({
        url: `/front/core/paper/getHotPaper`,
        method: 'get',
      })
  },
  getPageList(page, limit, searchObj) {
    return request({
      url: `/front/core/paper/getFrontPaperList/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  getPaperTopicById(id){
    return request({
      url: `/front/core/paper/getPaperTopicById/${id}`,
      method: 'get',
    })
  },
  automaticCorrecting(answer) {
    return request({
      url: `/front/core/paper/automaticCorrecting`,
      method: 'post',
      data: answer
    })
  },
  
}