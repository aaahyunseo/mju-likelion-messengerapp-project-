package org.mjulikelion.messengerapplication.config;

import lombok.RequiredArgsConstructor;
import org.mjulikelion.messengerapplication.authentication.AuthenticatedPrincipalArgumentResolver;
import org.mjulikelion.messengerapplication.interceptor.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Config implements WebMvcConfigurer {

    private final AuthenticatedPrincipalArgumentResolver authenticatedPrincipalArgumentResolver;
    private final Interceptor interceptor;
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        //인터셉터 등록
        registry.addInterceptor(interceptor)
                .addPathPatterns("/members/**","/messages/**");
    }

    @Override
    //어노테이션 등록
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedPrincipalArgumentResolver);
    }
}
