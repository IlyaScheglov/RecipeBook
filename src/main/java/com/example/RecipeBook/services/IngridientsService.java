package com.example.RecipeBook.services;


import com.example.RecipeBook.entities.Ingridients;
import com.example.RecipeBook.repos.IngridientsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngridientsService {

    private final IngridientsRepo ingridientsRepo;

    public List<Ingridients> findByUserIdAndNullRecipeId(long userId){
        return ingridientsRepo.findByUserIdAndNullRecipeId(userId, 0L);
    }

    public void addNewIngridients(long userId, int number){
        Ingridients ingridients = new Ingridients();
        ingridients.setNumber(number + 1);
        ingridients.setUserId(userId);
        ingridients.setRecipeId(0L);
        ingridientsRepo.save(ingridients);
    }

    public void deleteIngridient(long ingId){
        Ingridients ingridients = ingridientsRepo.findById(ingId);
        ingridientsRepo.delete(ingridients);
    }

    public void deleteExcess(long userId){
        List<Ingridients> ingsToDelete = findByUserIdAndNullRecipeId(userId);
        if (ingsToDelete.size() > 0){
            for (var el : ingsToDelete){
                deleteIngridient(el.getId());
            }
        }
    }

    public void editIngridientTitle(long id, String title){
        Ingridients ingridients = ingridientsRepo.findById(id);

        ingridients.setTitle(title);
        ingridientsRepo.save(ingridients);
    }

    public void editIngridientList(long id, String list){
        Ingridients ingridients = ingridientsRepo.findById(id);

        ingridients.setListOfIngridients(list);
        ingridientsRepo.save(ingridients);
    }

    public void changeRecipeId(long userId, long recipeId){
        List<Ingridients> ingridientsList = findByUserIdAndNullRecipeId(userId);

        for (var el : ingridientsList){
            el.setRecipeId(recipeId);
            ingridientsRepo.save(el);
        }
    }

    public List<Ingridients> getIngridientsByRecipeId(long recipeId){
        return ingridientsRepo.findByRecipeId(recipeId);
    }

    public void deleteIngridientsInRecipe(long recipeId){
        List<Ingridients> ingridientsToDelete = ingridientsRepo.findByRecipeId(recipeId);
        for (var el : ingridientsToDelete){
            ingridientsRepo.delete(el);
        }
    }

}
