package com.jzj.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.ResultCode;
import com.jzj.core.listener.ExcelTopicDTOListener;
import com.jzj.core.mapper.EduPaperMapper;
import com.jzj.core.mapper.EduPaperTopicMapper;
import com.jzj.core.mapper.EduTopicContentMapper;
import com.jzj.core.mapper.EduTopicMapper;
import com.jzj.core.pojo.dto.ExcelTopicDTO;
import com.jzj.core.pojo.entity.EduPaper;
import com.jzj.core.pojo.entity.EduPaperTopic;
import com.jzj.core.pojo.entity.EduTopic;
import com.jzj.core.pojo.entity.EduTopicContent;
import com.jzj.core.pojo.query.TopicQuery;
import com.jzj.core.pojo.vo.EduTopicEditVo;
import com.jzj.core.pojo.vo.EduTopicListVo;
import com.jzj.core.pojo.vo.EduTopicSaveVo;
import com.jzj.core.service.EduTopicContentService;
import com.jzj.core.service.EduTopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.InputStream;
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
    private EduTopicContentMapper topicContentMapper;
    @Resource
    private EduPaperTopicMapper paperTopicMapper;

    @Transactional
    @Override
    public boolean saveTopic(EduTopicSaveVo topicSaveVo) {
        //校验数据
        if(topicSaveVo==null) throw new BusinessException(ResultCode.ERROR,"数据异常");
        if(StringUtils.isEmpty(topicSaveVo.getTitleContent())) throw new BusinessException(ResultCode.ERROR,"题目题干不能为空");
        if(StringUtils.isEmpty(topicSaveVo.getCorrect())) throw new BusinessException(ResultCode.ERROR,"正确答案不能为空");

        //添加题目详情表数据
        EduTopicContent topicContent = new EduTopicContent();
        BeanUtils.copyProperties(topicSaveVo,topicContent);
        topicContentMapper.insert(topicContent);
        //添加题目表数据
        EduTopic topic = new EduTopic();
        BeanUtils.copyProperties(topicSaveVo,topic);
        topic.setTopicDetailsId(topicContent.getId());
        topic.setStatus(1);
        return baseMapper.insert(topic)>0;
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
        //判断是否在某个试卷中使用到该题
        Integer count = paperTopicMapper.selectCount(new QueryWrapper<EduPaperTopic>().eq("topic_id", id));
        if(count>0) throw new BusinessException(ResultCode.ERROR,"该题在试卷中有应用，无法删除,请先在试卷中删除该题");
        EduTopic topic = baseMapper.selectById(id);
        //删除题目详情数据
        topicContentMapper.deleteById(topic.getTopicDetailsId());
        //删除题目
        return baseMapper.deleteById(topic.getId())>0;

    }

    @Override
    public EduTopicEditVo getByIdTopic(Long id) {
        return baseMapper.getByIdTopic(id);
    }

    @Transactional
    @Override
    public boolean updateTopic(EduTopicSaveVo topicSaveVo) {
        //校验数据
        if(topicSaveVo==null) throw new BusinessException(ResultCode.ERROR,"数据异常");
        if(StringUtils.isEmpty(topicSaveVo.getTitleContent())) throw new BusinessException(ResultCode.ERROR,"题目题干不能为空");
        if(StringUtils.isEmpty(topicSaveVo.getContent())) throw new BusinessException(ResultCode.ERROR,"题目内容不能为空");
        if(StringUtils.isEmpty(topicSaveVo.getCorrect())) throw new BusinessException(ResultCode.ERROR,"正确答案不能为空");
        //封装题目表
        EduTopic topic = new EduTopic();
        BeanUtils.copyProperties(topicSaveVo,topic);
        //封装题目详情表
        EduTopicContent topicContent = new EduTopicContent();
        BeanUtils.copyProperties(topicSaveVo,topicContent);
        topicContent.setId(topic.getTopicDetailsId()); //详情id

        //提交
        baseMapper.updateById(topic);
        return topicContentMapper.updateById(topicContent)>0;
    }

    @Override
    public Map<String, Object> getTopicListById(Long id, Page<EduTopic> pageParam) {
        ArrayList<EduTopicListVo> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<EduTopic> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.eq("subject_id",id);
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

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, ExcelTopicDTO.class, new ExcelTopicDTOListener(baseMapper,topicContentMapper)).sheet().doRead();
    }
}
