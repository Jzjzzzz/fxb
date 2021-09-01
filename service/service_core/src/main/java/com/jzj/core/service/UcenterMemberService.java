package com.jzj.core.service;

import com.jzj.core.pojo.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.core.pojo.vo.LoginVo;
import com.jzj.core.pojo.vo.RegisterVo;

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

}
