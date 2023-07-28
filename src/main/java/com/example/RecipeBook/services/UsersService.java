package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.repos.UsersRepo;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepo usersRepo;

    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public List<Users> getAllUsers(){
        return usersRepo.findAll();
    }

    public String getUserLoginByRecipe(long id){
        Optional<Users> users = usersRepo.findById(id);
        List<Users> usersList = new ArrayList<>();
        users.ifPresent(usersList::add);
        String loginToReturn = "";
        for (var el : usersList){
            loginToReturn = el.getLogin();
        }
        return loginToReturn;
    }

    public Users getUserByLogin(String login){
        return usersRepo.findByLogin(login);
    }

    public void addNewUser(String login, String password, String name, String email){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, 16, 32);
        String hashedPassword = argon2.hash(2, 8192, 1, password);
        Users users = new Users();
        users.setActive(true);
        users.setLogin(login);
        users.setName(name);
        users.setEmail(email);
        users.setPassword(hashedPassword);
        usersRepo.save(users);
    }
}
