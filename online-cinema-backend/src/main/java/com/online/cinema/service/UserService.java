package com.online.cinema.service;

import com.online.cinema.persist.Role;
import com.online.cinema.persist.User;
import com.online.cinema.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    //autowire через конструктор
    private final UserRepository userRepository;

    public User findByLogin(String login){
        //todo UserNotFoundException после того, как Рома разберётся с исключениями
        return userRepository.findByLogin(login).orElseThrow(()->new RuntimeException("Пользователь не найден" + login));
    }

    public User findByEmail(String email){
        //todo UserEmailNotFoundException после того, как Рома разберётся с исключениями
        return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Пользователь не найден" + email));
    }

}
