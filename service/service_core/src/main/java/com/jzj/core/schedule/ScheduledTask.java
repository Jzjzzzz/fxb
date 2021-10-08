package com.jzj.core.schedule;
import com.jzj.core.service.EduStatisticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author Jzj
 * @Date 2021/10/8 17:03
 * @Version 1.0
 */
@Component
public class ScheduledTask {

    @Resource
    private EduStatisticsService statisticsService;

    /**
     * 统计站点数据定时任务
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task1(){
        statisticsService.statistics();
        System.out.println("统计站点数据定时任务执行了！！！");
    }
}
