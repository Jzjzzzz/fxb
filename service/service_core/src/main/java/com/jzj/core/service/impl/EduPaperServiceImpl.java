package com.jzj.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzj.commonutils.BusinessException;
import com.jzj.commonutils.ResultCode;
import com.jzj.core.mapper.*;
import com.jzj.core.pojo.entity.EduPaper;
import com.jzj.core.pojo.entity.EduPaperTopic;
import com.jzj.core.pojo.entity.EduTestPaperRecords;
import com.jzj.core.pojo.entity.EduTestTopicRecords;
import com.jzj.core.pojo.query.PaperQuery;
import com.jzj.core.pojo.vo.*;
import com.jzj.core.service.EduPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzj.core.utils.NplUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 试卷 服务实现类
 * </p>
 *
 * @author Jzj
 * @since 2021-08-18
 */
@Service
public class EduPaperServiceImpl extends ServiceImpl<EduPaperMapper, EduPaper> implements EduPaperService {
    @Resource
    private EduPaperTopicMapper eduPaperTopicMapper;

    @Resource
    private EduTopicMapper eduTopicMapper;

    @Resource
    private EduTestTopicRecordsMapper testTopicRecordsMapper;


    @Resource
    private EduTestPaperRecordsMapper testPaperRecordsMapper;

    @Transactional
    @Override
    public boolean savePaperOrTopic(EduPaperSaveVo paperSaveVo) {
        //校验数据
        if(paperSaveVo==null) throw new BusinessException(ResultCode.ERROR,"数据异常");
        if(org.apache.commons.lang3.StringUtils.isBlank(paperSaveVo.getPaperName())) throw new BusinessException(ResultCode.ERROR,"试卷名不能为空");
        if(StringUtils.isEmpty(paperSaveVo.getSuggestTime())) throw new BusinessException(ResultCode.ERROR,"考试时长不能为空");
        if(paperSaveVo.getSuggestTime()>4320) throw new BusinessException(ResultCode.ERROR,"考试时长不能大于三天");
        if(paperSaveVo.getSuggestTime()<=0) throw new BusinessException(ResultCode.ERROR,"考试时长异常");
        Integer count = baseMapper.selectCount(new QueryWrapper<EduPaper>().eq("paper_name", paperSaveVo.getPaperName()));
        if(count>0) throw new BusinessException(ResultCode.ERROR,"试卷名已存在");

        List<EduTopicListVo> topicListVoList = paperSaveVo.getTopicListVoList(); //获取题目集合
        //封装试卷表数据
        EduPaper eduPaper = new EduPaper();
        BeanUtils.copyProperties(paperSaveVo,eduPaper);
        eduPaper.setQuestionCount(topicListVoList.size()); //题目总数
        eduPaper.setStatus(1); //状态
        eduPaper.setScore(topicListVoList.stream().mapToInt((s)->s.getScore()).sum()); //总分
        baseMapper.insert(eduPaper);
        //封装试卷题目详情数据
        for (EduTopicListVo vo : topicListVoList) {
            EduPaperTopic eduPaperTopic = new EduPaperTopic();
            eduPaperTopic.setPaperId(eduPaper.getId()); //试卷id
            eduPaperTopic.setTopicId(vo.getId()); //题目id
            eduPaperTopic.setTopicContentId(vo.getTopicDetailsId());//题目详情id
            eduPaperTopicMapper.insert(eduPaperTopic);
        }
        return true;
    }
    @Transactional
    @Override
    public boolean updatePaper(EduPaperSaveVo paperSaveVo) {
        //校验数据
        if(paperSaveVo==null) throw new BusinessException(ResultCode.ERROR,"数据异常");
        if(org.apache.commons.lang3.StringUtils.isBlank(paperSaveVo.getPaperName())) throw new BusinessException(ResultCode.ERROR,"试卷名不能为空");
        if(StringUtils.isEmpty(paperSaveVo.getSuggestTime())) throw new BusinessException(ResultCode.ERROR,"考试时长不能为空");
        if(paperSaveVo.getSuggestTime()>4320) throw new BusinessException(ResultCode.ERROR,"考试时长不能大于三天");
        if(paperSaveVo.getSuggestTime()<=0) throw new BusinessException(ResultCode.ERROR,"考试时长异常");
        Integer count = baseMapper.selectCount(new QueryWrapper<EduPaper>().eq("paper_name", paperSaveVo.getPaperName()).ne("id",paperSaveVo.getId()));
        if(count>0) throw new BusinessException(ResultCode.ERROR,"试卷名已存在");

        List<EduTopicListVo> topicListVoList = paperSaveVo.getTopicListVoList(); //获取题目集合
        //修改试卷基本信息表
        EduPaper eduPaper = new EduPaper();
        BeanUtils.copyProperties(paperSaveVo,eduPaper);
        eduPaper.setQuestionCount(topicListVoList.size()); //题目总数
        eduPaper.setScore(topicListVoList.stream().mapToInt((s)->s.getScore()).sum()); //总分
        baseMapper.updateById(eduPaper);
        //修改试卷题目表
        eduPaperTopicMapper.delete(new QueryWrapper<EduPaperTopic>().eq("paper_id",eduPaper.getId())); //删除原有数据
        for (EduTopicListVo vo : topicListVoList) {
            EduPaperTopic eduPaperTopic = new EduPaperTopic();
            eduPaperTopic.setPaperId(eduPaper.getId()); //试卷id
            eduPaperTopic.setTopicId(vo.getId()); //题目id
            eduPaperTopic.setTopicContentId(vo.getTopicDetailsId());//题目详情id
            eduPaperTopicMapper.insert(eduPaperTopic);
        }


        return true;
    }

