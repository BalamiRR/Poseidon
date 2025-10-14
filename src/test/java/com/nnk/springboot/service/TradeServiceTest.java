package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeServiceTest {
    Trade trade = new Trade();

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeRepository tradeRepository;

    @BeforeEach
    public void initsTrade(){
        trade.setAccount("TradeAccount");
        trade.setType("TradeType");
        trade.setBuyQuantity(13.0);
        tradeService.insertTrade(trade);
    }

    @Test
    public void test_updateTrade() {
        Integer id = trade.getTradeId();
        Trade foundId = tradeService.findById(id);
        foundId.setAccount("Account");
        foundId.setType("Type");
        foundId.setBuyQuantity(11.9);

        Boolean updated = tradeService.updateTrade(id, foundId);
        assertTrue(updated);
    }

    @Test
    public void test_deleteTradeById() {
        Integer id = trade.getTradeId();
        tradeService.deleteById(id);
        Trade foundId = tradeService.findById(id);
        assertNull(foundId);
    }
}
