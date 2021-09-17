import request from '@/utils/request'

export default {

    getUserById(id){
        return request({
          url: `/ucenterservice/apimember/getById/${id}`,
          method: 'get',
        })
    },
    updatePersonal(user) {
    return request({
      url: `/ucenterservice/apimember/updatePersonal`,
      method: 'post',
      data: user
    })
  },
  updatePasswordById(userPasswordVo) {
    return request({
      url: `/ucenterservice/apimember/updatePassword`,
      method: 'post',
      data: userPasswordVo
    })
  }

}