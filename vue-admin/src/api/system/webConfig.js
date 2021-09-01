import request from '@/utils/request'
export default {
  getWebConfig(params) {
    return request({
      url: '/admin/core/config/getWebConfig',
      method: 'get',
      params
    })
  },
  editWebConfig(params) {
    return request({
      url: '/admin/core/config/editWebConfig',
      method: 'put',
      data: params
    })
  },
  uploadImage(data) {
    return request({
      url: '/eduoss/fileoss/upload',
      method: 'post',
      data: data
    })
  },
  delete(url) {
    return request({
      url: '/eduoss/fileoss/remove?url=' + url,
      method: 'delete'
    })
  }
}
