package com.derivativemarket.posttrade.org.services;

import com.derivativemarket.posttrade.org.dto.AuditiableDTO;
import com.derivativemarket.posttrade.org.dto.TradeDTO;
import com.derivativemarket.posttrade.org.entities.TradeEntity;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditReaderServiceImpl implements AuditReaderService{
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private final ModelMapper modelMapper;

    public AuditReaderServiceImpl(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public List<TradeDTO> getAuditRecord(long refId) {
        AuditReader reader= AuditReaderFactory.get(entityManagerFactory.createEntityManager());
        List<Number> revisions=reader.getRevisions(TradeEntity.class,refId);
        List<TradeEntity> tradeEntities= revisions.stream().map(revisionNumber-> reader.find(TradeEntity.class,refId,revisionNumber)).collect(Collectors.toList());
        return tradeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, TradeDTO.class))
                .collect(Collectors.toList());
    }
}
