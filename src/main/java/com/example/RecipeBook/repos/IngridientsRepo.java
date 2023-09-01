package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Ingridients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngridientsRepo extends JpaRepository<Ingridients,Long> {

    @Query("SELECT i FROM Ingridients i WHERE i.userId = :usId AND i.recipeId = :recId")
    List<Ingridients> findByUserIdAndNullRecipeId(@Param("usId") long userId, @Param("recId") long recipeId);

    Ingridients findById(long id);

    List<Ingridients> findByRecipeId(long recipeId);
}
