<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /试卷列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部试卷</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="/paper">全部</a>
                </li>
                <li v-for="(item,index) in this.subjectList" :key="index">
                  <a :title="item.name" @click="searchTwo(item.id,index)" href="#">{{item.name}}</a>
                </li>
              </ul>
            </dd>
          </dl>
     
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li>
                <a title="关注度" href="#">关注度</a>
              </li>
              <li :class="{'current bg-orange':gmtCreate!=''}">
                <a title="最新" @click="searchGmtCreate()"  href="#">最新</a>
                <span :class="{hide:gmtCreate==''}">↓</span>
              </li>
              <li >
                <a title="总分" href="#">总分&nbsp;
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="data.total==0">
              <em class="icon30 no-data-ico">&nbsp;</em>
              <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="data.total>0">
              <ul class="of" id="bna">
                  <li  v-for="item in this.data.items" :key="item.id">
                      <div class="cc-l-wrap">
                    <section class="course-img">
                      <img
                        src="~/assets/img/paper.png"
                        class="img-responsive"
                        :alt="item.paperName"
                      >
                      <div class="cc-mask">
                        <a :href="'/paper/'+item.id" title="开始考试" class="comm-btn c-btn-1">开始考试</a>
                      </div>
                    </section>
                    <h3 class="hLh30 txtOf mt10">
                      <a href="#" :title="item.paperName" class="course-title fsize18 c-333">{{item.paperName}}</a>
                    </h3>
                    <section class="mt10 hLh20 of">
                      <span class="fr jgTag bg-green">
                        <i class="c-fff fsize12 f-fA">考试时间：{{item.suggestTime}}ms</i>
                      </span>
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">总分：{{item.score}}</i>
                        |
                        <i class="c-999 f-fA">学科：{{item.subjectName}}</i>
                      </span>
                    </section>
                  </div>
                  </li>
              </ul>
              <div class="clear"></div>
          </article>
      </div>
        <!-- 公共分页 开始 -->
        <div>
            <div class="paging">
                <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                <a :class="{undisable: !data.hasPrevious}" 
                  @click.prevent="gotoPage(1)" href="#" title="首页">首</a>
                <a :class="{undisable: !data.hasPrevious}"
                  @click.prevent="gotoPage(data.current-1)" href="#" title="前一页">&lt;</a>
                <a v-for="page in data.pages" v-bind:key="page" 
                  :class="{current: data.current == page, undisable: data.current == page}" 
                  @click.prevent="gotoPage(page)" href="#" :title="'第'+page+'页'">{{page}}</a>
                <a :class="{undisable: !data.hasNext}" 
                  @click.prevent="gotoPage(data.current+1)" href="#" title="后一页">&gt;</a>
                <a :class="{undisable: !data.hasNext}" 
                  @click.prevent="gotoPage(data.pages)" href="#" title="末页">末</a>
                <div class="clear"></div>
            </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import subjectApi from '@/api/subject'
import paperApi from '@/api/paper'
export default {
  data () {
    return {
      page:1,
      data:{},
      subjectList:[], //课程分类
      searchObj: {
       
      }, // 查询表单对象
      buyCountSort:"",
      gmtCreate:"",
      priceSort:""
    }
  },
  created () {
    this.getlistSubject()
    this.getPageList()
    
  },
  methods:{
    //点击课程分类实现查询
    searchTwo(subjectId){
      this.searchObj.subjectId = subjectId;
      this.gotoPage(1)
    },
    //分页查询
    gotoPage(page) {
      this.page = page
      paperApi.getPageList(page, 8, this.searchObj).then(response => {
        this.data = response.data.data.map
      })
    },
    getlistSubject(){
      subjectApi.getListSubject()
      .then(response=>{
        this.subjectList = response.data.data.subjectList
      })
    },
    getPageList(){
      paperApi.getPageList(1,8,this.searchObj)
      .then(response=>{
        this.data = response.data.data.map
      })
    },
    //更新时间查询
    searchGmtCreate() {
      this.buyCountSort = "";
      this.gmtCreate = "1";
      this.priceSort = "";
      this.searchObj.buyCountSort = this.buyCountSort;
      this.searchObj.gmtCreate = this.gmtCreate;
      this.searchObj.priceSort = this.priceSort;
      this.gotoPage(this.page)
    },
  }
};
</script>
<style scoped>
  .active {
    background: #bdbdbd;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
</style>