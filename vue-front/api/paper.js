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
}