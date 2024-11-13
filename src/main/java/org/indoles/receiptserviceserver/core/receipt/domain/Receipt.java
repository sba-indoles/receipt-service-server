package org.indoles.receiptserviceserver.core.receipt.domain;


import lombok.Builder;
import lombok.Getter;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.indoles.receiptserviceserver.core.receipt.domain.validate.ValidateReceipt;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Receipt {

    private UUID id;
    private String productName;
    private long price;
    private long quantity;
    private ReceiptStatus receiptStatus;
    private long auctionId;
    private long sellerId;
    private long buyerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static final String ERROR_VARIATION_UPDATE_AT = "생성 시간보다 수정 시간이 더 작을 수 없습니다. 생성시간: %s, 수정시간: %s";

    @Builder
    public Receipt(
            final UUID id,
            final String productName,
            final long price,
            final long quantity,
            final ReceiptStatus receiptStatus,
            final long auctionId,
            final long sellerId,
            final long buyerId,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {
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

        ValidateReceipt.validateUpdateAt(this.createdAt, this.updatedAt);
    }

    public void markAsRefund() {
        ValidateReceipt.validateRefund(this.receiptStatus);
        this.receiptStatus = ReceiptStatus.REFUND;
    }

    public boolean isOwnedBy(long requestUserId) {
        return sellerId == requestUserId || buyerId == requestUserId;
    }

}
