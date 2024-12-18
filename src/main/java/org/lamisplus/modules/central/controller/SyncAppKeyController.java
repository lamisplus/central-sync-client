package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.IllegalTypeException;
import org.lamisplus.modules.central.domain.dto.FacilityAppKeyDto;
import org.lamisplus.modules.central.domain.entity.FacilityAppKey;
import org.lamisplus.modules.central.domain.mapper.SyncMapper;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.service.FacilityAppKeyService;
import org.springframework.beans.BeanUtils;
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
    public ResponseEntity<FacilityAppKey> create(@RequestBody FacilityAppKeyDto facilityAppKeyDto) {
        if(facilityAppKeyDto.getServerUrl().endsWith(SERVER_URL_SUFFIX)){
            throw new IllegalTypeException(FacilityAppKey.class, "Server url issue", "check url");
        }
        facilityAppKeyDto.setId(java.util.UUID.randomUUID());
        FacilityAppKey facilityAppKey = new FacilityAppKey();
        BeanUtils.copyProperties(facilityAppKey, facilityAppKeyDto);
        return ResponseEntity.ok(service.save(facilityAppKey));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FacilityAppKeyDto>> getAll() {
        List<FacilityAppKeyDto> keys = mapper.toFacilityAppKeyDtoList(service.findAll());
        for(FacilityAppKeyDto key : keys){
            key.setFacilityName(syncHistoryRepository.getFacilityNameById(Long.valueOf(key.getFacilityId())).orElse(""));
        }
        return ResponseEntity.ok(keys);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FacilityAppKeyDto> getById(@PathVariable("id") UUID id) {
        FacilityAppKeyDto key=mapper.toFacilityAppKeyDto(service.findById(id));
        key.setFacilityName(syncHistoryRepository.getFacilityNameById(Long.valueOf(key.getFacilityId())).orElse(""));
        return ResponseEntity.ok(key);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.accepted().build();
    }
}
