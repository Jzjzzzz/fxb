package com.jzj.core.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.core.pojo.entity.Dict;
import com.jzj.core.pojo.query.DictQuery;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-06
 */
public interface DictService extends IService<Dict> {



    IPage<Dict> listByParentId(Page<Dict> pageParam,DictQuery dictQuery, Long parentId);



    boolean removeByIdTop(Long id);

    boolean saveTop(Dict dict);

    boolean saveSun(Long parentId, Dict dict);

    IPage<Dict> listPage(Page<Dict> pageParam, DictQuery dictQuery);
}
