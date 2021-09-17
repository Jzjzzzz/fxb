<template>
  <el-row>

    <el-col :span="14" :offset="5">
      <div class="grid-content bg-purple">
        <el-tabs>
        <el-tab-pane label="统计">
          <div id="main" style="width: 600px;height:400px;"></div> 
        </el-tab-pane>
        <el-tab-pane label="修改个人资料">
          <el-form  :model="form"   ref="form">
          <el-row>
            <el-col :span="12">
            <div class="grid-content bg-purple">
              <!-- avatar -->
              <el-upload
                class="avatar-uploader"
                :action="uploadUrl+'/Avatar'"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="form.avatar" :src="form.avatar" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </div>
            </el-col>
            <el-col :span="12"><div class="grid-content bg-purple-light">
              <el-form-item label="昵称" :label-width="formLabelWidth" prop="name">
                <el-input type="text" style="width:300px" v-model="form.nickname" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth" prop="status">
                  <el-select style="width:300px" v-model="form.sex" placeholder="请选择" clearable>
                    <el-option :value="2" label="男"/>
                    <el-option :value="1" label="女"/>
                  </el-select>
                </el-form-item>
                <el-form-item label="年龄" :label-width="formLabelWidth" prop="sort">
                  <el-input style="width:300px" v-model="form.age" auto-complete="off"></el-input>
                </el-form-item>
            </div>
            </el-col>
          </el-row>
              <el-divider></el-divider>

            <el-form-item>
              <el-button type="primary" :disabled="isDisabled" style="margin-left:400px" @click="updatePersonal">修改</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="修改密码">
          <el-col :span="12">
            <div class="grid-content bg-purple-light">
            <el-form  :model="userPasswordVo"   ref="form">
              <el-form-item label="原密码" :label-width="formLabelWidth" prop="name">
                <el-input type="password" style="width:300px" v-model="userPasswordVo.oldPassword" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="新密码" :label-width="formLabelWidth" prop="sort">
                  <el-input type="password" style="width:300px" v-model="userPasswordVo.newPassword" auto-complete="off"></el-input>
                </el-form-item>
                    <el-divider></el-divider>

                <el-form-item>
                  <el-button type="primary" :disabled="isDisabled" style="margin-left:200px" @click="updatePasswordById">修改</el-button>
                </el-form-item>
            </el-form>
              
            </div>
            </el-col>
        </el-tab-pane>
      </el-tabs>
      </div>
    </el-col>

  </el-row>
</template>

<script>
import cookie from 'js-cookie'
import userApi from '@/api/user'
import echarts from 'echarts'


export default {
  data () {
    return {
      isDisabled:false, //防止表单重复提交
      formLabelWidth: '80px',
      form:{},
      imagecropperShow: false, // 是否显示上传组件
      imagecropperKey: 0, // 上传组件id
      uploadUrl:'http://localhost:80/eduoss/fileoss/upload/',
      loginInfo:{
        id: 0,
        age: '',
        avatar: '',
        mobile: '',
        nickname: '',
        sex: ''
      }, 
      userPasswordVo:{}, //修改密码实体
      list:{},
      chart: null,
      title: '',
      xData: [],
      yData: []
    }
  },
  mounted(){
    //用户信息
    this.showInfo()
    this.getUserById()
  },
  methods:{
    // 设置图标参数
    setChart() {
      // 基于准备好的dom，初始化echarts实例
      this.chart = echarts.init(document.getElementById('main'))
      // console.log(this.chart)
      // 指定图表的配置项和数据
      var option = {
        title: {
        text: '考试统计',
        left: 'center'
    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        orient: 'vertical',
        left: 'left',
    },
    series: [
        {
            name: '考试次数',
            type: 'pie',
            radius: '50%',
            data: this.xData,
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
       
        
      }
      this.chart.setOption(option)
    },
    //获取用户实时数据
    getUserById(){
      userApi.getUserById(this.loginInfo.id).
      then(response=>{
        this.form = response.data.data.model
        this.xData = this.form.userCountVoList
        this.setChart()
      })
      
    },
    //修改密码
    updatePasswordById(){
      this.isDisabled=true //禁用提交按钮
      this.userPasswordVo.id = this.loginInfo.id
      userApi.updatePasswordById(this.userPasswordVo)
      .then(response=>{
        this.isDisabled=false
        this.$message({
          message: response.data.message,
          type: 'success'
        });
        //清空cookie
        cookie.set('guli_token','', { domain: 'localhost' })
        cookie.set('guli_ucenter','', { domain: 'localhost' })
        //跳转页面
        this.$router.push({ path: '/login' })
      })
      .catch(response=>{
        this.$message({
          message: response.data.message,
          type: 'error'
        });
        this.isDisabled=false
    
      })
    },
    //修改个人资料
    updatePersonal(){
      this.isDisabled=true //禁用提交按钮
      userApi.updatePersonal(this.form)
      .then(response=>{
        this.isDisabled=false
        this.$message({
          message: response.data.message,
          type: 'success'
        });
        
      })
      .catch(response=>{
        this.$message({
          message: response.data.message,
          type: 'error'
        });
        this.isDisabled=false
    
      })
    },
    //从cookie中获取用户信息
     showInfo() {
      //debugger
      var jsonStr = cookie.get("guli_ucenter");      
      if (jsonStr) { 
        this.loginInfo = JSON.parse(jsonStr)
        this.loginInfo.password=''
      }
      if(this.loginInfo.id==0){
        this.$router.push({ path: '/login' })
      }
    },
      handleAvatarSuccess(res) {
        this.form.avatar = res.data.url
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      }
    
  }
}
</script>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>