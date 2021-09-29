package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.ResultCode;
import com.jzj.core.mapper.EduTestPaperRecordsMapper;
import com.jzj.core.pojo.entity.EduTestPaperRecords;
import com.jzj.core.pojo.entity.EduTestTopicRecords;
import com.jzj.core.mapper.EduTestTopicRecordsMapper;
import com.jzj.core.service.EduTestTopicRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-09-06
 */
@Service
public class EduTestTopicRecordsServiceImpl extends ServiceImpl<EduTestTopicRecordsMapper, EduTestTopicRecords> implements EduTestTopicRecordsService {

    @Resource
    private EduTestPaperRecordsMapper testPaperRecordsMapper;

    @Override
    public List<EduTestTopicRecords> getTestTopicListById(Long paperId) {

        return baseMapper.selectList(new QueryWrapper<EduTestTopicRecords>().eq("test_paper_id", paperId));
    }

    @Override
    public Boolean collectionTopic(Long topicId) {
        EduTestTopicRecords model = baseMapper.selectById(topicId);
        if(model.getIsCollect()==1) throw new BusinessException(ResultCode.ERROR,"该题已被收藏,请勿重复操作");
        model.setIsCollect(1);
        return baseMapper.updateById(model)>0;
    }

    @Override
    public Boolean cancelCollection(Long topicId) {
        EduTestTopicRecords model = baseMapper.selectById(topicId);
        if(model.getIsCollect()!=1) throw new BusinessException(ResultCode.ERROR,"该题未被收藏取消失败");
        model.setIsCollect(0);
        return baseMapper.updateById(model)>0;
    }


    @Transactional
    @Override
    public Boolean manualCorrect(Long topicId,int result) {
        //数据校验
        if(topicId==null) throw new BusinessException(ResultCode.ERROR,"当前题目不存在");
        //获取实体
        EduTestTopicRecords topic = baseMapper.selectById(topicId); //批改题目实体
        EduTestPaperRecords paper = testPaperRecordsMapper.selectById(topic.getTestPaperId()); //获取试卷详情实体

        //数据封装
        topic.setStatus(1); //状态设为已批改
        if(result==1){ //当批改为正确时
            topic.setResult(1); //结果
            paper.setSystemScore(paper.getSystemScore()+topic.getScore()); //叠加分数
            paper.setUserScore(paper.getUserScore()+topic.getScore()); //叠加分数
            paper.setQuestionCorrect(paper.getQuestionCorrect()+1); //叠加答对题数
        }else{
            topic.setResult(0);//结果
            topic.setScore(0); //得分重新设置为0
        }
        //持久化
        baseMapper.updateById(topic); //题目持久化
        Integer sum = baseMapper.selectCount(new QueryWrapper<EduTestTopicRecords>().eq("id", topicId).eq("status", 0)); //查找是否有剩余未改题目
        if(sum<=0) paper.setStatus(1); //设置试卷为批改完成
        int count = testPaperRecordsMapper.updateById(paper);  //试卷持久化
        return count>0;
    }

    @Override
    public List<EduTestTopicRecords> getCollectionList(Long userId) {
        return baseMapper.selectList(new QueryWrapper<EduTestTopicRecords>()
                .eq("user_id", userId)
                .eq("is_collect", 1));
    }


}
