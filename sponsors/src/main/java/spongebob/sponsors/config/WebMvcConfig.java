package spongebob.sponsors.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import spongebob.sponsors.common.CompanyAuthInterceptor;

/**
 * Spring MVC 설정 — 인터셉터 등록
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO:
        // registry.addInterceptor(new CompanyAuthInterceptor())
        //         .addPathPatterns("/company/**")       // /company/* 전체 보호
        //         .excludePathPatterns("/company/login"); // 로그인 페이지는 제외
    }
}
