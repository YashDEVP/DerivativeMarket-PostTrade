package com.derivativemarket.posttrade.org.repositories;

import com.derivativemarket.posttrade.org.entities.CompanyEntity;
import com.derivativemarket.posttrade.org.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface SessionRepository extends JpaRepository<SessionEntity,Long> {

    List<SessionEntity> findByCompany(CompanyEntity companyEntity);

    Optional<SessionEntity> findByRefreshToken(String refreshToken);
}
