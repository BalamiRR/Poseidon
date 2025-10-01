package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;

    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public void insertTrade(Trade trade) {
        tradeRepository.save(trade);
    }

    @Override
    public Boolean updateTrade(int id, Trade trade) {
        Optional<Trade> newTrade = tradeRepository.findById(id);
        boolean updated = false;
        if(newTrade.isPresent()){
            Trade updatedTrade = newTrade.get();
            updatedTrade.setAccount(trade.getAccount());
            updatedTrade.setType(trade.getType());
            updatedTrade.setBuyQuantity(trade.getBuyQuantity());
            tradeRepository.save(updatedTrade);
            updated = true;
            log.error("Successfully updated BidList with id {}", id);
            return updated;
        } else {
            log.error("Failed to update BidList {}", id);
            return updated;
        }
    }

    @Override
    public Trade findById(int id) {
        Optional<Trade> trade = tradeRepository.findById(id);
        if(trade.isPresent()){
            log.error("Successfully finding by id {}", id);
            return trade.get();
        } else {
            log.error("Error to finding by id {}", id);
            return null;
        }
    }
}
