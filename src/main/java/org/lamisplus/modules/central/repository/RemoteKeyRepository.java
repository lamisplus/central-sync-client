package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.RemoteKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RemoteKeyRepository extends JpaRepository<RemoteKey, Long> {
    List<RemoteKey> findAll();

    Optional<RemoteKey> findByUuid(String uuid);

    Optional<RemoteKey> findById(Long id);

    //public int save(RemoteKey remoteKey)

}
