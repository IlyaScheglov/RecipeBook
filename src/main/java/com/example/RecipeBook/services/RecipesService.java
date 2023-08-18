package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.RecipesToShow;
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

    private final UsersService usersService;

    private final LikesService likesService;

    private final FavouritesService favouritesService;


    public List<Recipes> getAllRecipes(){
        return recipesRepo.findAll();
    }

    public List<RecipesToShow> getAllRecipesToShow(Users user, String typeList){
        List<RecipesToShow> listToReturn = new ArrayList<>();
        List<Recipes> recipes = new ArrayList<>();
        if (typeList.equals("all")){
            recipes = recipesRepo.findAll();
        }
        else if (typeList.equals("own")){
            recipes = getOwnRecipes(user.getId());
        }
        else if (typeList.equals("liked")){
            recipes = getLikedRecipes(user.getId());
        }
        else if (typeList.equals("fav")){
            recipes = getFavouriteRecipes(user.getId());
        }

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

    public void addRecipe(Recipes recipes, long userId, String photoPath){
        recipes.setUserId(userId);
        recipes.setDishImage(photoPath);
        recipesRepo.save(recipes);


    }

}
