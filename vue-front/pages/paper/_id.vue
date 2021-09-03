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
                  <h3 style="padding-left:310px">计算机等级考试三</h3>
                </section>
              </div>
              <!--单选题 开始-->
              <div v-show="active==0">               
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <h3 ><strong>1 [单选题] 关于Java编译，下面哪一个正确（）</strong></h3>
                    </div>
                    <div class="text item">
                      <el-radio-group v-model="radio" >
                        <div style="padding:15px" >
                          <el-radio   v-model="radio" :label="1">A. Java程序经编译后产生machine code</el-radio>
                        </div>
                        <div style="padding:15px">
                          <el-radio v-model="radio" :label="2">B. Java程序经编译后会生产byte code</el-radio>
                        </div>
                        <div style="padding:15px">
                          <el-radio v-model="radio" :label="3">C. Java程序经编译后会产生DLL</el-radio>
                        </div>
                        <div style="padding:15px">
                          <el-radio v-model="radio" :label="4">D. 以上都不正确</el-radio>
                        </div> 
                      </el-radio-group>
                    </div>
                    <el-button size="small" type="success" @click="lastStep" style="margin-top:20px" v-show="active!=0" >上一题</el-button>
                    <el-button size="small" type="success" @click="next" style="margin-top:20px" >下一题</el-button>
                    <el-button class="shiny" icon="el-icon-star-off" size="small" type="warning" style="margin-top:20px" >收藏本题</el-button>
                  </el-card>   
              </div>
              <!-- /单选题 结束 -->

               <!--多选题 开始-->
              <div v-show="active==1">               
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <h3 ><strong>2 [多选题] 关于Java编译，下面哪一个正确（）</strong></h3>
                    </div>
                    <div class="text item">
                       <el-checkbox-group v-model="radio">
                         <div style="padding:15px">
                          <el-checkbox label="复选框 A">A. Java程序经编译后产生machine code</el-checkbox>
                         </div>
                         <div style="padding:15px">
                          <el-checkbox label="复选框 B">B. Java程序经编译后会生产byte code</el-checkbox>
                         </div>
                         <div style="padding:15px">
                          <el-checkbox label="复选框 C">C. Java程序经编译后会产生DLL</el-checkbox>
                         </div>
                         <div style="padding:15px">
                          <el-checkbox label="复选框 D">D. 以上都不正确</el-checkbox>
                         </div>
                      </el-checkbox-group>
                    </div>
                    <el-button size="small" type="success" @click="lastStep" style="margin-top:20px" v-show="active!=0" >上一题</el-button>
                    <el-button size="small" type="success" @click="next" style="margin-top:20px" >下一题</el-button>
                    <el-button class="shiny" icon="el-icon-star-off" size="small" type="warning" style="margin-top:20px" >收藏本题</el-button>
                  </el-card>   
              </div>
              <!-- /多选题 结束 -->

              <!--判断题 开始-->
              <div style="padding-top:8px"  v-show="active==2">               
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <h3 ><strong>3 [判断题] 关于Java编译，下面哪一个正确（）</strong></h3>
                    </div>
                    <div class="text item">
                      <el-radio-group v-model="radio" >
                        <div style="padding:15px" >
                          <el-radio   v-model="radio" :label="1">A. Java程序经编译后产生machine code</el-radio>
                        </div>
                        <div style="padding:15px">
                          <el-radio v-model="radio" :label="2">B. Java程序经编译后会生产byte code</el-radio>
                        </div>
                      </el-radio-group>
                    </div>
                    <el-button size="small" type="success" @click="lastStep" style="margin-top:20px" v-show="active!=0" >上一题</el-button>
                    <el-button size="small" type="success" @click="next" style="margin-top:20px" >下一题</el-button>
                    <el-button class="shiny" icon="el-icon-star-off" size="small" type="warning" style="margin-top:20px" >收藏本题</el-button>
                  </el-card>   
              </div>
              <!-- /判断题 结束 -->

              <!--问答题 开始-->
              <div style="padding-top:8px"  v-show="active==3">               
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <h3 ><strong>4 [简答题] 关于Java编译，下面哪一个正确（）</strong></h3>
                    </div>
                    <el-form ref="form" :model="form" >
                      <el-form-item label="答案">
                      <el-input type="textarea" v-model="form.desc"></el-input>
                    </el-form-item>
                    </el-form>
                    
                    <el-button size="small" type="success" @click="lastStep" style="margin-top:20px" v-show="active!=0" >上一题</el-button>
                    <el-button size="small" type="success" @click="next" style="margin-top:20px" >下一题</el-button>
                    <el-button class="shiny" icon="el-icon-star-off" size="small" type="warning" style="margin-top:20px" >收藏本题</el-button>
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
                    <vac :end-time="new Date().getTime() + 1200000" @finish="(vac) => finish(vac)">
                      <h3 slot="process"  slot-scope="{ timeObj }"  class="el-icon-time">剩余时间 : <span style="color:red">{{ `${timeObj.h}:${timeObj.m}:${timeObj.s}` }}</span></h3>
                      <h3 slot="finish">考试结束啦!</h3>
                    </vac>
                  </no-ssr>
                  
                </div>
                 <div style="margin-top:20px;margin-left:10px">
                  <h3  class="el-icon-s-data">总分 : 100</h3>
                 </div>
                 <div style="margin-top:20px;margin-left:10px">
                  <h3  class="el-icon-reading">题数 : 100</h3>
                 </div>
                 <el-divider ></el-divider>


              </section>
              
            </div>
            <div>
              <section class="stud-act-list" style="padding-left:10px"> 
                <h3>单选题</h3>
              </section>
                <el-button style=" margin: 7px;" @click.native="handleStep(index)" v-for="(item,index) in data" :key="index">{{index+1}}</el-button>
            </div>
            
            <el-button @click="drawer = true" type="primary" style="margin-left: 6px;">
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


import courseApi from "@/api/course"
export default {
  data () {
    return {
      form:{},
      radio: '1',
      active: 0,
      page:1,
      data:['1','2','3','4'],
      course:{},
      chapterList:[], //考试分类
      searchObj: {
       
      }, // 查询表单对象
      buyCountSort:"",
      gmtCreate:"",
      priceSort:"",
      subjectLevelOne:[],
      
      
    }
    
  },
  methods: {
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
       if (this.active++ > 4) this.active = 0;
      },
      //点击答题卡跳转题目
      handleStep(index){
        this.active = index
      }
  }
}
</script>

