package com.jzj.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.core.pojo.entity.EduTopic;
import com.jzj.core.pojo.query.TopicQuery;
import com.jzj.core.pojo.vo.EduTopicMultipleVo;

import java.util.Map;

/**
 * <p>
 * 题目 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-11
 */
public interface EduTopicService extends IService<EduTopic> {

    boolean saveMultiple(EduTopicMultipleVo multipleVo);

    Map<String, Object> listPage(Page<EduTopic> pageParam, TopicQuery topicQuery);

    boolean removeTopicById(Long id);
}
