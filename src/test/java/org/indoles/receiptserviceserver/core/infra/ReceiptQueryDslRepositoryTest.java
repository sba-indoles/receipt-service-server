package org.indoles.receiptserviceserver.core.infra;

import org.indoles.receiptserviceserver.core.context.RepositoryTest;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.indoles.receiptserviceserver.core.receipt.dto.request.BuyerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.request.SellerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.entity.ReceiptEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptQueryDslRepositoryTest extends RepositoryTest {

    @Nested
    class BuyerReceiptSearch {

        @Test
        @DisplayName("구매자는 조회 갯수만큼 거래 내역을 조회한다.")
        void buyerReceiptSearch_Success() {
            // given
            Long buyerId = 1L;
            int size = 100;
            int offset = 0;
            var condition = new BuyerReceiptSearchConditionRequest(offset, size);

            for (int i = 0; i < size + 1; i++) {
                receiptJpaRepository.save(ReceiptEntity.builder()
                        .id(UUID.randomUUID())
                        .productName("상품1")
                        .price(1000)
                        .quantity(1)
                        .receiptStatus(ReceiptStatus.PURCHASED)
                        .auctionId(4L)
                        .buyerId(buyerId)
                        .sellerId(2L)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
            }

            // when
            List<ReceiptEntity> receipts = receiptJpaRepository.findAllByBuyerId(buyerId, condition);

            // then
            assertThat(receipts).hasSize(size);
        }
    }

    @Test
    @DisplayName("구매자는 자신의 거래 내역만 조회한다.")
    void buyerReceiptSearch_OnlySelf() {
        // given
        Long buyerId = 1L;
        Long otherBuyerId = 2L;
        int size = 100;
        int offset = 10;
        var condition = new BuyerReceiptSearchConditionRequest(size, offset);

        receiptJpaRepository.save(ReceiptEntity.builder()
                .id(UUID.randomUUID())
                .productName("상품1")
                .price(1000)
                .quantity(1)
                .receiptStatus(ReceiptStatus.PURCHASED)
                .auctionId(4L)
                .buyerId(buyerId)
                .sellerId(2L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        receiptJpaRepository.save(ReceiptEntity.builder()
                .id(UUID.randomUUID())
                .productName("상품1")
                .price(1000)
                .quantity(1)
                .receiptStatus(ReceiptStatus.PURCHASED)
                .auctionId(4L)
                .buyerId(otherBuyerId)
                .sellerId(2L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        // when
        List<ReceiptEntity> receipts = receiptJpaRepository.findAllByBuyerId(buyerId, condition);

        // then
        assertThat(receipts)
                .map(ReceiptEntity::getBuyerId)
                .allMatch(receiptBuyerId -> Objects.equals(receiptBuyerId, buyerId));
    }

    @Nested
    class SellerReceiptSearch {

        @Test
        @DisplayName("판매자는 조회 갯수만큼 거래 내역을 조회한다.")
        void sellerReceiptSearch_Success() {
            // given
            Long sellerId = 1L;
            int size = 100;
            int offset = 0;
            var condition = new SellerReceiptSearchConditionRequest(offset, size);

            for (int i = 0; i < size + 1; i++) {
                receiptJpaRepository.save(ReceiptEntity.builder()
                        .id(UUID.randomUUID())
                        .productName("상품1")
                        .price(1000)
                        .quantity(1)
                        .receiptStatus(ReceiptStatus.PURCHASED)
                        .auctionId(4L)
                        .buyerId(2L)
                        .sellerId(sellerId)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
            }

            // when
            List<ReceiptEntity> receipts = receiptJpaRepository.findAllBySellerId(sellerId, condition);

            // then
            assertThat(receipts).hasSize(size);
        }

        @Test
        @DisplayName("판매자는 자신의 거래 내역만 조회한다.")
        void sellerReceiptSearch_OnlySelf() {
            // given
            Long sellerId = 1L;
            Long otherSellerId = 2L;
            int size = 100;
            int offset = 0;
            var condition = new SellerReceiptSearchConditionRequest(offset, size);

            receiptJpaRepository.save(ReceiptEntity.builder()
                    .id(UUID.randomUUID())
                    .productName("상품1")
                    .price(1000)
                    .quantity(1)
                    .receiptStatus(ReceiptStatus.PURCHASED)
                    .auctionId(4L)
                    .buyerId(2L)
                    .sellerId(sellerId)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());

            receiptJpaRepository.save(ReceiptEntity.builder()
                    .id(UUID.randomUUID())
                    .productName("상품1")
                    .price(1000)
                    .quantity(1)
                    .receiptStatus(ReceiptStatus.PURCHASED)
                    .auctionId(4L)
                    .buyerId(2L)
                    .sellerId(otherSellerId)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());
            // when
            List<ReceiptEntity> receipts = receiptJpaRepository.findAllBySellerId(sellerId, condition);

            // then
            assertThat(receipts)
                    .map(ReceiptEntity::getSellerId)
                    .allMatch(receiptSellerId -> Objects.equals(receiptSellerId, sellerId));
        }
    }
}
