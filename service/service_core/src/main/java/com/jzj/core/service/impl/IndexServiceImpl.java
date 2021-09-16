package com.jzj.core.service.impl;

import com.jzj.core.mapper.EduPaperMapper;
import com.jzj.core.mapper.EduTopicMapper;
import com.jzj.core.mapper.UcenterMemberMapper;
import com.jzj.core.pojo.entity.UcenterMember;
import com.jzj.core.pojo.vo.IndexCountVo;
import com.jzj.core.service.IndexService;
import com.jzj.core.service.UcenterMemberService;
import com.jzj.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


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

    @Override
    public IndexCountVo getCount() {
        IndexCountVo indexCountVo = new IndexCountVo();
        //统计当前注册会员数
        Integer userCount = ucenterMemberMapper.selectCount(null);
        indexCountVo.setUserCount(userCount);
        //统计当前题目数
        Integer topicCount = topicMapper.selectCount(null);
        indexCountVo.setTopicCount(topicCount);
        //统计当前试卷数
        Integer paperCount = paperMapper.selectCount(null);
        indexCountVo.setPaperCount(paperCount);
        //获取前7天日期
        ArrayList<String> stateTime = DateUtils.getStateTime(7);
        indexCountVo.setRecentDate(stateTime);
        //获取近7天会员增长量
        List<Integer> userSevenDays = ucenterMemberMapper.getUserSevenDays();
        indexCountVo.setUserNumber(userSevenDays);
        //获取近7天题目增长量
        List<Integer> topicSevenDays = topicMapper.getTopicSevenDays();
        indexCountVo.setTopicNumber(topicSevenDays);
        return indexCountVo;



    }
}
