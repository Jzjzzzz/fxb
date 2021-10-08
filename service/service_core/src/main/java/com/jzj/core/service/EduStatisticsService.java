package com.jzj.core.service;

import com.jzj.core.pojo.entity.EduStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 数据统计 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-10-08
 */
public interface EduStatisticsService extends IService<EduStatistics> {
    void statistics();
}
