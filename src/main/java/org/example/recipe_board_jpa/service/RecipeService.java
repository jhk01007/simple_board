package org.example.recipe_board_jpa.service;


import org.example.recipe_board_jpa.dto.EditDto;
import org.example.recipe_board_jpa.entity.Comment;
import org.example.recipe_board_jpa.entity.Image;
import org.example.recipe_board_jpa.entity.Recipe;
import org.example.recipe_board_jpa.dto.CommentDTO;
import org.example.recipe_board_jpa.dto.ImageDTO;
import org.example.recipe_board_jpa.dto.WriteDto;
import org.example.recipe_board_jpa.dto.join.RecipeWithServiceId;
import org.example.recipe_board_jpa.exception.BoardNotFoundException;
import org.example.recipe_board_jpa.exception.ImageNotFoundException;
import org.example.recipe_board_jpa.object_mapper.CommentMapper;
import org.example.recipe_board_jpa.object_mapper.RecipeMapper;
import org.example.recipe_board_jpa.repository.CommentRepository;
import org.example.recipe_board_jpa.repository.ImageRepository;
import org.example.recipe_board_jpa.repository.RecipeRepository;
import org.example.recipe_board_jpa.util.ImageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// SQL 실행은 리포지토리가 담당하고 나는 뭘 검사하고 판단하고 조작해내는 비즈니스 로직
@Service
public class RecipeService {
    private static final int COUNT_PER_PAGE = 10; // 한 페이지당 보여줄 글의 갯수
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;

    public RecipeService(RecipeRepository recipeRepository, CommentRepository commentRepository, ImageRepository imageRepository) {
        this.recipeRepository = recipeRepository;
        this.commentRepository = commentRepository;
        this.imageRepository = imageRepository;
    }


    @Transactional
    // 글 읽기 수행할 때 작성자와 읽는 사용자가 일치하는지 검사해서 조회수 증가
    public RecipeWithServiceId read(Long id) {
        Recipe recipe =
                recipeRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("해당 글을 찾을 수 없습니다."));
        addReadCount(recipe);
        return RecipeMapper.toDTO(recipe);
    }

    private void addReadCount(Recipe recipe) {
        recipe.setReadCount(recipe.getReadCount() + 1);
    }

    @Transactional
    public void write(WriteDto writeDto) throws IOException {

        Recipe recipe = new Recipe(null, writeDto.getWriterId(), writeDto.getFoodName(), "", writeDto.getProcess());
        recipeRepository.save(recipe);
        List<Image> imageList = ImageUtil.saveImages(writeDto.getImages()); // 폴더에 해당 파일들 저장시키고 저장정보 얻어오기
        System.out.println("파일 저장 완료 : "+ imageList);
        System.out.println("파일 정보 디비에 기록 완료 : "+ saveImageInfos(imageList, recipe));
    }

    private int saveImageInfos(List<Image> imageList, Recipe recipe){ // 작성된 글 하나에 파일이 여러개 첨부될 수 있음.
        if(imageList == null || imageList.isEmpty()) return 0;

        for(Image i: imageList){ // 파일이름 originalName, 저장된경로 savedPath만 설정되어 있으니까 게시글 번호 붙여서 insert 시켜야 됨!
            i.setRecipe(recipe);
        }
        return imageRepository.saveAll(imageList).size();
    }

    public ImageDTO getImageInfo(Long id){
        // file 다운로드 카운트를 update 한다던지 뭐 부가작업 필요하면 여기서 해야 함.
        Image image = imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException("이미지를 찾을 수 없습니다."));
        return new ImageDTO(image.getId(), image.getRecipe().getId(), image.getOriginalName(), image.getSavedPath());
    }

    @Transactional
    public void edit(Long id, EditDto editDto) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("해당 글을 찾을 수 없습니다."));
        recipe.setFoodName(editDto.getFoodName());
        recipe.setProcess(editDto.getProcess());
    }

    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }
//    public Board getBoardInfo(Long id) {
//        return recipeRepository.selectOne(id).orElseThrow(() -> new BoardNotFoundException("해당 글을 찾을 수 없습니다."));
//    }

    // 현재 보고자하는 페이지 정보가 들어왔을 때, 실제 해당 페이지에 보여져야 하는 List<Board>를 포함해서 페이지가 총 몇개 필요하고,
    // 하단 페이지 링크는 1-10 or 11~20같은 페이지 구간 계산

    public Map<String, Object> getRecipeList(int page) {
        int totalCount = (int) recipeRepository.count(); // 총 게시글의 갯수;
        int totalPageCount = totalCount % COUNT_PER_PAGE == 0 ? // 예를들어 글이 총 20개라면 2페이지가 필요하지만
                totalCount / COUNT_PER_PAGE : totalCount / COUNT_PER_PAGE + 1;  // 21~29개라면 3페이지가 필요함(그래서 + 1)

        int startPage = (page - 1) / 10 * 10 + 1; // 현재 페이지가 11, 12, 13, ... , 20이었을 때 -1해서 10~19로 바꾸고 /10*10 하면 11, 12, ..., 19 다 동일하게 10으로 동일됨
        int endPage = startPage + 9; // 한화면에 보여줄 페이지의 수는 총 10개 (예: 11~20)

        if(totalPageCount < endPage) endPage = totalPageCount; // 만약 총 페이지의 갯수가 계산된 마지막 페이지 수보다 많다면 총 페이지의 갯수가 마지막 페이지가 됨

        // spring data jpa가 지원하는 Pageable은 데이터베이스 상의 시작 인덱스 번호가 아니라 페이지 번호를 넣으면 알아서 데이터베이 상의 인덱스 번호로 변환시켜 실행시킴
        Pageable pageable = PageRequest.of(page - 1, COUNT_PER_PAGE);

        List<Recipe> recipes = recipeRepository.selectPageList(pageable); // 0페이지 0~9번 인덱스 ,1페이지 10~19번 인덱스
        List<RecipeWithServiceId> recipeDTOs = recipes.stream().map(RecipeMapper::toDTO).collect(Collectors.toList());
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("page", page);
        resultData.put("startPage", startPage);
        resultData.put("endPage", endPage);
        resultData.put("totalPageCount", totalPageCount);
        resultData.put("recipes", recipeDTOs);

        return resultData;
    }

    // 댓글 작성
    public void writeComment(CommentDTO commentDTO) {
        commentRepository.save(CommentMapper.toComment(commentDTO));
    }

    // 특정 게시글의 댓글 목록
    public List<CommentDTO> getComments(Long recipeId) {
        List<CommentDTO> commentDTOS =
                commentRepository.findByRecipeId(recipeId)
                        .stream()
                        .map(CommentMapper::toDTO)
                        .collect(Collectors.toList());

//        System.out.println(commentDTOS);

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
