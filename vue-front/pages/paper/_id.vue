<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /考试详情 开始 -->
    <section class="container">

   
      <!-- /考试封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <h3 style="padding-left:310px">计算机等级考试三</h3>
                </section>
              </div>
              <div style="padding-top:8px" >
                <div>
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <h3 ><strong>1 [单选题] 关于Java编译，下面哪一个正确（）</strong></h3>
                    </div>
                    <div  class="text item">
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
                    <el-button class="shiny" icon="el-icon-star-off" size="small" type="warning" style="margin-top:20px" >收藏本题</el-button>
                  </el-card>
                </div>
              </div>
              
            </div>
            
          </section>
          
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle ">
                <div style="margin-top:20px;margin-left:10px">
                  <h3 class="el-icon-time">用时 : <span style="color:red">01:42:22</span></h3>
                </div>
                 <div style="margin-top:20px;margin-left:10px">
                  <h3  class="el-icon-s-data">总分 : 100</h3>
                </div>
                 <el-divider ></el-divider>


              </section>
              <section class="stud-act-list"> 
                <h3>单选题</h3>
              </section>
            </div>
            <div>
                <el-button style=" margin: 10px;" @click.native="handleStep(index)" v-for="(item,index) in stepList" :key="index">{{index+1}}</el-button>
            </div>
            <!-- <el-steps :active="active" finish-status="success" align-center>
              <el-step  v-for="(item,index) in stepList" :key="index" :title="item"></el-step>
            </el-steps> -->
          </div>
        </aside>
        <div class="clear"></div>
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
      radio: '1',
     active: 0,
      page:1,
      data:['1','2','3','4','5','6','7'],
      course:{},
      chapterList:[], //考试分类
      searchObj: {
       
      }, // 查询表单对象
      buyCountSort:"",
      gmtCreate:"",
      priceSort:"",
      subjectLevelOne:[],
      stepList:[
        '第一题','第二题','第三题','第四题','第五题','第六题','第七题'
      ]
      
    }
    
  },
  methods: {
    next() {
        if (this.active++ > 5) this.active = 0;
      },
      handleStep(index){
        this.active = index
      }
  }
}
</script>
