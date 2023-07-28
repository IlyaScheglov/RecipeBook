package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouritesRepo extends JpaRepository<Favourites, Long> {

    @Query("SELECT f FROM Favourites f WHERE f.recipeId = :recId")
    List<Favourites> findFavouritesByRecipeId(@Param("recId") long recipeId);

    @Query("SELECT f FROM Favourites f WHERE f.userId = :usId AND f.recipeId = :recId")
    List<Favourites> favouriteOrNot(@Param("usId") long userId, @Param("recId") long recipeId);

    @Query("SELECT f FROM Favourites f WHERE f.userId = :usId AND f.recipeId = :recId")
    List<Favourites> findFavouriteIdByUserAndRecipeId(@Param("usId") long userId, @Param("recId") long recipeId);
}
