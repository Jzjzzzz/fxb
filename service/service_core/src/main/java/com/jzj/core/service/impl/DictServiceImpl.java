package com.jzj.core.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.commonutils.Assert;
import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.ResponseEnum;
import com.jzj.commonutils.ResultCode;
import com.jzj.core.mapper.DictMapper;
import com.jzj.core.pojo.entity.Dict;
import com.jzj.core.pojo.query.DictQuery;
import com.jzj.core.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-06
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


    @Override
    public boolean removeByIdTop(Long id) {
        //判断当前节点是否有子节点，找到当前的dict下级有没有子节点
        boolean hasChildren = this.hasChildren(id);
        Assert.isTrue(!hasChildren, ResponseEnum.Dic_Top_NotNull);
        int result = baseMapper.deleteById(id);
        if (result >= 1) return true;
        return false;
    }

    @Override
    public boolean saveTop(Dict dict) {
        //校验数据
        if(dict==null) throw new BusinessException(ResultCode.ERROR,"参数异常");
        if(StringUtils.isBlank(dict.getName())) throw new BusinessException(ResultCode.ERROR,"字典名称不能为空");
        Integer count = baseMapper.selectCount(new QueryWrapper<Dict>().eq("name", dict.getName()));
        if(count>0) throw new BusinessException(ResultCode.ERROR,"已存在该名称字典");
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        //查询出当前最大的id
        wrapper.eq("parent_id", 1).orderByDesc("id").last("limit 1");
        Dict dictMax = baseMapper.selectOne(wrapper);
        //当为空时设置初始值100否则向上添加100
        if (dictMax == null) {
            dict.setId(100L);
        }else {
            dict.setId(dictMax.getId() + 100L);
        }
        dict.setParentId(1L);
        int result = baseMapper.insert(dict);
        if (result >= 1)  return true;
        return false;
    }

    @Override
    public boolean saveSun(Long parentId, Dict dict) {
        //校验参数
        if(parentId==null) throw new BusinessException(ResultCode.ERROR,"父类不存在");
        if(dict==null) throw new BusinessException(ResultCode.ERROR,"参数错误");
        Integer nameCount = baseMapper.selectCount(new QueryWrapper<Dict>().eq("name", dict.getName()));
        if(nameCount>0) throw new BusinessException(ResultCode.ERROR,"已存在该名称字典");
        //判空
        if(StringUtils.isBlank(dict.getName())) throw new BusinessException(20001,"参数不能为空");
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        //查询所有子节点
        List<Dict> list = baseMapper.selectList(wrapper);
        for (Dict item : list) {
            if(item.getValue() ==dict.getValue()) throw new BusinessException(20001,"该值已存在");
        }
        //查询当前顶级节点下最后一个节点
        wrapper.orderByDesc("id").last("limit 1");
        Dict dictMax = baseMapper.selectOne(wrapper);
        if (dictMax == null) {
            dict.setId(parentId + 1);
        } else {
            dict.setId(dictMax.getId() + 1);
        }
        dict.setParentId(parentId);
        int count = baseMapper.insert(dict);
        if (count >= 1) return true;
        return false;
    }

    @Override
    public IPage<Dict> listPage(Page<Dict> pageParam, DictQuery dictQuery) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 1);
        if (dictQuery == null) return baseMapper.selectPage(pageParam, wrapper);
        String name = dictQuery.getName();
        String dictCode = dictQuery.getDictCode();
        wrapper.like(StringUtils.isNotBlank(name), "name", name);
        wrapper.like(StringUtils.isNotBlank(dictCode), "dict_code", dictCode);
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public boolean updateDictById(Dict dict) {
        //校验数据
        if(dict==null) throw new BusinessException(ResultCode.ERROR,"参数异常");
        if(StringUtils.isBlank(dict.getName())) throw new BusinessException(ResultCode.ERROR,"字典名称不能为空");
        Integer count = baseMapper.selectCount(new QueryWrapper<Dict>().eq("name", dict.getName()).ne("id",dict.getId()));
        if(count>0) throw new BusinessException(ResultCode.ERROR,"已存在该名称字典");
        return baseMapper.updateById(dict)>0;
    }

    @Override
    public IPage<Dict> listByParentId(Page<Dict> pageParam, DictQuery dictQuery, Long parentId) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id", parentId);
        if (dictQuery == null) return baseMapper.selectPage(pageParam, dictQueryWrapper);
        String name = dictQuery.getName();
        String dictCode = dictQuery.getDictCode();
        dictQueryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        dictQueryWrapper.like(StringUtils.isNotBlank(dictCode), "dict_code", dictCode);
        return baseMapper.selectPage(pageParam, dictQueryWrapper);
    }

    /**
     * 判断当前id所在的节点下是否有子节点
     *
     * @param parentId
     * @return
     */
    private boolean hasChildren(Long parentId) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) return true;
        return false;
    }


}
