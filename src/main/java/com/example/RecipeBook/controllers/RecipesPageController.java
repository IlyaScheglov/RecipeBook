package com.example.RecipeBook.controllers;

import com.example.RecipeBook.services.FavouritesService;
import com.example.RecipeBook.services.LikesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class RecipesPageController {

    private final LikesService likesService;

    private final FavouritesService favouritesService;

    public RecipesPageController(LikesService likesService, FavouritesService favouritesServicel, FavouritesService favouritesService) {
        this.likesService = likesService;
        this.favouritesService = favouritesService;
    }

    @RequestMapping("/btn_like_clicked/{recipeId}/{userId}")
    private String likeButtonClicked(@PathVariable(value = "recipeId") long recipeId,
                                     @PathVariable(value = "userId") long userId,
                                     RedirectAttributes redirectAttributes,
                                     @RequestHeader(required = false) String referer){
        if (likesService.thisUserLikeRecipeOrNot(userId, recipeId)){
            likesService.deleteLike(userId, recipeId);
        }
        else{
            likesService.addLike(userId, recipeId);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }

    @RequestMapping("/btn_favourite_clicked/{recipeId}/{userId}")
    private String favouriteButtonClicked(@PathVariable(value = "recipeId") long recipeId,
                                          @PathVariable(value = "userId") long userId,
                                          RedirectAttributes redirectAttributes,
                                          @RequestHeader(required = false) String referer){

        if (favouritesService.isRecipeFavouriteForThisUser(userId, recipeId)){
            favouritesService.deleteFavourite(userId,recipeId);
        }
        else{
            favouritesService.addFavourite(userId, recipeId);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }
}
