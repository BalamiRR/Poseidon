package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RuleNameServiceTest {

    RuleName ruleName = new RuleName();

    @Autowired
    private RuleNameService ruleNameService;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @BeforeAll
    public void initsRuleName() {
        ruleName.setName("Name");
        ruleName.setDescription("Description");
        ruleName.setJson("Json");
        ruleName.setTemplate("Template");
        ruleName.setSqlPart("INSERT");
        ruleName.setSqlStr("SelectFROM");
        ruleNameService.insertRuleName(ruleName);
    }

    @AfterAll
    public void cleanAllRuleName() {
        ruleNameRepository.deleteAll();
    }

    @Test
    public void test_findAllRuleName(){
        List<RuleName> listRuleName = ruleNameService.findAll();
        assertFalse(listRuleName.isEmpty());
    }

    @Test
    public void test_updateRuleName() {
        Integer id = ruleName.getId();
        RuleName foundId = ruleNameService.findyById(id);
        foundId.setName("FirstRule");
        foundId.setDescription("DescriptionOfTheRule");
        foundId.setTemplate("TheTemplatepart");
        foundId.setJson("JSONpart");
        foundId.setSqlPart("SQLPart");
        foundId.setSqlStr("SQLSTR");
        Boolean updated = ruleNameService.updateRuleName(id, foundId);
        assertTrue(updated);
    }

    @Test
    public void test_deleteRuleName() {
        Integer id = ruleName.getId();
        ruleNameService.deleteById(id);
        RuleName foundId = ruleNameService.findyById(id);
        assertNull(foundId);
    }
}