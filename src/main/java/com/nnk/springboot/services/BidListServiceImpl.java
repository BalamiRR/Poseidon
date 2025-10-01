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

    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public void insertBidList(BidList bidList) {
        bidListRepository.save(bidList);
    }

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
}

