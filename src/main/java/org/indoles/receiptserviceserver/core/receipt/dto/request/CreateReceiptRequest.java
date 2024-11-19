package org.indoles.receiptserviceserver.core.receipt.dto.request;

import lombok.Builder;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;

import java.util.UUID;

import static org.indoles.receiptserviceserver.core.receipt.dto.validate.ValidateReceiptSearchCondition.*;

@Builder
public record CreateReceiptRequest(
        UUID receiptId,
        String productName,
        long price,
        long quantity,
        ReceiptStatus receiptStatus,
        long auctionId,
        long sellerId,
        long buyerId
) {
    public CreateReceiptRequest {
        validateReceiptId(receiptId);
        validateProductName(productName);
        validatePrice(price);
        validateQuantity(quantity);
        validateReceiptStatus(receiptStatus);
        validateAuctionId(auctionId);
        validateSellerId(sellerId);
        validateBuyerId(buyerId);
    }
}
