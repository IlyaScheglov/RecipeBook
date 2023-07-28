package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.repos.RecipesRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipesService {

    private final RecipesRepo recipesRepo;

    public RecipesService(RecipesRepo recipesRepo) {
        this.recipesRepo = recipesRepo;
    }

    public List<Recipes> getAllRecipes(){
        return recipesRepo.findAll();
    }


    public void getRecipesWithLogin(){
        List<Recipes> recipes = recipesRepo.getRecipesWithUserLogin();
        for (var el : recipes){
            System.out.println(el);
        }
    }

}
