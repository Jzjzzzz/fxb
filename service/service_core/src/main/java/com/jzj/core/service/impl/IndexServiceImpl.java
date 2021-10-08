package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzj.core.mapper.*;
import com.jzj.core.pojo.entity.EduStatistics;
import com.jzj.core.pojo.vo.IndexCountVo;
import com.jzj.core.service.IndexService;
import com.jzj.util.DateUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;


/**
 * <p>
 * 首页 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-09-16
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private EduStatisticsMapper statisticsMapper;
    @Resource
    private UcenterMemberMapper ucenterMemberMapper;
    @Resource
    private EduTopicMapper topicMapper;

    @Override
    public IndexCountVo getCount() {
        EduStatistics eduStatistics = statisticsMapper.selectOne(new QueryWrapper<EduStatistics>().orderByDesc("gmt_create").last("limit 1"));
        IndexCountVo indexCountVo = new IndexCountVo();
        //统计当前注册会员数
        indexCountVo.setUserCount(eduStatistics.getUserCount());
        //统计当前题目数
        indexCountVo.setTopicCount(eduStatistics.getTopicCount());
        //统计当前试卷数
        indexCountVo.setPaperCount(eduStatistics.getPaperCount());
        //统计考试数量
        indexCountVo.setTestTimes(eduStatistics.getTestTimes());
        //获取前7天日期
        ArrayList<String> stateTime = DateUtils.getStateTime(7);
        indexCountVo.setRecentDate(stateTime);
        //获取近7天会员增长量
        indexCountVo.setUserNumber(ucenterMemberMapper.getUserSevenDays());
        //获取近7天题目增长量
        indexCountVo.setTopicNumber(topicMapper.getTopicSevenDays());
        return indexCountVo;
    }
}
