package org.indoles.receiptserviceserver.core.member.infra;


import org.indoles.receiptserviceserver.core.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findBySignInId(String signInId);

    boolean existsBySignInId(String signInId);
}
