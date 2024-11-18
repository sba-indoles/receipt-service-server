package org.indoles.receiptserviceserver.global.dto;



import org.indoles.receiptserviceserver.core.receipt.dto.request.SignInfoRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuctionRefundRequestMessage(
        SignInfoRequest buyerInfo,
        UUID receiptId,
        LocalDateTime requestTime
) {
}
