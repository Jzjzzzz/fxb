package com.jzj.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzj.core.pojo.entity.EduTopic;
import com.jzj.core.pojo.vo.EduTopicEditVo;
import com.jzj.core.pojo.vo.EduTopicListVo;

/**
 * <p>
 * 题目 Mapper 接口
 * </p>
 *
 * @author Jzj
 * @since 2021-08-11
 */
public interface EduTopicMapper extends BaseMapper<EduTopic> {
    EduTopicListVo getBaseTopicList(Long id);

    EduTopicEditVo getByIdTopic(Long id);
}
