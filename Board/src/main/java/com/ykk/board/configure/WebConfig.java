package com.ykk.board.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 브라우저에서 접근하는 경로
    private static final String RESOURCE_PATH = "/upload/**";

    // 실제 파일이 저장되는 경로
    private static final String SAVE_PATH = "file:///C:/upload/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCE_PATH)
                .addResourceLocations(SAVE_PATH);
    }
}
