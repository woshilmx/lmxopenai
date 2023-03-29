package com.lmx.project.model.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
*  待发送消息的内容
* */

/**
 * 返回的消息数据
 * */


/**
 * 返回的响应体
 * */
@AllArgsConstructor
@Data
public
class ChatGptRepose {

    private String id;
    private String object;
    private Long created;
    private String model;
    private Object usage;
    private List<Choice> choices;
    private String finish_reason;
    private int index;


}