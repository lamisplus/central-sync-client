package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.ConfigDto;
import org.lamisplus.modules.central.domain.dto.ConfigModuleDto;
import org.lamisplus.modules.central.domain.dto.ConfigTableDto;
import org.lamisplus.modules.central.domain.entity.Config;
import org.lamisplus.modules.central.domain.entity.ConfigModule;
import org.lamisplus.modules.central.domain.entity.ConfigTable;
import org.lamisplus.modules.central.domain.entity.FacilityAppKey;
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
    private final FacilityAppKeyService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FacilityAppKey> create(@RequestBody FacilityAppKey facilityAppKey) {
        facilityAppKey.setId(java.util.UUID.randomUUID());
        return ResponseEntity.ok(service.Save(facilityAppKey));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FacilityAppKey>> getAll() {
        List<FacilityAppKey> keys = service.FindAll();
        return ResponseEntity.ok(keys);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FacilityAppKey> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.FindById(id));
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> Delete(@PathVariable("id") UUID id) {
        service.Delete(id);
        return ResponseEntity.accepted().build();
    }
}
