package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.core.mapper.EduPaperTopicMapper;
import com.jzj.core.mapper.EduTopicMapper;
import com.jzj.core.pojo.entity.EduPaper;
import com.jzj.core.mapper.EduPaperMapper;
import com.jzj.core.pojo.entity.EduPaperTopic;
import com.jzj.core.pojo.entity.EduTopic;
import com.jzj.core.pojo.query.PaperQuery;
import com.jzj.core.pojo.vo.EduPaperListVo;
import com.jzj.core.pojo.vo.EduPaperSaveVo;
import com.jzj.core.pojo.vo.EduTopicListVo;
import com.jzj.core.service.EduPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private EduTopicMapper eduTopicMapper;

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
    @Transactional
    @Override
    public boolean updatePaper(EduPaperSaveVo paperSaveVo) {
        List<EduTopicListVo> topicListVoList = paperSaveVo.getTopicListVoList(); //获取题目集合
        //修改试卷基本信息表
        EduPaper eduPaper = new EduPaper();
        BeanUtils.copyProperties(paperSaveVo,eduPaper);
        eduPaper.setQuestionCount(topicListVoList.size()); //题目总数
        eduPaper.setScore(topicListVoList.stream().mapToInt((s)->s.getScore()).sum()); //总分
        baseMapper.updateById(eduPaper);
        //修改试卷题目表
        eduPaperTopicMapper.delete(new QueryWrapper<EduPaperTopic>().eq("paper_id",eduPaper.getId())); //删除原有数据
        for (EduTopicListVo vo : topicListVoList) {
            EduPaperTopic eduPaperTopic = new EduPaperTopic();
            eduPaperTopic.setPaperId(eduPaper.getId()); //试卷id
            eduPaperTopic.setTopicId(vo.getId()); //题目id
            eduPaperTopic.setTopicContentId(vo.getTopicDetailsId());//题目详情id
            eduPaperTopicMapper.insert(eduPaperTopic);
        }


        return true;
    }

    @Override
    public Map<String, Object> listPage(Page<EduPaper> pageParam, PaperQuery paperQuery) {
        ArrayList<EduPaperListVo> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<EduPaper> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if(!StringUtils.isEmpty(paperQuery)){
            if(!StringUtils.isEmpty(paperQuery.getSubjectId())){
                wrapper.eq("subject_id",paperQuery.getSubjectId());
            }
            if(!StringUtils.isEmpty(paperQuery.getPaperName())){
                wrapper.like("paper_name",paperQuery.getPaperName());
            }
        }
        IPage<EduPaper> selectPage = baseMapper.selectPage(pageParam, wrapper);
        List<EduPaper> records = selectPage.getRecords();
        for (EduPaper paper : records) {
            //封装数据
            EduPaperListVo paperList = baseMapper.getBasePaperList(paper.getId());
            list.add(paperList);
        }
        //获取数据总数
        long total = selectPage.getTotal();
        map.put("list",list);
        map.put("total",total);
        return map;
    }

    @Transactional
    @Override
    public boolean removePaperById(Long id) {
        //删除试卷题目表
        eduPaperTopicMapper.delete(new QueryWrapper<EduPaperTopic>().eq("paper_id",id));
        //删除试卷表
        int result = baseMapper.delete(new QueryWrapper<EduPaper>().eq("id", id));
        if(result>0){
            return true;
        }
        return false;
    }

    @Override
    public EduPaperSaveVo getByIdPaper(Long id) {
        EduPaperSaveVo paperSaveVo = new EduPaperSaveVo();
        ArrayList<EduTopicListVo> list = new ArrayList<>();
        //封装基本信息
        EduPaper paper = baseMapper.selectById(id);
        BeanUtils.copyProperties(paper,paperSaveVo);
        //封装题目
        List<EduPaperTopic> paperTopicList = eduPaperTopicMapper.selectList(new QueryWrapper<EduPaperTopic>().eq("paper_id", id));
        for (EduPaperTopic eduPaperTopic : paperTopicList) {
            EduTopicListVo topicListVo = eduTopicMapper.getBaseTopicList(eduPaperTopic.getTopicId());
            list.add(topicListVo);
        }
        paperSaveVo.setTopicListVoList(list);
        return paperSaveVo;
    }


}
