package com.example.springbootinit.manager;

import com.example.springbootinit.common.ErrorCode;
import com.example.springbootinit.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.exception.SparkException;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import io.github.briqt.spark4j.model.response.SparkTextUsage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于对接 AI 平台
 */
@Service
public class AiManager {

    @Resource
    private YuCongMingClient yuCongMingClient;
//    @Resource
//    private SparkClient sparkClient;
    /**
     * AI 生成问题的预设条件
     */
//    public static final String PRECONDITION = "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
//            "分析需求：\n" +
//            "{数据分析的需求或者目标}\n" +
//            "原始数据：\n" +
//            "{csv格式的原始数据，用,作为分隔符}\n" +
//            "请根据这两部分内容，严格按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
//            "【【【【【\n" +
//            "{前端 Echarts V5 的 option 配置对象 JSON 代码, 不要生成任何多余的内容，比如注释和代码块标记}\n" +
//            "【【【【【\n" +
//            "{明确的数据分析结论、越详细越好，不要生成多余的注释} \n" +
//            "最终格式是:  【【【【【 前端代码【【【【【分析结论 \n";
    /**
     * AI 对话
     *
     * @param modelId
     * @param message
     * @return
     */
    public String doChat(long modelId, String message) {
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        System.out.println(response.toString());
        if (response == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 响应错误");
        }
        return response.getData().getContent();
    }
    public String doChat1(String userInput) {
        SparkClient sparkClient=new SparkClient();
        StringBuilder response =new StringBuilder();
        // 设置认证信息
        sparkClient.appid="da83811c";
        sparkClient.apiSecret="YmUyOWU4Y2Y1MTYyZTczYWRiOGY3Yzcy";
        sparkClient.apiKey="21e312288cfacdf528d3161294a945b7";
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages=new ArrayList<>();
        messages.add(SparkMessage.systemContent("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
                "分析需求：\n" +
                        "{数据分析的需求或者目标}\n" +
                "原始数据：\n" +
                        "{csv格式的原始数据，用,作为分隔符}\n" +
                        "请根据这两部分内容，严格按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
                        "【【【【\n" +
                        "{前端 Echarts V5 的 option 配置对象 JSON 代码, 不要生成任何多余的内容，比如注释和代码块标记}\n" +
                        "【【【【\n" +
                        "{明确的数据分析结论、越详细越好，不要生成多余的注释,不要在【【【【之前加上回答：和】】】】} \n" +
                        "最终格式是:  【【【【 前端代码【【【【分析结论 \n"));
        messages.add(SparkMessage.userContent(userInput));
        // 构造请求
        SparkRequest sparkRequest=SparkRequest.builder()
                // 消息列表
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传，默认为2048。
                // V1.5取值为[1,4096]
                // V2.0取值为[1,8192]
                // V3.0取值为[1,8192]
                .maxTokens(2048)
// 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.2)
// 指定请求版本，默认使用最新2.0版本
                .apiVersion(SparkApiVersion.V3_0)
                .build();

        try {
            // 同步调用
            SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
            response.append(chatResponse.getContent());
            SparkTextUsage textUsage = chatResponse.getTextUsage();

            System.out.println("\n回答：" + chatResponse.getContent());
            System.out.println("\n提问tokens：" + textUsage.getPromptTokens()
                    + "，回答tokens：" + textUsage.getCompletionTokens()
                    + "，总消耗tokens：" + textUsage.getTotalTokens());
        } catch (SparkException e) {
            System.out.println("发生异常了：" + e.getMessage());
        }
        return response.toString();
    }
//    public String sendMesToAIUseXingHuo(final String content) {
//        List<SparkMessage> messages = new ArrayList<>();
//        messages.add(SparkMessage.userContent(content));
//        // 构造请求
//        SparkRequest sparkRequest = SparkRequest.builder()
//                // 消息列表
//                .messages(messages)
//                // 模型回答的tokens的最大长度,非必传,取值为[1,4096],默认为2048
//                .maxTokens(2048)
//                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
//                .temperature(0.2)
//                // 指定请求版本，默认使用最新2.0版本
//                .apiVersion(SparkApiVersion.V2_0)
//                .build();
//        // 同步调用
//        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
//        return chatResponse.getContent();
//    }

}
