package com.derivativemarket.posttrade.org.services;

import com.derivativemarket.posttrade.org.dto.TradeDTO;
import com.derivativemarket.posttrade.org.entities.TradeEntity;
import com.derivativemarket.posttrade.org.exception.ResourceNotFoundException;
import com.derivativemarket.posttrade.org.repositories.MarketRepository;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarketService {
    private final MarketRepository marketRepository;

    Logger logger= LoggerFactory.getLogger(MarketService.class);
    /* ModelMapper is use for converting one obj type o other based on the property that are same
     */
    private final ModelMapper modelMapper;

    public MarketService(MarketRepository marketRepository,ModelMapper modelMapper){
        this.marketRepository=marketRepository;
        this.modelMapper=modelMapper;
    }

    public Optional<TradeDTO> getTradeByTicketId(Long id) {
/*        Optional<TradeEntity> tradeEntity= marketRepository.findById(id);
        return modelMapper.map(tradeEntity, TradeDTO.class); */
        try {

            logger.trace("starting getTradeByTicketId method");
            return marketRepository.findById(id).map(tradeEntity -> modelMapper.map(tradeEntity, TradeDTO.class));
        }catch (Exception ex){
            logger.error("Exception in getTradeByTicketId method: " + ex);
            throw new RuntimeException(ex);
        }
    }

    public List<TradeDTO> getAllTrades() {
        try{
            logger.trace("starting getAllTrades method");
            List<TradeEntity> tradeEntities=marketRepository.findAll();
            logger.info("get the list of tradeEntity in getAllTrades method : " + tradeEntities);
            return tradeEntities
                    .stream()
                    .map(tradeEntity -> modelMapper.map(tradeEntity, TradeDTO.class))
                    .collect(Collectors.toList());
        }catch(Exception ex){
            logger.error("Exception in getAllTrades method: " + ex);
            throw new RuntimeException(ex);
        }
    }

    public TradeDTO insertTrade(TradeDTO inputTrade) {
        try {
            logger.trace("starting insertTrade method");
            TradeEntity inputTradeEntity = modelMapper.map(inputTrade, TradeEntity.class);
            TradeEntity savedTradeEntity = marketRepository.save(inputTradeEntity);
            logger.info("Successfully added trade in DB " + savedTradeEntity);
            return modelMapper.map(savedTradeEntity, TradeDTO.class);
        }catch(Exception ex){
            logger.error("Exception in insertTrade method: " + ex);
            throw new RuntimeException(ex);
        }
    }

    public TradeDTO updateTradeById(Long tradeId, TradeDTO tradeDTO) {
        try {
            logger.trace("starting insertTrade method");
            matchedTrade(tradeId);
            logger.info("Trade is present in DB");
            TradeEntity tradeEntity = modelMapper.map(tradeDTO, TradeEntity.class);
            tradeEntity.setRefId(tradeId);
            TradeEntity savedTrade = marketRepository.save(tradeEntity);
            logger.info("Successfully added trade in DB : " + savedTrade);
            return modelMapper.map(savedTrade, TradeDTO.class);
        }catch(Exception ex){
            logger.error("Exception in updateTradeById method: " + ex);
            throw new RuntimeException(ex);
        }
    }
    public void matchedTrade(Long refId){
        try {
            logger.trace("starting matchedTrade method");
            Boolean existTrade = marketRepository.existsById(refId);
            if (!existTrade) throw new ResourceNotFoundException("Trade is not found in org database " + refId);
        }catch(Exception ex){
            logger.error("Exception in matchedTrade method: " + ex);
            throw new RuntimeException(ex);
        }
    }
    public Boolean termination(Long refId) {
        try{
            logger.trace("starting matchedTrade method");
            matchedTrade(refId);
            logger.info("Trade is present in DB");
            marketRepository.deleteById(refId);
            logger.info("Trade is deleted successfully");
            return true;
        }catch (Exception ex){
            logger.error("Exception in termination method: " + ex);
            throw new RuntimeException(ex);
        }

    }
    /*Reflection method used to update value of property of particular class*/
    public TradeDTO reuploadTrade(Long refId, Map<String,Object> updates) {
        try{
            logger.trace("starting reuploadTrade method");
            matchedTrade(refId);
            logger.info("Trade is present in DB");
            TradeEntity tradeEntity=marketRepository.findById(refId).orElse(null);
            updates.forEach((field,value)->{
                Field fieldToBeUpated= ReflectionUtils.findRequiredField(TradeEntity.class,field);
                fieldToBeUpated.setAccessible(true);  //By this e can update private field of the class
                ReflectionUtils.setField(fieldToBeUpated,tradeEntity,value);
            });
            return modelMapper.map(marketRepository.save(tradeEntity), TradeDTO.class);
        }catch (Exception ex){
            logger.error("Exception in reuploadTrade method: " + ex);
            throw new RuntimeException(ex);
        }

    }
}
