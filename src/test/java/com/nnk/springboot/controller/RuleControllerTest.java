package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.RuleNameServiceImpl;
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
public class RuleControllerTest {

    @Mock
    private RuleNameServiceImpl ruleNameServiceImpl;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private RuleNameController ruleNameController;

    private RuleName ruleName;

    @BeforeEach
    void setUp() {
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("rule name");
        ruleName.setDescription("Description");
        ruleName.setJson("Json");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("Sqlstr");
        ruleName.setSqlPart("SqlPart");

        User user = new User();
        user.setId(1);
        user.setUsername("john");
        user.setPassword("John@123");
        user.setFullName("JohnDoe");
        user.setRole("USER");
        user.setEnabled(true);
    }

    @Test
    void home_ShouldReturnUserRuleNameView_AndAddAttributes() {
        //Authentication authentication, Model model
        when(authentication.getName()).thenReturn("john");
        when(ruleNameServiceImpl.findAll()).thenReturn(List.of());

        String viewName = ruleNameController.home(authentication, model);

        assertEquals("ruleName/list", viewName);
        verify(model).addAttribute("name", "Logged in as: john");
        verify(model).addAttribute("ruleNames", List.of());
        verify(ruleNameServiceImpl, times(1)).findAll();
    }

    @Test
    void testAddBidForm_ShouldRetunBidListView() {
        String viewName = ruleNameController.addRuleForm(ruleName);

        assertEquals("ruleName/add", viewName);
    }

    @Test
    void testValidate_ShouldReturnHasError(){
        //@Valid BidList bidList, BindingResult result
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ruleNameController.validate(ruleName, bindingResult);

        assertThat(viewName).isEqualTo("ruleName/add");
        verify(bindingResult, times(1)).hasErrors();
        verifyNoInteractions(ruleNameServiceImpl);
    }

    @Test
    void testValidate_ShouldReturnValidation(){
        //@PathVariable("bidListId") Integer id, Model model
        String viewName = ruleNameController.validate(ruleName, bindingResult);

        assertThat(viewName).isEqualTo("redirect:/ruleName/list");
        verify(bindingResult, times(1)).hasErrors();
    }

    @Test
    void testUpdateForm_Should(){
        //@PathVariable("bidListId") Integer id, Model model
        when(ruleNameServiceImpl.findyById(1)).thenReturn(ruleName);
        String viewName = ruleNameController.showUpdateForm(1, model);
        assertThat(viewName).isEqualTo("ruleName/update");
    }

    @Test
    void testUpdateBid_ShouldReturnHasErrors() {
        //@PathVariable("bidListId") Integer id, @Valid BidList bidList, BindingResult result, Model model
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ruleNameController.updateRuleName(1, ruleName, bindingResult, model);

        assertThat(viewName).isEqualTo("ruleName/update");
    }

    @Test
    void testUpdateBid_ShouldReturnUpdated() {
        //@PathVariable("bidListId") Integer id, @Valid BidList bidList, BindingResult result, Model model
        when(bindingResult.hasErrors()).thenReturn(false);
        when(ruleNameServiceImpl.updateRuleName(1, ruleName)).thenReturn(true);
        when(ruleNameServiceImpl.findAll()).thenReturn(List.of());

        String viewName = ruleNameController.updateRuleName(1, ruleName, bindingResult, model);

        assertThat(viewName).isEqualTo("redirect:/ruleName/list");
        verify(ruleNameServiceImpl, times(1)).updateRuleName(1, ruleName);
        verify(ruleNameServiceImpl, times(1)).findAll();
    }

    @Test
    void testDelete_ShouldReturnDeleted() {
        //@PathVariable("id") Integer id, Model model
        when(ruleNameServiceImpl.findAll()).thenReturn(List.of());

        String viewName = ruleNameController.deleteRuleName(1, model);

        assertThat(viewName).isEqualTo("redirect:/ruleName/list");
        verify(ruleNameServiceImpl, times(1)).deleteById(1);
        verify(ruleNameServiceImpl, times(1)).findAll();
    }


}