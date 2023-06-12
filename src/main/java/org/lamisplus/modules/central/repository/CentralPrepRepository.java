package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.CentralHts;
import org.lamisplus.modules.central.domain.entity.CentralPrep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CentralPrepRepository  extends JpaRepository<CentralPrep, Long> {
    Optional<CentralPrep> getCentralPrepByPrepUniqueNo(String prepUniqueNo);
}
