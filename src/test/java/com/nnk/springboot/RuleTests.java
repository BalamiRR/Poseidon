package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@BeforeEach
	public void setup() {
		ruleNameRepository.deleteAll();
	}

	@Test
	public void ruleTest() {
		RuleName rule = new RuleName("RuleName", "Description", "Json", "Template", "SQL", "SQLPart");
		rule.setSqlStr("ornek");

		// Save
		rule = ruleNameRepository.save(rule);
		assertNotNull(rule.getId());
		assertTrue(rule.getName().equals("RuleName"));

		// Update
		rule.setName("RuleNameUpdate");
		rule = ruleNameRepository.save(rule);
		assertTrue(rule.getName().equals("RuleNameUpdate"));

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		assertFalse(ruleList.isPresent());

		RuleName rulee = new RuleName("RuleName", "Description", "Json", "Template", "SQL", "SQLPart");
		rulee.setId(3);

	}
}
