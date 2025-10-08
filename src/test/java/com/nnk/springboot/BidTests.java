package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {
		BidList bid = new BidList("AccountTest", "TypeTest", 10d);

		// Save
		bid = bidListRepository.save(bid);
		assertNotNull(bid.getBidListId());
		assertEquals(10d, bid.getBidQuantity(), 0.01);
		assertEquals("AccountTest", bid.getAccount());
		assertEquals("TypeTest", bid.getType());

		// Update
		bid.setAccount("Account");
		bid.setType("Type");
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		assertEquals(bid.getBidQuantity(), 20d, 20d);
		assertEquals(bid.getAccount(), "Account", "Account");
		assertEquals(bid.getType(), "Type", "Type");

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());

		BidList bidd = new BidList("AccountTest", "TypeTest", 10d);
		bidd.setBidListId(1);
	}
}