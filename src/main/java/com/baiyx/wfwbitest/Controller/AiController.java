package com.baiyx.wfwbitest.Controller;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.SseStreamListener;
import com.plexpt.chatgpt.util.Proxys;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.Proxy;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: 白宇鑫
 * @Date: 2023年3月24日, 0024 下午 12:03:04
 * @Description: ChatGPT
 */
@Controller
@Api(tags = "AiController", description = "ChatGPT")
@RequestMapping("/AI")
public class AiController {

    @ApiOperation(value = "ChatGPT(开启异步)")
    @GetMapping("/sse")
    @CrossOrigin
    @ResponseBody
    @Async
    public CompletableFuture<String> sseEmitter(String prompt) {
        // 国内需要代理 国外不需要
        // Proxy proxy = Proxys.http("192.168.245.1", 15732);
        ChatGPT chatGPT = ChatGPT.builder()
                .timeout(600)
                .apiKey("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")
                // .proxy(proxy)
                .apiHost("https://api.openai.com/")
                .build()
                .init();
        // Message system = Message.ofSystem(prompt);
        Message message = Message.of(prompt);

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                // .model("gpt-3.5-turbo-0613")
                .messages(Arrays.asList(message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        System.out.println(res.getContent());
        return CompletableFuture.completedFuture(res.getContent());
    }

    @ApiOperation(value = "ChatGPT2(关闭异步)")
    @GetMapping("/sse2")
    @CrossOrigin
    @ResponseBody
    public String sseEmitter2(String prompt) {
        // 国内需要代理 国外不需要
        // Proxy proxy = Proxys.http("192.168.245.1", 15732);
        ChatGPT chatGPT = ChatGPT.builder()
                .timeout(600)
                .apiKey("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")
                // .proxy(proxy)
                .apiHost("https://api.openai.com/")
                .build()
                .init();
        // Message system = Message.ofSystem(prompt);
        Message message = Message.of(prompt);

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                // .model("gpt-3.5-turbo-0613")
                .messages(Arrays.asList(message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        System.out.println(res.getContent());
        return res.getContent();
    }
}
