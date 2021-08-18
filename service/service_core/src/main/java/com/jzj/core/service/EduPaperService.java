package com.jzj.core.service;

import com.jzj.core.pojo.entity.EduPaper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.core.pojo.vo.EduPaperSaveVo;

/**
 * <p>
 * 试卷 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-18
 */
public interface EduPaperService extends IService<EduPaper> {

    boolean savePaperOrTopic(EduPaperSaveVo paperSaveVo);
}
