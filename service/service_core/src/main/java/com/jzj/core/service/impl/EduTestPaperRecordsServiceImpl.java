package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.core.mapper.EduTestPaperRecordsMapper;
import com.jzj.core.pojo.entity.EduTestPaperRecords;
import com.jzj.core.service.EduTestPaperRecordsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考试记录 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-09-06
 */
@Service
public class EduTestPaperRecordsServiceImpl extends ServiceImpl<EduTestPaperRecordsMapper, EduTestPaperRecords> implements EduTestPaperRecordsService {

    @Override
    public Map<String, Object> getTestRecordList(Page<EduTestPaperRecords> recordsPage,Long userId) {
        QueryWrapper<EduTestPaperRecords> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.select().orderByDesc("gmt_create");
        baseMapper.selectPage(recordsPage, wrapper);
        List<EduTestPaperRecords> records = recordsPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", recordsPage.getCurrent());
        map.put("pages", recordsPage.getPages());
        map.put("size", recordsPage.getSize());
        map.put("total", recordsPage.getTotal());
        map.put("hasNext", recordsPage.hasNext());
        map.put("hasPrevious", recordsPage.hasPrevious());
        return map;
    }

    @Override
    public EduTestPaperRecords getTestPaperById(Long paperId) {
        return baseMapper.selectById(paperId);
    }
}
