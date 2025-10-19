package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.CurvePointService;
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

public class CurvePointControllerTest {

    @Mock
    private CurvePointService curvePointService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private CurveController curveController;

    private CurvePoint curvePoint;

    @BeforeEach
    void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(2);
        curvePoint.setTerm(1.2);
        curvePoint.setValue(22.0);

        User user = new User();
        user.setId(1);
        user.setUsername("john");
        user.setPassword("John@123");
        user.setFullName("JohnDoe");
        user.setRole("USER");
        user.setEnabled(true);
    }

    @Test
    void home_ShouldReturnCurvePointView_AndAddAttributes() {
        //Authentication authentication, Model model
        when(authentication.getName()).thenReturn("john");
        when(curvePointService.findAll()).thenReturn(List.of());

        String viewName = curveController.home(authentication, model);

        assertEquals("curvePoint/list", viewName);
        verify(model).addAttribute("name", "Logged in as: john");
        verify(model).addAttribute("curvePoints", List.of());
        verify(curvePointService, times(1)).findAll();
    }

    @Test
    void testAddForm_ShouldShowAddForm() {
        String viewName = curveController.addCurveForm(model);
        assertEquals("curvePoint/add", viewName);
    }

    @Test
    void testValidate_ShouldReturnValidation() {
        //@Valid CurvePoint curvePoint, BindingResult result
        String viewName = curveController.validate(curvePoint, bindingResult);

        assertThat(viewName).isEqualTo("redirect:/curvePoint/list");
        verify(bindingResult, times(1)).hasErrors();
    }

    @Test
    void testValidate_ShouldReturnHasError(){
        //@Valid BidList bidList, BindingResult result
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = curveController.validate(curvePoint, bindingResult);

        assertThat(viewName).isEqualTo("curvePoint/add");
        verify(bindingResult, times(1)).hasErrors();
        verifyNoInteractions(curvePointService);
    }

    @Test
    void testUpdateForm_ShouldShowUpdateForm(){
        //@PathVariable("bidListId") Integer id, Model model
        when(curvePointService.findById(1)).thenReturn(curvePoint);
        String viewName = curveController.showUpdateForm(1, model);
        assertThat(viewName).isEqualTo("curvePoint/update");
    }

    @Test
    void testUpdateBid_ShouldReturnHasErrors() {
        //@PathVariable("bidListId") Integer id, @Valid BidList bidList, BindingResult result, Model model
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = curveController.updateCurvePoint(1, curvePoint, bindingResult, model);

        assertThat(viewName).isEqualTo("curvePoint/update");
    }


    @Test
    void testUpdateBid_ShouldReturnUpdated() {
        //@PathVariable("bidListId") Integer id, @Valid BidList bidList, BindingResult result, Model model
        when(bindingResult.hasErrors()).thenReturn(false);
        when(curvePointService.updateCurvePoint(1, curvePoint)).thenReturn(true);
        when(curvePointService.findAll()).thenReturn(List.of());

        String viewName = curveController.updateCurvePoint(1, curvePoint, bindingResult, model);

        assertThat(viewName).isEqualTo("redirect:/curvePoint/list");
        verify(curvePointService, times(1)).updateCurvePoint(1, curvePoint);
        verify(curvePointService, times(1)).findAll();
    }

    @Test
    void testDelete_ShouldReturnDeleted() {
        //@PathVariable("id") Integer id, Model model
        when(curvePointService.findAll()).thenReturn(List.of());

        String viewName = curveController.deleteCurvePoint(1, model);

        assertThat(viewName).isEqualTo("redirect:/curvePoint/list");
        verify(curvePointService, times(1)).deleteById(1);
        verify(curvePointService, times(1)).findAll();
    }

}
