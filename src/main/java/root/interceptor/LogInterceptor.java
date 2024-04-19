package root.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ){
//        log.info("INTERCEPTOR");

        // bo /css/main.css ra khoi Log
        if (!request.getServletPath().equals("/css/main.css")) {
            log.warn(request.getMethod() + ":" + request.getServletPath());
        }

        return true; // true: di tiep, false: dung lai
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex
    ) throws Exception {
//        log.info("INTERCEPTOR DONE");
    }
}
