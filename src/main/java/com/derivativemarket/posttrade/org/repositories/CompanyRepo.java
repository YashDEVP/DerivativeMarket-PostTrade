package com.derivativemarket.posttrade.org.repositories;

import com.derivativemarket.posttrade.org.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity,Long> {
    Optional<CompanyEntity> findByCompanyEmail(String email);
}