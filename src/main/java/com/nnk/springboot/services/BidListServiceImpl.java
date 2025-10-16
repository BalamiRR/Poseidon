package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BidListServiceImpl implements BidListService {
    private final BidListRepository bidListRepository;

    /**
     * Retrieves all BidList entries from the database.
     * @return a list of all bid lists
     */
    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    /**
     * Inserts a new BidList into the database.
     * @param bidList the bid list object to be saved
     */
    @Override
    public void insertBidList(BidList bidList) {
        bidListRepository.save(bidList);
    }

    /**
     * Updates an existing BidList identified by its ID.
     * If the BidList exists, updates its fields (account, type, bid quantity)
     * and saves the new values to the database.
     *
     * @param id ID of the bid list to update
     * @param bidList updated bid list data
     * @return  if the update was successful, false otherwise
     */
    @Override
    public Boolean updateBidList(int id, BidList bidList) {
        Optional<BidList> list = bidListRepository.findById(id);
        boolean updated = false;
        if(list.isPresent()){
            BidList updateBidlist = list.get();
            updateBidlist.setBidQuantity(bidList.getBidQuantity());
            updateBidlist.setType(bidList.getType());
            updateBidlist.setAccount(bidList.getAccount());
            bidListRepository.save(updateBidlist);
            updated = true;
            log.error("Successfully updated BidList with id {}", id);
        } else {
            log.error("Failed to update BidList {}", id);
        }
        return updated;
    }

    /**
     * Retrieves a BidList by its unique ID.
     * @param id ID of the bid list to retrieve
     * @return the BidList object if found, otherwise null
     */
    @Override
    public BidList findById(int id) {
        Optional<BidList> bidList = bidListRepository.findById(id);
        if(bidList.isPresent()){
            log.error("Successfully find by id {}", id);
            return bidList.get();
        } else {
            log.error("Failed to find by id {}", id);
            return null;
        }
    }

    /**
     * Deletes a BidList by its unique ID.
     * If the bid list is found, it will be deleted from the database.
     * @param id ID of the bid list to delete
     */
    @Override
    public void deleteById(int id) {
        Optional<BidList> bidList = bidListRepository.findById(id);
        if (bidList.isPresent()) {
            bidListRepository.delete(bidList.get());
            log.error("Successfully deleted by id {}", id);
        } else {
            log.error("Failed to delete with id " + id);
        }
    }
}

