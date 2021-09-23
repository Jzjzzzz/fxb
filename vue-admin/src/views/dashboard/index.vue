<template>
  <div class="app-container">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-people">
          <i class="el-icon-user" style="font-size:50px"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">会员人数</div>
          <count-to :start-val="0" :end-val="list.userCount" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-people">
          <i class="el-icon-edit-outline" style="font-size:50px"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">题数</div>
          <count-to :start-val="0" :end-val="list.topicCount" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-people">
          <i class="el-icon-s-order" style="font-size:50px"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">试卷数</div>
          <count-to :start-val="0" :end-val="list.paperCount" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-people">
          <i class="el-icon-view" style="font-size:50px"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">在线考试次数</div>
          <count-to :start-val="0" :end-val="list.testTimes" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
      </el-col>
    </el-row>
    <div id="main" style="width: 1700px;height:450px;"></div> 
    <div id="main2" style="width: 1700px;height:450px;"></div> 
  </div>
</template>
<script>
import echarts from 'echarts'
import CountTo from 'vue-count-to'
import indexApi from '@/api/index'

export default {
  components: {
    CountTo
  },
  name: 'Dashboard',
   data() {
    return {
      list:{},
      chart: null,
      chart2: null,
      title: '',
      xDataOne: [],
      xDataTow: [],
      yData: []
    }
  },
  mounted(){
    this.initChartData()
    
  },
  methods: {
    // 准备图表数据
    initChartData() {
      indexApi.getCount()
      .then(response=>{
        this.list = response.data.model
        // 横轴时间
        this.xData = this.list.recentDate
        this.xDataOne = this.list.userNumber
        this.xDataTow = this.list.topicNumber
        this.setChart()
        this.setChartTow()
      })
      
    },
    // 设置图标参数
    setChart() {
      // 基于准备好的dom，初始化echarts实例
      this.chart = echarts.init(document.getElementById('main'))
      // console.log(this.chart)
      // 指定图表的配置项和数据
      var option = {
        title:{
          text: "近七天会员上涨量",
          left: "center",
          top: "top",
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
        type: 'category',
        data: this.xData
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: this.xDataOne,
            type: 'line'
        }],
       
        
      }
      this.chart.setOption(option)
    },
    // 设置图标参数
    setChartTow() {
      // 基于准备好的dom，初始化echarts实例
      this.chart2 = echarts.init(document.getElementById('main2'))
      // console.log(this.chart)
      // 指定图表的配置项和数据
      var option = {
        title:{
          text: "近七天题目上涨量",
          left: "center",
          top: "top",
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
        type: 'category',
        data: this.xData
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: this.xDataTow,
            type: 'line'
        }],
        
      }
      this.chart2.setOption(option)
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.panel-group {
  margin-top: 18px;
  .card-panel-col{
    margin-bottom: 32px;
  }
  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }
      .icon-people {
         background: #40c9c6;
      }
      .icon-message {
        background: #36a3f7;
      }
      .icon-money {
        background: #f4516c;
      }
      .icon-shopping {
        background: #34bfa3
      }
    }
    .icon-people {
      color: #40c9c6;
    }
    .icon-message {
      color: #36a3f7;
    }
    .icon-money {
      color: #f4516c;
    }
    .icon-shopping {
      color: #34bfa3
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
