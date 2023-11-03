package dev.cyan.travel.service;

import dev.cyan.travel.entity.Role;
import dev.cyan.travel.entity.User;
import dev.cyan.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    public User createUser(String username, String password, String firstName, String lastName, Role role) {
        return userRepository.insert(new User(username, password, firstName, lastName, role));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
