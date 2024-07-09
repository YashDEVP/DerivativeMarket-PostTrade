package com.derivativemarket.posttrade.org.controllers;

import com.derivativemarket.posttrade.org.dto.TradeDTO;
import com.derivativemarket.posttrade.org.entities.TradeEntity;
import com.derivativemarket.posttrade.org.repositories.MarketRepository;
import com.derivativemarket.posttrade.org.services.MarketService;
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

    private final MarketService marketService;

    public PostTradeController(MarketService marketService){
        this.marketService=marketService;
    }


    /* @PathVariable parameter i mandatory to pass and
    @RequestParam optional parameter then it will work
    */
    @GetMapping("/{ticketId}")
    public TradeDTO getTradeByTicketId(@PathVariable Long id){
       return marketService.getTradeByTicketId(id);
        // return new TradeDTO(1,ticketId,"Pimco","DBS",1000000,"IRS"
            //    , LocalDate.of(2024,7,8),"New");
    }

    /*get all the employee in sorted order by date*/
    @GetMapping()
    public List<TradeDTO> getAllTrades(@RequestParam(required = false, name ="inputAge") LocalDate date,@RequestParam(required = false)String SortBy){
        return marketService.getAllTrades();
    }

    /*browser always interact with get request and not interact with post request.
    @RequestBody when we need to pass whole json obj as parameter
     */
    @PostMapping
    public TradeDTO insertTrade(@RequestBody TradeDTO inputTrade){
        return marketService.insertTrade(inputTrade);
    }
}
