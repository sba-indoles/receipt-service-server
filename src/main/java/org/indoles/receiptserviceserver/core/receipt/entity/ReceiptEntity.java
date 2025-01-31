package org.indoles.receiptserviceserver.core.receipt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "RECEIPT")
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptEntity {

    @Id
    private UUID id;

    private String productName;

    private long price;

    private long quantity;

    @Enumerated(value = EnumType.STRING)
    private ReceiptStatus receiptStatus;

    private long auctionId;

    @NonNull
    private Long sellerId;

    @NonNull
    private Long buyerId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    private ReceiptEntity(
            UUID id,
            String productName,
            long price,
            long quantity,
            ReceiptStatus receiptStatus,
            long auctionId,
            Long sellerId,
            Long buyerId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.receiptStatus = receiptStatus;
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
