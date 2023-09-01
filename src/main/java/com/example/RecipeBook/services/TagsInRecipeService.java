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

    public void addNewTags(long recipeId, String tags){
        if (!tags.equals(null)) {
            String[] tagsArray = tags.split("#");

            if (tagsArray.length != 0) {
                List<String> tagsToDb = new ArrayList<>();
                for (int i = 0; i < tagsArray.length; i++) {
                    if (tagsArray[i] != "") {
                        tagsToDb.add(tagsArray[i]);
                    }
                }

                for (var el : tagsToDb) {
                    if (tagsService.getTagIdByTitle(el) != 0) {
                        TagsInRecipe tagsInRecipe = new TagsInRecipe();
                        tagsInRecipe.setRecipeId(recipeId);
                        tagsInRecipe.setTagId(tagsService.getTagIdByTitle(el));
                        tagsInRecipeRepo.save(tagsInRecipe);
                    }
                    else {
                        tagsService.addNewTag(el);
                        TagsInRecipe tagsInRecipe = new TagsInRecipe();
                        tagsInRecipe.setRecipeId(recipeId);
                        tagsInRecipe.setTagId(tagsService.getTagIdByTitle(el));
                        tagsInRecipeRepo.save(tagsInRecipe);
                    }
                }
            }
        }

    }

    public void deleteTagsInRecipe(long recipeId){
        List<TagsInRecipe> tagsInRecipesToDelete = tagsInRecipeRepo.findByRecipeId(recipeId);
        for (var el : tagsInRecipesToDelete){
            tagsInRecipeRepo.delete(el);
        }
    }

    public List<Long> findRecipeIdByTagId(long tagId){
        return tagsInRecipeRepo.findRecipeIdByTagId(tagId);
    }

}
