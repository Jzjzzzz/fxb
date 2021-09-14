<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">    
      <el-form-item>
       <el-select v-model="searchObj.subjectId" placeholder="学科" >
          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id" :label="item.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
       <el-select v-model="searchObj.questionId" placeholder="题型" >
          <el-option v-for="item in editUrlEnum" :key="item.value" :value="item.value" :label="item.name"></el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-popover placement="bottom" trigger="click">
          <el-button type="warning" size="mini"  v-for="item in editUrlEnum" :key="item.key"
                     @click="$router.push({ path: '/edu/topic/'+item.dictCode+'/'+0 })">{{item.name}}
          </el-button>
          <el-button slot="reference" type="primary" class="link-left">添加</el-button>
      </el-popover>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      stripe
      element-loading-text="数据加载中"
      fit
      highlight-current-row
    >
      <el-table-column type="index" width="50" align="center" />
      <el-table-column prop="name" label="科目名称" align="center" />
      <el-table-column prop="questionName" label="题型" align="center" />
      <el-table-column prop="titleContent" :show-overflow-tooltip='true' label="题干" align="center">
        <template slot-scope="scope">
          <p v-html='scope.row.titleContent'></p>
        </template>
      </el-table-column>
      <el-table-column prop="score" label="分数" align="center" />
        
      <el-table-column prop="difficult" label="难度" align="center" >
        <template slot-scope="scope">
          <el-rate
            v-model='scope.row.difficult'
            disabled
            text-color="#ff9900"
          >
          </el-rate>
        </template>
      </el-table-column>
      <el-table-column prop="gmtCreate" label="创建时间" align="center" />
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="approvalShow(scope)"
          >
            修改
          </el-button>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeById(scope.row.id)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
      <!-- 分页组件 -->
    <el-pagination
      
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10]"
      style="padding: 30px 0; text-align: center;"
      layout="total, sizes, prev, pager, next, jumper"
      background
      @size-change="changePageSize"
      @current-change="changeCurrentPage"
      
    />
  </div>
</template>
<script>
import topicApi from '@/api/edu/topic'

export default {
  data() {
    return {
      editUrlEnum:[],
      searchObj:{},
      subjectFilter:[], //科目列表
      page:1, //当前页
      limit:5,//每页记录数
      total:0,//总记录数
      listLoading:true, //当没有数据时显示
      list: [], //数据列表
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    //修改跳转
    approvalShow(scope){
      if(scope.row.questionId==1){
        this.$router.push({ path: '/edu/topic/single-choice'+'/'+scope.row.id })
      }else if(scope.row.questionId==2){
        this.$router.push({ path: '/edu/topic/multiple-choice'+'/'+scope.row.id })
      }else if(scope.row.questionId==3){
        this.$router.push({ path: '/edu/topic/completion'+'/'+scope.row.id })
      }else if(scope.row.questionId==4){
        this.$router.push({ path: '/edu/topic/shortAnswer-question'+'/'+scope.row.id })
      }else if(scope.row.questionId==5){
        this.$router.push({ path: '/edu/topic/trueFalse-choice'+'/'+scope.row.id })
      }
      
      
    },
    // 根据id删除数据
    removeById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          topicApi.removeById(id)
          .then(response => {
            this.$message.success(response.message)
            this.fetchData()
          })
        })
        .catch(error => {
          this.$message.info('取消删除')
        })
    },
    //分页相关方法
    changePageSize(size) {
      this.limit = size
      this.fetchData()
    },
    changeCurrentPage(page) {
      this.page = page
      this.fetchData()
      
    },
    //清空搜索框
    resetData() {
      this.searchObj = {}
      this.fetchData() 
      },
    fetchData(){
      //列表数据
      topicApi.listTopicPage(this.page,this.limit,this.searchObj)
      .then(response=>{
        this.list = response.data.map.list
        this.editUrlEnum = response.data.map.dictList
        this.subjectFilter = response.data.map.subjectList
        this.total = response.data.map.total
        this.listLoading = false
      })
    }
  }
}
</script>


