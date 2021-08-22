<template>
  <div class="app-container">
   <el-form :model="form" ref="form" label-width="100px"  >
      <el-form-item label="学科：" prop="subjectId" required>
        <el-select v-bind:disabled="this.$route.params.id!=0" v-model="form.subjectId" placeholder="学科">
          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id"
                     :label="item.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="试卷名称："  prop="paperName" required>
        <el-input v-model="form.paperName"/>
      </el-form-item>
          <el-form-item label="试卷简介："  prop="paperTitle" required>
        <el-input v-model="form.paperTitle"/>
      </el-form-item>
      <el-form-item label="建议时长：" prop="suggestTime" required>
        <el-input v-model="form.suggestTime" placeholder="分钟"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button type="success" @click="addTopic">添加题目</el-button>
        <el-button type="info" @click="goBackList">返回列表</el-button>

      </el-form-item>
    </el-form>
    
    <!-- 添加或修改对话框 -->
    <el-dialog   title="新增题目" :visible.sync="dialogVisible" width="70%">
      <el-table v-loading="listLoading" :data="list"
           @selection-change="handleSelectionChange" :row-key="handleReserve"    ref="multipleTable"  border fit highlight-current-row style="width: 100%">
      <el-table-column type="selection" reserve-selection width="40"></el-table-column>
      <el-table-column type="index" width="50" align="center" />
        <el-table-column prop="questionName" label="题型"  width="70px"/>
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
        </el-table>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确定</el-button>
     </span>
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
    </el-dialog>
   
  </div>
</template>
<script>
import topicApi from '@/api/edu/topic'
import paperApi from '@/api/edu/paper'

export default {
  data() {
    return {
      form:{
        id:'',
        subjectId:null,
        paperName:'',
        paperTitle:'',
        suggestTime:30,
        topicListVoList:[]
      },
      multipleTable:[],
      subjectFilter:[],
      page:1, //当前页
      limit:5,//每页记录数
      total:0,//总记录数
      listLoading:true, //当没有数据时显示
      dialogVisible: false, //添加或修改对话框
      list: [], //数据列表
      formLabelWidth: '80px',
      firstFlag: true
      
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    goBackList(){
      this.$confirm('返回将清空未保存信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$router.push({ path: '/edu/paper/list'})
        })
        .catch(error => {
          
        })
    },

    handleReserve (row) {
      return row.id
    },
    //新增
    createPaper(){
      this.form.topicListVoList = this.multipleTable
        paperApi.save(this.form)
        .then(response=>{
            this.$message.success(response.message)
            this.$router.push({ path: '/edu/paper/list'})
        })
    },
    //修改
    updatePaper(){
      this.form.topicListVoList = this.multipleTable
      paperApi.updateById(this.form)
        .then(response=>{
            this.$message.success(response.message)
            this.$router.push({ path: '/edu/paper/list'})
        })
    },
    submitForm(){
        if(this.$route.params.id!=0){
          this.updatePaper()
        }else{
          this.createPaper()
        }
    },

    handleSelectionChange (val) {
        this.multipleTable = val
    },
      //分页相关方法
    changePageSize(size) {
      this.limit = size
      this.addTopic()
    },
    changeCurrentPage(page) {
      this.page = page
      this.addTopic()
    },
    addTopic(){
        if(this.form.subjectId!=null){
            this.dialogVisible = true
          topicApi.listTopicPageBySubjectId(this.form.subjectId,this.page,this.limit)
          .then(response=>{
              this.list = response.data.map.list
              this.total = response.data.map.total
              this.listLoading=false
              if(this.firstFlag==true){
                this.form.topicListVoList.forEach(item=>{
                  this.$refs.multipleTable && this.$refs.multipleTable.toggleRowSelection(item, true)
              })
              this.firstFlag = false
              }  
          })
        }else{
            this.$message.error("请先选择课程")
        }  
      },
    fetchData(){
      topicApi.listSubject()
      .then(response=>{
        this.subjectFilter = response.data.list
      })
      if(this.$route.params.id!=0){
        paperApi.getById(this.$route.params.id)
        .then(response=>{
          this.form = response.data.model
          this.multipleTable =this.form.topicListVoList   
        })
      }
    }
  }
}
</script>