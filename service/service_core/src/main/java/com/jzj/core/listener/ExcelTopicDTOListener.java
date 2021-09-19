package com.jzj.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.jzj.core.mapper.EduTopicContentMapper;
import com.jzj.core.mapper.EduTopicMapper;
import com.jzj.core.pojo.dto.ExcelTopicDTO;
import com.jzj.core.pojo.entity.EduTopic;
import com.jzj.core.pojo.entity.EduTopicContent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Excel导入处理
 * @Author Jzj
 * @Date 2021/9/19 17:11
 * @Version 1.0
 */
@Slf4j
public class ExcelTopicDTOListener extends AnalysisEventListener<ExcelTopicDTO> {

    /**
     * 字母列表
     */
    private static final List<String> letterList = Arrays.asList("A", "B", "C", "D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T");


    /**
     * Spring 注入需要通过构造方法传参
     */
    private EduTopicMapper topicMapper;
    private EduTopicContentMapper topicContentMapper;

    public ExcelTopicDTOListener(EduTopicMapper topicMapper,EduTopicContentMapper topicContentMapper) {
        this.topicMapper = topicMapper;
        this.topicContentMapper = topicContentMapper;
    }


    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(ExcelTopicDTO data, AnalysisContext context) {

        //封装题目详情表
        EduTopicContent eduTopicContent = new EduTopicContent();
        eduTopicContent.setCorrect(data.getCorrect()); //答案
        eduTopicContent.setTitleContent(data.getTitleContent());
        List<String> contents = topicString(data);
        eduTopicContent.setContent(String.valueOf(contents));
        topicContentMapper.insert(eduTopicContent);


        //封装题目表
    EduTopic eduTopic = new EduTopic();
        eduTopic.setSubjectId(10L); //学科id
        eduTopic.setScore(5); //分数
        eduTopic.setDifficult(5); //难度
        eduTopic.setCorrect(data.getCorrect()); //正确答案
        eduTopic.setStatus(1); //状态
        eduTopic.setQuestionId(1); //题型
        eduTopic.setTopicDetailsId(eduTopicContent.getId()); //详情表id
        topicMapper.insert(eduTopic);
}
    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

        log.info("所有数据解析完成！");
    }


    /**
     * 对题目内容进行处理
     */
    private static List<String> topicString(ExcelTopicDTO data) {
        List<String> contentList = Arrays.asList(data.getContentA(), data.getContentB(), data.getContentC(), data.getContentD()); //答案列表
        ArrayList<String> content = new ArrayList<>(); //最终拼接答案
        for (int i =0;i<contentList.size();i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("prefix",letterList.get(i));
            map.put("content",contentList.get(i));
            String jsonString = JSON.toJSONString(map);
            content.add(jsonString);
        }
        return content;
    }

    public static void main(String[] args) {
        ExcelTopicDTO excelTopicDTO = new ExcelTopicDTO();
        excelTopicDTO.setContentA("5120m，1024m\n");
        excelTopicDTO.setContentB("5120m，2048m\n");
        excelTopicDTO.setContentC("10240m，2048m\n");
        excelTopicDTO.setContentD("10240m，2048m\n");
        List<String> contents = topicString(excelTopicDTO);
        System.out.println(contents);

    }

}
