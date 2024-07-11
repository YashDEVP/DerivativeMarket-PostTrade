package com.derivativemarket.posttrade.org.dto;

import com.derivativemarket.posttrade.org.annotation.SupWorkflowValidation;
import jakarta.validation.constraints.*;
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

    @NotNull(message="Required field in Trade: ticketId")
    //@Size(min=4,max=10,message="Length of TicketId should be in between 4 to 10")
    private Long ticketId;

    @NotNull
    private String party;

    @NotNull
    private String counterParty;

    @Positive(message="Amount can not be negative")
    //@Min(value=0,message="Amount can not be negative")
    private Integer amount;

    @NotNull
    private String productType;

    @NotNull
    @FutureOrPresent(message="You can not insert trade with past date")
    private LocalDate tradeDate;

    @NotNull(message = "You have to provide workflow")
    @SupWorkflowValidation
    //@Pattern(regexp="^(SEF|NOVATION|CAPFLOOR)$",message="Currently we are supporting these three role (IRS | NOVATION |CAPFLOOR)")
    private String workflow;
}
