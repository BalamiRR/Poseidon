package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.TradeServiceImpl;
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
public class TradeControllerTest {

    @Mock
    private TradeServiceImpl tradeServiceImpl;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private TradeController tradeController;

    private Trade trade;

    @BeforeEach
    void setUp() {
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account");
        trade.setType("Account");
        trade.setBuyQuantity(12.0);

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
        when(tradeServiceImpl.findAll()).thenReturn(List.of());

        String viewName = tradeController.home(authentication, model);

        assertEquals("trade/list", viewName);
        verify(model).addAttribute("name", "Logged in as: john");
        verify(model).addAttribute("trades", List.of());
        verify(tradeServiceImpl, times(1)).findAll();
    }

    @Test
    void testAddBidForm_ShouldRetunBidListView() {
        String viewName = tradeController.addUser(trade);

        assertEquals("trade/add", viewName);
    }

    @Test
    void testValidate_ShouldReturnHasError(){
        //@Valid BidList bidList, BindingResult result
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = tradeController.validate(trade, bindingResult);

        assertThat(viewName).isEqualTo("trade/add");
        verify(bindingResult, times(1)).hasErrors();
        verifyNoInteractions(tradeServiceImpl);
    }

    @Test
    void testValidate_ShouldReturnValidation(){
        //@PathVariable("bidListId") Integer id, Model model
        String viewName = tradeController.validate(trade, bindingResult);

        assertThat(viewName).isEqualTo("redirect:/trade/list");
        verify(bindingResult, times(1)).hasErrors();
    }

    @Test
    void testUpdateForm_Should(){
        //@PathVariable("bidListId") Integer id, Model model
        when(tradeServiceImpl.findById(1)).thenReturn(trade);
        String viewName = tradeController.showUpdateForm(1, model);
        assertThat(viewName).isEqualTo("trade/update");
    }

    @Test
    void testUpdateBid_ShouldReturnHasErrors() {
        //@PathVariable("bidListId") Integer id, @Valid BidList bidList, BindingResult result, Model model
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = tradeController.updateTrade(1, trade, bindingResult, model);

        assertThat(viewName).isEqualTo("trade/update");
    }

    @Test
    void testUpdateBid_ShouldReturnUpdated() {
        //@PathVariable("bidListId") Integer id, @Valid BidList bidList, BindingResult result, Model model
        when(bindingResult.hasErrors()).thenReturn(false);
        when(tradeServiceImpl.updateTrade(1, trade)).thenReturn(true);
        when(tradeServiceImpl.findAll()).thenReturn(List.of());

        String viewName = tradeController.updateTrade(1, trade, bindingResult, model);

        assertThat(viewName).isEqualTo("redirect:/trade/list");
        verify(tradeServiceImpl, times(1)).updateTrade(1, trade);
        verify(tradeServiceImpl, times(1)).findAll();
    }

    @Test
    void testDelete_ShouldReturnDeleted() {
        //@PathVariable("id") Integer id, Model model
        when(tradeServiceImpl.findAll()).thenReturn(List.of());

        String viewName = tradeController.deleteTrade(1, model);

        assertThat(viewName).isEqualTo("redirect:/trade/list");
        verify(tradeServiceImpl, times(1)).deleteById(1);
        verify(tradeServiceImpl, times(1)).findAll();
    }


}