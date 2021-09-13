<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /试卷列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">考试记录</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <!-- 表格 -->
        <el-table
          :data="list"
          style="width: 100%">
          <el-table-column type="index" width="50" align="center" />
          <el-table-column
            prop="paperName"
            label="试卷名称"
            align="center"
            width="180">
          </el-table-column>
          <el-table-column
            prop="userScore"
            label="得分"
            align="center"
            width="180">
          </el-table-column>
          <el-table-column
            prop="questionCorrect"
            align="center"
            label="做对题数">
          </el-table-column>
          <el-table-column
            prop="gmtCreate"
            align="center"
            label="创建时间">
          </el-table-column>
          <el-table-column label="操作" width="300" align="center">
            <template slot-scope="scope">
              <el-link :href="'/records/'+scope.row.id" :underline="false" >查看<i class="el-icon-view el-icon--right"></i> </el-link>
            </template>
          </el-table-column>
        </el-table>
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
import recordsApi from '@/api/records'
import cookie from 'js-cookie'

export default {
  data () {
    return {
      page:1,
      data:{}, 
      list:[],
      loginInfo:{
        id: 0,
        age: '',
        avatar: '',
        mobile: '',
        nickname: '',
        sex: ''
      },  
    }
  },
  created () {
    //用户信息
    this.showInfo()
    this.getPageList()
  },
  methods:{
    //从cookie中获取用户信息
     showInfo() {
      //debugger
      var jsonStr = cookie.get("guli_ucenter");
      console.log("jsonStr:"+jsonStr)
      
      if (jsonStr) { 
        this.loginInfo = JSON.parse(jsonStr)
      }
      if(this.loginInfo.id==0){
        this.$router.push({ path: '/login' })
      }
    },
    getPageList(){
      recordsApi.getTestRecordList(1,8,this.loginInfo.id)
      .then(response=>{
        this.data = response.data.data.map
        this.list = response.data.data.map.items
      })
    },
    //分页查询
    gotoPage(page) {
      this.page = page
      recordsApi.getTestRecordList(page, 8,this.loginInfo.id).then(response => {
        this.data = response.data.data.map
        this.list = response.data.data.map.items
      })
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