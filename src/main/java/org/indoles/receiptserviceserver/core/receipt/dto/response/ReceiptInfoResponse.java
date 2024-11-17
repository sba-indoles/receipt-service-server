package org.indoles.receiptserviceserver.core.receipt.dto.response;

import lombok.Builder;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ReceiptInfoResponse(
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
