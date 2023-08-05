package com.example.RecipeBook.controllers;

import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class FirstPageController {

    private final UsersService usersService;

    private final RecipesService recipesService;

    private final LikesService likesService;

    private final FavouritesService favouritesService;

    private final TagsInRecipeService tagsInRecipeService;



    @GetMapping("/")
    private String map(Model model){
        return "first_page";
    }

    @GetMapping("/recipeslist")
    private String recipesPage(Model model, Principal principal){
        model.addAttribute("user", usersService.getUserByPrincipal(principal));
        model.addAttribute("recipesList", recipesService.getAllRecipes());
        model.addAttribute("usersService", usersService);
        model.addAttribute("likesService", likesService);
        model.addAttribute("favouritesService", favouritesService);
        model.addAttribute("tagsInRecipeService", tagsInRecipeService);
        return "recipes_page";
    }

    @GetMapping("/profile")
    private String profile(Model model, Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        model.addAttribute("user", users);
        model.addAttribute("countOfMyRecipes", recipesService.getCountOfUserRecipes(users.getId()));
        model.addAttribute("countOfLikedRecipes", likesService.getCountOfUserLikes(users.getId()));
        model.addAttribute("countOfFavouriteRecipes", favouritesService.getCountOfUserFavourites(users.getId()));
        model.addAttribute("recipesList", recipesService.getRecipesByUserId(users.getId()));
        model.addAttribute("kindOfList", "Мои рецепты");
        model.addAttribute("usersService", usersService);
        model.addAttribute("likesService", likesService);
        model.addAttribute("favouritesService", favouritesService);
        model.addAttribute("tagsInRecipeService", tagsInRecipeService);
        return "profile";
    }

    @GetMapping("/favourites")
    private String favourites(Model model, Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        model.addAttribute("user", users);
        model.addAttribute("recipesList", favouritesService.getFavouriteRecipes(users.getId()));
        model.addAttribute("usersService", usersService);
        model.addAttribute("likesService", likesService);
        model.addAttribute("favouritesService", favouritesService);
        model.addAttribute("tagsInRecipeService", tagsInRecipeService);
        return "favourites";
    }

}
