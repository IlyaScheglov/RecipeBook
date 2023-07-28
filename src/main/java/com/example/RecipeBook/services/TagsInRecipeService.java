package com.example.RecipeBook.services;

import com.example.RecipeBook.repos.TagsInRecipeRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagsInRecipeService {

    private final TagsInRecipeRepo tagsInRecipeRepo;

    private final TagsService tagsService;

    public TagsInRecipeService(TagsInRecipeRepo tagsInRecipeRepo, TagsService tagsService) {
        this.tagsInRecipeRepo = tagsInRecipeRepo;
        this.tagsService = tagsService;
    }

    public List<String> getTagsInRecipe(long recipeId){
        List<Long> allRecipeTagsId = tagsInRecipeRepo.findAllRecipeTagsId(recipeId);
        List<String> listToReturn = new ArrayList<>();
        for (var el : allRecipeTagsId){
            listToReturn.add(tagsService.getTagTitle(el));
        }
        return listToReturn;
    }
}
