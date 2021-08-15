import request from '@/utils/request'

const api_name = '/admin/statistics/daily'
export default {

  createStatistics(day) {
    return request({
      url: `/staservice/sta/registerCount/${day}`,
      method: 'post'
    })
  },
  //获取统计数据
  showChart(searchObj) {
      return request({
          url: `/staservice/sta/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
          method: 'get'
      })
  }
}