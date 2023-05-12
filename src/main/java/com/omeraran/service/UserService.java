package com.omeraran.service;

import com.omeraran.exception.UserNotFoundException;
import com.omeraran.model.User;
import com.omeraran.model.UserRequest;
import com.omeraran.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("not found"));
    }

    public User createUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .mail(userRequest.getMail())
                .role(userRequest.getRole())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(UserRequest userRequest) {
        User existUser = getUserById(userRequest.getId());
        existUser.setUsername(userRequest.getUsername());
        existUser.setMail(userRequest.getMail());
        existUser.setRole(userRequest.getRole());
        return userRepository.save(existUser);
    }

    public void deleteUser(Long id) {
        User existUser = getUserById(id);
        userRepository.delete(existUser);
    }
}
