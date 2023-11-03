package dev.cyan.travel.controller.impl;

import dev.cyan.travel.controller.IController;
import dev.cyan.travel.entity.User;
import dev.cyan.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements IController<User> {
    @Autowired
    private UserRepository userRepository;

    //TODO write methods
    @Override
    public ResponseEntity<List<User>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<User> getById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<User> create(User user) {
        return null;
    }

    @Override
    public ResponseEntity<User> update(String id, User user) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        return null;
    }
}
