package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.ResultCode;
import com.jzj.core.pojo.entity.EduPaper;
import com.jzj.core.pojo.entity.EduSubject;
import com.jzj.core.pojo.entity.NoticeMessage;
import com.jzj.core.mapper.NoticeMessageMapper;
import com.jzj.core.pojo.query.MessageQuery;
import com.jzj.core.pojo.query.PaperQuery;
import com.jzj.core.pojo.vo.FrontPaperIndexVo;
import com.jzj.core.service.NoticeMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公告信息 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-09-27
 */
@Service
public class NoticeMessageServiceImpl extends ServiceImpl<NoticeMessageMapper, NoticeMessage> implements NoticeMessageService {

    @Override
    public boolean saveMessage(NoticeMessage noticeMessage) {
        //数据校验
        if(noticeMessage==null) throw new BusinessException(ResultCode.ERROR,"数据异常");
        if(StringUtils.isBlank(noticeMessage.getTitle())) throw new BusinessException(ResultCode.ERROR,"标题不能为空");
        if(noticeMessage.getTitle().length()>50) throw new BusinessException(ResultCode.ERROR,"标题长度不能大于50字");
        if(StringUtils.isBlank(noticeMessage.getContent())) throw new BusinessException(ResultCode.ERROR,"内容不能为空");

        noticeMessage.setCreateUser("管理员"); //管理员创建
        noticeMessage.setStatus(1); //正常状态
        noticeMessage.setType(1); //类型为公告
        return baseMapper.insert(noticeMessage)>0;
    }

    @Override
    public IPage<NoticeMessage> listPage(Page<NoticeMessage> pageParam, MessageQuery messageQuery) {
        QueryWrapper<NoticeMessage> wrapper = new QueryWrapper<>();
        if (messageQuery == null) return baseMapper.selectPage(pageParam, wrapper);
        String title = messageQuery.getTitle();
        if(!StringUtils.isBlank(title)){
            wrapper.like("title", title);
        }
        if(messageQuery.getType()!=0){
            wrapper.eq("type",messageQuery.getType());
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public Map<String, Object> getFrontMessageList(Page<NoticeMessage> messagePage) {
        baseMapper.selectPage(messagePage, null);
        Map<String, Object> map = new HashMap<>();
        map.put("items", messagePage.getRecords());
        map.put("current", messagePage.getCurrent());
        map.put("pages", messagePage.getPages());
        map.put("size", messagePage.getSize());
        map.put("total", messagePage.getTotal());
        map.put("hasNext", messagePage.hasNext());
        map.put("hasPrevious", messagePage.hasPrevious());
        return map;
    }


}
