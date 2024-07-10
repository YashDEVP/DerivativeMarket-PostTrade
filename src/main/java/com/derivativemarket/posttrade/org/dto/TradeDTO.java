package com.derivativemarket.posttrade.org.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

//POJO Class
//Added lombok property here
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeDTO {
    private long refId;

    private Long ticketId;

    private String party;

    private String counterParty;

    private Integer amount;

    private String productType;

    private LocalDate tradeDate;

    private String workflow;
}
