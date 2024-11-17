package org.indoles.receiptserviceserver.core.receipt.dto.response;

import lombok.Builder;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;

import java.util.UUID;

/**
 * 구매자가 거래 내역 목록을 조회할 때, 노출되는 각 내역의 데이터를 나타내는 DTO
 *
 * @param id          거내 내역 식별자입니다.
 * @param auctionId   구매한 경매의 식별자입니다.
 * @param type        거래 타입 {@link ReceiptStatus}
 * @param productName 상품명
 * @param quantity    구매 수량
 * @param price       구매 가격
 */
@Builder
public record BuyerReceiptSimpleInfoResponse(
        UUID id,
        Long auctionId,
        ReceiptStatus type,
        String productName,
        Long quantity,
        Long price
) {
}
