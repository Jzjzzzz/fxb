import request from '@/utils/request'
export default {
  
    getListSubject(){
      return request({
        url: `/front/core/subject/listSubject`,
        method: 'get',
      })
  }
}