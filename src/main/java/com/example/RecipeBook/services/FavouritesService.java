package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Favourites;
import com.example.RecipeBook.repos.FavouritesRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouritesService {

    private final FavouritesRepo favouritesRepo;

    public FavouritesService(FavouritesRepo favouritesRepo) {
        this.favouritesRepo = favouritesRepo;
    }

    public int getCountOfFavourites(long recipeId){
        List<Favourites> favouritesByRecipeId = favouritesRepo.findFavouritesByRecipeId(recipeId);
        return favouritesByRecipeId.size();
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
}