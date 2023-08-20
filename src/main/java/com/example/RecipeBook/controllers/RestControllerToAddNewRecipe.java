package com.example.RecipeBook.controllers;

import com.example.RecipeBook.entities.Ingridients;
import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.Steps;
import com.example.RecipeBook.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
public class RestControllerToAddNewRecipe {

    @Value("${upload.path}")
    private String uploadPath;

    private final IngridientsService ingridientsService;

    private final StepsService stepsService;

    private final UsersService usersService;

    private final RecipesService recipesService;

    private final TagsInRecipeService tagsInRecipeService;

    @GetMapping("/get_ingridients_to_add")
    public ResponseEntity getIngridientsToAdd(Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        return ResponseEntity.ok(ingridientsService.findByUserIdAndNullRecipeId(userId));
    }

    @GetMapping("/get_steps_to_add")
    public ResponseEntity getStepsToAdd(Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        return ResponseEntity.ok(stepsService.findStepsByUserIdAndNullRecipeId(userId));
    }

    @PostMapping("/add_new_ingridients")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNewIngridients(@RequestParam int countIngridients, Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        ingridientsService.addNewIngridients(userId, countIngridients);
    }

    @PostMapping("/add_new_steps")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNewSteps(@RequestParam int countSteps, Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        stepsService.addNewSteps(userId, countSteps);
    }

    @DeleteMapping("/delete_adding_ingridients")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddingIngridients(@RequestParam long ingId){
        ingridientsService.deleteIngridient(ingId);
    }

    @DeleteMapping("/delete_adding_steps")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddingSteps(@RequestParam long stepId){
        stepsService.deleteSteps(stepId);
    }

    @DeleteMapping("delete_excess_Ings_Steps")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExcessIngsSteps(Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        ingridientsService.deleteExcess(userId);
        stepsService.deleteExcess(userId);
    }

    @PostMapping(value = "/add_photo", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> addRecipe(@RequestParam("file") MultipartFile photo) throws IOException {
        if (!photo.isEmpty()){

                File uploadDir = new File(uploadPath);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "-" + photo.getOriginalFilename();

                photo.transferTo(new File(uploadPath + "/" + resultFileName));



                return ResponseEntity.ok("/img/" + resultFileName);
        }
        else{
            return ResponseEntity.ok("/img/nophoto.jpg");
        }
    }

    @PostMapping("/upload_recipe")
    public void addNewRecipe(@RequestBody Recipes recipes, @RequestParam String path,
                                             @RequestParam String tags, Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        long recipeId = recipesService.addRecipe(recipes,userId, path);

        tagsInRecipeService.addNewTags(recipeId, tags);

        ingridientsService.changeRecipeId(userId, recipeId);

        stepsService.changeRecipeId(userId, recipeId);
    }

    @PutMapping("/change_ing_title")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeIngTitle(@RequestParam long idOfIng, @RequestParam String titleOfIng){
        ingridientsService.editIngridientTitle(idOfIng, titleOfIng);
    }

    @PutMapping("/change_ing_list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeIngList(@RequestParam long idOfIng, @RequestParam String listOfIng){
        ingridientsService.editIngridientList(idOfIng, listOfIng);
    }

    @PutMapping("/change_steps_description")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStepsDescription(@RequestParam long idOfStep, @RequestParam String descOfStep){
        stepsService.editStepDescription(idOfStep, descOfStep);
    }


}
