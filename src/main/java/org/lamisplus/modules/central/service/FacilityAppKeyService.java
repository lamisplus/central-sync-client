package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.central.domain.entity.FacilityAppKey;
import org.lamisplus.modules.central.repository.FacilityAppKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacilityAppKeyService {
    public final FacilityAppKeyRepository repository;

    public FacilityAppKey save(FacilityAppKey appKey){
        List<FacilityAppKey> facilityAppKeys = repository.findFacilityAppKeyByFacilityId(appKey.getFacilityId());
        if(!facilityAppKeys.isEmpty()){
            log.info("Found app key for facility ID: {} ", appKey.getFacilityId());
            appKey.setId(facilityAppKeys.get(0).getId());
        } else {
            appKey.setId(UUID.randomUUID());
        }
        return repository.save(appKey);
    }

    public FacilityAppKey findById(UUID id){
        return repository.findById(id).orElse(null);
    }

    public List<FacilityAppKey> findAll(){
        return repository.findAll();
    }

    public FacilityAppKey findByFacilityId(int facilityId){
        return repository
                .findByFacilityId(facilityId)
                .orElseThrow(()-> new EntityNotFoundException(FacilityAppKey.class, "facility id", "facility id"));
    }

    public void delete(UUID id){
        repository.deleteById(id);
    }
}
