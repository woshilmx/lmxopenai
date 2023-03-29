package com.lmx.project.model.chatgpt;//package com.lmx.project.model.chatgpt;
//
//import lombok.Data;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
///**
// * @author wuKeFan
// * @date 2023-02-10 15:25:32
// */
//
//@Data
//@ConfigurationProperties(prefix = "openai")
//public class OpenAiProperties implements InitializingBean {
//    // 秘钥
//    String token;
//    // 超时时间
//    Integer timeout;
//
//    // 设置属性时同时设置给OpenAiUtils
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        OpenAiUtils.OPENAPI_TOKEN = token;
//        OpenAiUtils.TIMEOUT = timeout;
//    }
//}