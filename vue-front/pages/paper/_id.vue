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
                  <h3 style="padding-left:310px">{{form.paperName}}</h3>
                </section>
              </div>
              <!--单选题 开始-->
              <div style="padding-top:8px" v-show="active==index" v-for="(item,index) in form.singleChoiceList" :key="item.id">               
                  <el-card class="box-card" shadow="hover">
                    <div slot="header" class="clearfix">
                      <h3 >
                        <strong >{{index+1}}.  [{{item.questionName}}] ({{item.score}}分) 
                          <p style="display:inline-block;" v-html="item.titleContent"></p>
                        </strong>
                      </h3>
                    </div>
                    <div class="text item"  >
                      <el-radio-group v-model="answer.singleRadio[index]"  >
                        <div  style="padding:15px;width:740px;" v-for="option in JSON.parse(item.content)"  :key="option.prefix" >
                          <el-radio    :label="option.prefix">{{option.prefix}}. 
                            <span style="display:inline-block;word-break:break-all;white-space:normal" v-html="option.content"></span>
                          </el-radio>
                        </div>
                      </el-radio-group>
                    </div>
                    <el-row >
                      <el-col :span="17">
                        <el-button size="small" plain  type="success" @click="lastStep" style="margin-top:20px" v-if="active!=0" >上一题</el-button>
                        <el-button size="small" plain  type="success" @click="next" style="margin-top:20px" v-if="active<form.questionCount-1" >下一题</el-button>
                      </el-col>
                    <el-col :span="1.5" style="padding-top:27px">
                    <h5><strong>难度 : </strong></h5>
                    </el-col>
                    <el-col :span="5" style="padding-top:27px">
                    <el-rate 
                      v-model="item.difficult"
                      disabled
                      text-color="#ff9900"
                      score-template="{value}">
                    </el-rate>
                    </el-col>
                    </el-row>
                  </el-card>   
              </div>
              <!-- /单选题 结束 -->
               <!--多选题 开始-->
              <div style="padding-top:8px" v-show="active==index+form.singleChoiceNumber" v-for="(item,index) in form.multipleChoiceList" :key="item.id">               
                  <el-card class="box-card" shadow="hover">
                    <div slot="header" class="clearfix">
                      <h3 >
                        <strong >{{index+1+form.singleChoiceNumber}}.  [{{item.questionName}}] ({{item.score}}分) 
                          <p style="display:inline-block;" v-html="item.titleContent"></p>
                        </strong>
                      </h3>
                    </div>
                    <div class="text item">
                       <el-checkbox-group v-model="answer.multipleRadio[index]">
                         <div style="padding:15px" v-for="(option,index) in JSON.parse(item.content)"  :key="option.prefix">
                          <el-checkbox :key="index"  :label="option.prefix" >{{option.prefix}}. 
                            <span style="display:inline-block;" v-html="option.content"></span>
                          </el-checkbox>
                         </div>
                      </el-checkbox-group>
                    </div>
                    <el-row >
                      <el-col :span="17">
                        <el-button size="small" plain  type="success" @click="lastStep" style="margin-top:20px" v-if="active!=0" >上一题</el-button>
                        <el-button size="small" plain  type="success" @click="next" style="margin-top:20px" v-if="active<form.questionCount-1" >下一题</el-button>
                      </el-col>
                    <el-col :span="1.5" style="padding-top:27px">
                    <h5><strong>难度 : </strong></h5>
                    </el-col>
                    <el-col :span="5" style="padding-top:27px">
                    <el-rate 
                      v-model="item.difficult"
                      disabled
                      text-color="#ff9900"
                      score-template="{value}">
                    </el-rate>
                    </el-col>
                    </el-row>
                  </el-card>   
              </div>
              <!-- /多选题 结束 -->
              <!--判断题 开始-->
              <div style="padding-top:8px"  v-show="active==index+form.singleChoiceNumber+form.multipleChoiceNumber" v-for="(item,index) in form.judgeList" :key="item.id">               
                  <el-card class="box-card" shadow="hover">
                    <div slot="header" class="clearfix">
                      <h3 >
                        <strong >{{index+1+form.singleChoiceNumber+form.multipleChoiceNumber}}.  [{{item.questionName}}] ({{item.score}}分) 
                          <p style="display:inline-block;" v-html="item.titleContent"></p>
                        </strong>
                      </h3>    
                    </div>
                    <div class="text item">
                      <el-radio-group v-model="answer.judgeRadio[index]" >
                        <div style="padding:15px" v-for="option in JSON.parse(item.content)"  :key="option.prefix">
                          <el-radio  :label="option.prefix">{{option.prefix}}. 
                            <span style="display:inline-block;" v-html="option.content"></span>
                          </el-radio>
                        </div>
                      </el-radio-group>
                    </div>
                    <el-row >
                      <el-col :span="17">
                        <el-button size="small" plain  type="success" @click="lastStep" style="margin-top:20px" v-if="active!=0" >上一题</el-button>
                        <el-button size="small" plain  type="success" @click="next" style="margin-top:20px" v-if="active<form.questionCount-1" >下一题</el-button>
                      </el-col>
                    <el-col :span="1.5" style="padding-top:27px">
                    <h5><strong>难度 : </strong></h5>
                    </el-col>
                    <el-col :span="5" style="padding-top:27px">   
                    <el-rate 
                      v-model="item.difficult"
                      disabled
                      text-color="#ff9900"
                      score-template="{value}">
                    </el-rate>
                    </el-col>
                    </el-row>
                  </el-card>   
              </div>
              <!-- /判断题 结束 -->
              <!--问答题 开始-->
              <div style="padding-top:8px"  v-show="active==index+form.singleChoiceNumber+form.multipleChoiceNumber+form.judgeNumber" v-for="(item,index) in form.shortAnswerList" :key="item.id">               
                  <el-card class="box-card" shadow="hover">
                    <div slot="header" class="clearfix">
                      <h3 >
                        <strong >{{index+1+form.singleChoiceNumber+form.multipleChoiceNumber+form.judgeNumber}}.  [{{item.questionName}}] ({{item.score}}分) 
                          <p style="display:inline-block;" v-html="item.titleContent"></p>
                        </strong>
                      </h3>    
                    </div>
                    <el-form ref="form" :model="form" >
                      <el-form-item label="答案">
                      <el-input type="textarea" v-model="answer.essayQuestion[index]"></el-input>
                    </el-form-item>
                    </el-form>
                    <el-row >
                      <el-col :span="17">
                        <el-button size="small" plain  type="success" @click="lastStep" style="margin-top:20px" v-if="active!=0" >上一题</el-button>
                        <el-button size="small" plain  type="success" @click="next" style="margin-top:20px" v-if="active<form.questionCount-1" >下一题</el-button>
                      </el-col>
                    <el-col :span="1.5" style="padding-top:27px">
                    <h5><strong>难度 : </strong></h5>
                    </el-col>
                    <el-col :span="5" style="padding-top:27px">    
                    <el-rate 
                      v-model="item.difficult"
                      disabled
                      text-color="#ff9900"
                      score-template="{value}">
                    </el-rate>
                    </el-col>
                    </el-row>
                  </el-card>   
              </div>
              <!-- /问答题 结束 -->           
            </div>    
          </section> 
        </article>
        <aside class="fl col-3 " >
          <div class="i-box" >
            <div>
              <section class="c-infor-tabTitle ">
                <div style="margin-top:20px;margin-left:10px">
                  <no-ssr>
                    <vac ref="suggestTime" @start="start" :auto-start="false" :left-time="suggestTime" @finish="(vac) => finish(vac)">
                      <h3 slot="process"   slot-scope="{ timeObj }"   class="el-icon-time">剩余时间 : <span style="color:red" >{{ `${timeObj.h}:${timeObj.m}:${timeObj.s}` }}</span></h3>
                      <h3 slot="finish">考试结束啦!</h3>
                    </vac>
                  </no-ssr>
                </div>
                 <div style="margin-top:20px;margin-left:10px">
                  <h3  class="el-icon-s-data">总分 : {{form.score}}</h3>
                 </div>
                 <div style="margin-top:20px;margin-left:10px">
                  <h3  class="el-icon-reading">题数 : {{form.questionCount}}</h3>
                 </div>
                 <el-button :disabled="isDisabled" size="medium" v-show="isShow"  type="success" style="margin-left: 100px;margin-top:10px"  @click="startCountdown">
              开始考试
            </el-button>  
                 <el-divider ></el-divider>
                 
              </section> 
            </div>
            <div>
              <section v-if="form.singleChoiceNumber>0" class="stud-act-list" style="padding-left:5px" > 
                <h3>单选题</h3>
                <el-button style=" margin: 5px;" @click.native="handleStep(index)" v-for="(item,index) in form.singleChoiceList" :key="item.id">{{index+1}}</el-button>
              </section>
               <section v-if="form.multipleChoiceNumber>0" class="stud-act-list" style="padding-left:5px"> 
                <h3>多选题</h3>
                <el-button style=" margin: 5px;" @click.native="handleStep(index+form.singleChoiceNumber)" v-for="(item,index) in form.multipleChoiceList" :key="item.id">{{index+1+form.singleChoiceNumber}}</el-button>
              </section>
              <section v-if="form.judgeNumber>0" class="stud-act-list" style="padding-left:5px"> 
                <h3>判断题</h3>
                <el-button style=" margin: 5px;" @click.native="handleStep(index+form.multipleChoiceNumber+form.singleChoiceNumber)" v-for="(item,index) in form.judgeList" :key="item.id">{{index+1+form.multipleChoiceNumber+form.singleChoiceNumber}}</el-button>
              </section>
              <section v-if="this.form.shortAnswerNumber>0" class="stud-act-list" style="padding-left:5px"> 
                <h3>简答题</h3>
                <el-button style=" margin: 5px;" @click.native="handleStep(index+form.judgeNumber+form.multipleChoiceNumber+form.singleChoiceNumber)" v-for="(item,index) in form.shortAnswerList" :key="item.id">{{index+1+form.judgeNumber+form.multipleChoiceNumber+form.singleChoiceNumber}}</el-button>
              </section>
            </div>
            <el-button size="medium" v-if="isShow==false"  type="primary" style="margin-left: 100px;margin-top:10px" :disabled="isDisabled" @click="onSubmit">
              交卷
            </el-button>  
          </div>   
        </aside>    
      </div>
    </section>
    <!-- /考试详情 结束 -->
  </div>
