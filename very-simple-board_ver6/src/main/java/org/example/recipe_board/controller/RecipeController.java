package org.example.recipe_board.controller;

import org.example.recipe_board.dto.join.RecipeWithServiceId;
import org.example.recipe_board.exception.BoardNotFoundException;
import org.example.recipe_board.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.example.recipe_board.util.Alert.setAlertInfo;


@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ModelAndView getRecipeList(@RequestParam(name = "page", defaultValue = "1") int page) {
        Map<String, Object> pageData = recipeService.getRecipeList(page);
        System.out.println(pageData);
        ModelAndView modelAndView = new ModelAndView("recipe/recipe_list");
        modelAndView.addObject("pageData", pageData);

        return modelAndView;

    }

    @GetMapping("/{recipe_id}")
    public ModelAndView getBoardInfo(@PathVariable("recipe_id") Long recipeId) {
        System.out.println(recipeId);
        ModelAndView modelAndView = new ModelAndView();
        try {
            RecipeWithServiceId recipe = recipeService.read(recipeId);
            System.out.println(recipe);

            modelAndView.addObject("recipe", recipe);
            modelAndView.setViewName("recipe/recipe");
        } catch (BoardNotFoundException e) {
            setAlertInfo(modelAndView, e.getMessage(),  "/recipes");
            modelAndView.setViewName("alert");
        }
        return modelAndView;
    }

//    @GetMapping("/write")
//    public ModelAndView writeForm() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("recipe/write_form");
//
//        return modelAndView;
//    }

//    @PostMapping("/write")
//    public ModelAndView writeBoard(@ModelAttribute WriteDto writeDto, HttpSession session, @RequestParam(value="uploadFile", required = false) MultipartFile[] uploadFile) throws IOException {
//        String title = writeDto.getTitle();
//        String content = writeDto.getContent();
//        Long writerId = (Long) session.getAttribute("login");
//        System.out.println(Arrays.toString(uploadFile));
//
//        ModelAndView modelAndView = new ModelAndView("alert");
//
//        try {
//            //로그인된 사용자라면 글 제출 가능
//            if (writerId == null)
//                throw new AuthenticatedException("글을 작성할 권한이 없습니다. 로그인 해주세요.");
//
//            Board board = new Board(title, content, writerId);
//            recipeService.write(board, uploadFile);
//            setAlertInfo(modelAndView, "글이 작성되었습니다.", CONTEXT_PATH + "/boards");
//        } catch (AuthenticatedException e) {
//            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH);
//        }
//
//        return modelAndView;
//    }
//
//    @GetMapping("/edit/{board_id}")
//    public ModelAndView editForm(@PathVariable("board_id") Long board_id, HttpSession session) {
//        ModelAndView modelAndView = new ModelAndView();
//        try {
//            Board board = recipeService.read(board_id);
//            Long login = (Long) session.getAttribute("login");
//            if (!Objects.equals(board.getWriterId(), login))
//                throw new AuthenticatedException("수정할 권한이 없습니다.");
//
//            modelAndView.addObject("edit", board);
//            modelAndView.setViewName("board/edit_form");
//        } catch (BoardNotFoundException | AuthenticatedException e) {
//            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH);
//            modelAndView.setViewName("alert");
//        }
//
//        return modelAndView;
//    }
//
//    @PostMapping("/edit/{board_id}")
//    public ModelAndView edit(@PathVariable("board_id") Long board_id, @ModelAttribute EditDto editDto, HttpSession session) {
//        ModelAndView modelAndView = new ModelAndView("alert");
//        Long login = (Long) session.getAttribute("login");
//
//        try {
//            Board board = recipeService.getBoardInfo(board_id);
//            if (!Objects.equals(board.getWriterId(), login))
//                throw new AuthenticatedException("수정할 권한이 없습니다.");
//            String title = editDto.getTitle();
//            String content = editDto.getContent();
//            board.setTitle(title);
//            board.setContent(content);
//            recipeService.edit(board);
//            setAlertInfo(modelAndView, "수정되었습니다.", CONTEXT_PATH + "/boards/" + board_id);
//        } catch (BoardNotFoundException | AuthenticatedException e) {
//            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH);
//        }
//        return modelAndView;
//    }
//
//    @PostMapping("/delete/{board_id}")
//    public ModelAndView deleteBoard(@PathVariable("board_id") Long board_id, HttpSession session) {
//        ModelAndView modelAndView = new ModelAndView("alert");
//        Board board = null;
//        try {
//            board = recipeService.read(board_id);
//            Long login = (Long) session.getAttribute("login");
//            if (!Objects.equals(board.getWriterId(), login))
//                throw new AuthenticatedException("삭제할 권한이 없습니다.");
//
//            recipeService.delete(board_id);
//            setAlertInfo(modelAndView, "삭제되었습니다.", CONTEXT_PATH + "/boards");
//        } catch (BoardNotFoundException | AuthenticatedException e) {
//            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH + "/boards");
//        }
//
//        return modelAndView;
//    }
//
//    @GetMapping("/download")
//    public void download(@RequestParam("fid")Long id, HttpServletResponse response) throws IOException {
//        FileDTO fileDTO = recipeService.getFileInfo(id); // originalFile, savedPath 정보 조회함.
//
//        String fileName = new String(fileDTO.getOriginalName().getBytes("UTF-8"), "ISO-8859-1");
//
//        // response는 기본적으로 html을 응답하는 헤더가 설정되어 있음.
//        // 하지만, 지금은 html을 응답하는게 아니라 파일 그 자체를 전송할거임. 응답 객체의 헤더정보를 파일전송 버전으로 변경하자!
//        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\""); // filename="right.png"
//        response.setHeader("Content-Transfer-Encoding", "binary");
//
//        FileInputStream fis = new FileInputStream(fileDTO.getSavedPath());
//        OutputStream os = response.getOutputStream(); // response로 응답하는 스트림(문자열 단위 아니고 바이트 단위로 보내게 됨.)
//        FileCopyUtils.copy(fis, os);
//    }

}
