package com.example.RecipeBook.controllers;

import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Controller
@RequiredArgsConstructor
public class ProfilePageController {

    private final UsersService usersService;

    private final RecipesService recipesService;

    private final LikesService likesService;

    private final FavouritesService favouritesService;

    private final TagsInRecipeService tagsInRecipeService;



    @RequestMapping("/profile_likes")
    private String likesOnProfile(Model model, Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        model.addAttribute("user", users);
        model.addAttribute("countOfMyRecipes", recipesService.getCountOfUserRecipes(users.getId()));
        model.addAttribute("countOfLikedRecipes", likesService.getCountOfUserLikes(users.getId()));
        model.addAttribute("countOfFavouriteRecipes", favouritesService.getCountOfUserFavourites(users.getId()));
        model.addAttribute("recipesList", likesService.getLikedRecipes(users.getId()));
        model.addAttribute("kindOfList", "Лайкнутые рецепты");
        model.addAttribute("usersService", usersService);
        model.addAttribute("likesService", likesService);
        model.addAttribute("favouritesService", favouritesService);
        model.addAttribute("tagsInRecipeService", tagsInRecipeService);
        return "profile";
    }

    @RequestMapping("/profile_favourite")
    private String favouritesOnProfile(Model model, Principal principal){
        Users users = usersService.getUserByPrincipal(principal);
        model.addAttribute("user", users);
        model.addAttribute("countOfMyRecipes", recipesService.getCountOfUserRecipes(users.getId()));
        model.addAttribute("countOfLikedRecipes", likesService.getCountOfUserLikes(users.getId()));
        model.addAttribute("countOfFavouriteRecipes", favouritesService.getCountOfUserFavourites(users.getId()));
        model.addAttribute("recipesList", favouritesService.getFavouriteRecipes(users.getId()));
        model.addAttribute("kindOfList", "Рецепты в избранном");
        model.addAttribute("usersService", usersService);
        model.addAttribute("likesService", likesService);
        model.addAttribute("favouritesService", favouritesService);
        model.addAttribute("tagsInRecipeService", tagsInRecipeService);
        return "profile";
    }

    @GetMapping("/addrecipe")
    private String addRecipe(Model model){
        return "add_recipe";
    }

    @PostMapping(value = "/add_new_recipe")
    private String addRecipe(Model model, Principal principal, @RequestParam MultipartFile image,
                             @RequestParam String title, @RequestParam String description,
                             @RequestParam String tags, @RequestParam int time,
                             @RequestParam int portions) throws IOException {
        Users user = usersService.getUserByPrincipal(principal);
        if (!image.isEmpty()) {
            if ((image.getSize() / (1024 * 1024)) <= 2){
                String filePath = "src/main/resources/static/img/" + image.getOriginalFilename();
                byte[] bytes = image.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(bytes);
                stream.close();
                recipesService.addNewRecipe(title,description,tags,time, portions,
                        "img/" + image.getOriginalFilename(), user.getId());
                return "redirect:/profile";
            }
            else{
                model.addAttribute("errorFileSize", true);
                return "add_recipe";
            }
        }
        else{
            model.addAttribute("errorNoFile", true);
            return "add_recipe";
        }
    }

}
