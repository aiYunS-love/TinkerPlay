package com.baiyx.wfwbitest.Controller;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.SseStreamListener;
import com.plexpt.chatgpt.util.Proxys;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.Proxy;
import java.util.Arrays;

/**
 * @Author: 白宇鑫
 * @Date: 2023年3月24日, 0024 下午 12:03:04
 * @Description: ChatGPT
 */
@Controller
@Api(tags = "AiController", description = "ChatGPT")
@RequestMapping("/AI")
public class AiController {

    @ApiOperation(value = "ChatGPT")
    @GetMapping("/sse")
    @CrossOrigin
    public SseEmitter sseEmitter(String prompt) {
        //国内需要代理 国外不需要
        Proxy proxy = Proxys.http("116.117.134.135", 9999);
        ChatGPTStream chatGPTStream = ChatGPTStream.builder()
                .timeout(600)
                .apiKey("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")
                .proxy(proxy)
                .apiHost("https://api.openai.com/")
                .build()
                .init();
        SseEmitter sseEmitter = new SseEmitter(-1L);
        SseStreamListener listener = new SseStreamListener(sseEmitter);
        Message message = Message.of(prompt);
        listener.setOnComplate(msg -> {
            //回答完成，可以做一些事情
        });
        chatGPTStream.streamChatCompletion(Arrays.asList(message), listener);
        return sseEmitter;
    }
}
