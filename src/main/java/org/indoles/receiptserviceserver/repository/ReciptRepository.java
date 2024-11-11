package org.indoles.receiptserviceserver.repository;

import org.indoles.receiptserviceserver.entity.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReciptRepository extends JpaRepository<ReceiptEntity, UUID> {

    Optional<ReceiptEntity> findById(UUID id);

    List<ReceiptEntity> findAllByBuyerId(Long buyerId);

    List<ReceiptEntity> findAllBySellerId(Long sellerId);

    Optional<ReceiptEntity> findByIdAndUpdatedAt(UUID id, LocalDateTime updatedAt);
}
