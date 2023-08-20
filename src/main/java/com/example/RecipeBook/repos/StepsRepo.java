package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Steps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StepsRepo extends JpaRepository<Steps, Long> {

    @Query("SELECT s FROM Steps s WHERE s.userId = :usId AND s.recipeId = :recId")
    List<Steps> findStepsByUserIdAndNullRecipeId(@Param("usId") long userId, @Param("recId") long recipeId);

    Steps findById(long id);
}
