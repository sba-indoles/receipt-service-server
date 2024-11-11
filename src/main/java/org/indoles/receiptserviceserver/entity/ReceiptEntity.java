package org.indoles.receiptserviceserver.entity;

import jakarta.persistence.*;
import lombok.*;
import org.indoles.receiptserviceserver.entity.enums.ReceiptStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "RECEIPT")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptEntity {

    @Id
    private UUID id;

    @NonNull
    private String productName;

    @NonNull
    private Long price;

    @NonNull
    private Long quantity;

    @NonNull
    @Enumerated(value = EnumType.STRING)
    private ReceiptStatus receiptStatus;

    @NonNull
    private Long auctionId;

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
            Long price,
            Long quantity,
            ReceiptStatus receiptStatus,
            Long auctionId,
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
