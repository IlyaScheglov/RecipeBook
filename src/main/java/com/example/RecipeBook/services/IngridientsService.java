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

    public List<Ingridients> findByUserIdAndNullListIngs(long userId){
        return ingridientsRepo.findByUserIdAndNullListOfIngs(userId, "");
    }

    public void addNewIngridients(long userId, int number){
        Ingridients ingridients = new Ingridients();
        ingridients.setNumber(number + 1);
        ingridients.setUserId(userId);
        ingridients.setListOfIngridients("");
        ingridientsRepo.save(ingridients);
    }

    public void deleteIngridient(long ingId){
        Ingridients ingridients = ingridientsRepo.findById(ingId);
        ingridientsRepo.delete(ingridients);
    }

    public void deleteExcess(long userId){
        List<Ingridients> ingsToDelete = findByUserIdAndNullListIngs(userId);
        if (ingsToDelete.size() > 0){
            for (var el : ingsToDelete){
                deleteIngridient(el.getId());
            }
        }
    }
}
