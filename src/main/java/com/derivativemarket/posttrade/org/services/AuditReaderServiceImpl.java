package com.derivativemarket.posttrade.org.services;

import com.derivativemarket.posttrade.org.dto.AuditiableDTO;
import com.derivativemarket.posttrade.org.dto.TradeDTO;
import com.derivativemarket.posttrade.org.entities.TradeEntity;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditReaderServiceImpl implements AuditReaderService{

    Logger logger= LoggerFactory.getLogger(AuditReaderServiceImpl.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private final ModelMapper modelMapper;

    public AuditReaderServiceImpl(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public List<TradeDTO> getAuditRecord(long refId) {
        try {
            logger.trace("starting getAuditRecord method");
            AuditReader reader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());
            List<Number> revisions = reader.getRevisions(TradeEntity.class, refId);
            List<TradeEntity> tradeEntities = revisions.stream().map(revisionNumber -> reader.find(TradeEntity.class, refId, revisionNumber)).collect(Collectors.toList());
            List<TradeDTO> tradeDTOS= tradeEntities
                    .stream()
                    .map(employeeEntity -> modelMapper.map(employeeEntity, TradeDTO.class))
                    .collect(Collectors.toList());
            logger.info("Successfully get the audit record: " + tradeDTOS );
            return tradeDTOS;
        }catch(Exception exception) {
            logger.error("Exception auditReaderServiceImpl: " + exception.getMessage());
            throw new RuntimeException(exception);
        }
    }
}
