package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Favourites;
import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.repos.FavouritesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouritesService {

    private final FavouritesRepo favouritesRepo;



    public int getCountOfFavourites(long recipeId){
        return favouritesRepo.findFavouritesByRecipeId(recipeId).size();
    }

    public int getCountOfUserFavourites(long userId){
        return favouritesRepo.findByUserId(userId).size();
    }

    public boolean isRecipeFavouriteForThisUser(long userId, long recipeId){
        List<Favourites> favouritesRecipesForUser = favouritesRepo.favouriteOrNot(userId, recipeId);
        if (favouritesRecipesForUser.size() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public void addFavourite(long userId, long recipeId){
        Favourites favourites = new Favourites();
        favourites.setUserId(userId);
        favourites.setRecipeId(recipeId);
        favouritesRepo.save(favourites);
    }

    public void deleteFavourite(long userId, long recipeId){
        List<Favourites> favouritesIdList = favouritesRepo.findFavouriteIdByUserAndRecipeId(userId, recipeId);
        Favourites favourites = new Favourites();
        for(var el : favouritesIdList){
            favourites = el;
        }
        favouritesRepo.delete(favourites);
    }


    public List<Long> getFavouriteRecipesId(long userId){
        List<Favourites> favourites = favouritesRepo.findByUserId(userId);
        List<Long> recipesIdToReturn = new ArrayList<>();
        for (var el : favourites){
            recipesIdToReturn.add(el.getRecipeId());
        }
        return recipesIdToReturn;
    }

}
