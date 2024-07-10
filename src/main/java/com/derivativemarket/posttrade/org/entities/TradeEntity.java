package com.derivativemarket.posttrade.org.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/* we can store sensitive information here instead of TradeDTO it is not exposed to user
*/
/*@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor all annotation are related to lombok */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="isdaaccount")
public class TradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long refId;

    private Long ticketId;

    private String party;

    private String counterParty;

    private Integer amount;

    private String productType;

    private LocalDate tradeDate;

    private String workflow;

}