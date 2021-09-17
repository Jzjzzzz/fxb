package com.jzj.core.mapper;

import com.jzj.core.pojo.entity.EduTestPaperRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzj.core.pojo.vo.UserCountVo;

import java.util.List;

/**
 * <p>
 * 考试记录 Mapper 接口
 * </p>
 *
 * @author Jzj
 * @since 2021-09-06
 */
public interface EduTestPaperRecordsMapper extends BaseMapper<EduTestPaperRecords> {

    List<UserCountVo> getByIdCount(Long id);
}
