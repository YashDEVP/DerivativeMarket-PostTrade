package com.derivativemarket.posttrade.org.services;

import com.derivativemarket.posttrade.org.entities.CompanyEntity;
import com.derivativemarket.posttrade.org.entities.SessionEntity;
import com.derivativemarket.posttrade.org.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT=2;

    public void generateNewSession(CompanyEntity companyEntity,String refreshToken){
        List<SessionEntity> userSession=sessionRepository.findByCompany(companyEntity);
        if(userSession.size()==SESSION_LIMIT){
            userSession.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
            SessionEntity leastRecentlyUsedSession=userSession.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        SessionEntity newSession=SessionEntity.builder()
                .company(companyEntity)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(newSession);
    }
    public void validateSession(String refreshToken){
        SessionEntity session= sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new SessionAuthenticationException("Session not found for RefreshToken"+
                        refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

}
