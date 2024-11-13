package org.indoles.receiptserviceserver.core.receipt.dto;

import lombok.Builder;
import org.indoles.receiptserviceserver.core.receipt.domain.ReceiptStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ReceiptInfo(
        UUID receiptId,
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
}
