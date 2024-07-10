package com.derivativemarket.posttrade.org.controllers;

import com.derivativemarket.posttrade.org.dto.TradeDTO;
import com.derivativemarket.posttrade.org.entities.TradeEntity;
import com.derivativemarket.posttrade.org.repositories.MarketRepository;
import com.derivativemarket.posttrade.org.services.MarketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*Contains @Controller and @ResponseBody
@ResponseBody Return JSON and XML directly to the response body
@ComponentScan it scan all annotation and then execute acc to annotation it reads.
*/
@RestController
@RequestMapping(path="/trades")
public class PostTradeController {

    private final MarketService marketService;

    public PostTradeController(MarketService marketService){
        this.marketService=marketService;
    }


    /* @PathVariable parameter i mandatory to pass and
    @RequestParam optional parameter then it will work
    */
    @GetMapping("/{ticketId}")
    public ResponseEntity<TradeDTO> getTradeByTicketId(@PathVariable Long id){
       Optional<TradeDTO> tradeDTO=marketService.getTradeByTicketId(id);
      return tradeDTO
              .map(tradeDTO1 -> ResponseEntity.ok(tradeDTO1))
              .orElse(ResponseEntity.notFound().build());
    }

    /*get all the employee in sorted order by date*/
    @GetMapping()
    public ResponseEntity<List<TradeDTO>> getAllTrades(@RequestParam(required = false, name ="inputAge") LocalDate date,@RequestParam(required = false)String SortBy){
        List<TradeDTO> tradesDTO= marketService.getAllTrades();
        return ResponseEntity.ok(tradesDTO);
    }

    /*browser always interact with get request and not interact with post request.
    @RequestBody when we need to pass whole json obj as parameter
     */
    @PostMapping
    public ResponseEntity<TradeDTO> insertTrade(@RequestBody TradeDTO inputTrade){
        TradeDTO tradeDTO= marketService.insertTrade(inputTrade);
        return new ResponseEntity<>(tradeDTO, HttpStatus.CREATED);
    }

    @PutMapping(path="/{tradeId}")
    public ResponseEntity<TradeDTO> updateTradeById(@RequestBody TradeDTO tradeDTO,@PathVariable Long tradeId){
        return ResponseEntity.ok(marketService.updateTradeById(tradeId,tradeDTO));
    }

    @DeleteMapping(path="/{refId}")
    public ResponseEntity<Boolean> termination(@PathVariable Long refId){
       Boolean isTerminated=marketService.termination(refId);
       if(isTerminated) return ResponseEntity.ok(true);
       return ResponseEntity.notFound().build();

    }

    @PatchMapping(path="/{refId}")
    public ResponseEntity<TradeDTO> reuploadTrade(@PathVariable Long refId, @RequestBody Map<String,Object> updates){
        TradeDTO tradeDTO=marketService.reuploadTrade(refId,updates);
        if(tradeDTO==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tradeDTO);
    }
}
