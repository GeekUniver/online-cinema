package com.online.cinema.controller;

import com.online.cinema.config.Const;
import com.online.cinema.exceptions.UserNotFoundException;
import com.online.cinema.persist.User;
import com.online.cinema.response.Response;
import com.online.cinema.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    protected UserService getUserService() {
        return userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<Boolean>> register(@Valid @RequestBody User model, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new Response<>(errors));
        }
        model.setUserRole(Const.ROLE_CLIENT);
        return ResponseEntity.ok(new Response<>(getUserService().saveOrUpdateUser(model) != null));
    }

    @GetMapping(params = {"email"})
    public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.getByEmail(email).isPresent());
    }

    @Secured({Const.ROLE_ADMIN, Const.ROLE_CLIENT})
    @PostMapping("/login")
    public ResponseEntity<Response<User>> login(@Valid @RequestBody User model, BindingResult result) throws UserNotFoundException {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new Response<>(errors));
        }
        User user = userService.getByEmail(model.getEmail()).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        return ResponseEntity.ok(new Response<>(user));
    }
}
