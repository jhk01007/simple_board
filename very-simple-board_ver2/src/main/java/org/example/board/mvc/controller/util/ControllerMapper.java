package org.example.board.mvc.controller.util;

import org.example.board.mvc.controller.BoardController;
import org.example.board.mvc.controller.MainController;
import org.example.board.mvc.controller.MemberController;
import org.example.board.mvc.controller.MyController;

public class ControllerMapper {
    private static BoardController boardController = new BoardController();
    private static MemberController memberController = new MemberController();
    private static MainController mainController = new MainController();

    public static MyController getController(String command) {

        if("/boards".equals(command))
            return boardController;
        else if("/members".equals(command))
            return memberController;
        else if("/main".equals(command))
            return mainController;
        else
            return null;

    }
}
