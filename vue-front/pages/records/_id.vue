<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /考试详情 开始 -->
    <section class="container">
      <!-- /考试封面介绍 -->
      <div class="mt20 c-infor-box" >
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <h3 style="padding-left:310px">{{data.paperName}}</h3>
                </section>
              </div>
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
                    <p v-if="item.status==1" style="font-size:16px;padding:10px">得分 : <el-tag size="small">{{item.score}}</el-tag></p>
                    <el-divider></el-divider>
                    <p style="font-size:16px;padding:10px">填写答案 : <span v-html="item.userAnswer"></span></p>
                    <el-divider></el-divider>
                    <p style="font-size:16px;padding:10px">正确答案 : <span v-html="item.correct"></span></p>
                    <el-divider></el-divider>
                    <p style="font-size:16px;padding:10px" >解析 : <span style="display:inline-block;" v-html="item.analyzes"></span></p>
                    <el-button @click="onSubmit(item.id)" v-if="item.status==0" size="small" type="warning" style="margin-top:20px" >批改</el-button>
                    <el-button class="shiny" @click="btnCollection(item.id)" icon="el-icon-star-off" size="small" type="warning" style="margin-top:20px" >收藏本题</el-button>
                  </el-card>   
              </div>
              <!-- /题目 结束 -->
            </div>    
          </section> 
        </article>
        <aside class="fl col-3 " >
          <div class="i-box" >
            <div>
              <section class="c-infor-tabTitle ">
                 <div>
                   <el-divider >总分</el-divider>
                   <h3 style="padding-left:105px">{{data.paperScore}}/{{data.userScore}}</h3>
                 </div>
                 <div>
                   <el-divider >题数</el-divider>
                   <h3 style="padding-left:115px">{{data.questionCount}}/{{data.questionCorrect}}</h3>
                 </div>
                 <div>
                   <el-divider >考试用时</el-divider>
                   <h3 style="padding-left:113px">{{data.doTime}}秒</h3>
                 </div>
              </section>
            </div>
          </div> 
        </aside>
      </div>
    </section>
    <!-- /考试详情 结束 -->
  </div>
</template>
<script>
import '/static/DynamicButton/style.css'
import recordsApi from '@/api/records'
export default {
  data () {
    return {
     list:[], //题目列表
     data:{}, //试卷详情
     radio:[], //单选题用户填写答案列表
     judgeRadio:[], //判断题用户填写答案列表
     essayQuestion:[], //简答题用户填写答案列表
    }
  },
  created () {
    this.fetchData()
  },
  methods:{
    //收藏错题
    btnCollection(topicId){
      recordsApi.collectionError(topicId)
      .then(response=>{
        if(response.data.data.result){
          this.$message.success("收藏成功")
        }
      })
    },
    // 批改问答题
    onSubmit(id) {   
      this.$confirm('批改', '提示', {
        confirmButtonText: '正确',
        cancelButtonText: '错误',
        type: 'warning',
      })
        .then(() => {
          recordsApi.manualCorrect(id,1)
          .then(response=>{
            this.$message.success("批改成功")
            this.fetchData()
          })
        })
        .catch(error => {
          recordsApi.manualCorrect(id,0)
          .then(response=>{
            this.$message.success("批改失败")
            this.fetchData()
          })
        })
    },
   fetchData(){
     recordsApi.getTestTopicListById(this.$route.params.id)
     .then(response=>{
       this.list = response.data.data.list
       this.data = response.data.data.paper      
     })
   }
  }
};
</script>
