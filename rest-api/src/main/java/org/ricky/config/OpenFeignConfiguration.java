package org.ricky.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.ricky.client.fallback.JudgeClientFallbackFactory;
import org.ricky.client.fallback.ProblemClientFallbackFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.ricky.common.constants.CommonConstants.*;
import static org.ricky.common.utils.ValidationUtils.requireNonNull;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className OpenFeignConfiguration
 * @desc
 */
@Configuration
public class OpenFeignConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        requireNonNull(requestAttributes, "ServletRequestAttributes must not be null.");
        HttpServletRequest request = requestAttributes.getRequest();
        requestTemplate.header(AUTHORIZATION, BEARER + request.getHeader(AUTH_COOKIE_NAME));
    }

    @Bean
    public ProblemClientFallbackFactory problemClientFallbackFactory() {
        return new ProblemClientFallbackFactory();
    }

    @Bean
    public JudgeClientFallbackFactory judgeClientFallbackFactory() {
        return new JudgeClientFallbackFactory();
    }

}
