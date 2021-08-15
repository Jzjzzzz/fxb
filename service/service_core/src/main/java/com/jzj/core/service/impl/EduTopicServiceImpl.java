package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.core.mapper.EduTopicMapper;
import com.jzj.core.pojo.entity.EduTopic;
import com.jzj.core.pojo.entity.EduTopicContent;
import com.jzj.core.pojo.query.TopicQuery;
import com.jzj.core.pojo.vo.EduTopicListVo;
import com.jzj.core.pojo.vo.EduTopicMultipleVo;
import com.jzj.core.service.DictService;
import com.jzj.core.service.EduSubjectService;
import com.jzj.core.service.EduTopicContentService;
import com.jzj.core.service.EduTopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 题目 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-11
 */
@Service
public class EduTopicServiceImpl extends ServiceImpl<EduTopicMapper, EduTopic> implements EduTopicService {
    @Resource
    private EduTopicContentService topicContentService;
    @Resource
    private EduSubjectService subjectService;
    @Resource
    private DictService dictService;

    @Transactional
    @Override
    public boolean saveMultiple(EduTopicMultipleVo multipleVo) {
        //添加题目详情表数据
        EduTopicContent topicContent = new EduTopicContent();
        BeanUtils.copyProperties(multipleVo,topicContent);
        topicContentService.save(topicContent);
        //添加题目表数据
        EduTopic topic = new EduTopic();
        BeanUtils.copyProperties(multipleVo,topic);
        topic.setTopicDetailsId(topicContent.getId());
        topic.setStatus(1);
        topic.setQuestionId(1);
        baseMapper.insert(topic);
        return true;
    }

    @Override
    public Map<String, Object> listPage(Page<EduTopic> pageParam, TopicQuery topicQuery) {
        ArrayList<EduTopicListVo> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<EduTopic> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if(!StringUtils.isEmpty(topicQuery)){
            if(!StringUtils.isEmpty(topicQuery.getSubjectId())){
                wrapper.eq("subject_id",topicQuery.getSubjectId());
            }
            if(!StringUtils.isEmpty(topicQuery.getQuestionId())){
                wrapper.eq("question_id",topicQuery.getQuestionId());
            }
        }
        IPage<EduTopic> selectPage = baseMapper.selectPage(pageParam, wrapper);
        List<EduTopic> records = selectPage.getRecords();
        for (EduTopic topic : records) {
            //封装数据
            EduTopicListVo topicListVo = baseMapper.getBaseTopicList(topic.getId());
            list.add(topicListVo);
        }
        //获取数据总数
        long total = selectPage.getTotal();
        map.put("list",list);
        map.put("total",total);
        return map;
    }

    @Transactional
    @Override
    public boolean removeTopicById(Long id) {
        EduTopic topic = baseMapper.selectById(id);
        //删除题目详情数据
        topicContentService.removeById(topic.getTopicDetailsId());
        //删除题目
        baseMapper.deleteById(topic.getId());
        return true;
    }
}
