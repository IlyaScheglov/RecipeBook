package com.example.RecipeBook.controllers;

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

@RestController
@RequiredArgsConstructor
public class RestControllerForAll {

    private final RecipesService recipesService;

    private final LikesService likesService;

    private final FavouritesService favouritesService;

    private final UsersService usersService;



    @GetMapping("/get_recipes")
    public ResponseEntity getAllRecipes(Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        return ResponseEntity.ok(recipesService.getAllRecipesToShow(users, "all"));
    }



    @GetMapping("/get_favourite_recipes")
    public ResponseEntity getFavouriteRecipes(Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        return ResponseEntity.ok(recipesService.getAllRecipesToShow(users, "fav"));
    }

    @GetMapping("/get_recipes_in_profile")
    public ResponseEntity getRecipesInProfile(@RequestParam String typeList, Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        return ResponseEntity.ok(recipesService.getAllRecipesToShow(users, typeList));
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
