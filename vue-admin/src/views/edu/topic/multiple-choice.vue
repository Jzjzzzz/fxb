<template>
  <div class="app-container">
    <el-form :model="form" ref="form" :rules="rules" label-width="100px" >
      <el-form-item label="学科：" prop="subjectId" required>
        <el-select v-model="form.subjectId" placeholder="学科" >
          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id" :label="item.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="题干：" prop="titleContent" required>
        <tinymce  :height="300"  v-model="form.titleContent"/>
      </el-form-item>
      <el-form-item label="选项：" >
        <el-form-item :label="item.prefix" :key="item.prefix"  v-for="(item,index) in form.items"  label-width="50px" class="question-item-label">
          <el-input v-model="item.prefix"  style="width:50px;" />
          <tinymce :height="300" v-model="item.content" />
          <el-button type="danger" size="mini" class="question-item-remove" icon="el-icon-delete" @click="questionItemRemove(index)"></el-button>
        </el-form-item>
      </el-form-item>
      <el-form-item label="解析：" prop="analyzes" >
        <tinymce :height="300" v-model="form.analyzes"/>
      </el-form-item>
      <el-form-item label="分数：" prop="score" required>
        <el-input-number v-model="form.score"  :step="1" :min="0" :max="100"></el-input-number>
      </el-form-item>
      <el-form-item label="难度：" required >
        <el-rate v-model="form.difficult" class="question-item-rate" style="padding-top:10px"></el-rate>
      </el-form-item>
      <el-form-item label="正确答案：" prop="correctArray" required>
        <el-checkbox-group v-model="form.correctArray">
          <el-checkbox @change="selectChange" v-for="item in form.items" :label="item.prefix" :key="item.prefix">{{item.prefix}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :disabled="isDisabled" @click="onSubmit">提交</el-button>
        <el-button type="success" @click="questionItemAdd">添加选项</el-button>
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
      isDisabled:false, //防止表单重复提交
      subjectFilter:[], //科目列表
      form:{
        subjectId: null,
        correct: '',
        titleContent: '',
        score: '',
        difficult: 0,
        content: '',
        analyzes: '',
        questionId: 2,
        correctArray: [],
        items: [
          {  prefix: 'A', content: '' },
          {  prefix: 'B', content: '' },
          {  prefix: 'C', content: '' },
          {  prefix: 'D', content: '' }
        ],
        
      },
      dialogVisible: false,
      rules: {
        subjectId: [{ required: true, message: '学科不能为空', trigger: 'blur' }],
        titleContent: [{ required: true, message: '题干不能为空', trigger: 'blur' }],
        score: [{ required: true, message: '分数不能为空', trigger: 'blur' },
        { type: 'number', message: '分数必须为数字类型'}],
        difficult: [{ required: true, message: '难度不能为空', trigger: 'blur' }],
        correctArray: [{ required: true, message: '正确答案不能为空', trigger: 'blur' }]
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
    //添加选项
    questionItemAdd () {
      if(this.form.items.length>20){
        this.$message.error('选项不能大于20个')
      }else{
        let items = this.form.items
        let newLastPrefix
        if (items.length > 0) {
          let last = items[items.length - 1]
          newLastPrefix = String.fromCharCode(last.prefix.charCodeAt() + 1)
        } else {
          newLastPrefix = 'A'
        }
        items.push({  prefix: newLastPrefix, content: '' })
        this.$forceUpdate()
      }
      
    },
    //移除选项
    questionItemRemove (index) {
      if(this.form.items.length<=1){
        this.$message.error('至少要有一个选项')
      }else{
        this.form.items.splice(index, 1)
      }
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
          this.form.items = JSON.parse(this.form.content)
          this.form.correctArray = this.form.correct.split(',')
          this.form.correct=''
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
          this.isDisabled=true
          this.form.content = JSON.stringify(this.form.items) 
          this.form.correct =  this.form.correctArray.toString()
          topicApi.saveTopic(this.form)
           .then(response=>{
            this.$message.success(response.message)
            this.$router.push({ path: '/edu/topic/list'})
      })
      .catch(response=>{
        this.isDisabled=false
      })
         }
      })
    },
    updateMultiple(){   
       this.$refs.form.validate(valid=>{
        if (!valid) {
           console.log('校验出错')
         }else{
          this.isDisabled=true
          this.form.content = JSON.stringify(this.form.items) 
          this.form.correct =  this.form.correctArray.toString()
          topicApi.updateTopicById(this.form)
          .then(response=>{
            this.$message.success(response.message)
            this.$router.push({ path: '/edu/topic/list'})
          })
          .catch(response=>{
            this.isDisabled=false
          })
         }
      })
    }
  }
}
</script>


