package com.jzj.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.core.pojo.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.core.pojo.query.SubjectQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-10
 */
public interface EduSubjectService extends IService<EduSubject> {

    IPage<EduSubject> listPage(Page<EduSubject> pageParam, SubjectQuery subjectQuery);


}
