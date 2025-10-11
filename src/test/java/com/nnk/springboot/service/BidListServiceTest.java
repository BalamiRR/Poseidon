package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidListServiceTest {
    BidList bidList = new BidList();

    @Autowired
    private BidListService bidListService;

    @Autowired
    private BidListRepository bidListRepository;

    @BeforeEach
    @Transactional
    public void initBidList() {
        bidList.setAccount("account");
        bidList.setType("type");
        bidList.setBidQuantity(10.0);
        bidListService.insertBidList(bidList);

        bidList = bidListRepository.findAll().get(0);

        assertNotNull(bidList.getBidListId(), "BidList ID should not be null after insert");
    }

    @AfterAll
    public void cleanBidList(){
        bidListRepository.deleteAll();
    }

    @Test
    public void test_findAllBidList(){
        List<BidList> lists = bidListService.findAll();
        assertFalse(lists.isEmpty());
    }

    @Test
    public void test_getBidListById (){
        Integer id = bidList.getBidListId();
        BidList foundId = bidListService.findById(id);
        assertEquals("account", foundId.getAccount());
        assertEquals("type", foundId.getType());
        assertEquals(10.0, foundId.getBidQuantity(), 0.01);
    }

    @Test
    public void test_updateBidList(){
        Integer id = bidList.getBidListId();
        assertNotNull(id, "ID should not be null");

        BidList foundId = bidListService.findById(id);
        assertNotNull(foundId, "foundId should not be null");

        foundId.setAccount("Accounts");
        foundId.setType("Types");

        Boolean updated = bidListService.updateBidList(id, foundId);
        assertTrue(updated);
    }

    @Test
    public void test_deleteById() {
        Integer id = bidList.getBidListId();
        bidListService.deleteById(id);
        BidList byId = bidListService.findById(id);
        assertNull(byId);
    }
}