package com.example.RecipeBook.controllers;

import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.Tags;
import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestControllerForEdit {

    private final RecipesService recipesService;

    private final TagsInRecipeService tagsInRecipeService;

    private final StepsService stepsService;

    private final IngridientsService ingridientsService;

    private final UsersService usersService;

    @GetMapping("/get_recipe_info_to_edit")
    public ResponseEntity getrecipeInfoToEdit(@RequestParam String recipeId){

        return ResponseEntity.ok(recipesService.getRecipeById(Long.parseLong(recipeId)));

    }

    @GetMapping("/get_recipe_tags_to_edit")
    public ResponseEntity<String> getRecipeTagsToEdit(@RequestParam String recipeId){
        List<String> tagsList = tagsInRecipeService.getTagsInRecipe(Long.parseLong(recipeId));
        String result = "";

        for (var el : tagsList){
            result += ("#" + el);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get_steps_to_edit")
    public ResponseEntity getStepsToEdit(@RequestParam String recipeId){

        return ResponseEntity.ok(stepsService.getStepsByRecipeId(Long.parseLong(recipeId)));

    }

    @GetMapping("/get_ingridients_to_edit")
    public ResponseEntity getIngridientsToEdit(@RequestParam String recipeId){

        return ResponseEntity.ok(ingridientsService.getIngridientsByRecipeId(Long.parseLong(recipeId)));

    }

    @PutMapping("/update_image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateImage(@RequestParam String recipeId, @RequestParam String path){

        recipesService.updateImage(Long.parseLong(recipeId), path);

    }

    @PutMapping("/edit_recipe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editRecipe(@RequestBody Recipes recipe, @RequestParam String tags){

        recipesService.updateRecipe(recipe);
        tagsInRecipeService.addNewTags(recipe.getId(), tags);
    }

    @GetMapping("/get_user_info_to_edit")
    public ResponseEntity getUserInfoToEdit(Principal principal){
        return ResponseEntity.ok(usersService.getUserByPrincipal(principal));
    }

    @PutMapping("/edit_user_profile")
    public ResponseEntity editUserProfile(@RequestParam String email, @RequestParam String name,
                                @RequestParam String oldPassword, @RequestParam String newPassword,
                                Principal principal){
        Users user = usersService.getUserByPrincipal(principal);
        boolean isEditSaved = usersService.editUserProfile(user, email, name, oldPassword, newPassword);

        return ResponseEntity.ok(isEditSaved);
    }

}
