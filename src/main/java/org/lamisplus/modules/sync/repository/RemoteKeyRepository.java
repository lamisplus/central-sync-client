package org.lamisplus.modules.sync.repository;

import org.lamisplus.modules.sync.domain.entity.RemoteKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface RemoteKeyRepository extends JpaRepository<RemoteKey, Long> {
    List<RemoteKey> findAll();

    Optional<RemoteKey> findByUuid(String uuid);

    Optional<RemoteKey> findById(Long id);

    //public int save(RemoteKey remoteKey)

}
