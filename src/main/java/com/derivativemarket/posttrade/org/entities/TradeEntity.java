package com.derivativemarket.posttrade.org.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class TradeEntity extends AuditiableEntity{

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
