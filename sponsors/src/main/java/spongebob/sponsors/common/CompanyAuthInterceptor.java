package spongebob.sponsors.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * /company/** 경로를 보호하는 인터셉터.
 * 세션에 "companyId" 가 없으면 /company/login 으로 리다이렉트한다.
 *
 * 등록 위치: WebMvcConfig.java → addInterceptors()
 */
public class CompanyAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // TODO:
        // 1. request.getSession(false) 로 세션을 가져온다 (없으면 null)
        // 2. 세션이 null 이거나 session.getAttribute("companyId") == null 이면
        //    response.sendRedirect("/company/login") 후 return false
        // 3. 세션이 있으면 return true (통과)
        throw new UnsupportedOperationException("TODO: preHandle 구현");
    }
}
