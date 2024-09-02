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

    @Query("select r from Recipe r")
    List<Recipe> selectPageList(Pageable pageable);
}
