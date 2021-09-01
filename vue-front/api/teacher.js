import request from '@/utils/request'
const api_name = '/edu/teacher'
export default {
  getPageList(page, limit) {   
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontList/${page}/${limit}`,
      method: 'post'
    })
  },
  getById(teacherId) {
    return request({
        url: `/eduservice/teacherfront/getTeacherFrontInfo/${teacherId}`,
        method: 'get'
    })
}
}