package com.example.RecipeBook.controllers;

import com.example.RecipeBook.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstPageController {

    private final UsersService usersService;

    private final RecipesService recipesService;

    private final LikesService likesService;

    private final FavouritesService favouritesService;

    private final TagsInRecipeService tagsInRecipeService;

    public FirstPageController(UsersService usersService, RecipesService recipesService, LikesService likesService, FavouritesService favouritesService, TagsInRecipeService tagsInRecipeService) {
        this.usersService = usersService;
        this.recipesService = recipesService;
        this.likesService = likesService;
        this.favouritesService = favouritesService;
        this.tagsInRecipeService = tagsInRecipeService;
    }

    @GetMapping("/")
    private String map(Model model){
        return "first_page";
    }

    @GetMapping("/recipeslist")
    private String recipesPage(Model model){
        recipesService.getRecipesWithLogin();
        model.addAttribute("recipesList", recipesService.getAllRecipes());
        model.addAttribute("usId", 1L);
        model.addAttribute("usersService", usersService);
        model.addAttribute("likesService", likesService);
        model.addAttribute("favouritesService", favouritesService);
        model.addAttribute("tagsInRecipeService", tagsInRecipeService);
        return "recipes_page";
    }



}
