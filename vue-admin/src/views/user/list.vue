<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">    
      <el-form-item>
        <el-input v-model="searchObj.nickname" placeholder="会员名"/>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchObj.mobile" placeholder="手机号"/>
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
      <el-table-column prop="nickname" label="会员昵称" align="center" />
      <el-table-column prop="mobile" label="手机号" align="center" />
      <el-table-column prop="avatar" label="头像" align="center" >
        <template slot-scope="scope">
          <div class="demo-image__preview">
            <el-image 
              style="width: 100px; height: 100px"
              :src="scope.row.avatar" 
              >
            </el-image>
          </div>

        </template>
      </el-table-column>

      <el-table-column prop="isDisabled" width="100" label="状态" align="center">
        <template slot-scope="scope">
          <template v-if="scope.row.isDisabled == 1">
            <span>已禁用</span>
          </template>
          <template v-if="scope.row.isDisabled == 0">
            <span>正常</span>
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
    <!-- 添加或修改对话框 -->
    <el-dialog  @open="isDisabled=false" :before-close="onClearFrom" title="新增用户" :visible.sync="dialogVisible" width="30%">
      <el-form :model="form" :rules="rules"  ref="form">
        <el-form-item label="昵称" :label-width="formLabelWidth" prop="nickname">
        <el-input type="text" v-model="form.nickname" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth" prop="mobile">
        <el-input type="text" v-model="form.mobile" auto-complete="off"></el-input>
        </el-form-item>
        <!-- logo -->
        <el-form-item label="头像" :label-width="formLabelWidth">
          <!-- 缩略图 -->
            <pan-thumb :image="form.avatar"/>
            <!-- 文件上传按钮 -->
            <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
            </el-button>

            <!--v-show：是否显示上传组件
                      :key：类似于id，如果一个页面多个图片上传控件，可以做区分
                      :url：后台上传的url地址
                      @close：关闭上传组件
                      @crop-upload-success：上传成功后的回调 -->
            <image-cropper
                          v-show="imagecropperShow"
                          :width="auto"
                          :height="auto"
                          :key="imagecropperKey"
                          :url="BASE_API+uploadUrl+'/Avatar'"
                          field="file"
                          @close="close"
                          @crop-upload-success="cropSuccess"/>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth" prop="isDisabled">
          <el-select v-model="form.isDisabled" placeholder="请选择" clearable>
            <el-option :value="0" label="正常"/>
            <el-option :value="1" label="禁用"/>
          </el-select>
        </el-form-item>
       <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
        <el-input type="password" v-model="form.password" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onClearFrom">
          取消
        </el-button>
        <el-button type="primary" @click="onSubmit" :disabled="isDisabled">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
import userApi from '@/api/user/user'
export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      BASE_API: process.env.BASE_API, // 接口API地址
      imagecropperShow: false, // 是否显示上传组件
      imagecropperKey: 0, // 上传组件id
      isDisabled:true, //防止表单重复提交
      page:1, //当前页
      limit:5,//每页记录数
      total:0,//总记录数
      listLoading:true, //当没有数据时显示
      searchObj:{
        nickname:'', //昵称
        mobile:'' //手机号
      },//条件封装对象
      list: [], //数据列表
      dialogVisible: false, //添加或修改对话框
      formLabelWidth: '80px',
      form: {
      },
      rules: {
        nickname: [
          { required: true, message: '昵称不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1到20个字符' }
        ],
        mobile: [
          { required: true, message: '手机号不能为空', trigger: 'blur' }
        ],
        isDisabled: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
      },
      uploadUrl:'/eduoss/fileoss/upload/'
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    //清空form表单
    onClearFrom(){
      this.dialogVisible=false
      this.form= {
        
      }
      this.$refs.form.resetFields(); //清空校验规则
    },
    //新增修改提交表单
    onSubmit(){
      if(this.form.id!=null){
        this.updateTop()
      }else{
        this.cteateTop()
      }
    },
    //新增
    cteateTop(){
      this.$refs.form.validate(valid=>{
         if (!valid) {
           console.log('校验出错')
         }else{
           this.isDisabled=true //禁用提交按钮
           userApi.save(this.form)
           .then(response=>{
            this.dialogVisible = false
            this.$message.success(response.message)
            this.fetchData()
            this.onClearFrom()
      })
      .catch(response=>{
        this.isDisabled=false
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
          this.isDisabled=true //禁用提交按钮
          userApi.updateById(this.form)
          .then(response=>{
            this.dialogVisible = false
            this.$message.success(response.message)
            this.fetchData()
            this.onClearFrom()
      })
      .catch(response=>{
        this.isDisabled=false
      })
        }
      })
    },
    // 上传成功后的回调函数(头像上传相关)
    cropSuccess(data) {
      this.imagecropperShow = false
      this.form.avatar = data.url
      // 上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1
    },
    // 关闭上传组件(头像上传相关)
    close() {
      this.imagecropperShow = false
      // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1
    },
    // 根据id删除数据
    removeById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          userApi.removeById(id)
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
      this.searchObj = {
        name:''
      }
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
    //根据id查询
    approvalShow(id){
      this.dialogVisible = true
      userApi.getById(id)
      .then(response=>{
        this.form = response.data.model
        if(this.form.isDisabled){
          this.form.isDisabled = 1
        }else {
          this.form.isDisabled = 0
        }
        this.form.password=""
      })
    },
    //列表数据
    fetchData() {
      userApi.listPage(this.page,this.limit,this.searchObj)
      .then(response => {
        this.list = response.data.list.records
        this.total = response.data.list.total
        this.listLoading = false
      })
    }
  }
}
</script>
