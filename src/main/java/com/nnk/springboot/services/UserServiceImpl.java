package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            logger.info("Query to find User with username " + username);
            return user;
        } else {
            logger.error("Failed to find User with username " + username);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("Query to find User with id " + id);
            return optionalUser.get();
        } else {
            logger.error("Failed to find User with id " + id);
        }
        return null;
    }

    @Override
    public Boolean updateUser(int id, User user) {
        boolean updated = false;
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User newUser = optionalUser.get();
            newUser.setFull_name(user.getFull_name());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(encoder.encode(user.getPassword()));
            newUser.setRole(user.getRole());
            userRepository.save(newUser);
            updated = true;
            logger.info("User with id " + id + " is updated as " + newUser);
        } else {
            logger.error("Failed to update User with id " + id + " as " + user);
        }
        return updated;
    }

    @Override
    public void save(User user) {
        User newUser = new User();
        newUser.setFull_name(user.getFull_name());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole(user.getRole() == null ? "USER" : user.getRole());

        userRepository.saveAndFlush(newUser);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
        logger.info("User " + user + " is deleted!");
    }
}
