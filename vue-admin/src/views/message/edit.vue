<template>
  <div class="app-container">
   <el-form :rules="rules"  :model="form" ref="form" label-width="100px"  >
      <el-form-item label="公告标题："  prop="title" >
        <el-input v-model="form.title"/>
      </el-form-item>
      <el-form-item label="公告内容：" prop="content" >
        <tinymce :height="300" v-model="form.content"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm" :disabled="isDisabled">提交</el-button>
        <el-button type="info" @click="goBackList">返回列表</el-button>
      </el-form-item>
    </el-form>

   
  </div>
</template>
<script>
import Tinymce from '@/components/Tinymce'
import messageApi from '@/api/message/message'


export default {
  components: { Tinymce },
  data() {
    return {
        form:{},
        isDisabled:false, //防止表单重复提交
        rules: {
        title: [{ required: true, message: '公告标题不能为空', trigger: 'blur' }],
        content: [{ required: true, message: '公告内容不能为空', trigger: 'blur' }],
      },      
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    //新增修改提交表单
    submitForm(){
      if(this.$route.params.id==0){
        this.cteateMessage()
      }
      else(
        this.updateMessage()
      )
    },
    //新增
    cteateMessage(){  
      this.$refs.form.validate(valid=>{
        if (!valid) {
           console.log('校验出错')
         }else{
            this.isDisabled=true
            messageApi.save(this.form)
           .then(response=>{
            this.$message.success(response.message)
            this.$router.push({ path: '/message/message/list'})
      })
       .catch(response=>{
        this.isDisabled=false
      })
         }
      })
    },
    //修改
    updateMessage(){
      this.$refs.form.validate(valid=>{
        if (!valid) {
           console.log('校验出错')
         }else{
          this.isDisabled=true
          messageApi.updateById(this.form)
            .then(response=>{
                this.$message.success(response.message)
                this.$router.push({ path: '/message/message/list'})
            })
            .catch(response=>{
              this.isDisabled=false
            })
         }
      })
    },
    fetchData(){
      //当id不为0的时候回显数据
      if(this.$route.params.id!=0){
        messageApi.getById(this.$route.params.id)
        .then(response=>{
          this.form = response.data.model
        })
      }
    },
  }
}
</script>