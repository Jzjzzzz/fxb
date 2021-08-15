<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true"  class="demo-form-inline">
      
      <el-form-item>
        <el-input v-model="searchObj.name" placeholder="字典名"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button
        class="grid-content bg-purple"
        type="primary"
        icon="el-icon-edit"
        @click="dialogVisible = true"
      >
        新增
      </el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
      <el-button style="margin-left:1100px" type="danger" @click="resetCache()" >清空缓存</el-button>
    </el-form>
    <el-alert
    title="警告"
    type="warning"
    description="每次新增或者修改字典数据需要点击清空缓存"
    show-icon
    close-text="知道了"
    >
  </el-alert>
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
      <el-table-column prop="name" label="字典名称" align="center" />
      <el-table-column label="值" width="100" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.$index % 5 == 0" type="warning">
            {{ scope.row.value }}
          </el-tag>
          <el-tag v-if="scope.$index % 5 == 1" type="success">
            {{ scope.row.value }}
          </el-tag>
          <el-tag v-if="scope.$index % 5 == 2" type="info">
            {{ scope.row.value }}
          </el-tag>
          <el-tag v-if="scope.$index % 5 == 3" type="danger">
            {{ scope.row.value }}
          </el-tag>
          <el-tag v-if="scope.$index % 5 == 4">
            {{ scope.row.value }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" width="100" label="状态" align="center">
        <template slot-scope="scope">
          <template v-if="scope.row.status == 1">
            <span>正常</span>
          </template>
          <template v-if="scope.row.status == 0">
            <span>停用</span>
          </template>
        </template>
      </el-table-column> 

      <el-table-column prop="remark" label="备注" align="center" />
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
    <!-- 添加或修改对话框 -->
    <el-dialog :before-close="onClearFrom" title="新增字典" :visible.sync="dialogVisible" width="30%">
      <el-form :model="form" :rules="rules"  ref="form">
        <el-form-item label="字典名" :label-width="formLabelWidth" prop="name">
        <el-input type="text" v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="值" :label-width="formLabelWidth" prop="value">
          <el-input v-model="form.value" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="字典状态" :label-width="formLabelWidth" prop="status">
          <el-select v-model="form.status" placeholder="请选择" clearable>
            <el-option :value="1" label="正常"/>
            <el-option :value="0" label="停用"/>
          </el-select>
        </el-form-item>
        <el-form-item label="字典备注"  :label-width="formLabelWidth" prop="remark">
          <el-input type="textarea" v-model="form.remark" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onClearFrom">
          取消
        </el-button>
        <el-button type="primary" @click="onSubmit">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import dictApi from '@/api/system/dict'
export default {
  data() {
    return {
      page:1, //当前页
      limit:5,//每页记录数
      total:0,//总记录数
      listLoading:true, //当没有数据时显示
      searchObj:{
        name:''
      },//条件封装对象
      list: [], //数据字典列表
      dialogVisible: false, //添加或修改对话框
      formLabelWidth: '80px',
      form: {
        name: '',
        status: 1, //状态
        remake: ""
      },
      dictTypeUid: '', // 从SysDictType传递过来的
      rules: {
        name: [
          { required: true, message: '字典名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1到20个字符' }
        ],
        value: [
          { required: true, message: '字典值不能为空', trigger: 'blur' },
          { pattern: /^[0-9]\d*$/, message: '排序字段只能为自然数' }
        ],
        status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    //传递过来的dictTypeUid
    this.dictTypeUid = this.$route.params.id
    console.log(this.dictTypeUid)
    this.fetchData()
  },
  methods: {
    //清空redis缓存按钮
    resetCache(){
      dictApi.emptyDictByParentId(this.dictTypeUid)
      .then(response=>{
        this.$message.success(response.message)
      })
    },
    //清空form表单
    onClearFrom(){
      this.dialogVisible=false
      this.form= {
        name: '',
        status: 1, //状态
        remake: ""
      }
      this.$refs.form.resetFields(); //清空校验规则
    },
    //新增修改提交表单
    onSubmit(){
      if(this.form.id!=null){
        this.updateTop()
      }else{
        this.cteateSun()
      }
    },
    //新增
    cteateSun(){
      this.$refs.form.validate(valid=>{
         if (!valid) {
           console.log('校验出错')
         }else{
           dictApi.saveSun(this.dictTypeUid,this.form)
           .then(response=>{
            this.dialogVisible = false
            this.$message.success(response.message)
            this.fetchData()
            this.onClearFrom()
      })
         }
      })
    },
    //修改
    updateTop(){
      this.$refs.form.validate(valid=>{
        if (!valid) {
          console.log('校验出错')
        }else{
          dictApi.updateById(this.form)
          .then(response=>{
            this.dialogVisible = false
            this.$message.success(response.message)
            this.fetchData()
            this.onClearFrom()
      })
        }
      })
    },
    //根据id查询字典
    approvalShow(id){
      this.dialogVisible = true
      dictApi.getById(id)
      .then(response=>{
        this.form = response.data.model
        if(this.form.status){
          this.form.status = 1
        }else {
          this.form.status = 0
        }
      })
    },
    //清空搜索框
    resetData() {
      this.searchObj = {
        name:''
      }
      this.fetchData() 
      },
    // 根据id删除数据
    removeById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          dictApi.removeByIdTop(id)
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
    //字典列表数据
    fetchData() {
      dictApi.listByParentId(this.page,this.limit,this.dictTypeUid,this.searchObj)
      .then(response => {
        this.list = response.data.listPage.records
        this.total = response.data.listPage.total
        this.listLoading = false
      })
    }
  }
}
</script>
