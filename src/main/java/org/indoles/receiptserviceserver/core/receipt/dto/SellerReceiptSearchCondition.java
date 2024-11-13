package org.indoles.receiptserviceserver.core.receipt.dto;


import static org.indoles.receiptserviceserver.core.receipt.dto.validate.ValidateReceiptSearchCondition.validateSizeBetween;

/**
 * 판매자가 거래 내역 목록 조회시 적용할 수 있는 조건을 나타내는 DTO
 *
 * @param size 조회할 거래 내역의 개수 (default: 10) (Min: 1, Max: 100)
 */
public record SellerReceiptSearchCondition(
        int offset,
        int size
) {

    public SellerReceiptSearchCondition {
        validateSizeBetween(1, 100, size);
    }
}
