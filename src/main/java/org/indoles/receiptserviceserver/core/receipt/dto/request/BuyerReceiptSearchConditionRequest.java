package org.indoles.receiptserviceserver.core.receipt.dto.request;


import static org.indoles.receiptserviceserver.core.receipt.dto.validate.ValidateReceiptSearchCondition.validateOffset;
import static org.indoles.receiptserviceserver.core.receipt.dto.validate.ValidateReceiptSearchCondition.validateSizeBetween;

/**
 * 구매자가 거래 내역 목록 조회시 적용할 수 있는 조건을 나타내는 DTO
 * @param offset 조회할 거래 내역의 시작 위치 (default: 0)
 * @param size 조회할 거래 내역의 개수 (default: 10) (Min: 1, Max: 100)
 */
public record BuyerReceiptSearchConditionRequest(
        int offset,
        int size
) {

    public BuyerReceiptSearchConditionRequest {
        validateSizeBetween(1, 100, size);
        validateOffset(offset);
    }
}
