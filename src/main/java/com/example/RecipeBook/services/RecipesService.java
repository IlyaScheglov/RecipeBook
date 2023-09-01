package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Likes;
import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.RecipesToShow;
import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.repos.RecipesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RecipesService {

    private final RecipesRepo recipesRepo;

    private final TagsInRecipeService tagsInRecipeService;

    private final UsersService usersService;

    private final LikesService likesService;

    private final FavouritesService favouritesService;

    private final TagsService tagsService;


    public RecipesToShow findLikedOrFavRecipe(long recipeId, Users user){
        RecipesToShow result = new RecipesToShow();

        List<Recipes> recipes = new ArrayList<>();
        recipes.add(recipesRepo.findByRecipeId(recipeId));
        List<RecipesToShow> recipesToShow = refactorRecipesToRecToShow(recipes, user);

        for (var el : recipesToShow){
            result = el;
        }

        return result;
    }

    public List<RecipesToShow> getRecipesSortedByCategory(long categoryId, Users user, long lastElementId){
        List<Recipes> recipes = recipesRepo.findByCategoryTypeWithoutMapped(categoryId, lastElementId);
        return limitRecipeList(recipes, user);
    }

    public List<RecipesToShow> getRecipesSortedByTagsOrTitle(String enteredData, Users user, long lastElementId){
        if (!enteredData.equals("")) {
            long tagId = tagsService.getTagIdByTitle(enteredData);
            if (tagId != 0) {
                List<Long> recipeIdList = tagsInRecipeService.findRecipeIdByTagId(tagId);
                List<Recipes> recipes = new ArrayList<>();
                for (var el : recipeIdList) {
                    if (el > lastElementId){
                        recipes.add(recipesRepo.findByRecipeId(el));
                    }
                }
                return limitRecipeList(recipes, user);
            }
            else {
                List<Recipes> recipes = recipesRepo.findRecipeWhereTitleLikeWithoutMapped("%" + enteredData + "%", lastElementId);
                return limitRecipeList(recipes, user);
            }
        }
        else {
            return getAllRecipesToShow(user, "all", lastElementId);
        }
    }

    public List<RecipesToShow> getAllRecipesToShow(Users user, String typeList, long lastElementId){
        if (typeList.equals("all")){
            List<Recipes> recipes = recipesRepo.findAllWithoutMapped(lastElementId);
            return limitRecipeList(recipes, user);
        }
        else if (typeList.equals("own")){
            return refactorRecipesToRecToShow(getOwnRecipes(user.getId()), user);
        }
        else if (typeList.equals("liked")){
            return refactorRecipesToRecToShow(getLikedRecipes(user.getId()), user);
        }
        else if (typeList.equals("fav")){
            return refactorRecipesToRecToShow(getFavouriteRecipes(user.getId()), user);
        }
        else {
            return null;
        }

    }

    private List<RecipesToShow> limitRecipeList(List<Recipes> recipes, Users user){
        if (recipes.size() > 3){
            List<Recipes> limitedListRecipes = recipes.stream().limit(4).toList();
            return refactorRecipesToRecToShow(limitedListRecipes, user);
        }
        else {
            return refactorRecipesToRecToShow(recipes, user);
        }
    }


    private List<RecipesToShow> refactorRecipesToRecToShow(List<Recipes> recipes, Users user){
        List<RecipesToShow> listToReturn = new ArrayList<>();

        for (var el : recipes){
            RecipesToShow recShow = new RecipesToShow();
            recShow.setId(el.getId());
            recShow.setTitle(el.getTitle());
            recShow.setDishImage(el.getDishImage());
            recShow.setDescription(el.getDescription());
            recShow.setCookingTimeMinutes(el.getCookingTimeMinutes());
            recShow.setPortions(el.getPortions());
            recShow.setUserUsername(usersService.getUserUsernameByRecipe(el));
            recShow.setListTags(tagsInRecipeService.getTagsInRecipe(el.getId()));
            recShow.setCountLikes(likesService.getCountOfLikes(el.getId()));
            recShow.setCountFavourites(favouritesService.getCountOfFavourites(el.getId()));
            recShow.setLikedByUser(likesService.thisUserLikeRecipeOrNot(user.getId(), el.getId()));
            recShow.setFavouriteByUser(favouritesService.isRecipeFavouriteForThisUser(user.getId(), el.getId()));

            listToReturn.add(recShow);
        }

        return listToReturn;
    }


    private List<Recipes> getOwnRecipes(long userId){
        return recipesRepo.findByUserId(userId);
    }

    private List<Recipes> getLikedRecipes(long userId){
        List<Long> recipeIdList = likesService.getLikedRecipesId(userId);
        List<Recipes> recipesToReturn = new ArrayList<>();
        for (var el : recipeIdList){
            recipesToReturn.add(getRecipeById(el));
        }
        return recipesToReturn;
    }

    private List<Recipes> getFavouriteRecipes(long userId){
        List<Long> recipeIdList = favouritesService.getFavouriteRecipesId(userId);
        List<Recipes> recipesToReturn = new ArrayList<>();
        for (var el : recipeIdList){
            recipesToReturn.add(getRecipeById(el));
        }
        return recipesToReturn;
    }

    public List<Recipes> getRecipesByUserId(long userId){
        return recipesRepo.findByUserId(userId);
    }

    public int getCountOfUserRecipes(long userId){
        return recipesRepo.findByUserId(userId).size();
    }

    public Recipes getRecipeById(long recipeId){
        return recipesRepo.findByRecipeId(recipeId);
    }

    public boolean isThisRecipeByThisUser(Recipes recipes, long userId){
        if (userId == recipes.getUserId()){
            return true;
        }
        else{
            return false;
        }
    }

    public Long addRecipe(Recipes recipes, long userId, String photoPath){
        recipes.setUserId(userId);
        recipes.setDishImage(photoPath);
        recipesRepo.save(recipes);
        return recipes.getId();
    }

    public void deleteRecipe(Recipes recipes){
        recipesRepo.delete(recipes);
    }

    public void updateImage(long recipeId, String path){
        Recipes recipe = getRecipeById(recipeId);
        recipe.setDishImage(path);
        recipesRepo.save(recipe);
    }

    public void updateRecipe(Recipes recipes){
        recipesRepo.save(recipes);
    }

    public Recipes findMostLikedRecipeLastWeek(List<Long> lastWeekLiked){
        if(lastWeekLiked.size() != 0){
            long mostLikedId = 0L;
            int countLikesOnRecipe = 0;

            for (var el : lastWeekLiked){
                int countDublicates = Collections.frequency(lastWeekLiked, el);
                if (countDublicates > countLikesOnRecipe){
                    mostLikedId = el;
                    countLikesOnRecipe = countDublicates;
                }
            }

            return recipesRepo.findByRecipeId(mostLikedId);

        }
        else{
            List<Recipes> allRecipes = recipesRepo.findAll();
            Random random = new Random();
            return allRecipes.get(random.nextInt(allRecipes.size() - 1));
        }
    }

}
