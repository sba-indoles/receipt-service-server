package org.indoles.receiptserviceserver.core.receipt.dto.response;

import lombok.Builder;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ReceiptInfoResponse(
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
