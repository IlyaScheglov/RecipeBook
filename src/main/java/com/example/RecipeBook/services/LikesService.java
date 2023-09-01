package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Likes;
import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.repos.LikesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

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
            for (var el : likesToDelete) {
                likesRepo.delete(el);
            }
    }

    public void addLike(long userId, long recipeId){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Likes likes = new Likes();
        likes.setUserId(userId);
        likes.setRecipeId(recipeId);
        likes.setDateOfLike(timestamp);
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

    public void deleteAllRecipeLikes(long recipeId){

        List<Likes> likesToDelete = likesRepo.findLikesByRecipeId(recipeId);

        for (var el : likesToDelete){
            likesRepo.delete(el);
        }

    }

    public List<Long> findLastWeekLiked(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        return likesRepo.findLastWeekLiked(timestamp);
    }

}
