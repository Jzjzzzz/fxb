package com.jzj.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.core.pojo.entity.NoticeMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzj.core.pojo.query.MessageQuery;
import com.jzj.core.pojo.query.PaperQuery;

import java.util.Map;

/**
 * <p>
 * 公告信息 服务类
 * </p>
 *
 * @author Jzj
 * @since 2021-09-27
 */
public interface NoticeMessageService extends IService<NoticeMessage> {

    boolean saveMessage(NoticeMessage noticeMessage);

    IPage<NoticeMessage> listPage(Page<NoticeMessage> objectPage, MessageQuery messageQuery);

    Map<String, Object> getFrontMessageList(Page<NoticeMessage> objectPage);
}
