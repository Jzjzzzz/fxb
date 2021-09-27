<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /试卷列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">消息</span>
        </h2>
      </header>
      <!-- /无数据提示 开始-->
              <section class="no-data-wrap" v-if="this.list.length<=0">
                  <em class="icon30 no-data-ico">&nbsp;</em>
                  <span class="c-666 fsize14 ml10 vam">暂无数据</span>
              </section>
      <!-- /无数据提示 结束-->
      <section class="c-sort-box"> 
        <!-- 表格 -->
        <el-table
          :data="list"
          style="width: 100%">
          <el-table-column type="index" width="50" align="center" />
          <el-table-column
            prop="title"
            label="标题"
            align="center"
            width="180">
          </el-table-column>
          <el-table-column prop="type" width="100" label="类型" align="center">
            <template slot-scope="scope">
            <template v-if="scope.row.type == 1">
                <span>公告</span>
            </template>
            <template v-if="scope.row.type == 2">
                <span>反馈</span>
            </template>
            </template>
          </el-table-column>
          <el-table-column prop="status" width="100" label="状态" align="center">
                <template slot-scope="scope">
                <template v-if="scope.row.status == 1">
                    <span>正常</span>
                </template>
                <template v-if="scope.row.status == 2">
                    <span>已回复</span>
                </template>
                </template>
          </el-table-column>
          <el-table-column
            prop="gmtCreate"
            align="center"
            label="创建时间">
          </el-table-column>
          <el-table-column label="操作" width="300" align="center">
            <template slot-scope="scope">
              <el-link :href="'/message/'+scope.row.id" :underline="false" >查看<i class="el-icon-view el-icon--right"></i> </el-link>
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
import messageApi from '@/api/message'
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
      messageApi.getPageList(1,8,this.loginInfo.id)
      .then(response=>{
        this.data = response.data.data.map
        this.list = response.data.data.map.items
      })
    },
    //分页查询
    gotoPage(page) {
      this.page = page
      messageApi.getPageList(page, 8,this.loginInfo.id).then(response => {
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