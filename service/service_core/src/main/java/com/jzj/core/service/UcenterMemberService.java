package com.jzj.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.core.pojo.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.core.pojo.query.UserQuery;
import com.jzj.core.pojo.vo.FrontUserVo;
import com.jzj.core.pojo.vo.LoginVo;
import com.jzj.core.pojo.vo.RegisterVo;
import com.jzj.core.pojo.vo.UserPasswordVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-29
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    IPage<UcenterMember> getUserList(Page<UcenterMember> memberPage, UserQuery userQuery);

    boolean adminSave(UcenterMember ucenterMember);

    boolean updateUserById(UcenterMember ucenterMember);

    boolean updatePasswordById(UserPasswordVo userPasswordVo);


    FrontUserVo getByIdCount(Long id);
}
