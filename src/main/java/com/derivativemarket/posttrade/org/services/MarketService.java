package com.derivativemarket.posttrade.org.services;

import com.derivativemarket.posttrade.org.dto.TradeDTO;
import com.derivativemarket.posttrade.org.entities.TradeEntity;
import com.derivativemarket.posttrade.org.repositories.MarketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketService {
    private final MarketRepository marketRepository;

    /* ModelMapper is use for converting one obj type o other based on the property that are same
    */
    private final ModelMapper modelMapper;

    public MarketService(MarketRepository marketRepository,ModelMapper modelMapper){
        this.marketRepository=marketRepository;
        this.modelMapper=modelMapper;
    }

    public TradeDTO getTradeByTicketId(Long id) {
        TradeEntity tradeEntity= marketRepository.findById(id).orElse(null);
        return modelMapper.map(tradeEntity, TradeDTO.class);
    }

    public List<TradeDTO> getAllTrades() {
        List<TradeEntity> tradeEntities=marketRepository.findAll();
        return tradeEntities
                .stream()
                .map(tradeEntity -> modelMapper.map(tradeEntity, TradeDTO.class))
                .collect(Collectors.toList());
    }

    public TradeDTO insertTrade(TradeDTO inputTrade) {
        TradeEntity inputTradeEntity=modelMapper.map(inputTrade,TradeEntity.class);
        TradeEntity savedTradeEntity= marketRepository.save(inputTradeEntity);
        return modelMapper.map(savedTradeEntity, TradeDTO.class);
    }
}
