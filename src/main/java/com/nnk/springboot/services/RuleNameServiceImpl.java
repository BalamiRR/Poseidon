package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class RuleNameServiceImpl implements RuleNameService{
    private final RuleNameRepository ruleNameRepository;

    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public void insertRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    @Override
    public Boolean updateRuleName(int id, RuleName ruleName) {
        Optional<RuleName> newRuleName = ruleNameRepository.findById(id);
        boolean updated = false;
        if (newRuleName.isPresent()) {
            RuleName updatedRuleName = newRuleName.get();
            updatedRuleName.setName(ruleName.getName());
            updatedRuleName.setDescription(ruleName.getDescription());
            updatedRuleName.setJson(ruleName.getJson());
            updatedRuleName.setTemplate(ruleName.getTemplate());
            updatedRuleName.setSqlPart(ruleName.getSqlPart());
            updatedRuleName.setSqlPart(ruleName.getSqlPart());
            ruleNameRepository.save(updatedRuleName);
            updated = true;
            return updated;
        } else {
            return  updated;
        }
    }

    @Override
    public RuleName findyById(int id) {
        Optional<RuleName> newRuleName = ruleNameRepository.findById(id);
        if(newRuleName.isPresent()){
            log.error("Successfully find by id {}", id);
            return newRuleName.get();
        } else {
            log.error("Failed to find by id {}", id);
            return null;
        }
    }

    @Override
    public void deleteById(int id) {
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        if (ruleName.isPresent()) {
            ruleNameRepository.delete(ruleName.get());
            log.error("Successfully deleted by id {}", id);
        } else {
            log.error("Failed to delete with id " + id);
        }
    }
}
