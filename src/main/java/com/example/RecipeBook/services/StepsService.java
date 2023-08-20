package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Ingridients;
import com.example.RecipeBook.entities.Steps;
import com.example.RecipeBook.repos.StepsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StepsService {

    private final StepsRepo stepsRepo;

    public List<Steps> findStepsByUserIdAndNullRecipeId(long userId){
        return stepsRepo.findStepsByUserIdAndNullRecipeId(userId, 0L);
    }

    public void addNewSteps(long userId, int number){
        Steps steps = new Steps();
        steps.setNumber(number + 1);
        steps.setUserId(userId);
        steps.setRecipeId(0L);
        stepsRepo.save(steps);
    }

    public void deleteSteps(long stepId){
        Steps steps = stepsRepo.findById(stepId);
        stepsRepo.delete(steps);
    }

    public void deleteExcess(long userId){
        List<Steps> stepsToDelete = findStepsByUserIdAndNullRecipeId(userId);
        if (stepsToDelete.size() > 0){
            for (var el : stepsToDelete){
                deleteSteps(el.getId());
            }
        }
    }

    public void editStepDescription(long id, String description){
        Steps steps = stepsRepo.findById(id);

        steps.setDescription(description);
        stepsRepo.save(steps);
    }

    public void changeRecipeId(long userId, long recipeId){
        List<Steps> stepsList = findStepsByUserIdAndNullRecipeId(userId);

        for (var el : stepsList){
            el.setRecipeId(recipeId);
            stepsRepo.save(el);
        }
    }

}