    @Override
    public List<FrontPaperIndexVo> getHotPaperList() {
        List<FrontPaperIndexVo> list = baseMapper.getHotPaperList();
        return list;
    }

    @Override
    public Map<String, Object> getFrontPaperList(Page<EduPaper> eduPaperPage, PaperQuery paperQuery) {
        QueryWrapper<EduPaper> wrapper = new QueryWrapper<>();
        ArrayList<FrontPaperIndexVo> list = new ArrayList<>();

        if (!StringUtils.isEmpty(paperQuery.getSubjectId())) {
            wrapper.eq("subject_id", paperQuery.getSubjectId());
        }
        if (!StringUtils.isEmpty(paperQuery.getGmtCreate())) {
            wrapper.orderByDesc("gmt_create");
        }


        baseMapper.selectPage(eduPaperPage, wrapper);

        List<EduPaper> records = eduPaperPage.getRecords();
        for (EduPaper paper : records) {
            //封装数据
            FrontPaperIndexVo paperList = baseMapper.getBasePaperById(paper.getId());
            list.add(paperList);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", list);
        map.put("current", eduPaperPage.getCurrent());
        map.put("pages", eduPaperPage.getPages());
        map.put("size", eduPaperPage.getSize());
        map.put("total", eduPaperPage.getTotal());
        map.put("hasNext", eduPaperPage.hasNext());
        map.put("hasPrevious", eduPaperPage.hasPrevious());

        return map;
    }

    @Override
    public FrontPaperIndexVo getPaperById(Long id) {
        FrontPaperIndexVo paperList = baseMapper.getBasePaperById(id);
        return paperList;
    }



    @Override
    public Map<String, Object> listPage(Page<EduPaper> pageParam, PaperQuery paperQuery) {
        ArrayList<EduPaperListVo> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<EduPaper> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if(!StringUtils.isEmpty(paperQuery)){
            if(!StringUtils.isEmpty(paperQuery.getSubjectId())){
                wrapper.eq("subject_id",paperQuery.getSubjectId());
            }
            if(!StringUtils.isEmpty(paperQuery.getPaperName())){
                wrapper.like("paper_name",paperQuery.getPaperName());
            }
        }
        IPage<EduPaper> selectPage = baseMapper.selectPage(pageParam, wrapper);
        List<EduPaper> records = selectPage.getRecords();
        for (EduPaper paper : records) {
            //封装数据
            EduPaperListVo paperList = baseMapper.getBasePaperList(paper.getId());
            list.add(paperList);
        }
        //获取数据总数
        long total = selectPage.getTotal();
        map.put("list",list);
        map.put("total",total);
        return map;
    }

    @Transactional
    @Override
    public boolean removePaperById(Long id) {
        //删除试卷题目表
        eduPaperTopicMapper.delete(new QueryWrapper<EduPaperTopic>().eq("paper_id",id));
        //删除试卷表
        int result = baseMapper.delete(new QueryWrapper<EduPaper>().eq("id", id));
        if(result>0){
            return true;
        }
        return false;
    }

    @Override
    public EduPaperSaveVo getByIdPaper(Long id) {
        EduPaperSaveVo paperSaveVo = new EduPaperSaveVo();
        ArrayList<EduTopicListVo> list = new ArrayList<>();
        //封装基本信息
        EduPaper paper = baseMapper.selectById(id);
        BeanUtils.copyProperties(paper,paperSaveVo);
        //封装题目
        List<EduPaperTopic> paperTopicList = eduPaperTopicMapper.selectList(new QueryWrapper<EduPaperTopic>().eq("paper_id", id));
        for (EduPaperTopic eduPaperTopic : paperTopicList) {
            EduTopicListVo topicListVo = eduTopicMapper.getBaseTopicList(eduPaperTopic.getTopicId());
            list.add(topicListVo);
        }
        paperSaveVo.setTopicListVoList(list);
        return paperSaveVo;
    }

    @Override
    public FrontPaperTopicVo getPaperTopicById(Long id) {
        FrontPaperTopicVo model = new FrontPaperTopicVo();
        EduPaper paper = baseMapper.selectById(id); //查询试卷基本信息
        BeanUtils.copyProperties(paper,model); //封装试卷基本信息
        int examTime =model.getSuggestTime()*60*1000;
        model.setSuggestTime(examTime);
        List<EduPaperTopic> paperTopicList = eduPaperTopicMapper.selectList(new QueryWrapper<EduPaperTopic>().eq("paper_id", id)); //查询该试卷所有题目

        ArrayList<FrontTopicVo> singleChoiceList = new ArrayList<>(); //单选题集合
        ArrayList<FrontTopicVo> multipleChoiceList = new ArrayList<>(); //多选题集合
        ArrayList<FrontTopicVo> judgeList = new ArrayList<>(); //判断题集合
        ArrayList<FrontTopicVo> shortAnswerList = new ArrayList<>(); //简答题集合


        int singleChoiceNumber =0,multipleChoiceNumber =0,shortAnswerNumber =0,judgeNumber =0; //初始化题数

        for (EduPaperTopic paperTopic : paperTopicList) {
            //查询封装题目
            FrontTopicVo frontTopicVo;
            frontTopicVo = eduTopicMapper.getByIdTopicFront(paperTopic.getTopicId(),100L);

            //判断题型按题型存入集合
            if(frontTopicVo.getQuestionId()==1){
                singleChoiceList.add(frontTopicVo); //封装单选题
                singleChoiceNumber++;
            }else if (frontTopicVo.getQuestionId()==2){
                multipleChoiceList.add(frontTopicVo); //封装多选题
                multipleChoiceNumber++;
            }else if (frontTopicVo.getQuestionId()==4){
                shortAnswerList.add(frontTopicVo); //封装简答题
                shortAnswerNumber++;
            }else if (frontTopicVo.getQuestionId()==5){
                judgeList.add(frontTopicVo); //封装判断题
                judgeNumber++;
            }

        }
        //装箱
        model.setSingleChoiceNumber(singleChoiceNumber);
        model.setMultipleChoiceNumber(multipleChoiceNumber);
        model.setShortAnswerNumber(shortAnswerNumber);
        model.setJudgeNumber(judgeNumber);
        model.setSingleChoiceList(singleChoiceList);
        model.setMultipleChoiceList(multipleChoiceList);
        model.setShortAnswerList(shortAnswerList);
        model.setJudgeList(judgeList);
        return model;
    }

    @Override
    public void automaticGrading(FrontPaperFillAnswerVo fillAnswer) {
        Long paperId = fillAnswer.getId(); //试卷id

        //对试卷数据进行封装
        EduPaper paper = baseMapper.selectById(paperId); //试卷model
        EduTestPaperRecords testPaperRecords = new EduTestPaperRecords();
        BeanUtils.copyProperties(paper,testPaperRecords);
        testPaperRecords.setPaperScore(paper.getScore()); //试卷原始分数
        testPaperRecords.setStatus(1); //状态修改
        testPaperRecords.setDoTime(fillAnswer.getDoTime()); //用时
        testPaperRecords.setUserId(fillAnswer.getUserId()); //用户id
        testPaperRecordsMapper.insert(testPaperRecords);
        List<Integer> count = Arrays.asList(0,0,1);//第一个是总得分，第二个是总答对题数，第三个是序号


        //单选题
        List<Long> singleIds = fillAnswer.getSingleIds(); //单选题id列表
        List<String> singleRadio = fillAnswer.getSingleRadio(); //单选题答案
        verifyRadio(testPaperRecords.getId(),singleIds,singleRadio,count);

        //多选题
        List<Long> multipleIds = fillAnswer.getMultipleIds(); //多选题id列表
        String[][] multipleRadio = fillAnswer.getMultipleRadio(); //多选题答案
        verifyMultiple(testPaperRecords.getId(),multipleIds,multipleRadio,count);

        //判断题
        List<Long> judgeIds = fillAnswer.getJudgeIds(); //判断题id列表
        List<String> judgeRadio = fillAnswer.getJudgeRadio(); //判断题答案
        verifyRadio(testPaperRecords.getId(),judgeIds,judgeRadio,count);

        //问答题
        List<Long> essayIds = fillAnswer.getEssayIds();//问答题id列表
        List<String> essayQuestion = fillAnswer.getEssayQuestion();//问答题答案
        verifyQuestions(testPaperRecords.getId(),essayIds,essayQuestion,count);


        //试卷统计数据
        testPaperRecords.setSystemScore(count.get(0)); //系统判断得分
        testPaperRecords.setUserScore(count.get(0)); //最终得分
        testPaperRecords.setQuestionCorrect(count.get(1)); //答对题数
        testPaperRecordsMapper.updateById(testPaperRecords);
    }

    /**
     * 对单选题，判断题进行批改操作
     * @param testPaperId 试卷统计表id
     * @param ids 题目id列表
     * @param answerList 用户填写答案列表
     * @param count 统计数据
     * @return 返回修改后的统计数据
     */
    private  List<Integer> verifyRadio(Long testPaperId,List<Long> ids,List<String> answerList,List<Integer> count){
        //初始化
        Integer sumScore = count.get(0); //总得分
        Integer sumQuestionNumber = count.get(1); //总答对题数
        Integer serial = count.get(2); //序号
        for (int i = 0; i <ids.size() ; i++) {
            EduTestTopicRecords model = new EduTestTopicRecords();
            FrontPaperAnswerVo topic = eduTopicMapper.getByIdTopicFrontAnswer(ids.get(i)); //根据题目id查询题目详情
            String answer = answerList.get(i); //当前题目答案
            //对数据进行装箱
            BeanUtils.copyProperties(topic,model);
            model.setTestPaperId(testPaperId); //试卷统计表id
            model.setSerial(serial); //题目序号
            model.setUserAnswer(answer);
            serial++; //叠加序号
            //当答案匹配时
            if(answer.equals(topic.getCorrect())){
                sumScore+=topic.getScore(); //叠加分数
                model.setResult(1); //结果

                sumQuestionNumber++;
            }else{
                model.setResult(0);
                model.setScore(0); //当前题目得分
            }
            testTopicRecordsMapper.insert(model);
        }
        //统计
        count.set(0,sumScore);
        count.set(1,sumQuestionNumber);
        count.set(2,serial);
        return count;
    }

    /**
     * 对多选题进行批改操作
     * @param testPaperId 试卷统计表id
     * @param ids 题目id列表
     * @param answerList 用户填写答案列表
     * @param count 统计数据
     * @return
     */
    private List<Integer> verifyMultiple(Long testPaperId,List<Long> ids,String[][] answerList,List<Integer> count){
        //初始化
        Integer sumScore = count.get(0); //总得分
        Integer sumQuestionNumber = count.get(1); //总答对题数
        Integer serial = count.get(2); //序号
        for (int i = 0; i <ids.size() ; i++) {
            EduTestTopicRecords model = new EduTestTopicRecords();
            FrontPaperAnswerVo topic = eduTopicMapper.getByIdTopicFrontAnswer(ids.get(i)); //根据题目id查询题目详情
            String[] answers = answerList[i];//用户输入的答案
            String [] correct =topic.getCorrect().split(","); //正确答案
            //对数据进行装箱
            BeanUtils.copyProperties(topic,model);
            model.setTestPaperId(testPaperId); //试卷统计表id
            model.setSerial(serial); //题目序号
            model.setUserAnswer(Arrays.toString(answers));
            serial++; //叠加序号
            //当答案匹配时
            if(!Arrays.asList(answers).isEmpty() && Arrays.asList(correct).containsAll(Arrays.asList(answers))){
                sumScore+=topic.getScore(); //叠加分数
                model.setResult(1); //结果
                sumQuestionNumber++;
            }else{
                model.setResult(0);
                model.setScore(0); //当前题目得分
            }
            testTopicRecordsMapper.insert(model);
        }
        //统计
        count.set(0,sumScore);
        count.set(1,sumQuestionNumber);
        count.set(2,serial);
        return count;
    }

    /**
     * 对问答题进行批改操作
     * @param testPaperId 试卷统计表id
     * @param ids 题目id列表
     * @param answerList 用户填写答案列表
     * @param count 统计数据
     * @return 返回修改后的统计数据
     */
    private  List<Integer> verifyQuestions(Long testPaperId,List<Long> ids,List<String> answerList,List<Integer> count){
        //初始化
        Integer sumScore = count.get(0); //总得分
        Integer sumQuestionNumber = count.get(1); //总答对题数
        Integer serial = count.get(2); //序号
        for (int i = 0; i <ids.size() ; i++) {
            EduTestTopicRecords model = new EduTestTopicRecords();
            FrontPaperAnswerVo topic = eduTopicMapper.getByIdTopicFrontAnswer(ids.get(i)); //根据题目id查询题目详情
            String answer = answerList.get(i); //用户填写的答案
            String correct = topic.getCorrect(); //正确答案
            //对数据进行装箱
            BeanUtils.copyProperties(topic,model);
            model.setTestPaperId(testPaperId); //试卷统计表id
            model.setSerial(serial); //题目序号
            model.setUserAnswer(answer);
            serial++; //叠加序号

            Float similarity= 0F;
            if(org.apache.commons.lang.StringUtils.isNotBlank(answer)){
                similarity = NplUtils.similarity(correct, answer); //获取相似度
            }

            //当答案匹配时
            if(similarity>0.85){
                sumScore+=topic.getScore(); //叠加分数
                model.setResult(1); //结果
                sumQuestionNumber++;
            }else{
                model.setResult(0);
                model.setScore(0); //当前题目得分
            }
            testTopicRecordsMapper.insert(model);
        }
        //统计
        count.set(0,sumScore);
        count.set(1,sumQuestionNumber);
        count.set(2,serial);
        return count;

    }

}
