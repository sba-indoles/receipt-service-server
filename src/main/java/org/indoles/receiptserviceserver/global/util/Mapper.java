package org.indoles.receiptserviceserver.global.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.dto.response.BuyerReceiptSimpleInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.response.ReceiptInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.response.SellerReceiptSimpleInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.entity.ReceiptEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Mapper {

    public static ReceiptInfoResponse convertToReceiptInfo(Receipt receipt) {
        return ReceiptInfoResponse.builder()
                .receiptId(receipt.getId())
                .productName(receipt.getProductName())
                .price(receipt.getPrice())
                .quantity(receipt.getQuantity())
                .receiptStatus(receipt.getReceiptStatus())
                .auctionId(receipt.getAuctionId())
                .sellerId(receipt.getSellerId())
                .buyerId(receipt.getBuyerId())
                .createdAt(receipt.getCreatedAt())
                .updatedAt(receipt.getUpdatedAt())
                .build();
    }

    public static BuyerReceiptSimpleInfoResponse convertToBuyerReceiptSimpleInfo(Receipt history) {
        return BuyerReceiptSimpleInfoResponse.builder()
                .id(history.getId())
                .auctionId(history.getAuctionId())
                .type(history.getReceiptStatus())
                .productName(history.getProductName())
                .price(history.getPrice())
                .quantity(history.getQuantity())
                .build();
    }

    public static SellerReceiptSimpleInfoResponse convertToSellerReceiptSimpleInfo(Receipt history) {
        return SellerReceiptSimpleInfoResponse.builder()
                .id(history.getId())
                .auctionId(history.getAuctionId())
                .type(history.getReceiptStatus())
                .productName(history.getProductName())
                .price(history.getPrice())
                .quantity(history.getQuantity())
                .build();
    }

    public static Receipt convertToReceipt(ReceiptEntity receiptEntity) {
        return Receipt.builder()
                .id(receiptEntity.getId())
                .productName(receiptEntity.getProductName())
                .price(receiptEntity.getPrice())
                .quantity(receiptEntity.getQuantity())
                .receiptStatus(receiptEntity.getReceiptStatus())
                .auctionId(receiptEntity.getAuctionId())
                .sellerId(receiptEntity.getSellerId())
                .buyerId(receiptEntity.getBuyerId())
                .createdAt(receiptEntity.getCreatedAt())
                .updatedAt(receiptEntity.getUpdatedAt())
                .build();
    }

    public static ReceiptEntity convertToReceiptEntity(Receipt receipt) {
        return ReceiptEntity.builder()
                .id(receipt.getId())
                .productName(receipt.getProductName())
                .price(receipt.getPrice())
                .quantity(receipt.getQuantity())
                .receiptStatus(receipt.getReceiptStatus())
                .auctionId(receipt.getAuctionId())
                .sellerId(receipt.getSellerId())
                .buyerId(receipt.getBuyerId())
                .createdAt(receipt.getCreatedAt())
                .updatedAt(receipt.getUpdatedAt())
                .build();
    }
}
