package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	@BeforeEach
	public void setup() {
		tradeRepository.deleteAll();
	}

	@Test
	public void tradeTest() {
		Trade tradee = new Trade("TradeAccount", "Type");
		tradee.setTradeId(1);

		Trade trade = new Trade("TradeAccount", "Type", 10.0);

		// Save
		trade = tradeRepository.save(trade);
		assertNotNull(trade.getTradeId());
		assertEquals("TradeAccount", trade.getAccount());

		// Update
		trade.setAccount("TradeAccountUpdate");
		trade = tradeRepository.save(trade);
		assertEquals("TradeAccountUpdate", trade.getAccount());

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		assertFalse(tradeList.isPresent());
	}
}
