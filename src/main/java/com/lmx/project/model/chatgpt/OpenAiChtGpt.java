package com.lmx.project.model.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public
class OpenAiChtGpt {
    private String role;
    private String content;
}
