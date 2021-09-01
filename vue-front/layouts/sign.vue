<template>
  <div class="sign">
    <!--标题-->
    <div class="logo">
      <img src="~/assets/img/logo3.png" alt="logo">
    </div>
    <!--表单-->
    <nuxt/>
  </div>
</template>
<script>
import webConfigApi from "@/api/webConfig" //（网站配置）



export default {
  data(){
    return{
      webconfig:{
        logo:'',
        name:'',
        summary:'',
        keyword:'',
        author:'',
        recordNum:'',
        github:'',
        gitee:'',
        qqNumber:'',
        email:'',
        weChat:'',

      }, //网站配置
     
    }
  },
  created(){
    

    this.getWebConfig()
  },
  methods:{
    //获取网站配置信息
    getWebConfig(){
      webConfigApi.getWebConfigRedis()
      .then(response=>{
        this.webconfig = response.data.data.webConfig
      })
    },
  }
};
</script>