package com.example.RecipeBook.services;

import com.example.RecipeBook.config.WebSecurityConfig;
import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.Roles;
import com.example.RecipeBook.entities.UserRecipesCount;
import com.example.RecipeBook.entities.Users;
import com.example.RecipeBook.repos.UsersRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService{

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }

    private final UsersRepo usersRepo;


    public List<Users> getAllUsers(){
        return usersRepo.findAll();
    }

    public String getUserUsernameByRecipe(Recipes recipes){
        Optional<Users> users = usersRepo.findById(recipes.getUserId());
        List<Users> usersList = new ArrayList<>();
        users.ifPresent(usersList::add);
        String usernameToReturn = "";
        for (var el : usersList){
            usernameToReturn = el.getUsername();
        }
        return usernameToReturn;
    }

    public Users findByUsername(String username){
        return usersRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = findByUsername(username);
        if (users == null){
            new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username));
        }
        return new org.springframework.security.core.userdetails.User(
                users.getUsername(),
                users.getPassword(),
                users.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles.name())).collect(Collectors.toList())
        );
    }

    public void addNewUser(String username, String password, String name, String email){
        Users users = new Users();
        users.setActive(true);
        users.setRoles(Collections.singleton(Roles.USER));
        users.setUsername(username);
        users.setName(name);
        users.setEmail(email);
        users.setPassword(passwordEncoder().encode(password));
        usersRepo.save(users);
    }

    public Users getUserByPrincipal(Principal principal){
        if(principal == null) {
            return new Users();
        }
        return usersRepo.findByUsername(principal.getName());
    }

    public boolean checkPassword(Users users, String password){
        String hashedPassword = users.getPassword();
        return BCrypt.checkpw(password, hashedPassword);
    }

    public UserRecipesCount makeCountUserRecipes(int ownCount, int likedCount, int favouriteCount){
        UserRecipesCount userRecipesCount = new UserRecipesCount();
        userRecipesCount.setOwnCount(ownCount);
        userRecipesCount.setLikedCount(likedCount);
        userRecipesCount.setFavouriteCount(favouriteCount);
        return userRecipesCount;
    }


}
