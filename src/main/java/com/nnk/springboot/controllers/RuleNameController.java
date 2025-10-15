package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class RuleNameController {
    // TODO: Inject RuleName service
    private final RuleNameServiceImpl ruleNameServiceImpl;

    @RequestMapping("/ruleName/list")
    public String home(Authentication authentication, Model model) {
        String name = authentication.getName();
        model.addAttribute("name", "Logged in as: " + name);
        model.addAttribute("ruleNames", ruleNameServiceImpl.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result) {
        if(result.hasErrors()){
            return "ruleName/add";
        }
        ruleNameServiceImpl.insertRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        RuleName ruleName = ruleNameServiceImpl.findyById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        if(result.hasErrors()){
            return "ruleName/update";
        }

        Boolean updatedRuleName = ruleNameServiceImpl.updateRuleName(id, ruleName);
        if(updatedRuleName){
            model.addAttribute("ruleNames", ruleNameServiceImpl.findAll());
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        ruleNameServiceImpl.deleteById(id);
        model.addAttribute("ruleNames", ruleNameServiceImpl.findAll());
        return "redirect:/ruleName/list";
    }
}
