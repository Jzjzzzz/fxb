package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.core.mapper.EduSubjectMapper;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.pojo.query.SubjectQuery;
import com.jzj.core.service.EduSubjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-10
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public IPage<EduSubject> listPage(Page<EduSubject> pageParam, SubjectQuery subjectQuery) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        if (subjectQuery == null) return baseMapper.selectPage(pageParam, wrapper);
        String name = subjectQuery.getName();
        wrapper.like(StringUtils.isNotBlank(name), "name", name);
        return baseMapper.selectPage(pageParam, wrapper);
    }


}
