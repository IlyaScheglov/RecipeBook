package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikesRepo extends JpaRepository<Likes, Long> {

    @Query("SELECT l FROM Likes l WHERE l.recipeId = :recId")
    List<Likes> findLikesByRecipeId(@Param("recId") long recipeId);

    @Query("SELECT l FROM Likes l WHERE l.userId = :usId AND l.recipeId = :recId")
    List<Likes> likedOrNot(@Param("usId") long userId, @Param("recId") long recipeId);

    @Query("SELECT l FROM Likes l WHERE l.recipeId = :recId AND l.userId = :usId")
    List<Likes> findIdOfLikeByUserAndRecipeId(@Param("recId") long recipeId, @Param("usId") long userId);

    List<Likes> findByUserId(long userId);

}
