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
                    <div class="text item">
                      <el-radio-group v-model="radio" >
                        <div style="padding:15px" v-for="option in JSON.parse(item.content)"  :key="option.prefix">
                          <el-radio  :label="option.prefix">{{option.prefix}}. 
                            <span style="display:inline-block;" v-html="option.content"></span>
                          </el-radio>
                        </div>
                      </el-radio-group>
                    </div>
                    <el-row >
                      <el-col :span="17">
                        <el-button size="small" type="success" @click="lastStep" style="margin-top:20px" v-if="active!=0" >上一题</el-button>
                        <el-button size="small" type="success" @click="next" style="margin-top:20px" v-if="active<form.questionCount-1" >下一题</el-button>
                        <el-button class="shiny" icon="el-icon-star-off" size="small" type="warning" style="margin-top:20px" >收藏本题</el-button>
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
                       <el-checkbox-group v-model="radio">
                         <div style="padding:15px" v-for="option in JSON.parse(item.content)"  :key="option.prefix">
                          <el-checkbox  :label="option.prefix" >{{option.prefix}}. 
                            <span style="display:inline-block;" v-html="option.content"></span></el-checkbox>
                         </div>
                      </el-checkbox-group>
                    </div>
                    <el-row >
                      <el-col :span="17">
                        <el-button size="small" type="success" @click="lastStep" style="margin-top:20px" v-if="active!=0" >上一题</el-button>
                        <el-button size="small" type="success" @click="next" style="margin-top:20px" v-if="active<form.questionCount-1" >下一题</el-button>
                        <el-button class="shiny" icon="el-icon-star-off" size="small" type="warning" style="margin-top:20px" >收藏本题</el-button>
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
                      <el-radio-group v-model="radio" >
                        <div style="padding:15px" v-for="option in JSON.parse(item.content)"  :key="option.prefix">
                          <el-radio  :label="option.prefix">{{option.prefix}}. 
                            <span style="display:inline-block;" v-html="option.content"></span>
                          </el-radio>
                        </div>
                      </el-radio-group>
                    </div>

                    <el-row >
                      <el-col :span="17">
                        <el-button size="small" type="success" @click="lastStep" style="margin-top:20px" v-if="active!=0" >上一题</el-button>
                        <el-button size="small" type="success" @click="next" style="margin-top:20px" v-if="active<form.questionCount-1" >下一题</el-button>
                        <el-button class="shiny" icon="el-icon-star-off" size="small" type="warning" style="margin-top:20px" >收藏本题</el-button>
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
                      <el-input type="textarea" v-model="form.desc"></el-input>
                    </el-form-item>
                    </el-form>
                    <el-row >
                      <el-col :span="17">
                        <el-button size="small" type="success" @click="lastStep" style="margin-top:20px" v-if="active!=0" >上一题</el-button>
                        <el-button size="small" type="success" @click="next" style="margin-top:20px" v-if="active<form.questionCount-1" >下一题</el-button>
                        <el-button class="shiny" icon="el-icon-star-off" size="small" type="warning" style="margin-top:20px" >收藏本题</el-button>
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
                    <vac :end-time="new Date().getTime() + 1800000" @finish="(vac) => finish(vac)">
                      <h3 slot="process"  slot-scope="{ timeObj }"  class="el-icon-time">剩余时间 : <span style="color:red">{{ `${timeObj.h}:${timeObj.m}:${timeObj.s}` }}</span></h3>
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
            
            <el-button size="medium"  type="primary" style="margin-left: 100px;margin-top:10px">
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
export default {
  data () {
    return {
      form:{},
      radio: '1',
      active: 0,
    }
    
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData(){
      paperApi.getPaperTopicById(this.$route.params.id)
      .then(response=>{ 
        this.form = response.data.data.model
      })
    },
    //倒计时结束
    finish(vac){
      // alert('考试结束啦！')
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

