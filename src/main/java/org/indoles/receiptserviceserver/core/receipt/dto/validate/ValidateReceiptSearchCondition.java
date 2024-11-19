package org.indoles.receiptserviceserver.core.receipt.dto.validate;

import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.indoles.receiptserviceserver.global.exception.BadRequestException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;

import java.util.UUID;

public class ValidateReceiptSearchCondition {

    public static final String ERROR_NULL_VALUE = "%s는 Null일 수 없습니다.";
    public static final String ERROR_PRODUCT_NAME = "상품 이름은 비어있을 수 없습니다.";


    public static void validateSizeBetween(int from, int to, int size){
        if (size < from || size > to) {
            throw new BadRequestException("size는 " + from + " 이상 " + to + " 이하의 값이어야 합니다.", ErrorCode.G001);
        }
    }
    public static void validateOffset(int offset) {
        if (offset < 0) {
            throw new BadRequestException("offset은 0 이상의 값이어야 합니다.", ErrorCode.G002);
        }
    }

    /**
     * 경매 입찰 시 거래 내역 서버에 전송할 DTO의 유효성 검사
     */

    public static void validateAuctionId(long auctionId) {
        if (auctionId <= 0) {
            throw new BadRequestException("경매 ID를 찾을 수 없습니다.", ErrorCode.A032);
        }
    }

    public static void validateBuyerId(long buyerId) {
        if (buyerId <= 0) {
            throw new BadRequestException("구매자 ID를 찾을 수 없습니다.", ErrorCode.A033);
        }
    }

    public static void validateSellerId(long sellerId) {
        if (sellerId <= 0) {
            throw new BadRequestException("판매자 ID를 찾을 수 없습니다.", ErrorCode.A034);
        }
    }

    public static void validateReceiptStatus(ReceiptStatus receiptStatus) {
        validateNotNull(receiptStatus, "거래 내역 상태");
    }

    public static void validateReceiptId(UUID receiptId) {
        validateNotNull(receiptId, "거래 내역 ID");
    }

    public static void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new BadRequestException(String.format(ERROR_NULL_VALUE, fieldName), ErrorCode.G000);
        }
    }

    public static void validateProductName(String productName) {
        if (productName.trim().isEmpty()) {
            throw new BadRequestException(ERROR_PRODUCT_NAME, ErrorCode.A001);
        }
    }

    public static void validatePrice(long price) {
        if (price < 0) {
            throw new BadRequestException("경매 입찰 요청 가격은 음수일 수 없습니다. 요청가격: " + price, ErrorCode.A026);
        }
    }

    public static void validateQuantity(long quantity) {
        if (quantity <= 0) {
            throw new BadRequestException("경매 입찰 요청 수량은 0보다 커야합니다. 요청수량: " + quantity, ErrorCode.A027);
        }
    }
}
