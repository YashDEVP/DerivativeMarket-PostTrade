package com.derivativemarket.posttrade.org.controllers;

import com.derivativemarket.posttrade.org.dto.TradeDTO;
import com.derivativemarket.posttrade.org.entities.TradeEntity;
import com.derivativemarket.posttrade.org.exception.ResourceNotFoundException;
import com.derivativemarket.posttrade.org.repositories.MarketRepository;
import com.derivativemarket.posttrade.org.services.MarketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/*Contains @Controller and @ResponseBody
@ResponseBody Return JSON and XML directly to the response body
@ComponentScan it scan all annotation and then execute acc to annotation it reads.
*/
@RestController
@RequestMapping(path="/trades")
public class PostTradeController {


    Logger logger= LoggerFactory.getLogger(PostTradeController.class);

    private final MarketService marketService;

    public PostTradeController(MarketService marketService){
        this.marketService=marketService;
    }


    /* @PathVariable parameter is mandatory to pass and
    @RequestParam optional parameter then it will work
    */
    /*refId in GetMapping and refId should have same name otherwise it will give exception*/
    @GetMapping("/{refId}")
    public ResponseEntity<TradeDTO> getTradeByTicketId(@PathVariable(name="refId") Long id){
        try {
            logger.trace("Starting of getTradeByTicketId Controller");
            Optional<TradeDTO> tradeDTO = marketService.getTradeByTicketId(id);
            return tradeDTO
                    .map(tradeDTO1 -> ResponseEntity.ok(tradeDTO1))
                    .orElseThrow(() -> new ResourceNotFoundException("Trade was not found"));
        }catch(Exception ex){
            logger.error("Exception: getTradeByTicketId Controller : " + ex);
            throw new RuntimeException(ex);
        }
    }

//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handledEmployeeNotFound(NoSuchElementException exception){
//        return new ResponseEntity<>("Trade was not found in the database",HttpStatus.NOT_FOUND);
//    }

    /*get all the employee in sorted order by date*/
    @GetMapping()
    public ResponseEntity<List<TradeDTO>> getAllTrades(@RequestParam(required = false, name ="inputAge") LocalDate date,@RequestParam(required = false)String SortBy){
       try {
           logger.trace("Starting of getAllTrades Controller");
           List<TradeDTO> tradesDTO = marketService.getAllTrades();
           return ResponseEntity.ok(tradesDTO);
       }catch(Exception ex){
           logger.error("Exception: getAllTrades Controller : " + ex);
           throw new RuntimeException(ex);
       }
    }

    /*browser always interact with get request and not interact with post request.
    @RequestBody when we need to pass whole json obj as parameter
     */
    @PostMapping
    public ResponseEntity<TradeDTO> insertTrade(@RequestBody @Valid TradeDTO inputTrade){
        try{
            logger.trace("Starting of insertTrade Controller");
        TradeDTO tradeDTO= marketService.insertTrade(inputTrade);
        return new ResponseEntity<>(tradeDTO, HttpStatus.CREATED);
        }catch(Exception ex){
            logger.error("Exception: insertTrade Controller : " + ex);
            throw new RuntimeException(ex);
        }
    }

    @PutMapping(path="/{tradeId}")
    public ResponseEntity<TradeDTO> updateTradeById(@RequestBody @Valid TradeDTO tradeDTO,@PathVariable Long tradeId){
        try {
            logger.trace("Starting of updateTradeById Controller");
            return ResponseEntity.ok(marketService.updateTradeById(tradeId, tradeDTO));
        }catch(Exception ex){
            logger.error("Exception: updateTradeById Controller : " + ex);
            throw new RuntimeException(ex);
        }
    }

    @DeleteMapping(path="/{refId}")
    public ResponseEntity<Boolean> termination(@PathVariable Long refId){
        try {
            logger.trace("Starting of termination Controller");
            Boolean isTerminated = marketService.termination(refId);
            if (isTerminated) return ResponseEntity.ok(true);
            return ResponseEntity.notFound().build();
        }catch(Exception ex){
            logger.error("Exception: termination Controller : " + ex);
            throw new RuntimeException(ex);
        }
    }

    @PatchMapping(path="/{refId}")
    public ResponseEntity<TradeDTO> reuploadTrade(@PathVariable Long refId, @RequestBody Map<String,Object> updates){
        try {
            logger.trace("Starting of reuploadTrade Controller");
            TradeDTO tradeDTO = marketService.reuploadTrade(refId, updates);
            if (tradeDTO == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(tradeDTO);
        }catch(Exception ex){
            logger.error("Exception: reuploadTrade Controller : " + ex);
            throw new RuntimeException(ex);
        }
    }
}
