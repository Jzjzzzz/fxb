package com.jzj.core.mapper;

import com.jzj.core.pojo.entity.EduPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzj.core.pojo.vo.EduPaperListVo;
import com.jzj.core.pojo.vo.EduTopicListVo;
import com.jzj.core.pojo.vo.FrontPaperIndexVo;

import java.util.List;

/**
 * <p>
 * 试卷 Mapper 接口
 * </p>
 *
 * @author Jzj
 * @since 2021-08-18
 */
public interface EduPaperMapper extends BaseMapper<EduPaper> {
    EduPaperListVo getBasePaperList(Long id);

    List<FrontPaperIndexVo> getHotPaperList();

    FrontPaperIndexVo getBasePaperById(Long id);


}
