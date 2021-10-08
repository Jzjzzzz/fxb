package com.jzj.core.service.impl;

import com.jzj.core.mapper.*;
import com.jzj.core.pojo.entity.EduStatistics;
import com.jzj.core.service.EduStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 数据统计 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-10-08
 */
@Service
public class EduStatisticsServiceImpl extends ServiceImpl<EduStatisticsMapper, EduStatistics> implements EduStatisticsService {
    @Resource
    private UcenterMemberMapper ucenterMemberMapper;
    @Resource
    private EduTopicMapper topicMapper;
    @Resource
    private EduPaperMapper paperMapper;
    @Resource
    private EduTestPaperRecordsMapper testPaperRecordsMapper;

    @Override
    public void statistics() {
        EduStatistics model = new EduStatistics();
        //统计当前注册会员数
        model.setUserCount(Long.valueOf(ucenterMemberMapper.selectCount(null)));
        //统计当前题目数
        model.setTopicCount(Long.valueOf(topicMapper.selectCount(null)));
        //统计当前试卷数
        model.setPaperCount(Long.valueOf(paperMapper.selectCount(null)));
        //统计考试数量
        model.setTestTimes(Long.valueOf(testPaperRecordsMapper.selectCount(null)));
        baseMapper.insert(model);
    }
}
