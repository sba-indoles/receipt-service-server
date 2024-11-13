package org.indoles.receiptserviceserver.core.receipt.dto;

import lombok.Builder;
import org.indoles.receiptserviceserver.core.receipt.domain.ReceiptStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ReceiptInfo(
        UUID receiptId,
        String productName,
        long price,
        long quantity,
        ReceiptStatus receiptStatus,
        long auctionId,
        long sellerId,
        long buyerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}