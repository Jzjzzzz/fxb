import request from '@/utils/request'
export default {
  
  //新增科目
  save(paper) {
    return request({
      url: `/admin/core/paper/savePaper`,
      method: 'post',
      data: paper
    })
  },
  
}
