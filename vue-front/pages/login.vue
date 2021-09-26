<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="user">

        <el-form-item class="input-prepend restyle" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
          <div >
            <el-input type="text" placeholder="手机号" v-model="user.mobile"/>
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>

        <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="密码" v-model="user.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>

        <div class="btn">
          <input type="button"  class="sign-in-button" value="登录" @click="varify(submitLogin)">
        </div>
      </el-form>
      
    </div>

  </div>
</template>

<script>
  import '~/assets/css/sign.css'
  import '~/assets/css/iconfont.css'
  import cookie from 'js-cookie'
  import loginApi from '@/api/login'

  export default {
    layout: 'sign',

    data () {
      return {
        
        //封装登录的手机号和密码
        user:{
          mobile:'',
          password:''
        },
        //用户信息
        loginInfo:{
          id: '',
          age: '',
          avatar: '',
          mobile: '',
          nickname: '',
          sex: ''
        },
        //图形验证码封装信息
        codeVo:{
          randStr:'',
          ticket:''
        }
      }
    },

    methods: {
      //滑块验证
      varify(submit) {
          let appid = '2033594012'; // 腾讯云控制台中对应这个项目的 appid
          //生成一个滑块验证码对象
          let captcha = new TencentCaptcha(appid, function (res) {
          // 用户滑动结束或者关闭弹窗，腾讯返回的内容
          if (res.ret === 0) {
            var obj =new Object();         
            obj.randStr = res.randstr
            obj.ticket = res.ticket
            loginApi.verificationCode(obj)
            .then(response=>{
              if(response.data.data.captchaCode){
                submit && submit()
              }
            })
                } else {
                  
            // 提示用户完成验证
          }
          });
          // 滑块显示
          captcha.show();
      },
      //第一步，调用接口登录，返回token字符串
      submitLogin(){
        loginApi.submitLogin(this.user)
        .then(response=>{
          //第二步，获取token字符串,放到cookie里面去
          //第一个参数是值名称，第二个是值，第三个是作用范围
          cookie.set('guli_token',response.data.data.token, { domain: 'localhost' })
          
          //第四步,调用接口根据请求头token获取用户信息
          loginApi.getLoginInfo()
          .then(response=>{
            this.loginInfo =JSON.stringify(response.data.data.userInfo) 
            //用户信息存入cookie
            cookie.set('guli_ucenter',this.loginInfo, { domain: 'localhost' })

            //跳转页面
            window.location.href = "/";
          })

        })
        .catch(response=>{
          })
      }
    }
  }
</script>
<style>
   .el-form-item__error{
    z-index: 9999999;
  }
</style>