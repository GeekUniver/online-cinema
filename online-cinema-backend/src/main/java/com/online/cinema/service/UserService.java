package com.online.cinema.service;

import com.online.cinema.exceptions.UserNotFoundException;
import com.online.cinema.persist.User;
import com.online.cinema.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByLogin(String login) throws UsernameNotFoundException, UserNotFoundException {
        //todo UserNotFoundException после того, как Рома разберётся с исключениями
        return userRepository.findByLogin(login).orElseThrow(()->new UserNotFoundException("Пользователь не найден" + login));
    }

    public User findByEmail(String email){
        //todo UserEmailNotFoundException после того, как Рома разберётся с исключениями
        return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Пользователь не найден" + email));
    }

    public Optional<User> getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User saveOrUpdateUser(User user) {
        if (!user.getPassword().startsWith("{bcrypt}")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return this.userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
