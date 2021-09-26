package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.ResultCode;
import com.jzj.core.pojo.entity.EduTestTopicRecords;
import com.jzj.core.mapper.EduTestTopicRecordsMapper;
import com.jzj.core.service.EduTestTopicRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public List<EduTestTopicRecords> getCollectionList(Long userId) {
        return baseMapper.selectList(new QueryWrapper<EduTestTopicRecords>()
                .eq("user_id", userId)
                .eq("is_collect", 1));
    }


}
