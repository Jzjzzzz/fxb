package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.ResultCode;
import com.jzj.core.mapper.EduTestPaperRecordsMapper;
import com.jzj.core.pojo.entity.UcenterMember;
import com.jzj.core.mapper.UcenterMemberMapper;
import com.jzj.core.pojo.query.UserQuery;
import com.jzj.core.pojo.vo.*;
import com.jzj.core.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.util.JwtUtils;
import com.jzj.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private EduTestPaperRecordsMapper testPaperRecordsMapper;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //校验参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)) throw new BusinessException(20001,"error");
        //获取会员
        UcenterMember member = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(null == member) throw new BusinessException(20001,"该用户信息不存在！");
        //校验密码
        if(!MD5.encrypt(password).equals(member.getPassword())) throw new BusinessException(20001,"密码错误！");
        //校验是否被禁用
        if(member.getIsDisabled()) throw new BusinessException(20001,"账号已被禁用，请联系管理员！");
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
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) throw new BusinessException(20001,"error");

        //校验校验验证码
        //从redis获取发送的验证码
        // String mobleCode = redisTemplate.opsForValue().get(mobile);
        // if(!code.equals(mobleCode)) {
        //     throw new BusinessException(20001,"error");
        // }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(count.intValue() > 0) throw new BusinessException(20001,"error");

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://fxb-jzj.oss-cn-guangzhou.aliyuncs.com/Avatar/2021/09/18/4ca7d066-d641-4461-8310-a9882fe5da6b.jpeg");
        this.save(member);
    }

    @Override
    public IPage<UcenterMember> getUserList(Page<UcenterMember> memberPage, UserQuery userQuery) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        if (userQuery == null) return baseMapper.selectPage(memberPage, wrapper);
        if(StringUtils.isNotBlank(userQuery.getMobile())){
            wrapper.like("mobile",userQuery.getMobile());
        }
        if(StringUtils.isNotBlank(userQuery.getNickname())){
            wrapper.like("nickname",userQuery.getNickname());
        }
        return baseMapper.selectPage(memberPage, wrapper);
    }

    @Override
    public boolean adminSave(UcenterMember ucenterMember) {
        //数据校验
        if(ucenterMember==null) throw new BusinessException(ResultCode.ERROR,"数据异常");
        if(StringUtils.isAllBlank(ucenterMember.getNickname(),ucenterMember.getMobile(),ucenterMember.getPassword())) throw new BusinessException(ResultCode.ERROR,"必填项不能为空");
        ucenterMember.setAge(18); //设置年龄
        ucenterMember.setSex(1); //设置性别
        ucenterMember.setPassword(MD5.encrypt(ucenterMember.getPassword())); //密码加密
        return baseMapper.insert(ucenterMember)>0;
    }

    @Override
    public boolean updateUserById(UcenterMember ucenterMember) {
        //数据校验
        if(ucenterMember==null) throw new BusinessException(ResultCode.ERROR,"数据异常");
        if(StringUtils.isAllBlank(ucenterMember.getNickname(),ucenterMember.getMobile(),ucenterMember.getPassword())) throw new BusinessException(ResultCode.ERROR,"必填项不能为空");
        ucenterMember.setPassword(MD5.encrypt(ucenterMember.getPassword())); //密码加密
        return baseMapper.updateById(ucenterMember)>0;
    }

    @Override
    public boolean updatePasswordById(UserPasswordVo userPasswordVo) {
        //校验数据
        if(userPasswordVo==null) throw new BusinessException(ResultCode.ERROR,"数据异常");
        if(StringUtils.isBlank(userPasswordVo.getOldPassword()) && StringUtils.isBlank(userPasswordVo.getNewPassword())) throw new BusinessException(ResultCode.ERROR,"密码不能为空");
        UcenterMember user = baseMapper.selectById(userPasswordVo.getId());
        if(!user.getPassword().equals(MD5.encrypt(userPasswordVo.getOldPassword()))) throw new BusinessException(ResultCode.ERROR,"原密码错误");
        if(userPasswordVo.getNewPassword().length()<6) throw new BusinessException(ResultCode.ERROR,"密码长度不能小于6");
        user.setPassword(MD5.encrypt(userPasswordVo.getNewPassword()));
        return baseMapper.updateById(user)>0;
    }

    @Override
    public FrontUserVo getByIdCount(Long id) {
        FrontUserVo frontUserVo = new FrontUserVo();
        UcenterMember ucenterMember = baseMapper.selectById(id);
        BeanUtils.copyProperties(ucenterMember,frontUserVo);
        List<UserCountVo> userCountVoList =  testPaperRecordsMapper.getByIdCount(id);
        frontUserVo.setUserCountVoList(userCountVoList);
        return frontUserVo;
    }

}
