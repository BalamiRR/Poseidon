package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    User user = new User();

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void initsUser() {
        user.setUsername("Fuat");
        user.setPassword("Abc123@");
        user.setFullName("FuatKara");
        user.setRole("USER");
        userService.save(user);
        user = userRepository.findByUsername("Fuat");
    }

    @Test
    public void test_updateUser(){
        User existingUser = userRepository.findByUsername("Fuat");
        Integer id = existingUser.getId();
        existingUser.setUsername("Furkan");
        existingUser.setPassword("Abcd123@");
        Boolean updated = userService.updateUser(id, existingUser);
        assertTrue(updated);
    }

    @Test
    public void test_deleteUser(){
        Integer id = user.getId();
        User found = userService.findById(id);
        userService.delete(found);
        User deletedId = userService.findById(id);
        assertNull(deletedId);
    }

}