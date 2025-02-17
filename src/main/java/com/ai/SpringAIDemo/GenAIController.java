package com.ai.SpringAIDemo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenAIController {
    private final ChatService chatService;
    private final ImageService imageService;

    public GenAIController(ChatService chatService, ImageService imageService) {
        this.chatService = chatService;
        this.imageService = imageService;
    }

    @GetMapping("ask-ai")
    public String getResponse(@RequestParam String prompt) {
        return chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt) {
        return chatService.getResponseOptions(prompt);
    }

    @GetMapping("generate-image")
    public void generateImage(HttpServletResponse httpServletResponse,
                              @RequestParam String prompt)
            throws IOException {
        ImageResponse imageResponse = imageService.generateImage(prompt);
        String url = imageResponse.getResult().getOutput().getUrl();
        httpServletResponse.sendRedirect(url);
    }

    @GetMapping("generate-image-options")
    public List<String> generateImageOptions(HttpServletResponse httpServletResponse,
                                             @RequestParam String prompt)
            throws IOException {
        ImageResponse imageResponse = imageService.generateImage(prompt);
        List<String> urls = imageResponse.getResults().stream()
                .map(result -> result.getOutput().getUrl())
                .toList();
        return urls;
    }
}