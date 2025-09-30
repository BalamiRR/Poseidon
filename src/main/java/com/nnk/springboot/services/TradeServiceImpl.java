package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

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
}
