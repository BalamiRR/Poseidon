package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface RuleNameService {
    List<RuleName> findAll();
    void insertRuleName(RuleName ruleName);
}
