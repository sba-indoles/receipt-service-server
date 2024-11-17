package org.indoles.receiptserviceserver.global.dto;



import org.indoles.receiptserviceserver.core.receipt.dto.response.SignInInfoResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuctionRefundRequestMessage(
        SignInInfoResponse buyerInfo,
        UUID receiptId,
        LocalDateTime requestTime
) {
}
