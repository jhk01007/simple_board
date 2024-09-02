package org.example.recipe_board.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.recipe_board.domain.Image;
import org.example.recipe_board.dto.ImageDTO;

import java.util.List;

@Mapper
public interface ImageRepository {
    int insertImages(@Param("imageList") List<Image> imageList); // SQL에서 여러 개를 한번에 insert 하는 기능(성능 생각하면 이렇게 해야함. 커넥션 연결이 비교적 비싼 리소스라서..)
    int insertImage(Image image); // 때로는 차라리 디비 연결을 여러번 해버리는게 유리한 경우도 있을 수는 있음.
    List<Image> selectImages(Long recipeId); // 특정 게시글에 첨부된 여러 개의 파일목록 읽어오기.
    Image selectImage(Long id); // 파일 다운로드 기능 구현할 때 하나의 파일 정보 조회하기.
}