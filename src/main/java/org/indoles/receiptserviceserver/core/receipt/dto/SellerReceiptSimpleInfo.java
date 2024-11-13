package org.indoles.receiptserviceserver.core.receipt.dto;

import java.util.UUID;
import lombok.Builder;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;

/**
 * 판매자가 자신의 경매와 관련있는 거래 내역 목록을 조회 시 사용하는  DTO
 *
 * @param id          거래 내역 식별자
 * @param auctionId   경매 식별자
 * @param type        거래 타입 {@link ReceiptStatus}
 * @param productName 상품명
 * @param price       거래 가격
 * @param quantity    거래 수량
 */
@Builder
public record SellerReceiptSimpleInfo(
        UUID id,
        Long auctionId,
        ReceiptStatus type,
        String productName,
        Long price,
        Long quantity
) {
}
