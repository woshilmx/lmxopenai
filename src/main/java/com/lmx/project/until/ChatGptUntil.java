package com.lmx.project.until;

import cn.hutool.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lmx.project.model.chatgpt.OpenAiChtGpt;
import com.lmx.project.model.chatgpt.ChatGptRepose;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ChatGptUntil {

    @Value("${openai.token}")
    private String ApiKey;
//    @Value("${proxy.host}")
//    private String host;
//    @Value("${proxy.port}")
//    private int port;

    /**
     * 获取chatgpt的答案
     */
    public ChatGptRepose getRespost(List<OpenAiChtGpt> messagelist) {
        String url = "https://api.openai.com/v1/chat/completions";
        HashMap<String, Object> bodymap = new HashMap<>();

        bodymap.put("model", "gpt-3.5-turbo");
//        bodymap.put("stream",true);
        bodymap.put("messages", messagelist);
        Gson gson = new Gson();
        String s = gson.toJson(bodymap);
        System.out.println("传递的参数是"+s);
//        System.out.println(s);
        HttpRequest authorization1 = null;
//        System.out.println("apikey:"+ApiKey);
//        if (host != null && !host.equals("") && port != 0) {
//            authorization1 = HttpRequest.post(url).setHttpProxy(host, port)
//                    .header("Authorization", "Bearer " + ApiKey)
//                    .header("Content-Type", "application/json")
//                    .body(s);
//        } else {
            authorization1 = HttpRequest.post(url)
                    .header("Authorization", "Bearer " + ApiKey)
                    .header("Content-Type", "application/json")
                    .body(s);
//        }

//        System.out.println(authorization1);
        String authorization = authorization1
                .execute()
                .body();
        System.out.println(authorization);
//        System.out.println(authorization);
        ChatGptRepose o = gson.fromJson(authorization, new TypeToken<ChatGptRepose>() {
        }.getType());

        return o;
    }


    public static void main(String[] args) {
        ArrayList<OpenAiChtGpt> messagelist = new ArrayList<>();
        messagelist.add(new OpenAiChtGpt("user", "大熊猫在我国的分布情况"));
        messagelist.add(new OpenAiChtGpt("assistant","大熊猫分布在中国主要的四省区：四川、" +
                "陕西、甘肃和云南。其中，四川省是大熊猫的主要栖息地，占全球百分之九十的总数。陕西省和甘肃省大熊猫数量较少，主要分布" +
                "在延安和天水地区。云南省的大熊猫数" +
                "量非常罕见，只有少数野生个体。总的来说，大熊猫的分布范围主要在中国的西南部山区，海拔在800米至4000米之间。"));
        messagelist.add(new OpenAiChtGpt("user", "东北虎在我国的分布是"));
        String url = "https://api.openai.com/v1/chat/completions";
        HashMap<String, Object> bodymap = new HashMap<>();

        bodymap.put("model", "gpt-3.5-turbo");
//        bodymap.put("stream",true);
        bodymap.put("messages", messagelist);
        Gson gson = new Gson();
        String s = gson.toJson(bodymap);
//        System.out.println(s);
        HttpRequest authorization1 = null;
//        System.out.println("apikey:"+ApiKey);
//        if (host != null && !host.equals("") && port != 0) {
            authorization1 = HttpRequest.post(url).setHttpProxy("127.0.0.1", 10809)
                    .header("Authorization", "Bearer " + "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")
                    .header("Content-Type", "application/json")
                    .body(s);
//        System.out.println(authorization1);
        String authorization = authorization1
                .execute()
                .body();
        System.out.println(authorization);
    }

}
