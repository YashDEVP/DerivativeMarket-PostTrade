package com.derivativemarket.posttrade.org.controllers;

import com.derivativemarket.posttrade.org.dto.TradeDTO;
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
    @GetMapping("/{ticketId}")
    /* @PathVariable parameter i mandatory to pass and
    @RequestParam optional parameter then it will work
    */
    public TradeDTO getTradeByTicketId(@PathVariable Long ticketId){
        return new TradeDTO(ticketId,"Pimco","DBS",1000000,"IRS"
                , LocalDate.of(2024,7,8),"New");
    }

    /*get all the employee in sorted order by date*/
    @GetMapping()
    public List<TradeDTO> getAllTrades(@RequestParam(required = false) LocalDate date){
        return null;
    }

    /*browser always interact with get request and not interact with post request.
    @RequestBody when we need to pass whole json obj as parameter
     */
    @PostMapping
    public TradeDTO insertTrade(@RequestBody TradeDTO Trade){
        return null;
    }
}
