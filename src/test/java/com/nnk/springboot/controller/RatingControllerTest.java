package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private RatingController ratingController;

    private Rating rating;

    @BeforeEach
    void setUp() {
        rating = new Rating();
        rating.setMoodysRating("Moodys");
        rating.setSandPRating("SandP");
        rating.setFitchRating("Fitch");
        rating.setOrderNumber(22);

        User user = new User();
        user.setId(1);
        user.setUsername("john");
        user.setPassword("John@123");
        user.setFullName("JohnDoe");
        user.setRole("USER");
        user.setEnabled(true);
    }

    @Test
    void home_ShouldReturnUserBidListView_AndAddAttributes() {
        //Authentication authentication, Model model
        when(authentication.getName()).thenReturn("john");
        when(ratingService.findAll()).thenReturn(List.of());

        String viewName = ratingController.home(authentication, model);

        assertEquals("rating/list", viewName);
        verify(model).addAttribute("name", "Logged in as: john");
        verify(model).addAttribute("ratings", List.of());
        verify(ratingService, times(1)).findAll();
    }

    @Test
    void testAddBidForm_ShouldRetunBidListView() {
        String viewName = ratingController.addRatingForm(rating);

        assertEquals("rating/add", viewName);
        verifyNoMoreInteractions(model);
    }

    @Test
    void testValidate_ShouldReturnHasError(){
        //@Valid BidList bidList, BindingResult result
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ratingController.validate(rating, bindingResult);

        assertThat(viewName).isEqualTo("rating/add");
        verify(bindingResult, times(1)).hasErrors();
        verifyNoInteractions(ratingService);
    }

    @Test
    void testValidate_ShouldReturnValidation(){
        //@PathVariable("bidListId") Integer id, Model model
        String viewName = ratingController.validate(rating, bindingResult);

        assertThat(viewName).isEqualTo("redirect:/rating/list");
        verify(bindingResult, times(1)).hasErrors();
    }

    @Test
    void testUpdateForm_Should(){
        //@PathVariable("bidListId") Integer id, Model model
        when(ratingService.findById(1)).thenReturn(rating);
        String viewName = ratingController.showUpdateForm(1, model);
        assertThat(viewName).isEqualTo("rating/update");
    }

    @Test
    void testUpdateBid_ShouldReturnHasErrors() {
        //@PathVariable("bidListId") Integer id, @Valid BidList bidList, BindingResult result, Model model
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ratingController.updateRating(1, rating, bindingResult, model);

        assertThat(viewName).isEqualTo("rating/update");
    }

}
