package org.indoles.receiptserviceserver.global;

import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.indoles.receiptserviceserver.core.receipt.entity.ReceiptEntity;
import org.indoles.receiptserviceserver.global.util.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReceiptMapperTest {

    @Test
    @DisplayName("거래 내역 엔티티를 도메인으로 변환하면 정보가 동일하다")
    void transferReceiptEntityToReceipt() {
        // given
        ReceiptEntity entity = ReceiptEntity.builder()
                .id(UUID.randomUUID())
                .auctionId(2L)
                .productName("상품 이름")
                .price(1000L)
                .quantity(1L)
                .sellerId(3L)
                .buyerId(4L)
                .receiptStatus(ReceiptStatus.PURCHASED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(1))
                .build();

        // when
        Receipt domainEntity = Mapper.convertToReceipt(entity);

        // then
        assertAll(
                () -> assertEquals(entity.getId(), domainEntity.getId()),
                () -> assertEquals(entity.getAuctionId(), domainEntity.getAuctionId()),
                () -> assertEquals(entity.getProductName(), domainEntity.getProductName()),
                () -> assertEquals(entity.getPrice(), domainEntity.getPrice()),
                () -> assertEquals(entity.getQuantity(), domainEntity.getQuantity()),
                () -> assertEquals(entity.getSellerId(), domainEntity.getSellerId()),
                () -> assertEquals(entity.getBuyerId(), domainEntity.getBuyerId()),
                () -> assertEquals(entity.getReceiptStatus(), domainEntity.getReceiptStatus()),
                () -> assertEquals(entity.getCreatedAt(), domainEntity.getCreatedAt()),
                () -> assertEquals(entity.getUpdatedAt(), domainEntity.getUpdatedAt())
        );
    }

    @Test
    @DisplayName("거래 내역 도메인을 엔티티로 변환하면 정보가 동일하다")
    void transferReceiptToReceiptEntity() {
        // given
        Receipt domainEntity = Receipt.builder()
                .id(UUID.randomUUID())
                .auctionId(2L)
                .productName("상품 이름")
                .price(1000L)
                .quantity(1L)
                .sellerId(3L)
                .buyerId(4L)
                .receiptStatus(ReceiptStatus.PURCHASED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(1))
                .build();

        // when
        ReceiptEntity entity = Mapper.convertToReceiptEntity(domainEntity);

        // then
        assertAll(
                () -> assertEquals(domainEntity.getId(), entity.getId()),
                () -> assertEquals(domainEntity.getAuctionId(), entity.getAuctionId()),
                () -> assertEquals(domainEntity.getProductName(), entity.getProductName()),
                () -> assertEquals(domainEntity.getPrice(), entity.getPrice()),
                () -> assertEquals(domainEntity.getQuantity(), entity.getQuantity()),
                () -> assertEquals(domainEntity.getSellerId(), entity.getSellerId()),
                () -> assertEquals(domainEntity.getBuyerId(), entity.getBuyerId()),
                () -> assertEquals(domainEntity.getReceiptStatus(), entity.getReceiptStatus()),
                () -> assertEquals(domainEntity.getCreatedAt(), entity.getCreatedAt()),
                () -> assertEquals(domainEntity.getUpdatedAt(), entity.getUpdatedAt())
        );
    }
}
