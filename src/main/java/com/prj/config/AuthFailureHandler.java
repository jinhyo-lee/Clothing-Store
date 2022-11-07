package com.prj.config;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;

        if (exception instanceof BadCredentialsException)
            errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다.";
        else if (exception instanceof InternalAuthenticationServiceException)
            errorMessage = "내부 시스템에 오류가 발생하였습니다.";
        else if (exception instanceof AuthenticationCredentialsNotFoundException)
            errorMessage = "인증에 필요한 권한이 필요합니다.";
        else
            errorMessage = "알 수 없는 문제가 발생하였습니다.";

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/loginError?error=true&errorMessage=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
