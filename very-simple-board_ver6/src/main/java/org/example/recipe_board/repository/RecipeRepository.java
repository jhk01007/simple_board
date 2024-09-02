package org.example.recipe_board.repository;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.recipe_board.domain.Recipe;
import org.example.recipe_board.dto.join.RecipeWithServiceId;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RecipeRepository {
    int insert(Recipe recipe);
//    int update(Board board, String category) throws SQLException;
    int update(Recipe recipe);
    int delete(Long id);
    List<Recipe> selectAll();
    int selectCount();
    List<RecipeWithServiceId> selectPageList(@Param("sr") int startRow, @Param("cnt") int count);
    Optional<RecipeWithServiceId> selectOne(Long id);
}
