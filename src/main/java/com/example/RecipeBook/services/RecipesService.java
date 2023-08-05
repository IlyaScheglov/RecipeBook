package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.repos.RecipesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipesService {

    private final RecipesRepo recipesRepo;

    private final TagsInRecipeService tagsInRecipeService;


    public List<Recipes> getAllRecipes(){
        return recipesRepo.findAll();
    }

    public List<Recipes> getRecipesByUserId(long userId){
        return recipesRepo.findByUserId(userId);
    }

    public int getCountOfUserRecipes(long userId){
        return recipesRepo.findByUserId(userId).size();
    }

    public Recipes getRecipeById(long recipeId){
        Optional<Recipes> recipes = recipesRepo.findById(recipeId);
        List<Recipes> recipesList = new ArrayList<>();
        recipes.ifPresent(recipesList::add);
        Recipes result = new Recipes();
        for (var el : recipesList){
            result = el;
        }
        return result;
    }

    public void addNewRecipe(String title, String description, String tags,
                             int time, int portion, String photoPath, long userId){



        if ((time > 0) && (portion > 0)){

            Recipes recipes = new Recipes();
            recipes.setTitle(title);
            recipes.setDescription(description);
            recipes.setCookingTimeMinutes(time);
            recipes.setPortions(portion);
            recipes.setDishImage(photoPath);
            recipes.setCategoryType(1L);
            recipes.setUserId(userId);
            recipesRepo.save(recipes);

            tagsInRecipeService.addNewTags(recipes, tags);
        }
    }

}
