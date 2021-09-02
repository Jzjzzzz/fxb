<template>
  <div>
    <!-- 幻灯片 开始 -->
  <el-carousel :interval="4000" type="card" height="350px">
    <el-carousel-item v-for="banner in bannerList.slideshowList" :key="banner.id">
      <h3 class="medium">
        <img :src="banner" alt  width="950px"/>
      </h3>
    </el-carousel-item>
  </el-carousel>
<!-- 幻灯片 结束 -->
    
     <div id="aCoursesList">
      <!-- 热门试卷 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门试卷</span>
            </h2>
          </header>
          <div>
            <article class="comm-course-list">
              <ul class="of" id="bna">
                
                <li v-for="paper in this.paperList" :key="paper.id">
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <img
                        src="~/assets/img/paper.png"
                        class="img-responsive"
                        :alt="paper.paperName"
                      >
                      <div class="cc-mask">
                        <a href="#" title="开始考试" class="comm-btn c-btn-1">开始考试</a>
                      </div>
                    </section>
                    <h3 class="hLh30 txtOf mt10">
                      <a href="#" :title="paper.paperName" class="course-title fsize18 c-333">{{paper.paperName}}</a>
                    </h3>
                    <section class="mt10 hLh20 of">
                      <span class="fr jgTag bg-green">
                        <i class="c-fff fsize12 f-fA">考试时间：{{paper.suggestTime}}ms</i>
                      </span>
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">总分：{{paper.score}}</i>
                        |
                        <i class="c-999 f-fA">学科：{{paper.subjectName}}</i>
                      </span>
                    </section>
                  </div>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="/paper" title="全部试卷" class="comm-btn c-btn-2">全部试卷</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /热门试卷 结束 -->
      <!-- 温故知新 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">温故知新</span>
            </h2>
          </header>
          <div>
            <article class="i-teacher-list">
              <ul class="of">
                <li v-for="teacher in teacherList" :key="teacher.id">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a href="/teacher/1" :title="teacher.name">
                        <img :alt="teacher.name" :src="teacher.avatar">
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a href="/teacher/1" :title="teacher.name" class="fsize18 c-666">{{teacher.name}}</a>
                    </div>
                    <div class="hLh30 txtOf tac">
                      <span class="fsize14 c-999">{{teacher.career}}</span>
                    </div>
                    <div class="mt15 i-q-txt">
                      <p
                        class="c-999 f-fA"
                      >{{teacher.intro}}</p>
                    </div>
                  </section>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="#" title="全部文章" class="comm-btn c-btn-2">全部文章</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /温故知新 结束 -->
    </div>
  </div>
</template>

<script>
import bannerApi from '@/api/banner'
import paperApi from '@/api/paper'

export default {
  
  data () {
    return {
      swiperOption: {
        //配置分页
        pagination: {
          el: '.swiper-pagination'//分页的dom节点
        },
        //配置导航
        navigation: {
          nextEl: '.swiper-button-next',//下一页dom节点
          prevEl: '.swiper-button-prev'//前一页dom节点
        },
        autoplay: {
          delay: 3000,//时间 毫秒
          disableOnInteraction: false,//用户操作之后是否停止自动轮播默认true
        },
      },
      bannerList:[
        
      ] ,//幻灯片集合
      paperList:[],
      teacherList:[],
    }
  },
  created(){
   this.getBannerList()
   this.getHotPaperList()
  },
 
  methods:{
    //查询热门试卷
    getHotPaperList(){
      paperApi.getHotPaper()
      .then(response=>{
       this.paperList = response.data.data.paperHotList
      })
    },

    //查询banner数据
    getBannerList(){
      bannerApi.getListBanner()
        .then(response=>{
          
        this.bannerList = response.data.data.slideshowVo
    })
    }
  }
}
</script>
<style>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>