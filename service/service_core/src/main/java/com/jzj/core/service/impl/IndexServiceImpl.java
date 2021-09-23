package com.jzj.core.service.impl;

import com.jzj.core.mapper.EduPaperMapper;
import com.jzj.core.mapper.EduTestPaperRecordsMapper;
import com.jzj.core.mapper.EduTopicMapper;
import com.jzj.core.mapper.UcenterMemberMapper;
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
    private UcenterMemberMapper ucenterMemberMapper;
    @Resource
    private EduTopicMapper topicMapper;
    @Resource
    private EduPaperMapper paperMapper;
    @Resource
    private EduTestPaperRecordsMapper testPaperRecordsMapper;

    @Override
    public IndexCountVo getCount() {
        IndexCountVo indexCountVo = new IndexCountVo();
        //统计当前注册会员数
        indexCountVo.setUserCount(ucenterMemberMapper.selectCount(null));
        //统计当前题目数
        indexCountVo.setTopicCount(topicMapper.selectCount(null));
        //统计当前试卷数
        indexCountVo.setPaperCount(paperMapper.selectCount(null));
        //统计考试数量
        indexCountVo.setTestTimes(testPaperRecordsMapper.selectCount(null));
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
