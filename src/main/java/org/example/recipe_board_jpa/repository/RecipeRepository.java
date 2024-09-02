package org.example.recipe_board_jpa.repository;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.recipe_board_jpa.entity.Recipe;
import org.example.recipe_board_jpa.dto.join.RecipeWithServiceId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
//    int insert(Recipe recipe);
////    int update(Board board, String category) throws SQLException;
//    int update(Recipe recipe);
//    int delete(Long id);
//    List<Recipe> selectAll();
//    int selectCount();
//    List<RecipeWithServiceId> selectPageList(@Param("sr") int startRow, @Param("cnt") int count);
//    Optional<RecipeWithServiceId> selectOne(Long id);

    @Query("select r from Recipe r")
    List<Recipe> selectPageList(Pageable pageable);
}
