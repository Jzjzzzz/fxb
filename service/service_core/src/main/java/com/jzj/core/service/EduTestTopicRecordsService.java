package com.jzj.core.service;

import com.jzj.core.pojo.entity.EduTestTopicRecords;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-09-06
 */
public interface EduTestTopicRecordsService extends IService<EduTestTopicRecords> {

    List<EduTestTopicRecords> getTestTopicListById(Long paperId);

    Boolean collectionTopic(Long topicId);

    List<EduTestTopicRecords> getCollectionList(Long userId);

    Boolean cancelCollection(Long topicId);

    Boolean manualCorrect(Long topicId,int result);
}
