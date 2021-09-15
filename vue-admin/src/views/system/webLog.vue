<template>
  <div class="app-container">
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
      <el-table-column prop="ip" label="IP" align="center" />
      <el-table-column prop="url" label="url" align="center" />
      <el-table-column prop="method" label="请求方式" align="center" />
      <el-table-column prop="description" label="描述" align="center" />
      <el-table-column prop="gmtCreate" label="创建时间" align="center" />
      <el-table-column prop="spendTime" label="用时(毫秒)" align="center" />
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
import webLogApi from '@/api/system/webLog'
export default {
  data() {
    return {
      page:1, //当前页
      limit:5,//每页记录数
      total:0,//总记录数
      listLoading:true, //当没有数据时显示

      list: [], //数据列表
      formLabelWidth: '80px',
    
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
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
       webLogApi.listPage(this.page,this.limit,this.searchObj)
      .then(response => {
        this.list = response.data.list.records
        this.total = response.data.list.total
        this.listLoading = false
      })
    }
  }
}
</script>
