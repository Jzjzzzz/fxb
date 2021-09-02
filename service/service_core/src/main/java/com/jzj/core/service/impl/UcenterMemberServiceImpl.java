package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzj.commonutils.BusinessException;
import com.jzj.core.pojo.entity.UcenterMember;
import com.jzj.core.mapper.UcenterMemberMapper;
import com.jzj.core.pojo.vo.LoginVo;
import com.jzj.core.pojo.vo.RegisterVo;
import com.jzj.core.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.util.JwtUtils;
import com.jzj.util.MD5;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-29
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(mobile)) {
            throw new BusinessException(20001,"error");
        }

        //获取会员
        UcenterMember member = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(null == member) {
            throw new BusinessException(20001,"该用户信息不存在！");
        }

        //校验密码
        if(!MD5.encrypt(password).equals(member.getPassword())) {
            throw new BusinessException(20001,"密码错误！");
        }

        //校验是否被禁用
        if(member.getIsDisabled()) {
            throw new BusinessException(20001,"账号已被禁用，请联系管理员！");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new BusinessException(20001,"error");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        // String mobleCode = redisTemplate.opsForValue().get(mobile);
        // if(!code.equals(mobleCode)) {
        //     throw new BusinessException(20001,"error");
        // }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new BusinessException(20001,"error");
        }

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://guli-jzj.oss-cn-guangzhou.aliyuncs.com/2021/07/28/16.jpeg");
        this.save(member);
    }


}
