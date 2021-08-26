<template>
  <div class="app-container">
    <el-form :model="form" :rules="rules" ref="form" label-width="100px" >
      <el-form-item label="学科：" prop="subjectId" required>
        <el-select v-model="form.subjectId" placeholder="学科" >
          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id" :label="item.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="题干：" prop="titleContent" required>
        <tinymce  :height="300"  v-model="form.titleContent"/>
      </el-form-item>
      <el-form-item label="答案" prop="correct" required>
        <tinymce :height="300" v-model="form.correct"/>
      </el-form-item>
      <el-form-item label="解析：" prop="analyzes" >
        <tinymce :height="300" v-model="form.analyzes"/>
      </el-form-item>
      <el-form-item label="分数：" prop="score" required>
        <el-input-number v-model="form.score" :precision="1" :step="1" :max="100"></el-input-number>
      </el-form-item>
      <el-form-item label="难度：" required>
        <el-rate v-model="form.difficult" class="question-item-rate"></el-rate>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>
    
   
  </div>
</template>

<script>
import topicApi from '@/api/edu/topic'
import Tinymce from '@/components/Tinymce'

export default {
  components: { Tinymce },
  data() {
    return {
      subjectFilter:[], //科目列表
      form:{
        subjectId: null,
        correct: '',
        titleContent: '',
        score: '',
        difficult: 0,
        content: '',
        analyzes: '',
        questionId: 4,     
      },
      dialogVisible: false,
      rules: {
        subjectId: [{ required: true, message: '学科不能为空', trigger: 'blur' }],
        titleContent: [{ required: true, message: '题干不能为空', trigger: 'blur' }],
        score: [{ required: true, message: '分数不能为空', trigger: 'blur' },
        { type: 'number', message: '分数必须为数字类型'}],
        difficult: [{ required: true, message: '难度不能为空', trigger: 'blur' }],
        correct: [{ required: true, message: '正确答案不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {  
    //强制更新视图
    selectChange(){
      this.$forceUpdate()
    },
   
    fetchData(){
      topicApi.listSubject()
      .then(response=>{
        this.subjectFilter = response.data.list
      })
      //当id不为0的时候回显数据
      if(this.$route.params.id!=0){
        topicApi.getById(this.$route.params.id)
        .then(response=>{
          this.form = response.data.model
        })
      }
    },
    //新增修改提交表单
    onSubmit(){
      if(this.$route.params.id==0){
        this.cteateMultiple()
      }
      else(
        this.updateMultiple()
      )
    },
    //新增
    cteateMultiple(){  
      this.$refs.form.validate(valid=>{
        if (!valid) {
           console.log('校验出错')
         }else{
           topicApi.saveTopic(this.form)
           .then(response=>{
            this.$message.success(response.message)
            this.$router.push({ path: '/edu/topic/list'})
      })
         }
      })
    },
    updateMultiple(){
      this.$refs.form.validate(valid=>{
        if (!valid) {
           console.log('校验出错')
         }else{
           topicApi.updateTopicById(this.form)
      .then(response=>{
        this.$message.success(response.message)
        this.$router.push({ path: '/edu/topic/list'})
      })
         }
      })
      
    }
  }
}
</script>


