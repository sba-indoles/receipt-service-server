package org.indoles.receiptserviceserver.global.dto;



import org.indoles.receiptserviceserver.core.receipt.dto.SignInInfo;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuctionRefundRequestMessage(
        SignInInfo buyerInfo,
        UUID receiptId,
        LocalDateTime requestTime
) {
}
