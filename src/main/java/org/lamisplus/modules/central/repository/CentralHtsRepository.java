package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.CentralHts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CentralHtsRepository  extends JpaRepository<CentralHts, Long> {
    Optional<CentralHts> getCentralHtsByHtsUniqueNo(String htsUniqueNo);
}
