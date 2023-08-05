package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.TagsInRecipe;
import com.example.RecipeBook.repos.TagsInRecipeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagsInRecipeService {

    private final TagsInRecipeRepo tagsInRecipeRepo;

    private final TagsService tagsService;



    public List<String> getTagsInRecipe(long recipeId){
        List<Long> allRecipeTagsId = tagsInRecipeRepo.findAllRecipeTagsId(recipeId);
        List<String> listToReturn = new ArrayList<>();
        for (var el : allRecipeTagsId){
            listToReturn.add(tagsService.getTagTitle(el));
        }
        return listToReturn;
    }

    public void addNewTags(Recipes recipes, String tags){
        String[] tagsArray = tags.split("#");

        if (tagsArray.length != 0){
            List<String> tagsToDb = new ArrayList<>();
            for(int i = 0; i > tagsArray.length; i++){
                if (tagsArray[i] != ""){
                    tagsToDb.add(tagsArray[i]);
                }
            }
            for(var el : tagsToDb){
                if(tagsService.getTagIdByTitle(el) != 0){
                    TagsInRecipe tagsInRecipe = new TagsInRecipe();
                    tagsInRecipe.setRecipeId(recipes.getId());
                    tagsInRecipe.setTagId(tagsService.getTagIdByTitle(el));
                    tagsInRecipeRepo.save(tagsInRecipe);
                }
                else{
                    tagsService.addNewTag(el);
                    TagsInRecipe tagsInRecipe = new TagsInRecipe();
                    tagsInRecipe.setRecipeId(recipes.getId());
                    tagsInRecipe.setTagId(tagsService.getTagIdByTitle(el));
                    tagsInRecipeRepo.save(tagsInRecipe);
                }
            }
        }
    }


}
