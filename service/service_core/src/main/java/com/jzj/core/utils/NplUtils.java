package com.jzj.core.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.nlp.v20190408.NlpClient;
import com.tencentcloudapi.nlp.v20190408.models.Similarity;
import com.tencentcloudapi.nlp.v20190408.models.TextSimilarityRequest;
import com.tencentcloudapi.nlp.v20190408.models.TextSimilarityResponse;

/**
 * 自然语言处理工具类
 * @Author Jzj
 * @Date 2021/9/10 0:37
 * @Version 1.0
 */
public class NplUtils {
    public static Float similarity(String originalString, String inputString){
        try{
            Credential cred = new Credential(TenCentProperties.SECRET_ID, TenCentProperties.SECRET_KEY);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("nlp.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            NlpClient client = new NlpClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            TextSimilarityRequest req = new TextSimilarityRequest();
            req.setSrcText(originalString);

            String[] targetText1 = {inputString};
            req.setTargetText(targetText1);

            // 返回的resp是一个TextSimilarityResponse的实例，与请求对象对应
            TextSimilarityResponse resp = client.TextSimilarity(req);
            Similarity[] similarity = resp.getSimilarity();
            return similarity[0].getScore();
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
