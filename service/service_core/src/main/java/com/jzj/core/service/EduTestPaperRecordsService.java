package com.jzj.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.core.pojo.entity.EduTestPaperRecords;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 考试记录 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-09-06
 */
public interface EduTestPaperRecordsService extends IService<EduTestPaperRecords> {

    Map<String, Object> getTestRecordList(Page<EduTestPaperRecords> recordsPage,Long userId);

    EduTestPaperRecords getTestPaperById(Long paperId);
}
