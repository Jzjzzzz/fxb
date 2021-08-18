<template>
  <div class="app-container">
   <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="学科：" prop="subjectId" required>
        <el-select v-model="form.subjectId" placeholder="学科">
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
      </el-form-item>
    </el-form>
    
    <!-- 添加或修改对话框 -->
    <el-dialog  :before-close="onClearFrom" title="新增题目" :visible.sync="dialogVisible" width="70%">
      <el-table v-loading="listLoading" :data="list"
           @selection-change="handleSelectionChange"   ref="multipleTable"  border fit highlight-current-row style="width: 100%">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column type="index" width="50" align="center" />
        <el-table-column prop="questionName" label="题型" :formatter="questionTypeFormatter" width="70px"/>
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
      
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    submitForm(){
        this.form.topicListVoList = this.multipleTable
        paperApi.save(this.form)
        .then(response=>{
            this.$message.success(response.message)
            this.$router.push({ path: '/edu/paper/list'})
        })
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
    }
  }
}
</script>