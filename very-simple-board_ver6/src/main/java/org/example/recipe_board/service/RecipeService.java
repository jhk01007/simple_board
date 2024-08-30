package org.example.recipe_board.service;


import org.example.recipe_board.domain.Recipe;
import org.example.recipe_board.dto.CommentDTO;
import org.example.recipe_board.dto.join.RecipeWithServiceId;
import org.example.recipe_board.exception.BoardNotFoundException;
import org.example.recipe_board.object_mapper.CommentMapper;
import org.example.recipe_board.object_mapper.RecipeMapper;
import org.example.recipe_board.repository.CommentRepository;
import org.example.recipe_board.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// SQL 실행은 리포지토리가 담당하고 나는 뭘 검사하고 판단하고 조작해내는 비즈니스 로직
@Service
public class RecipeService {
    private static final int COUNT_PER_PAGE = 10; // 한 페이지당 보여줄 글의 갯수
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;

    public RecipeService(RecipeRepository recipeRepository, RecipeMapper recipeMapper, CommentRepository commentRepository) {
        this.recipeRepository = recipeRepository;
        this.commentRepository = commentRepository;
    }


//    private final FileRepository fileRepository;
//    private final CommentRepository commentRepository;

//    public RecipeService(BoardRepository boardRepository, FileRepository fileRepository, CommentRepository commentRepository) {
//        this.boardRepository = boardRepository;
//        this.fileRepository = fileRepository;
//        this.commentRepository = commentRepository;
//    }

    // 글 읽기 수행할 때 작성자와 읽는 사용자가 일치하는지 검사해서 조회수 증가
    public RecipeWithServiceId read(Long id) {
        RecipeWithServiceId recipe =
                recipeRepository.selectOne(id).orElseThrow(() -> new BoardNotFoundException("해당 글을 찾을 수 없습니다."));
        addReadCount(RecipeMapper.toRecipe(recipe));
        return recipe;
    }

    private int addReadCount(Recipe recipe) {
        recipe.setReadCount(recipe.getReadCount() + 1);
        return recipeRepository.update(recipe);
    }
//
//    @Transactional
//    public int write(Board board, MultipartFile[] uploadFile) throws IOException {
//        int result =  recipeRepository.insert(board);
//        List<FileDTO> savedFiles = FileUtil.saveFiles(uploadFile); // 폴더에 해당 파일들 저장시키고 저장정보 얻어오기
//        System.out.println("파일 저장 완료 : "+savedFiles);
//        System.out.println("파일 정보 디비에 기록 완료 : "+ saveFileInfos(savedFiles, board.getId()));
//        return result;
//    }
//
//    private int saveFileInfos(List<FileDTO> fileDTOList, Long board_id){ // 작성된 글 하나에 파일이 여러개 첨부될 수 있음.
//        if(fileDTOList == null || fileDTOList.isEmpty()) return 0;
//
//        for(FileDTO f: fileDTOList){ // 파일이름originalName, 저장된경로savedPath만 설정되어 있으니까 게시글 번호 붙여서 insert 시켜야 됨!
//            f.setBoardId(board_id);
//        }
//        return fileRepository.insertFiles(fileDTOList);
//    }
//
//    public FileDTO getFileInfo(Long id){
//        // file 다운로드 카운트를 update 한다던지 뭐 부가작업 필요하면 여기서 해야 함.
//        return fileRepository.selectFile(id);
//    }
//
//    public int edit(Board board) {
//        return recipeRepository.update(board);
//    }
//
//    public int delete(Long id) {
//        return recipeRepository.delete(id);
//    }
//    public Board getBoardInfo(Long id) {
//        return recipeRepository.selectOne(id).orElseThrow(() -> new BoardNotFoundException("해당 글을 찾을 수 없습니다."));
//    }

    // 현재 보고자하는 페이지 정보가 들어왔을 때, 실제 해당 페이지에 보여져야 하는 List<Board>를 포함해서 페이지가 총 몇개 필요하고,
    // 하단 페이지 링크는 1-10 or 11~20같은 페이지 구간 계산

    public Map<String, Object> getRecipeList(int page) {
        int totalCount = recipeRepository.selectCount(); // 총 게시글의 갯수;
        int totalPageCount = totalCount % COUNT_PER_PAGE == 0 ? // 예를들어 글이 총 20개라면 2페이지가 필요하지만
                totalCount / COUNT_PER_PAGE : totalCount / COUNT_PER_PAGE + 1;  // 21~29개라면 3페이지가 필요함(그래서 + 1)

        int startPage = (page - 1) / 10 * 10 + 1; // 현재 페이지가 11, 12, 13, ... , 20이었을 때 -1해서 10~19로 바꾸고 /10*10 하면 11, 12, ..., 19 다 동일하게 10으로 동일됨
        int endPage = startPage + 9; // 한화면에 보여줄 페이지의 수는 총 10개 (예: 11~20)

        if(totalPageCount < endPage) endPage = totalPageCount; // 만약 총 페이지의 갯수가 계산된 마지막 페이지 수보다 많다면 총 페이지의 갯수가 마지막 페이지가 됨

        List<RecipeWithServiceId> recipes = recipeRepository.selectPageList((page - 1) * COUNT_PER_PAGE, COUNT_PER_PAGE); // 0페이지 0~9번 인덱스 ,1페이지 10~19번 인덱스

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("page", page);
        resultData.put("startPage", startPage);
        resultData.put("endPage", endPage);
        resultData.put("totalPageCount", totalPageCount);
        resultData.put("recipes", recipes);

        return resultData;
    }

    // 댓글 작성
    public void writeComment(CommentDTO commentDTO) {
        commentRepository.insert(CommentMapper.toComment(commentDTO));
    }

    // 특정 게시글의 댓글 목록
    public List<CommentDTO> getComments(Long boardId) {
        List<CommentDTO> commentDTOS = commentRepository.selectListByBoardId(boardId);

        for(CommentDTO childDTO : commentDTOS) {
            if(childDTO.getParentId() != null) {
                for(CommentDTO parent: commentDTOS) {
                    if(childDTO.getParentId().equals(parent.getId())) {
                        parent.getChildren().add(childDTO);
                    }
                }
            }
        }

        for(int i = 0; i < commentDTOS.size(); i++) {
            if(commentDTOS.get(i).getParentId() != null) {
                commentDTOS.remove(i);
                i--;
            }
        }

        System.out.println(commentDTOS);
        return commentDTOS;
    }
}
