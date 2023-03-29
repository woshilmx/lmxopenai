package com.lmx.project.controller;

import com.lmx.project.common.BaseResponse;
import com.lmx.project.common.ErrorCode;
import com.lmx.project.common.ResultUtils;
import com.lmx.project.exception.BusinessException;
import com.lmx.project.model.chatgpt.ChatGptRepose;
import com.lmx.project.model.chatgpt.OpenAiChtGpt;
import com.lmx.project.until.ChatGptUntil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("openai")
@Slf4j
public class ChatGptConttoller {
    @Resource
    private ChatGptUntil chatGptUntil;
    /**
     * 人工智能回答问题
     * */
//    @ApiOperation(value = "人工智能回答问题",notes = "text参数是问题内容")
    @PostMapping()
    public BaseResponse<String> getresult(@RequestBody  String text) {
        log.info("问题:{}",text);
        ArrayList<OpenAiChtGpt> messagelist = new ArrayList<>();
        messagelist.add(new OpenAiChtGpt("user", text));
        ChatGptRepose respost = chatGptUntil.getRespost(messagelist);
        if (respost != null) {
//            log.info(respost.toString());
            if (respost.getChoices()!=null && respost.getChoices().size()>0){
                log.info("开始回答{}",respost.getChoices().get(0).getMessage().getContent());
                return ResultUtils.success(respost.getChoices().get(0).getMessage().getContent());
            }else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "平台助手错误，请重试");
            }
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "平台助手错误，请重试");
        }

    }
}
