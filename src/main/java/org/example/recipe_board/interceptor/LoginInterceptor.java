package org.example.recipe_board.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import static org.example.recipe_board.util.Alert.setAlertInfo;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long loginId = (Long) request.getSession().getAttribute("login");

        if(loginId == null) {
            setAlertInfo(request, "비회원은 접근할 수 없습니다. 로그인 해주세요.", request.getContextPath()+"/members/login");
            request.getRequestDispatcher("/WEB-INF/views/alert.jsp").forward(request, response);
            return false;
        }

        return true;
    }
}
