package edu.neu.csye6225.spring2018;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/* Login Configuration */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
    public final static String SESSION_KEY = "email";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        addInterceptor.excludePathPatterns("/user/home");
        addInterceptor.excludePathPatterns("/user/register");
        addInterceptor.excludePathPatterns("/user/index");
        addInterceptor.excludePathPatterns("/user/login");
        addInterceptor.excludePathPatterns("/user/registered");
        addInterceptor.excludePathPatterns("/user/loggedin");
        addInterceptor.excludePathPatterns("/user/login_err");
        addInterceptor.excludePathPatterns("/user/register_err");
        addInterceptor.excludePathPatterns("/user/search");
        addInterceptor.excludePathPatterns("/user/upload");

        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            HttpSession session = request.getSession();
            System.out.print(session.getAttribute(SESSION_KEY));

//            check if any user existed in the session
            if (session.getAttribute(SESSION_KEY) == null) {
                String url = "/user/index";
                response.sendRedirect(url);
                return false;
            }
            else{
                return true;
            }
//            check whether user is logged in
        }
    }
}