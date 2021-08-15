package com.jzj.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.Dict;
import com.jzj.core.pojo.query.DictQuery;
import com.jzj.core.service.DictService;
import com.jzj.core.utils.DictUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-08-06
 */
@Api(tags = "数据字典后台管理")
@RestController
@RequestMapping("/admin/core/dict")
@Slf4j
public class AdminDictController {
    @Resource
    private DictService dictService;

    @ApiOperation("查询所有上级节点数据列表")
    @PostMapping("/listByTop/{page}/{limit}")
    public R listByTop(@PathVariable Long page,@PathVariable Long limit,@RequestBody(required = false) DictQuery dictQuery){
        Page<Dict> pageParam = new Page<>(page, limit);
        IPage<Dict> listPage = dictService.listPage(pageParam, dictQuery);
        return R.ok().data("listPage",listPage);
    }

    @ApiOperation("根据上级id获取子节点数据列表")
    @PostMapping("/listByParentId/{page}/{limit}/{parentId}")
    public R listByParentId(@PathVariable Long page,@PathVariable Long limit,@PathVariable Long parentId,@RequestBody(required = false) DictQuery dictQuery){
        Page<Dict> pageParam = new Page<>(page, limit);
        IPage<Dict> listPage = dictService.listByParentId(pageParam,dictQuery,parentId);
        return R.ok().data("listPage",listPage);
    }


    @ApiOperation("新增子节点")
    @PostMapping("/saveSun/{parentId}")
    public R saveSun(@PathVariable Long parentId, @RequestBody Dict dict){
        boolean result = dictService.saveSun(parentId,dict);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @ApiOperation("新增顶级节点")
    @PostMapping("/saveTop")
    public R saveTop(@RequestBody Dict dict){
        boolean result = dictService.saveTop(dict);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }

    @ApiOperation("根据字典ID查询数据字典")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        Dict model = dictService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }

    @ApiOperation("修改数据字典")
    @PutMapping("/update")
    public R update(@RequestBody Dict dict){
        boolean result = dictService.updateById(dict);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }

    @ApiOperation("根据ID删除数据字典上级节点")
    @DeleteMapping("/removeByIdTop/{id}")
    public R removeByIdTop(@PathVariable Long id){
        boolean result = dictService.removeByIdTop(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }
    @ApiOperation("清空redis缓存")
    @GetMapping("/emptyDictByParentId/{parentId}")
    public R emptyDictByParentId(@PathVariable Long parentId){
        boolean result = DictUtils.emptyRedis(parentId);
        if(result) return R.ok().message("清空缓存成功");
        return R.error().message("暂无缓存数据需要清除");
    }

}

