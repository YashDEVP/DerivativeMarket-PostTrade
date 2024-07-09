package com.derivativemarket.posttrade.org.dto;

import java.time.LocalDate;

//POJO Class

public class TradeDTO {
    private long refId;

    private Long ticketId;

    private String party;

    private String counterParty;

    private Integer amount;

    private String productType;

    private LocalDate tradeDate;

    private String workflow;


    public TradeDTO(){

    }

    public TradeDTO(long refId, Long ticketId, String party, String counterParty, Integer amount, String productType, LocalDate tradeDate, String workflow) {
        this.refId = refId;
        this.ticketId = ticketId;
        this.party = party;
        this.counterParty = counterParty;
        this.amount = amount;
        this.productType = productType;
        this.tradeDate = tradeDate;
        this.workflow = workflow;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setCounterParty(String counterParty) {
        this.counterParty = counterParty;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public String getParty() {
        return party;
    }

    public String getCounterParty() {
        return counterParty;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getProductType() {
        return productType;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public String getWorkflow() {
        return workflow;
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }
}
