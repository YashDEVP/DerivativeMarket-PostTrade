package com.derivativemarket.posttrade.org.services;


import com.derivativemarket.posttrade.org.dto.TradeDTO;

import java.util.List;

public interface AuditReaderService {
    public List<TradeDTO> getAuditRecord(long refId);
}
