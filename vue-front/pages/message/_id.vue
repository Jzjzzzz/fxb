<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /考试详情 开始 -->
    <section class="container">
      <!-- /考试封面介绍 -->
      <div class="mt20 c-infor-box" >
        <article class="fl" style="width:1200px">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title"> 
                  <h3 style="padding-left:500px">{{form.title}}</h3>
                </section>
              </div>
              <p style="font-size:16px" v-html="form.content"></p>
            </div>    
          </section> 
        </article>
       
      </div>
    </section>
    <!-- /考试详情 结束 -->
  </div>
</template>
<script>
import '/static/DynamicButton/style.css'
import messageApi from '@/api/message'
import cookie from 'js-cookie'
export default {
  data () {
    return {
      loginInfo:{
        id: 0,
        age: '',
        avatar: '',
        mobile: '',
        nickname: '',
        sex: ''
      }, 
      form:{}
    }
  },
  created () {
    //用户信息
    this.showInfo()
    this.fetchData()
  },
  methods:{
    //从cookie中获取用户信息
     showInfo() {
      //debugger
      var jsonStr = cookie.get("guli_ucenter");
      
      if (jsonStr) { 
        this.loginInfo = JSON.parse(jsonStr)
      }
      if(this.loginInfo.id==0){
        this.$router.push({ path: '/login' })
      }
    },
   fetchData(){
     messageApi.getById(this.$route.params.id)
     .then(response=>{
       this.form = response.data.data.model
     })
   }
  }
};
</script>
