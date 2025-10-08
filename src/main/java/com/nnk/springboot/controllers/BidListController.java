package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class BidListController {
    private final BidListService bidListService;

    @GetMapping("/bidList/list")
    public String home(Authentication authentication, Model model) {
        String name = authentication.getName();
        model.addAttribute("name", "Logged in as: " + name);
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result) {
        if(result.hasErrors()){
            return "bidList/add";
        }
        bidListService.insertBidList(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{bidListId}")
    public String showUpdateForm(@PathVariable("bidListId") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidList bidList = bidListService.findById(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{bidListId}")
    public String updateBid(@PathVariable("bidListId") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            return "bidList/update";
        }
        Boolean updated = bidListService.updateBidList(id, bidList);
        if(updated){
            model.addAttribute("bidLists", bidListService.findAll());
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteById(id);
        model.addAttribute("bidLists", bidListService.findAll());
        return "redirect:/bidList/list";
    }
}
