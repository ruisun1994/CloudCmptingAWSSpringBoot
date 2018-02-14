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


/* 登录配置 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
    public final static String SESSION_KEY = "email";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        addInterceptor.excludePathPatterns("/user/login");
        addInterceptor.excludePathPatterns("/user/register");
        addInterceptor.excludePathPatterns("/user/index");
        addInterceptor.excludePathPatterns("/user/registered");
        addInterceptor.excludePathPatterns("/user/loggedin");
        addInterceptor.excludePathPatterns("/user/login_err");
        addInterceptor.excludePathPatterns("/user/register_err");
        addInterceptor.excludePathPatterns("/user/userlist");

        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            HttpSession session = request.getSession();

//            判断是否已有该用户登录的session
            if (session.getAttribute(SESSION_KEY) != null) {
                return true;
            }

//            跳转到登录页
            String url = "/user/index";
            response.sendRedirect(url);
            return false;
        }
    }
}
