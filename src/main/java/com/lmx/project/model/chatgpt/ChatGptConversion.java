package com.lmx.project.model.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatGptConversion {

    /**
     * 当前登录用户的id
     * */
    private Long userid;

    /**
     * 对话的文本
     * */
    private String text;
}
