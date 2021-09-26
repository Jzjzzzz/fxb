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
                  <h3 style="padding-left:500px">错题集</h3>
                </section>
              </div>
              <!-- /无数据提示 开始-->
              <section class="no-data-wrap" v-if="this.list.length<=0">
                  <em class="icon30 no-data-ico">&nbsp;</em>
                  <span class="c-666 fsize14 ml10 vam">暂无收藏题目</span>
              </section>
              <!-- /无数据提示 结束-->  
              <!--题目 开始-->
              <div style="padding:20px"  v-for="(item,index) in list" :key="item.id">
                           
                  <el-card class="box-card" shadow="hover">
                    <div slot="header" class="clearfix">
                      <h3 >
                        <strong >{{index+1}}. ({{item.score}}分) 
                          <span v-if="item.questionId==1">[单选题]</span>
                          <span v-if="item.questionId==2">[多选题]</span>
                          <span v-if="item.questionId==5">[判断题]</span>
                          <span v-if="item.questionId==4">[简答题]</span>
                          <p style="display:inline-block;" v-html="item.titleContent"></p>
                          <i v-if="item.result==0" class="el-icon-close" style="color:red;font-size:25px"></i>
                          <i v-if="item.result==1" class="el-icon-check" style="color:red;font-size:25px"></i>
                        </strong>
                      </h3>
                    </div>
                    <!-- 单选题 开始-->
                    <div class="text item" v-if="item.questionId==1">
                      <el-radio-group v-model="radio[index]"  >
                        <div style="padding:15px" v-for="option in JSON.parse(item.content)"  :key="option.prefix" > 
                          <el-radio  disabled	 :label="option.prefix">{{option.prefix}}. 
                            <span style="display:inline-block;" v-html="option.content"></span>
                          </el-radio>
                        </div>
                      </el-radio-group>
                    </div>
                    <!-- 单选题 结束-->
                    <!-- 多选题 开始 -->
                    <div class="text item" v-if="item.questionId==2">
                       <el-checkbox-group v-model="radio">
                         <div style="padding:15px" v-for="(option,index) in JSON.parse(item.content)"  :key="option.prefix">
                          <el-checkbox  disabled :key="index"  :label="option.prefix" >{{option.prefix}}. 
                            <span style="display:inline-block;" v-html="option.content"></span>
                          </el-checkbox>
                         </div>
                      </el-checkbox-group>
                    </div>
                    <!-- 多选题 结束 -->
                    <!-- 判断题 开始 -->
                    <div class="text item" v-if="item.questionId==5">
                      <el-radio-group v-model="judgeRadio[index]" >
                        <div style="padding:15px" v-for="option in JSON.parse(item.content)"  :key="option.prefix">
                          <el-radio  disabled :label="option.prefix">{{option.prefix}}. 
                            <span style="display:inline-block;" v-html="option.content"></span>
                          </el-radio>
                        </div>
                      </el-radio-group>
                    </div>
                    <!-- 判断题 结束 -->
                    <!-- 简答题 开始 -->
                    <div class="text item" v-if="item.questionId==4">
                      <el-form ref="form" :model="form" >
                        <el-input disabled type="textarea" v-model="essayQuestion[index]"></el-input>
                      </el-form>
                    </div>
                    <!-- 简答题 结束 -->                  
                    <el-divider></el-divider>
                    <p style="font-size:16px;padding:10px">得分 : <el-tag size="small">{{item.score}}</el-tag></p>
                    <el-divider></el-divider>
                    <p style="font-size:16px;padding:10px">填写答案 : <span v-html="item.userAnswer"></span></p>
                    <el-divider></el-divider>
                    <p style="font-size:16px;padding:10px">正确答案 : <span v-html="item.correct"></span></p>
                    <el-divider></el-divider>
                    <p style="font-size:16px;padding:10px" >解析 : <span style="display:inline-block;" v-html="item.analyzes"></span></p>
                    <el-button  @click="btnCollection(item.id)" icon="el-icon-star-off" size="small" type="danger" style="margin-top:20px" >取消收藏</el-button>
                  </el-card>   
              </div>
              <!-- /题目 结束 -->
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
import recordsApi from '@/api/records'
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
     list:[], //题目列表
     radio:[], //单选题用户填写答案列表
     judgeRadio:[], //判断题用户填写答案列表
     essayQuestion:[], //简答题用户填写答案列表
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
    //取消收藏
    btnCollection(topicId){
      recordsApi.cancelCollection(topicId)
      .then(response=>{
                if(response.data.data.result){
          this.$message.success("取消收藏成功")
          this.fetchData()
        }
      })
    },
   fetchData(){
     recordsApi.getCollectionList(this.loginInfo.id)
     .then(response=>{
       this.list = response.data.data.list
     })
   }
  }
};
</script>
