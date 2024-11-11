package org.indoles.receiptserviceserver.core.payment.infra;

import jakarta.persistence.LockModeType;
import org.indoles.receiptserviceserver.core.payment.entity.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ReceiptJpaRepository extends JpaRepository<ReceiptEntity, UUID>, ReceiptQueryDslRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from ReceiptEntity r where r.id = :id")
    Optional<ReceiptEntity> findByIdForUpdate(UUID id);
}

