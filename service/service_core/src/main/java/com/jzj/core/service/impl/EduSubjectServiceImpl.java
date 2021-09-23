package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.ResultCode;
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
    public IPage<EduSubject>  listPage(Page<EduSubject> pageParam, SubjectQuery subjectQuery) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        if (subjectQuery == null) return baseMapper.selectPage(pageParam, wrapper);
        String name = subjectQuery.getName();
        wrapper.like(StringUtils.isNotBlank(name), "name", name);
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public boolean saveSubject(EduSubject subject) {
        //校验数据
        if(subject==null) throw new BusinessException(ResultCode.ERROR,"数据异常");
        if(StringUtils.isBlank(subject.getName())) throw new BusinessException(ResultCode.ERROR,"科目名称不能为空");
        String subjectName = subject.getName().trim(); //科目名称
        if(subjectName.length()>20) throw new BusinessException(ResultCode.ERROR,"科目名长度不能大于20");
        if(subject.getSort()<0) throw new BusinessException(ResultCode.ERROR,"排序不能小于0");
        if(subject.getSort()>5000000)throw new BusinessException(ResultCode.ERROR,"排序不能大于5000000");
        //持久化操作
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("name",subjectName);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0) throw new BusinessException(ResultCode.ERROR,"科目名称已存在");
        subject.setName(subjectName); //重新设置去除空白符
        int insert = baseMapper.insert(subject);
        if(insert>0) return true;
        return false;
    }

    @Override
    public boolean updateSubjectById(EduSubject subject) {
        //校验数据
        if(subject==null) throw new BusinessException(ResultCode.ERROR,"数据异常");
        if(subject.getId()==null) throw new BusinessException(ResultCode.ERROR,"非法操作");
        if(StringUtils.isBlank(subject.getName())) throw new BusinessException(ResultCode.ERROR,"科目名称不能为空");
        String subjectName = subject.getName().trim(); //科目名称
        if(subjectName.length()>20) throw new BusinessException(ResultCode.ERROR,"科目名长度不能大于20");
        Integer count = baseMapper.selectCount(new QueryWrapper<EduSubject>().eq("name",subjectName).ne("id",subject.getId()));
        if(count>0) throw new BusinessException(ResultCode.ERROR,"科目名称已存在");
        if(subject.getSort()<0) throw new BusinessException(ResultCode.ERROR,"排序不能小于0");
        if(subject.getSort()>5000000)throw new BusinessException(ResultCode.ERROR,"排序不能大于5000000");
        //持久化操作
        subject.setName(subjectName); //重新设置去除空白符
        int insert = baseMapper.updateById(subject);
        if(insert>0) return true;
        return false;
    }


}
