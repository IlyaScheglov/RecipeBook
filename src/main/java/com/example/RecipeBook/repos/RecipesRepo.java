package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipesRepo extends JpaRepository<Recipes, Long> {

    List<Recipes> findByUserId(long userId);

    @Query("SELECT r FROM Recipes r WHERE r.id = :recId")
    Recipes findByRecipeId(@Param("recId") long recipeId);


    @Query("SELECT r FROM Recipes r WHERE r.categoryType = :ctgType AND r.id > :lastId")
    List<Recipes> findByCategoryTypeWithoutMapped(@Param("ctgType") long categoryType, @Param("lastId") long lastId);

    @Query("SELECT r FROM Recipes r WHERE upper(r.title) LIKE upper(:partTitle) AND r.id > :lastId")
    List<Recipes> findRecipeWhereTitleLikeWithoutMapped(@Param("partTitle") String partTitle, @Param("lastId") long lastId);

    @Query("SELECT r FROM Recipes r WHERE r.id > :lastId")
    List<Recipes> findAllWithoutMapped(@Param("lastId") long lastId);


}
