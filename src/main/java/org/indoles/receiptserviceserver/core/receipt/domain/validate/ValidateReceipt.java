package org.indoles.receiptserviceserver.core.receipt.domain.validate;

import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.indoles.receiptserviceserver.global.exception.BadRequestException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;

import java.time.LocalDateTime;

public class ValidateReceipt {

    public static final String ERROR_VARIATION_UPDATE_AT = "생성 시간보다 수정 시간이 더 작을 수 없습니다. 생성시간: %s, 수정시간: %s";
    public static final String ERROR_ALREADY_REFUNDED = "이미 환불된 입찰 내역입니다.";

    public static void validateUpdateAt(LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException(String.format(ERROR_VARIATION_UPDATE_AT, createdAt, updatedAt));
        }
    }

    public static void validateRefund(ReceiptStatus receiptStatus) {
        if (receiptStatus.equals(ReceiptStatus.REFUND)) {
            throw new BadRequestException(ERROR_ALREADY_REFUNDED, ErrorCode.R002);
        }
    }
}
