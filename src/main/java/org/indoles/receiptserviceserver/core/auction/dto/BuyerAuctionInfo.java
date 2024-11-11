package org.indoles.receiptserviceserver.core.auction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import org.indoles.receiptserviceserver.core.auction.domain.PricePolicy;
import org.indoles.receiptserviceserver.global.exception.BadRequestException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * 구매자가 조회할 수 있는 경매 정보 - isShowStock에 따라서 currentStock 노출 여부가 결정됩니다
 */
@Builder
public record BuyerAuctionInfo(
        Long auctionId,
        Long sellerId,
        String productName,
        long originPrice,
        long currentPrice,
        @JsonInclude(NON_NULL)
        Long originStock,
        @JsonInclude(NON_NULL)
        Long currentStock,
        long maximumPurchaseLimitCount,
        PricePolicy pricePolicy,
        Duration variationDuration,
        LocalDateTime startedAt,
        LocalDateTime finishedAt
) {

    public static final String ERROR_PRODUCT_NAME = "상품 이름은 비어있을 수 없습니다.";
    public static final String ERROR_ORIGIN_PRICE = "상품 원가는 0보다 커야 합니다. 상품 원가: %d";
    public static final String ERROR_CURRENT_PRICE = "현재 가격은 0보다 커야 합니다. 현재 가격: %d";
    public static final String ERROR_STOCK = "재고는 0보다 작을 수 없습니다. 재고: %d";
    public static final String ERROR_MAXIMUM_PURCHASE_LIMIT_COUNT = "최대 구매 수량 제한은 0보다 커야 합니다. 최대 구매 수량 제한: %d";
    public static final String ERROR_VARIATION_DURATION = "변동 시간 단위는 0보다 커야 합니다. 변동 시간: %s";
    public static final String ERROR_NULL_VALUE = "%s는 Null일 수 없습니다.";

    public BuyerAuctionInfo {
        validateNotNull(auctionId, "경매 ID");
        validateNotNull(sellerId, "판매자 ID");
        validateNotNull(productName, "상품 이름");
        validateNotNull(pricePolicy, "경매 유형");
        validateNotNull(variationDuration, "가격 변동 주기");
        validateNotNull(startedAt, "경매 시작 시간");
        validateNotNull(finishedAt, "경매 종료 시간");

        validateProductName(productName);
        validateOriginPrice(originPrice);
        validateCurrentPrice(currentPrice);
        validateMaximumPurchaseLimitCount(maximumPurchaseLimitCount);
        validateVariationDuration(variationDuration);
    }

    private void validateProductName(String productName) {
        if (productName.isEmpty()) {
            throw new BadRequestException(ERROR_PRODUCT_NAME, ErrorCode.A001);
        }
    }

    private void validateOriginPrice(long originPrice) {
        if (originPrice <= 0) {
            throw new BadRequestException(String.format(ERROR_ORIGIN_PRICE, originPrice), ErrorCode.A002);
        }
    }

    private void validateCurrentPrice(long currentPrice) {
        if (currentPrice <= 0) {
            throw new BadRequestException(String.format(ERROR_CURRENT_PRICE, currentPrice), ErrorCode.A011);
        }
    }

    private void validateMaximumPurchaseLimitCount(long maximumPurchaseLimitCount) {
        if (maximumPurchaseLimitCount <= 0) {
            throw new BadRequestException(String.format(ERROR_MAXIMUM_PURCHASE_LIMIT_COUNT, maximumPurchaseLimitCount),
                    ErrorCode.A003);
        }
    }

    private void validateVariationDuration(Duration variationDuration) {
        if (variationDuration.isNegative() || variationDuration.isZero()) {
            throw new BadRequestException(String.format(ERROR_VARIATION_DURATION, variationDuration), ErrorCode.A005);
        }
    }

    private void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new BadRequestException(String.format(ERROR_NULL_VALUE, fieldName), ErrorCode.G000);
        }
    }
}