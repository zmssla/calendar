package com.office.calendar.member.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/*
    Spring Security에서 AccessDeniedHandlerf를 구현한 클래스는
    접근거부(인가(Authorization), 403) 발생 시 handle()메서드를 호출한다.
 */

@Slf4j
public class MemberAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("handle()");

        response.sendRedirect("/member/access_denied");

    }
}
