package com.derivativemarket.posttrade.org.controllers;

import com.derivativemarket.posttrade.org.dto.TradeDTO;
import com.derivativemarket.posttrade.org.entities.TradeEntity;
import com.derivativemarket.posttrade.org.services.AuditReaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/audit")
public class AuditController {

    @Autowired
    private AuditReaderServiceImpl auditReaderService;

    @GetMapping(path="/refId/{refId}")
    List<TradeDTO> getTradeRef(@PathVariable Long refId){
        return auditReaderService.getAuditRecord(refId);
    }


}
