package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.TagsInRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagsInRecipeRepo extends JpaRepository<TagsInRecipe, Long> {

    @Query("SELECT t.tagId FROM TagsInRecipe t WHERE recipeId = :recId")
    List<Long> findAllRecipeTagsId(@Param("recId") long recipeId);

    List<TagsInRecipe> findByRecipeId(long recipeId);

    @Query("SELECT t.recipeId FROM TagsInRecipe t WHERE tagId = :tId")
    List<Long> findRecipeIdByTagId(@Param("tId") long tagId);
}
