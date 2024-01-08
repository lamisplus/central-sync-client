package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.IllegalTypeException;
import org.lamisplus.modules.central.domain.dto.FacilityAppKeyDto;
import org.lamisplus.modules.central.domain.entity.FacilityAppKey;
import org.lamisplus.modules.central.domain.mapper.SyncMapper;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.service.FacilityAppKeyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/sync/app-key")
public class SyncAppKeyController {
    public static final String SERVER_URL_SUFFIX = "/";
    private final FacilityAppKeyService service;
    private final SyncHistoryRepository syncHistoryRepository;
    private final SyncMapper mapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FacilityAppKey> create(@RequestBody FacilityAppKey facilityAppKey) {
        if(facilityAppKey.getServerUrl().endsWith(SERVER_URL_SUFFIX)){
            throw new IllegalTypeException(FacilityAppKey.class, "Server url issue", "check url");
        }
        facilityAppKey.setId(java.util.UUID.randomUUID());
        return ResponseEntity.ok(service.Save(facilityAppKey));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FacilityAppKeyDto>> getAll() {
        List<FacilityAppKeyDto> keys = mapper.toFacilityAppKeyDtoList(service.FindAll());
        for(FacilityAppKeyDto key : keys){
            key.setFacilityName(syncHistoryRepository.getFacilityNameById(Long.valueOf(key.getFacilityId())).orElse(""));
        }
        return ResponseEntity.ok(keys);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FacilityAppKeyDto> getById(@PathVariable("id") UUID id) {
        FacilityAppKeyDto key=mapper.toFacilityAppKeyDto(service.FindById(id));
        key.setFacilityName(syncHistoryRepository.getFacilityNameById(Long.valueOf(key.getFacilityId())).orElse(""));
        return ResponseEntity.ok(key);
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> Delete(@PathVariable("id") UUID id) {
        service.Delete(id);
        return ResponseEntity.accepted().build();
    }
}
