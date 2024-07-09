package com.derivativemarket.posttrade.org.controllers;

import com.derivativemarket.posttrade.org.dto.TradeDTO;
import com.derivativemarket.posttrade.org.entities.TradeEntity;
import com.derivativemarket.posttrade.org.repositories.MarketRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/*Contains @Controller and @ResponseBody
@ResponseBody Return JSON and XML directly to the response body
@ComponentScan it scan all annotation and then execute acc to annotation it reads.
*/
@RestController
@RequestMapping(path="/trades")
public class PostTradeController {

    private final MarketRepository marketRepository;

    public PostTradeController(MarketRepository marketRepository){
        this.marketRepository=marketRepository;
    }


    /* @PathVariable parameter i mandatory to pass and
    @RequestParam optional parameter then it will work
    */
    @GetMapping("/{ticketId}")
    public TradeEntity getTradeByTicketId(@PathVariable Long id){
       return marketRepository.findById(id).orElse(null);
        // return new TradeDTO(1,ticketId,"Pimco","DBS",1000000,"IRS"
            //    , LocalDate.of(2024,7,8),"New");
    }

    /*get all the employee in sorted order by date*/
    @GetMapping()
    public List<TradeEntity> getAllTrades(@RequestParam(required = false, name ="inputAge") LocalDate date,@RequestParam(required = false)String SortBy){
        return marketRepository.findAll();
    }

    /*browser always interact with get request and not interact with post request.
    @RequestBody when we need to pass whole json obj as parameter
     */
    @PostMapping
    public TradeEntity insertTrade(@RequestBody TradeEntity inputTrade){
        return marketRepository.save(inputTrade);
    }
}
