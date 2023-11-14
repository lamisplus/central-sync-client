package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.entity.Config;
import org.lamisplus.modules.central.domain.entity.FacilityAppKey;
import org.lamisplus.modules.central.repository.ConfigRepository;
import org.lamisplus.modules.central.repository.FacilityAppKeyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacilityAppKeyService {
    public final FacilityAppKeyRepository repository;

    public FacilityAppKey Save(FacilityAppKey appKey){
        return repository.save(appKey);
    }

    public FacilityAppKey FindById(UUID Id){
        return repository.findById(Id).orElse(null);
    }

    public List<FacilityAppKey> FindAll(){
        return repository.findAll();
    }

    public FacilityAppKey FindByFacilityId(String facilityId){
        return repository.findFacilityAppKeyByFacilityId(facilityId);
    }

    public void Delete(UUID id){
        repository.deleteById(id);
    }
}
