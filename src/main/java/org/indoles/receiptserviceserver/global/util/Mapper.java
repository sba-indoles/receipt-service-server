package org.indoles.receiptserviceserver.global.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.dto.BuyerReceiptSimpleInfo;
import org.indoles.receiptserviceserver.core.receipt.dto.ReceiptInfo;
import org.indoles.receiptserviceserver.core.receipt.dto.SellerReceiptSimpleInfo;
import org.indoles.receiptserviceserver.core.receipt.entity.ReceiptEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Mapper {

    public static ReceiptInfo convertToReceiptInfo(Receipt receipt) {
        return ReceiptInfo.builder()
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

    public static BuyerReceiptSimpleInfo convertToBuyerReceiptSimpleInfo(Receipt history) {
        return BuyerReceiptSimpleInfo.builder()
                .id(history.getId())
                .auctionId(history.getAuctionId())
                .type(history.getReceiptStatus())
                .productName(history.getProductName())
                .price(history.getPrice())
                .quantity(history.getQuantity())
                .build();
    }

    public static SellerReceiptSimpleInfo convertToSellerReceiptSimpleInfo(Receipt history) {
        return SellerReceiptSimpleInfo.builder()
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
