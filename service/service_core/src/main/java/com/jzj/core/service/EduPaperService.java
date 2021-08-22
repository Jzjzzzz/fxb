package com.jzj.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.core.pojo.entity.EduPaper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.pojo.query.PaperQuery;
import com.jzj.core.pojo.vo.EduPaperSaveVo;

import java.util.Map;

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

    Map<String, Object> listPage(Page<EduPaper> pageParam, PaperQuery paperQuery);

    boolean removePaperById(Long id);

    EduPaperSaveVo getByIdPaper(Long id);

    boolean updatePaper(EduPaperSaveVo paperSaveVo);
}