</template>
<script>
import '/static/DynamicButton/style.css'
import paperApi from "@/api/paper"
import cookie from 'js-cookie'
export default {
  data () {
    return { 
      isShow:true,
      suggestTime:0,
      isDisabled:true, //防止表单重复提交
      loginInfo:{
        id: 0,
        age: '',
        avatar: '',
        mobile: '',
        nickname: '',
        sex: ''
      }, 
      form:{}, //试卷信息和题目信息
      radio: '1',
      //学生填写的集合
      answer:{
        id:this.$route.params.id,
        singleRadio:[], //单选答案列表
        multipleRadio:[], //多选答案列表
        judgeRadio:[], //判断题答案列表
        essayQuestion:[], //问答题答案列表
        singleIds:[], //单选题序号
        multipleIds:[],//多选题序号
        judgeIds:[],//判断题序号
        essayIds:[], //问答题序号
        userId:0, //用户id
        doTime:0, //用时
        isAuto:1, //是否自动批改

      },
      active: 0, //控制题目显示
    }
    
  },
  
  created(){
//用户信息
    this.showInfo()
    this.fetchData()
  },  
  methods: {
    start() {
      this.isShow = !this.isShow
      setInterval(this.timer, 1000); 
    },
    startCountdown() {
      const vm = this
      vm.$refs.suggestTime.startCountdown(true)
    },
    timer() {
        this.answer.doTime ++;
      },
    //从cookie中获取用户信息
     showInfo() {
      //debugger
      var jsonStr = cookie.get("guli_ucenter");
      
      if (jsonStr) { 
        this.loginInfo = JSON.parse(jsonStr)
        this.answer.userId = this.loginInfo.id
      }
      if(this.loginInfo.id==0){
        this.$router.push({ path: '/login' })
      }
    },
    fetchData(){
      
      paperApi.getPaperTopicById(this.$route.params.id)
      .then(response=>{ 
        this.form = response.data.data.model
        this.suggestTime = this.form.suggestTime
        this.isDisabled=false
        //初始化单选题的数组大小
        for (let index = 1; index <= this.form.singleChoiceNumber; index++) {
          this.answer.singleRadio.push("")
        }
        //初始化多选题的二维数组大小
        for (let index = 1; index <= this.form.multipleChoiceNumber; index++) {
          this.answer.multipleRadio.push(new Array())
        }
        //初始化判断题的数组大小
        for (let index = 1; index <= this.form.judgeNumber; index++) {
          this.answer.judgeRadio.push("")
        }
        //初始化简答题的数组大小
        for (let index = 1; index <= this.form.shortAnswerNumber; index++) {
          this.answer.essayQuestion.push("")
        } 
      })
      
    },
    // 交卷选择按钮
    onSubmit(id) {
      
      this.$confirm('批改模式（自动批改批改成绩仅供参考!）', '提示', {
        confirmButtonText: '自动批改',
        cancelButtonText: '手动批改',
        type: 'warning',
      })
        .then(() => {
          this.submitPaper()
        })
        .catch(error => {
          tihs.answer.isAuto=0
          this.submitPaper()
        })
    },
    //交卷
    submitPaper(){
      this.isDisabled=true //禁用提交按钮
      //按序列顺序封装不要打乱
      //封装单选题id
      for (let i = 0; i < this.form.singleChoiceNumber; i++) {
        this.answer.singleIds.push(this.form.singleChoiceList[i].id)
      }
      //封装多选题id
      for (let i = 0; i < this.form.multipleChoiceNumber; i++) {
        this.answer.multipleIds.push(this.form.multipleChoiceList[i].id)
      }
      //封装判断题id
      for (let i = 0; i < this.form.judgeNumber; i++) {
        this.answer.judgeIds.push(this.form.judgeList[i].id)
      }
       //封装问答题id
      for (let i = 0; i < this.form.shortAnswerNumber; i++) {
        this.answer.essayIds.push(this.form.shortAnswerList[i].id)
      }
      paperApi.automaticCorrecting(this.answer)
      .then(response=>{
        this.$message.success("交卷成功")
        this.$router.push({ path: '/records' })
      })
      .catch(response=>{
        this.isDisabled=false
      })
    },

    //倒计时结束自动交卷，走自动批改方法
    finish(vac){
      this.answer.doTime = this.suggestTime/1000
      this.submitPaper()
    },
    //上一题
    lastStep(){
       this.active--
    },
    //下一题
    next() {
      this.active+1
       if (this.active++ > this.form.questionCount) this.active = 0;
      },
      //点击答题卡跳转题目
      handleStep(index){
        this.active = index
      }
  }
}
</script>