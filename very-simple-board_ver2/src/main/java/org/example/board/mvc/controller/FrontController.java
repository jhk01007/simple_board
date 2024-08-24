package org.example.board.mvc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.board.mvc.controller.util.ControllerMapper;
import org.example.board.mvc.view.MyView;

import java.io.IOException;

@WebServlet({"/main/*", "/boards/*", "/members/*"})
public class FrontController extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String servletPath = req.getServletPath();
        MyController controller = ControllerMapper.getController(servletPath); // ControllerMapper에서 적절한 컨트롤러를 받음
        if(controller == null) { // 해당 요청을 처리할 컨트롤러 객체가 없는 경우
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String viewName = controller.process(req, resp);
        MyView myView = viewResolver(viewName);
        myView.render(req, resp); // 뷰 렌더링
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
