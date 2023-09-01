package com.example.RecipeBook.controllers;

import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.RecipesToShow;
import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.services.FavouritesService;
import com.example.RecipeBook.services.LikesService;
import com.example.RecipeBook.services.RecipesService;
import com.example.RecipeBook.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestControllerForAll {

    private final RecipesService recipesService;

    private final LikesService likesService;

    private final FavouritesService favouritesService;

    private final UsersService usersService;



    @GetMapping("/get_recipes")
    public ResponseEntity getAllRecipes(@RequestParam String typeList, @RequestParam String enteredData,
                                        @RequestParam long lastElementId, Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        List<RecipesToShow> recipesToShow = new ArrayList<>();
        if (typeList.equals("allRecipes")){
            recipesToShow = recipesService.getAllRecipesToShow(users, "all", lastElementId);
            return ResponseEntity.ok(checkRecipesSize(recipesToShow));
        }
        else if(typeList.equals("filterByCategory")){
            long categoryId = Long.parseLong(enteredData);
            recipesToShow = recipesService.getRecipesSortedByCategory(categoryId, users, lastElementId);
            return ResponseEntity.ok(checkRecipesSize(recipesToShow));
        }
        else if(typeList.equals("filterByTagsOrTitle")){
            recipesToShow = recipesService.getRecipesSortedByTagsOrTitle(enteredData, users, lastElementId);
            return ResponseEntity.ok(checkRecipesSize(recipesToShow));
        }
        else{
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    private List<Object> checkRecipesSize(List<RecipesToShow> recipesToShows){
        List<Object> result = new ArrayList<>();
        if (recipesToShows.size() > 3){
            List<RecipesToShow> limitedListRecipes = recipesToShows.stream().limit(3).toList();
            result.addAll(limitedListRecipes);
            result.add(true);
            return result;
        }
        else{
            result.addAll(recipesToShows);
            result.add(false);
            return result;
        }
    }


    @GetMapping("/find_liked_or_fav_recipe")
    public ResponseEntity findLikedOrFavRecipe(@RequestParam long recipeId, Principal principal){
        Users user = usersService.getUserByPrincipal(principal);
        return ResponseEntity.ok(recipesService.findLikedOrFavRecipe(recipeId, user));
    }


    @GetMapping("/get_favourite_recipes")
    public ResponseEntity getFavouriteRecipes(Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        return ResponseEntity.ok(recipesService.getAllRecipesToShow(users, "fav", 0L));
    }

    @GetMapping("/get_recipes_in_profile")
    public ResponseEntity getRecipesInProfile(@RequestParam String typeList, Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        return ResponseEntity.ok(recipesService.getAllRecipesToShow(users, typeList, 0L));
    }



    @GetMapping("/get_count_of_recipes")
    public ResponseEntity getCountUserRecipes(Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        int ownCount = recipesService.getCountOfUserRecipes(users.getId());
        int likedCount = likesService.getCountOfUserLikes(users.getId());
        int favouriteCount = favouritesService.getCountOfUserFavourites(users.getId());
        int[] userCountArray = {ownCount, likedCount, favouriteCount};
        return ResponseEntity.ok(userCountArray);
    }

    @PostMapping("/like_recipe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void likeRecipe(@RequestParam long recipeId, Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        likesService.addLike(userId, recipeId);
    }

    @DeleteMapping("/dislike_recipe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dislikeRecipe(@RequestParam long recipeId, Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        likesService.deleteLike(userId, recipeId);
    }

    @PostMapping("/favourite_recipe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void favouriteRecipe(@RequestParam long recipeId, Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        favouritesService.addFavourite(userId, recipeId);
    }

    @DeleteMapping("/not_favourite_recipe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void notFavouriteRecipe(@RequestParam long recipeId, Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        favouritesService.deleteFavourite(userId, recipeId);
    }

}
