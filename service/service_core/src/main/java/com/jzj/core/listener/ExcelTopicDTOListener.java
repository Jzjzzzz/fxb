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
import org.apache.commons.lang3.StringUtils;

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
    private static final List<String>  LETTER_LIST = Arrays.asList("A", "B", "C", "D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T");


    /**
     * Spring 注入需要通过构造方法传参
     */
    private EduTopicMapper topicMapper;
    private EduTopicContentMapper topicContentMapper;

    //学科id
    private Long subjectId;

    public ExcelTopicDTOListener(EduTopicMapper topicMapper,EduTopicContentMapper topicContentMapper,Long subjectId) {
        this.topicMapper = topicMapper;
        this.topicContentMapper = topicContentMapper;
        this.subjectId = subjectId;
    }


    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(ExcelTopicDTO data, AnalysisContext context) {

        //初始化
        EduTopicContent eduTopicContent = new EduTopicContent();
        EduTopic eduTopic = new EduTopic();

        //封装题目详情表
        eduTopicContent.setCorrect(data.getCorrect()); //答案
        eduTopicContent.setTitleContent(data.getTitleContent());

        //当内容为空时是简答题，内容不为空时是单选题，并对内容进行处理
        if(StringUtils.isAllBlank(data.getContentA(),data.getContentB(),data.getContentC(),data.getContentD())){
            eduTopic.setQuestionId(4); //简答题
            eduTopic.setScore(10); //分数
        }else {
            List<String> contents = topicString(data); //封装选项
            eduTopicContent.setContent(String.valueOf(contents));
            eduTopic.setQuestionId(1); //单选题
            eduTopic.setScore(5); //分数
        }

        topicContentMapper.insert(eduTopicContent);


        //封装题目表
        eduTopic.setSubjectId(subjectId); //学科id

        eduTopic.setDifficult(5); //难度
        eduTopic.setCorrect(data.getCorrect()); //正确答案
        eduTopic.setStatus(1); //状态

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
            map.put("prefix",LETTER_LIST.get(i));
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
