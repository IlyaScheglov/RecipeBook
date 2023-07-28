package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Likes;
import com.example.RecipeBook.repos.LikesRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesService {

    private final LikesRepo likesRepo;

    public LikesService(LikesRepo likesRepo) {
        this.likesRepo = likesRepo;
    }

    public int getCountOfLikes(long recipeId){
        List<Likes> likesByRecipeId = likesRepo.findLikesByRecipeId(recipeId);
        return likesByRecipeId.size();
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
}
