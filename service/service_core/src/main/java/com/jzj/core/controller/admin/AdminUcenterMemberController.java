package com.jzj.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.R;
import com.jzj.core.pojo.entity.Dict;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.pojo.entity.UcenterMember;
import com.jzj.core.pojo.query.UserQuery;
import com.jzj.core.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Jzj
 * @since 2021-08-29
 */
@RestController
@RequestMapping("/admin/core/user")
@Api(tags = "会员操作后台管理")
public class AdminUcenterMemberController {
    @Resource
    private UcenterMemberService memberService;

    @ApiOperation("获取会员信息列表")
    @PostMapping("/listPage/{page}/{limit}")
    public R getUserList(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) UserQuery userQuery){
        Page<UcenterMember> memberPage = new Page<>(page, limit);
        IPage<UcenterMember> listPage = memberService.getUserList(memberPage, userQuery);
        return R.ok().data("list",listPage);
    }
    @ApiOperation("根据Id删除会员")
    @DeleteMapping("/removeById/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = memberService.removeById(id);
        if(result) return R.ok().message("删除成功");
        return R.error().message("删除失败");
    }

    @ApiOperation("根据ID查询会员")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id){
        UcenterMember model = memberService.getById(id);
        if(model!=null) return R.ok().data("model",model);
        return R.error().message("该条数据不存在");
    }
    @ApiOperation("管理员新增会员")
    @PostMapping("/save")
    public R saveSubject(@RequestBody UcenterMember ucenterMember){
        boolean result = memberService.adminSave(ucenterMember);
        if(result) return R.ok().message("新增成功");
        return R.error().message("新增失败");
    }
    @ApiOperation("管理员修改会员")
    @PutMapping("/update")
    public R update(@RequestBody UcenterMember ucenterMember){
        boolean result = memberService.updateUserById(ucenterMember);
        if(result) return R.ok().message("修改成功");
        return R.error().message("修改失败");
    }
}

