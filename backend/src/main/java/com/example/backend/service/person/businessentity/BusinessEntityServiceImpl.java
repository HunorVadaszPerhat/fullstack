package com.example.backend.service.person.businessentity;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.repository.person.businessentity.BusinessEntityRepository;
import com.example.backend.dto.person.businessentity.BusinessEntityDto;
import com.example.backend.mapper.person.businessentity.BusinessEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BusinessEntityServiceImpl implements BusinessEntityService {

    private final BusinessEntityRepository businessEntityRepository;
    private final BusinessEntityMapper businessEntityMapper;

    @Override
    public BusinessEntityDto create(BusinessEntityDto dto) {
        BusinessEntity entity = businessEntityMapper.toEntity(dto);
        BusinessEntity saved = businessEntityRepository.save(entity);
        return businessEntityMapper.toDto(saved);
    }

    @Override
    public BusinessEntityDto getById(Integer id) {
        BusinessEntity entity = businessEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found"));
        return businessEntityMapper.toDto(entity);
    }

    @Override
    public List<BusinessEntityDto> getAll() {
        return businessEntityRepository.findAll().stream()
                .map(businessEntityMapper::toDto)
                .toList();
    }

    @Override
    public BusinessEntityDto update(Integer id, BusinessEntityDto dto) {
        BusinessEntity entity = businessEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found"));

        entity.setRowguid(dto.getRowguid());
        entity.setModifiedDate(dto.getModifiedDate());

        return businessEntityMapper.toDto(businessEntityRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!businessEntityRepository.existsById(id)) {
            throw new EntityNotFoundException("BusinessEntity not found");
        }
        businessEntityRepository.deleteById(id);
    }
}