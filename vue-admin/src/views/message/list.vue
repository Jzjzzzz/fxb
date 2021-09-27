<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.title" placeholder="标题"/>
      </el-form-item> 
      <el-form-item>
        <el-select v-model="searchObj.type" placeholder="类型" >
          <el-option v-for="item in typeList" :key="item.type" :value="item.type" :label="item.name"></el-option>
        </el-select>
      </el-form-item>   
      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>  
      <router-link :to="{path:'/message/edit/0'}" class="link-left">
          <el-button type="primary">添加</el-button>
      </router-link>
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
      <el-table-column prop="title" label="标题" align="center" />
      <el-table-column prop="createUser" label="创建者" align="center" />
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

      <el-table-column prop="gmtCreate" label="创建时间" align="center" />
      <el-table-column prop="gmtModified" label="修改时间" align="center" />
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="approvalShow(scope.row.id)"
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
import messageApi from '@/api/message/message'
export default {
  data() {
    return {
      typeList:[{type:1,name:'公告'},{type:2,name:'反馈'}],
      searchObj:{
      },//条件封装对象
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
    approvalShow(id){
        this.$router.push({ path: '/message/edit'+'/'+id })
    },
    // 根据id删除数据
    removeById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          messageApi.removeById(id)
          .then(response => {
            this.$message.success(response.message)
            this.fetchData()
          })
        })
        .catch(error => {
          this.$message.info('取消删除')
        })
    },
    //清空搜索框
    resetData() {
      this.searchObj = {}
      this.fetchData() 
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
    //列表数据
    fetchData() {
      messageApi.listPage(this.page,this.limit,this.searchObj)
      .then(response => {
        this.list = response.data.listPage.records
        this.total = response.data.listPage.total
        this.listLoading = false
      })
    }
   
  }
}
</script>
