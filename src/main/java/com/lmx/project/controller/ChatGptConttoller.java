package com.lmx.project.controller;

import cn.hutool.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lmx.project.common.BaseResponse;
import com.lmx.project.common.ErrorCode;
import com.lmx.project.common.ResultUtils;
import com.lmx.project.exception.BusinessException;
import com.lmx.project.model.chatgpt.ChatGptConversion;
import com.lmx.project.model.chatgpt.ChatGptRepose;
import com.lmx.project.model.chatgpt.OpenAiChtGpt;
import com.lmx.project.until.ChatGptUntil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("openai")
@Slf4j
public class ChatGptConttoller {
    @Resource
    private ChatGptUntil chatGptUntil;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 人工智能回答问题
     */
//    @ApiOperation(value = "人工智能回答问题",notes = "text参数是问题内容")
    @PostMapping()
    public BaseResponse<String> getresult(@RequestBody String text) {
        log.info("问题:{}", text);
        ArrayList<OpenAiChtGpt> messagelist = new ArrayList<>();
        messagelist.add(new OpenAiChtGpt("user", text));
        ChatGptRepose respost = chatGptUntil.getRespost(messagelist);
        if (respost != null) {
//            log.info(respost.toString());
            if (respost.getChoices() != null && respost.getChoices().size() > 0) {
                log.info("开始回答{}", respost.getChoices().get(0).getMessage().getContent());
                return ResultUtils.success(respost.getChoices().get(0).getMessage().getContent());
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "平台助手错误，请重试");
            }
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "平台助手错误，请重试");
        }
    }

/**
* 实现与chatgot的对话功能
* */
    @PostMapping("conversion")
    public BaseResponse<String> getConversionResult(@RequestBody ChatGptConversion chatGptConversion) {
        if (chatGptConversion == null || chatGptConversion.getUserid() == null || chatGptConversion.getUserid() == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户id不能为空");
        }
        if (chatGptConversion.getText() == null || chatGptConversion.getText().equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "问题不能为空");
        }
//        获取该用户之前的对话
        String s = stringRedisTemplate.opsForValue().get("userid:" + chatGptConversion.getUserid());
        Gson gson = new Gson();
        ArrayList<OpenAiChtGpt> messagelist = (ArrayList<OpenAiChtGpt>) gson.fromJson(s, new TypeToken<ArrayList<OpenAiChtGpt>>() {
        }.getType());

        log.info("问题:{}", chatGptConversion.toString());

        if (messagelist == null) {
            messagelist = new ArrayList<>();
            messagelist.add(new OpenAiChtGpt("user", chatGptConversion.getText()));
        } else {
            messagelist.add(new OpenAiChtGpt("user", chatGptConversion.getText()));
        }
//        获取chatgptapi的结果
        log.info("问题列表:{}", messagelist.toString());
        ChatGptRepose respost = chatGptUntil.getRespost(messagelist);
//        ArrayList<OpenAiChtGpt> messagelist = new ArrayList<>();
//        messagelist.add(new OpenAiChtGpt("user", text));
//
        if (respost != null) {
//            log.info(respost.toString());
            if (respost.getChoices() != null && respost.getChoices().size() > 0) {
                String content = respost.getChoices().get(0).getMessage().getContent();
                log.info("开始回答{}", content);
                messagelist.add(new OpenAiChtGpt("assistant", content));
                String messagelistjson = gson.toJson(messagelist);
// 当回答成功时，保存回答的结果到redis中
                stringRedisTemplate.opsForValue().set("userid:" + chatGptConversion.getUserid(), messagelistjson,1,TimeUnit.DAYS);
                return ResultUtils.success(content);
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "平台助手错误，请重试");
            }
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "平台助手错误，请重试");
        }
    }

    /**
    * 根据用户id获取之前的对话信息
    * */
    @GetMapping("conversion")
    public BaseResponse<ArrayList<OpenAiChtGpt>> getConversion(Long userid) {
        if (userid == null || userid == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String s = stringRedisTemplate.opsForValue().get("userid:" + userid);
        Gson gson = new Gson();
        ArrayList<OpenAiChtGpt> messagelist = (ArrayList<OpenAiChtGpt>) gson.fromJson(s, new TypeToken<ArrayList<OpenAiChtGpt>>() {
        }.getType());
        return ResultUtils.success(messagelist);

    }
}
