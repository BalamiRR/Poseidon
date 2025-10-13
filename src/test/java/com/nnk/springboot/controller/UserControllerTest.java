package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;

    private List<User> mockUsers;

    private User user;
    private User updatedUser;
    private User inValid;
    private User dublicatedUser;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("john");
        user.setPassword("Admin@123");
        user.setFullName("JohnDoe");
        user.setRole("USER");
        user.setEnabled(true);

        User user1 = new User();
        user1.setId(1);
        user1.setUsername("adminn");
        user1.setFullName("Administrator");
        user1.setRole("ADMIN");
        user1.setEnabled(true);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("userA");
        user2.setFullName("Normal User");
        user2.setRole("USER");
        user2.setEnabled(true);

        updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setUsername("john");
        updatedUser.setPassword("NewPass@1");
        updatedUser.setFullName("John Doe");
        updatedUser.setRole("USER");

        dublicatedUser = new User();
        dublicatedUser.setId(1);
        dublicatedUser.setUsername("fuat");
        dublicatedUser.setPassword("NewPasss@1");
        dublicatedUser.setFullName("fuatkara");
        dublicatedUser.setRole("USER");


        inValid = new User();
        int id = 1;
        inValid.setId(id);
        inValid.setUsername("validUser");
        inValid.setPassword("abc");
        inValid.setRole("USER");

        mockUsers = Arrays.asList(user1, user2);
    }

    @Test
    void home_ShouldReturnUserListView_AndAddAttributes() {
        when(authentication.getName()).thenReturn("adminn");
        when(userService.findAll()).thenReturn(mockUsers);

        String viewName = userController.home(authentication, model);

        assertEquals("user/list", viewName);
        verify(model).addAttribute("name", "Logged in as: adminn");
        verify(model).addAttribute("users", mockUsers);
        verify(userService, times(1)).findAll();
    }

    @Test
    void testAddUser_ShouldRetunUserView() {
        String viewName = userController.addUser(new User());
        assertThat(viewName).isEqualTo("user/add");
    }

    @Test
    void testValidate_ShouldReturnIsValid() {
        when(userService.findByUsername("john")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userController.validate(user, bindingResult, model);

        assertThat(viewName).isEqualTo("redirect:/user/list");
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testValidate_ShouldReturnAlreadyExists() {
        when(userService.findByUsername("john")).thenReturn(new User());

        String viewName = userController.validate(user, bindingResult, model);

        assertThat(viewName).isEqualTo("user/add");
        verify(userService,never()).save(any(User.class));
        verify(model).addAttribute("duplicateError", true);
    }

    @Test
    void testValidate_ShouldReturnInValid(){
        user.setPassword("ab");
        when(userService.findByUsername("john")).thenReturn(null);

        String viewName = userController.validate(user, bindingResult, model);

        assertThat(viewName).isEqualTo("user/add");
        verify(model).addAttribute("patternError", true);
        verify(userService, never()).save(any(User.class));
    }

    @Test
    void testUpdateForm_ShouldReturnViewUpdateForm(){
        when(userService.findById(1)).thenReturn(user);

        String viewName = userController.showUpdateForm(1,model);

        assertThat(viewName).isEqualTo("user/update");
        assertThat(user.getPassword()).isEqualTo("");
        verify(model, times(1)).addAttribute("user", user);
        verify(userService, times(1)).findById(1);
    }

    @Test
    void testUpdateUser_ShouldReturnUpdateUserSuccessfully(){
        when(userService.findById(1)).thenReturn(user);
        when(userService.findByUsername("john")).thenReturn(user);
        when(bindingResult.hasErrors()).thenReturn(false);

        when(userService.updateUser(1, updatedUser)).thenReturn(true);
        when(userService.findAll()).thenReturn(List.of(updatedUser));

        String viewName = userController.updateUser(1, updatedUser, bindingResult ,model);

        assertThat(viewName).isEqualTo("redirect:/user/list");
        verify(userService).updateUser(1,updatedUser);
        verify(model, times(1)).addAttribute("users", List.of(updatedUser));
    }

    @Test
    void testUpdateUser_ShouldReturnIsInValid() {
        when(userService.findById(1)).thenReturn(user);
        when(userService.findByUsername("validUser")).thenReturn(null);

        String viewName = userController.updateUser(1, inValid, bindingResult, model);

        assertThat(viewName).isEqualTo("user/update");
        verify(userService, never()).updateUser(eq(1), any(User.class));
        verify(model).addAttribute(eq("patternError"), eq(true));
    }

    @Test
    void testUpdateUser_ShouldReturnDuplicateError() {
        when(userService.findById(1)).thenReturn(user);
        when(userService.findByUsername("fuat")).thenReturn(new User());

        String viewName = userController.updateUser(1, dublicatedUser, bindingResult, model);

        assertThat(viewName).isEqualTo("user/update");
        verify(userService, never()).updateUser(anyInt(), any(User.class));
        verify(model).addAttribute(eq("duplicateError"), eq(true));
    }

    @Test
    void testDelete_ShouldReturnDeletedUser(){
        //@PathVariable("id") Integer id, Model model
        when(userService.findById(1)).thenReturn(user);
        when(userService.findAll()).thenReturn(List.of());

        String viewName = userController.deleteUser(1, model);

        assertThat(viewName).isEqualTo("redirect:/user/list");
        verify(userService).delete(user);
        verify(userService).findAll();
        verifyNoMoreInteractions(userService);
    }
}