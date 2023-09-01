package com.example.RecipeBook.controllers;

import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;



@Controller
@RequiredArgsConstructor
public class AdditionalPagesController {

    private final RecipesService recipesService;

    private final UsersService usersService;

    private final CategoriesService categoriesService;

    private final TagsInRecipeService tagsInRecipeService;

    private final IngridientsService ingridientsService;

    private final StepsService stepsService;

    private final LikesService likesService;

    private final FavouritesService favouritesService;

    @GetMapping("/recipeslist/addrecipe")
    private String addRecipe(Model model){
        return "add_recipe";
    }

    @GetMapping("/recipeslist/info/{id}")
    private String showRecipeInfo(Model model, @PathVariable("id") long recipeId, Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        Recipes recipe = recipesService.getRecipeById(recipeId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeByThisUser", recipesService.isThisRecipeByThisUser(recipe, userId));
        model.addAttribute("category", categoriesService.getCategoryById(recipe.getCategoryType()));
        model.addAttribute("tags", tagsInRecipeService.getTagsInRecipe(recipeId));
        model.addAttribute("listIngridients", ingridientsService.getIngridientsByRecipeId(recipeId));
        model.addAttribute("listSteps", stepsService.getStepsByRecipeId(recipeId));
        return "recipe_info";
    }

    @RequestMapping("/recipeslist/info/{id}/delete")
    private String deleteRecipe(Model model, @PathVariable("id") long recipeId){
        Recipes recipeToDelete = recipesService.getRecipeById(recipeId);
        recipesService.deleteRecipe(recipeToDelete);
        tagsInRecipeService.deleteTagsInRecipe(recipeId);
        ingridientsService.deleteIngridientsInRecipe(recipeId);
        stepsService.deleteStepsInRecipe(recipeId);
        likesService.deleteAllRecipeLikes(recipeId);
        favouritesService.deleteAllRecipeFavourites(recipeId);
        return "redirect:/profile";
    }

    @GetMapping("/recipeslist/info/{id}/edit")
    private String editRecipePage(Model model, @PathVariable("id") long recipeId){
        model.addAttribute("recipeId", recipeId);
        return "edit_recipe_page";
    }

    @GetMapping("/profile/edit_user")
    private String editUserInfo(Model model){
        return "edit_profile_page";
    }
}
