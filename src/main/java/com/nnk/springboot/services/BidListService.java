package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import java.util.List;

public interface BidListService {
    List<BidList> findAll();
    void insertBidList(BidList bidList);
    Boolean updateBidList(int id, BidList bidList);
    BidList findById(int id);
}
