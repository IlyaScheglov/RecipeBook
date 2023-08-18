package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Steps;
import com.example.RecipeBook.repos.StepsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StepsService {

    private final StepsRepo stepsRepo;

    public List<Steps> findStepsByUserIdAndNullDescription(long userId){
        return stepsRepo.findStepsByUserIdAndNullDescription(userId, "");
    }

    public void addNewSteps(long userId, int number){
        Steps steps = new Steps();
        steps.setNumber(number + 1);
        steps.setUserId(userId);
        steps.setDescription("");
        stepsRepo.save(steps);
    }

    public void deleteSteps(long stepId){
        Steps steps = stepsRepo.findById(stepId);
        stepsRepo.delete(steps);
    }

    public void deleteExcess(long userId){
        List<Steps> stepsToDelete = findStepsByUserIdAndNullDescription(userId);
        if (stepsToDelete.size() > 0){
            for (var el : stepsToDelete){
                deleteSteps(el.getId());
            }
        }
    }

}
