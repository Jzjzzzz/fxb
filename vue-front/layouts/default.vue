<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#" :title="webconfig.name">
            <img :src="webconfig.logo"  :alt="webconfig.name">
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/paper" tag="li" active-class="current">
              <a>试卷</a>
            </router-link>
            <router-link to="/records" tag="li" active-class="current" v-show="this.loginInfo.id!=0" >
              <a>考试记录</a>
            </router-link>
            <router-link to="/colletion" tag="li" active-class="current" v-show="this.loginInfo.id!=0">
              <a>错题集</a>
            </router-link>

          </ul>
           <!-- / nav -->
          <ul class="h-r-login">
              <li v-if="!loginInfo.id" id="no-login">
                  <a href="/login" title="登录">
                      <em class="icon18 login-icon">&nbsp;</em>
                      <span class="vam ml5">登录</span>
                  </a>
                  |
                  <a href="/register" title="注册">
                      <span class="vam ml5">注册</span>
                  </a>
              </li>
              <li v-if="loginInfo.id" id="is-login-one" class="mr10">
                  <a id="headerMsgCountId" href="/message" title="消息">
                      <em class="icon18 news-icon">&nbsp;</em>
                  </a>
                  <q class="red-point" style="display: none">&nbsp;</q>
              </li>
              <li v-if="loginInfo.id" id="is-login-two" class="h-r-user">
                  <a href="/ucenter" title>
                      <img
                          :src="loginInfo.avatar"
                          width="30"
                          height="30"
                          class="vam picImg"
                          alt
                          >
                      <span id="userName" class="vam disIb">{{ loginInfo.nickname }}</span>
                  </a>
                  <a href="javascript:void(0);" title="退出" @click="logout()" class="ml5">退出</a>
              </li>
              <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>       
  
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->
      
    <nuxt/>

    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="http://www.jzjzzzz.icu/" :title="webconfig.author" target="_blank" >{{webconfig.author}}</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a :href="webconfig.gitee" title="码云" target="_blank">码云</a>|
                <a :href="webconfig.github" title="github" target="_blank">github</a>|
                <span >管理员QQ：{{webconfig.qqNumber}}</span>
                <span>邮箱：{{webconfig.email}}</span>
              </section>
              <section class="b-f-link mt10">
                <span>©2021版权归{{webconfig.author}}所有</span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
import webConfigApi from "@/api/webConfig" //（网站配置）
import "~/assets/css/reset.css";
import "~/assets/css/theme.css";
import "~/assets/css/global.css";
import "~/assets/css/web.css";
import cookie from 'js-cookie'
import loginApi from '@/api/login' //（微信登录相关）
import paperApi from '@/api/paper'


export default {
  data(){
    return{
      form:{},
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
      token:'', //（微信登录相关）
      loginInfo:{
        id: 0,
        age: '',
        avatar: '',
        mobile: '',
        nickname: '',
        sex: ''
      },
      searchObj:{}
    }
  },
  created(){
    //获取路径里面token值（微信登录相关）
    this.token = this.$route.query.token
    if(this.token){
      this.wxLogin()
      
    }
    //普通登录
    this.showInfo()
    
    this.getWebConfig()
  },
  methods:{
    //首页查询试卷
    getPageList(){
      this.$router.push({ path: '/paper' })
    },
    //获取网站配置信息
    getWebConfig(){
      webConfigApi.getWebConfigRedis()
      .then(response=>{
        this.webconfig = response.data.data.webConfig
      })
    },

    //微信登录显示用户信息（微信登录相关）
    wxLogin(){
      //把token值存入cookie中
      cookie.set('guli_token', this.token, {domain: 'localhost'})
      cookie.set('guli_ucenter', '', {domain: 'localhost'})
      //调用接口，根据token值获取用户信息
      loginApi.getLoginInfo()
          .then(response=>{
            this.loginInfo =response.data.data.userInfo
            //用户信息存入cookie
            cookie.set('guli_ucenter',JSON.stringify(response.data.data.userInfo), { domain: 'localhost' })
          })
    },
     //从cookie中获取用户信息
     showInfo() {
      //debugger
      var jsonStr = cookie.get("guli_ucenter");
      if (jsonStr) { 
        this.loginInfo = JSON.parse(jsonStr)
      }
    },
    //退出
    logout(){
      //清空cookie
      cookie.set('guli_token','', { domain: 'localhost' })
      cookie.set('guli_ucenter','', { domain: 'localhost' })
      //跳转页面
      window.location.href = "/";
    }
  }
};
</script>