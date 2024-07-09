package com.derivativemarket.posttrade.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.derivativemarket.posttrade.org.entities.TradeEntity;


public interface MarketRepository extends JpaRepository<TradeEntity,Long> {

}
