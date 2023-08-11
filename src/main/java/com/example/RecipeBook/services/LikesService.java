package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Likes;
import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.repos.LikesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepo likesRepo;



    public int getCountOfLikes(long recipeId){
        return likesRepo.findLikesByRecipeId(recipeId).size();
    }

    public int getCountOfUserLikes(long userId){
        return likesRepo.findByUserId(userId).size();
    }

    public boolean thisUserLikeRecipeOrNot(long userId, long recipeId){
        List<Likes> likesByUserOnRecipe = likesRepo.likedOrNot(userId, recipeId);
        if (likesByUserOnRecipe.size() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void deleteLike(long userId, long recipeId){
        List<Likes> likesToDelete = likesRepo.findIdOfLikeByUserAndRecipeId(recipeId, userId);
            Likes likes = new Likes();
            for (var el : likesToDelete) {
                likes = el;
            }
            likesRepo.delete(likes);
    }

    public void addLike(long userId, long recipeId){
        Likes likes = new Likes();
        likes.setUserId(userId);
        likes.setRecipeId(recipeId);
        likesRepo.save(likes);
    }


    public List<Long> getLikedRecipesId(long userId){
        List<Likes> likes = likesRepo.findByUserId(userId);
        List<Long> recipesIdToReturn = new ArrayList<>();
        for (var el : likes){
            recipesIdToReturn.add(el.getRecipeId());
        }
        return recipesIdToReturn;
    }

}
