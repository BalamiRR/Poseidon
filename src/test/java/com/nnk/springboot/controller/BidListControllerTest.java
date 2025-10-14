package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListControllerTest {

    @Mock
    private BidListService bidListService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private BidListController bidListController;

    private BidList bidList;

    @BeforeEach
    void setUp() {
        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Account Test");
        bidList.setType("Type");
        bidList.setBidQuantity(11.0);
    }

    @Test
    void home_ShouldReturnUserBidListView_AndAddAttributes() {
        //Authentication authentication, Model model
        when(authentication.getName()).thenReturn("john");
        when(bidListService.findAll()).thenReturn(List.of());

        String viewName = bidListController.home(authentication, model);

        assertEquals("bidList/list", viewName);
        verify(model).addAttribute("name", "Logged in as: john");
        verify(model).addAttribute("bidLists", List.of());
        verify(bidListService, times(1)).findAll();
    }

    @Test
    void testAddBidForm_ShouldRetunBidListView() {
        String viewName = bidListController.addBidForm(model);

        assertEquals("bidList/add", viewName);
        verify(model).addAttribute(eq("bidList"), any(BidList.class));
        verifyNoMoreInteractions(model);
    }

    @Test
    void testValidate_ShouldReturnHasError(){
        //@Valid BidList bidList, BindingResult result
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = bidListController.validate(bidList, bindingResult);

        assertThat(viewName).isEqualTo("bidList/add");
        verify(bindingResult, times(1)).hasErrors();
        verifyNoInteractions(bidListService);
    }

    @Test
    void testValidate_ShouldReturnValidation(){
        //@PathVariable("bidListId") Integer id, Model model
        String viewName = bidListController.validate(bidList, bindingResult);

        assertThat(viewName).isEqualTo("redirect:/bidList/list");
        verify(bindingResult, times(1)).hasErrors();
    }

    @Test
    void testUpdateForm_Should(){
        //@PathVariable("bidListId") Integer id, Model model
        when(bidListService.findById(1)).thenReturn(bidList);
        String viewName = bidListController.showUpdateForm(1, model);
        assertThat(viewName).isEqualTo("bidList/update");
    }

    @Test
    void testUpdateBid_ShouldReturnHasErrors() {
        //@PathVariable("bidListId") Integer id, @Valid BidList bidList, BindingResult result, Model model
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = bidListController.updateBid(1, bidList, bindingResult, model);

        assertThat(viewName).isEqualTo("bidList/update");
    }

    @Test
    void testUpdateBid_ShouldReturnUpdated() {
        //@PathVariable("bidListId") Integer id, @Valid BidList bidList, BindingResult result, Model model
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bidListService.updateBidList(1, bidList)).thenReturn(true);
        when(bidListService.findAll()).thenReturn(List.of());

        String viewName = bidListController.updateBid(1, bidList, bindingResult, model);

        assertThat(viewName).isEqualTo("redirect:/bidList/list");
        verify(bidListService, times(1)).updateBidList(1, bidList);
        verify(bidListService, times(1)).findAll();
    }

    @Test
    void testDelete_ShouldReturnDeleted() {
        //@PathVariable("id") Integer id, Model model
        when(bidListService.findAll()).thenReturn(List.of());

        String viewName = bidListController.deleteBid(1, model);

        assertThat(viewName).isEqualTo("redirect:/bidList/list");
        verify(bidListService, times(1)).deleteById(1);
        verify(bidListService, times(1)).findAll();
    }

}