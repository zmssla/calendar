package com.office.calendar.config;

import com.office.calendar.member.MemberSigninInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${calendar.image.upload.resource.locations}")
    private String calendarImageUploadResourceLocations;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // for Windows
        /*
        registry.addResourceHandler("/planUploadImg/**")
                .addResourceLocations("file:///c://calendar/upload/");
         */

        // for Ubuntu
        /*
        registry.addResourceHandler("/planUploadImg/**")
                .addResourceLocations("file:///calendar/upload/");
         */

        registry.addResourceHandler("/planUploadImg/**")
                .addResourceLocations(calendarImageUploadResourceLocations);

    }

    //    final private MemberSigninInterceptor memberSigninInterceptor;
//
//    @Autowired
//    public WebConfig(MemberSigninInterceptor memberSigninInterceptor) {
//        this.memberSigninInterceptor = memberSigninInterceptor;
//
//    }
//
//    /*
//    InterceptorRegistry: Spring MVC에서 인터셉터를 '등록'하고 '설정'하는 역할
//    addInterceptor: 인터셉터로 등록
//    addPathPatterns: 인터셉터를 적용할 URL 패턴을 지정
//    excludePathPatterns: 인터셉터 제외 URL 패턴을 지정
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(memberSigninInterceptor)
//                .addPathPatterns(
//                        "/member/modify"
//                );
//
//        /*
//        registry.addInterceptor(memberSigninInterceptor)
//                .addPathPatterns(
//                        "/member/**"
//                )
//                .excludePathPatterns(
//                        "/member/signup",
//                        "/member/signup_confirm",
//                        "/member/signin",
//                        "/member/signin_confirm"
//                );
//         */
//    }
}
