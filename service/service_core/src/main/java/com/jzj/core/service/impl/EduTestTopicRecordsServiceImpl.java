package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
}
