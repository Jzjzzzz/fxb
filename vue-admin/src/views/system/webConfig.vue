<template>
  <div class="app-container">
    <el-tabs type="border-card">
      <el-tab-pane label="网站信息">
        <span slot="label">
          <i class="el-icon-date"></i>
          网站信息
        </span>

        <el-form ref="form" :model="form" label-width="80px">
          <!-- logo -->
        <el-form-item label="网站Logo">
          <!-- 缩略图 -->
            <pan-thumb :image="form.logo"/>
            <!-- 文件上传按钮 -->
            <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换Logo
            </el-button>

            <!--v-show：是否显示上传组件
                      :key：类似于id，如果一个页面多个图片上传控件，可以做区分
                      :url：后台上传的url地址
                      @close：关闭上传组件
                      @crop-upload-success：上传成功后的回调 -->
            <image-cropper
                          v-show="imagecropperShow"
                          :width="200"
                          :height="78"
                          :key="imagecropperKey"
                          :url="BASE_API+uploadUrl+'/Logo'"
                          field="file"
                          @close="close"
                          @crop-upload-success="cropSuccess"/>
        </el-form-item>
        
          <el-row :gutter="24">
            <el-col :span="10">
              <el-form-item label="网站名称" prop="name">
                <el-input v-model="form.name" style="width: 400px"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="10">
              <el-form-item label="关键字" prop="keyword">
                <el-input
                  v-model="form.keyword"
                  style="width: 400px"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="网站介绍" prop="summary">
                <el-input
                  v-model="form.summary"
                  style="width: 400px"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="24">
            <el-col :span="10">
              <el-form-item label="作者" prop="author">
                <el-input v-model="form.author" style="width: 400px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="备案号" prop="recordNum">
                <el-input
                  v-model="form.recordNum"
                  style="width: 400px"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item>
            <el-button type="primary" @click="submitForm()">
              保 存
            </el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="评论和打赏">
        <span slot="label">
          <i class="el-icon-date"></i>
          首页轮播图
        </span>
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="轮播图" prop="slideshowOne">
              <el-upload
                  class="upload-demo"
                  :action="handleBeforeUploadImg()"
                  :on-preview="handlePreview"
                  :on-remove="handleRemove"
                  :on-success="handleSuccess"
                  :file-list="fileList"
                  :limit="3"
                  :on-exceed="handleExceed"
                  list-type="picture">
                  <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="submitForm()"
              v-permission="'/webConfig/editWebConfig'"
            >
              保 存
            </el-button>
            
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane v-permission="'/webConfig/getWebConfig'">
        <span slot="label">
          <i class="el-icon-date"></i>
          关注我们
        </span>
        <el-form
          style="margin-left: 20px;"
          label-position="left"
          :model="form"
          label-width="80px"
          ref="form"
        >
          <el-checkbox-group v-model="form.showList">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" style="width: 400px"></el-input>
            </el-form-item>
            <el-form-item label="QQ号" prop="qqNumber">
              <el-input v-model="form.qqNumber" style="width: 400px"></el-input>
            </el-form-item> 
            <el-form-item label="github" prop="github">
              <el-input v-model="form.github" style="width: 400px"></el-input>
            </el-form-item>
            <el-form-item label="Gitee" prop="gitee">
              <el-input v-model="form.gitee" style="width: 400px"></el-input>
            </el-form-item>
            <el-form-item label="微信" prop="weChat">
              <el-input v-model="form.weChat" style="width: 400px"></el-input>
            </el-form-item>
          </el-checkbox-group>
          <el-form-item>
            <el-button
              type="primary"
              @click="submitForm()"
              v-permission="'/webConfig/editWebConfig'"
            >
              保 存
            </el-button>
            
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<style>
.hide .el-upload--picture-card {
  display: none;
}
</style>
<script>
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
import webConfigApi from '@/api/system/webConfig'
export default {
  components: { ImageCropper, PanThumb },
  // 定义数据模型
  data() {
    return {

      fileList: [{url:''},{url:''},{url:''}],
      BASE_API: process.env.BASE_API, // 接口API地址
      imagecropperShow: false, // 是否显示上传组件
      imagecropperKey: 0, // 上传组件id
      form: {
        name: '',
        title: '',
        keyword: '',
        summary: '',
        author: '',
        logo: '',
        recordNum: '',
        openComment: '1',
        aliPay: '',
        weixinPay: '',
        aliPayPhoto: '',
        weixinPayPhoto: '',
        showList: [],
        loginTypeList: []
      },
      
      uploadUrl:'/eduoss/fileoss/upload/'  
    }
  },
  // 页面渲染成功后获取数据
  created() {
    this.fetchData()
  },

  // 定义方法
  methods: {
    //(轮播图上传相关)
    handleSuccess(response){
      this.fileList.push(response.data.url)
    },
    //(轮播图上传相关)
    handleExceed(){
      this.$message.error('轮播图不能大于3张')
    },
    handleBeforeUploadImg: function() {
      return this.BASE_API + this.uploadUrl+'slideshow'
    },
    //(轮播图上传相关)
     handleRemove(file) {

        for (const key in this.fileList) {
          if (file.uid == this.fileList[key].uid) {
           this.fileList.splice(key,1)
          }
        }
      },
      //(轮播图上传相关) 
      handlePreview(file) {
        console.log(file);
      },
    // 上传成功后的回调函数(Logo上传相关)
    cropSuccess(data) {
      console.log(data)
      this.imagecropperShow = false
      this.form.logo = data.url
      // 上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1
      this.submitForm()
    },
    // 关闭上传组件(Logo上传相关)
    close() {
      this.imagecropperShow = false
      // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1
    },

    fetchData() {
      // 调用api
      webConfigApi.getWebConfig().then(response => {
        this.form = response.data.webConfig
       
        this.fileList[0].url = this.form.slideshowOne
        this.fileList[1].url = this.form.slideshowTwo
        this.fileList[2].url = this.form.slideshowThree


      })
    },

    submitForm() {
      let form = this.form
      form.slideshowOne = this.fileList[0] 
      form.slideshowTwo = this.fileList[1] 
      form.slideshowThree = this.fileList[2] 
      webConfigApi.editWebConfig(form).then(response => {
        this.$message.success('保存成功')
        this.fetchData()
      })
    }
  }
}
</script>
