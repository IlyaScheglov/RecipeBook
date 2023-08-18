package com.example.RecipeBook.controllers;

import com.example.RecipeBook.entities.Ingridients;
import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.Steps;
import com.example.RecipeBook.services.IngridientsService;
import com.example.RecipeBook.services.RecipesService;
import com.example.RecipeBook.services.StepsService;
import com.example.RecipeBook.services.UsersService;
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

    @GetMapping("/get_ingridients_to_add")
    public ResponseEntity getIngridientsToAdd(Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        return ResponseEntity.ok(ingridientsService.findByUserIdAndNullListIngs(userId));
    }

    @GetMapping("/get_steps_to_add")
    public ResponseEntity getStepsToAdd(Principal principal){
        long userId = usersService.getUserByPrincipal(principal).getId();
        return ResponseEntity.ok(stepsService.findStepsByUserIdAndNullDescription(userId));
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
    public void addRecipe(@RequestParam("file") MultipartFile photo) throws IOException {

        if(!photo.isEmpty()){

                File uploadDir = new File(uploadPath);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "-" + photo.getOriginalFilename();

                photo.transferTo(new File(uploadPath + "/" + resultFileName));


        }
    }
}
