package com.jzj.core.service.impl;

import com.jzj.core.mapper.EduPaperTopicMapper;
import com.jzj.core.pojo.entity.EduPaper;
import com.jzj.core.mapper.EduPaperMapper;
import com.jzj.core.pojo.entity.EduPaperTopic;
import com.jzj.core.pojo.entity.EduTopic;
import com.jzj.core.pojo.vo.EduPaperSaveVo;
import com.jzj.core.pojo.vo.EduTopicListVo;
import com.jzj.core.service.EduPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.IntSummaryStatistics;
import java.util.List;

/**
 * <p>
 * 试卷 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-18
 */
@Service
public class EduPaperServiceImpl extends ServiceImpl<EduPaperMapper, EduPaper> implements EduPaperService {
    @Resource
    private EduPaperTopicMapper eduPaperTopicMapper;



    @Transactional
    @Override
    public boolean savePaperOrTopic(EduPaperSaveVo paperSaveVo) {
        List<EduTopicListVo> topicListVoList = paperSaveVo.getTopicListVoList(); //获取题目集合
        //封装试卷表数据
        EduPaper eduPaper = new EduPaper();
        BeanUtils.copyProperties(paperSaveVo,eduPaper);
        eduPaper.setQuestionCount(topicListVoList.size()); //题目总数
        eduPaper.setStatus(1); //状态
        eduPaper.setScore(topicListVoList.stream().mapToInt((s)->s.getScore()).sum()); //总分
        baseMapper.insert(eduPaper);
        //封装试卷题目详情数据
        for (EduTopicListVo vo : topicListVoList) {
            EduPaperTopic eduPaperTopic = new EduPaperTopic();
            eduPaperTopic.setPaperId(eduPaper.getId()); //试卷id
            eduPaperTopic.setTopicId(vo.getId()); //题目id
            eduPaperTopic.setTopicContentId(vo.getTopicDetailsId());//题目详情id
            eduPaperTopicMapper.insert(eduPaperTopic);
        }



        return true;
    }
}
