package com.derivativemarket.posttrade.org.services;

import com.derivativemarket.posttrade.org.dto.TradeDTO;
import com.derivativemarket.posttrade.org.entities.TradeEntity;
import com.derivativemarket.posttrade.org.repositories.MarketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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

    public TradeDTO updateTradeById(Long tradeId, TradeDTO tradeDTO) {
        TradeEntity tradeEntity=modelMapper.map(tradeDTO, TradeEntity.class);
        tradeEntity.setRefId(tradeId);
        TradeEntity savedTrade= marketRepository.save(tradeEntity);
        return modelMapper.map(savedTrade, TradeDTO.class);
    }
    public Boolean matchedTrade(Long refId){
        return marketRepository.existsById(refId);
    }
    public Boolean termination(Long refId) {
        if(!matchedTrade(refId))return false;
        marketRepository.deleteById(refId);
        return true;

    }
    /*Reflection method used to update value of property of particular class*/
    public TradeDTO reuploadTrade(Long refId, Map<String,Object> updates) {
            if(!matchedTrade(refId)) return null;
            TradeEntity tradeEntity=marketRepository.findById(refId).orElse(null);
            updates.forEach((field,value)->{
                    Field fieldToBeUpated= ReflectionUtils.findRequiredField(TradeEntity.class,field);
                    fieldToBeUpated.setAccessible(true);  //By this e can update private field of the class
                    ReflectionUtils.setField(fieldToBeUpated,tradeEntity,value);
        });
            return modelMapper.map(marketRepository.save(tradeEntity), TradeDTO.class);


    }
}
