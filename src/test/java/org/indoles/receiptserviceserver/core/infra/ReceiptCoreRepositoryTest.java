package org.indoles.receiptserviceserver.core.infra;

import org.indoles.receiptserviceserver.core.context.RepositoryTest;
import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.indoles.receiptserviceserver.core.receipt.infra.ReceiptCoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReceiptCoreRepositoryTest extends RepositoryTest {

    @Autowired
    ReceiptCoreRepository receiptCoreRepository;

    @Nested
    class findReceipt {

        @Test
        @DisplayName("거래내역의 id로 거래내역을 조회한다")
        void findReceiptById_Success() {
            // given
            Receipt receipt = Receipt.builder()
                    .id(UUID.randomUUID())
                    .productName("상품 이름")
                    .price(1000L)
                    .quantity(1L)
                    .receiptStatus(ReceiptStatus.PURCHASED)
                    .auctionId(1L)
                    .sellerId(1L)
                    .buyerId(2L)
                    .build();
            Receipt saved = receiptCoreRepository.save(receipt);

            // when
            Receipt found = receiptCoreRepository.findById(saved.getId()).get();

            // then
            assertAll(
                    () -> assertThat(found.getId()).isEqualTo(saved.getId()),
                    () -> assertThat(found.getProductName()).isEqualTo(saved.getProductName()),
                    () -> assertThat(found.getPrice()).isEqualTo(saved.getPrice()),
                    () -> assertThat(found.getQuantity()).isEqualTo(saved.getQuantity()),
                    () -> assertThat(found.getReceiptStatus()).isEqualTo(saved.getReceiptStatus()),
                    () -> assertThat(found.getAuctionId()).isEqualTo(saved.getAuctionId()),
                    () -> assertThat(found.getSellerId()).isEqualTo(saved.getSellerId()),
                    () -> assertThat(found.getBuyerId()).isEqualTo(saved.getBuyerId()),
                    () -> assertThat(found.getCreatedAt()).isEqualTo(saved.getCreatedAt()),
                    () -> assertThat(found.getUpdatedAt()).isEqualTo(saved.getUpdatedAt())
            );
        }

        @Test
        @DisplayName("거래내역 id에 해당하는 거래내역이 없으면 empty를 반환한다")
        void findReceiptById_Empty() {
            // given
            UUID notExistId = UUID.randomUUID();

            // when
            boolean found = receiptCoreRepository.findById(notExistId).isPresent();

            // then
            assertThat(found).isFalse();
        }
    }

    @Nested
    class saveReceipt {
        @Test
        @DisplayName("정상적으로 거래 내역을 저장한다.")
        void saveReceipt_Success() {
            // given
            Receipt receipt = Receipt.builder()
                    .id(UUID.randomUUID())
                    .productName("상품 이름")
                    .price(1000L)
                    .quantity(1L)
                    .receiptStatus(ReceiptStatus.PURCHASED)
                    .auctionId(1L)
                    .sellerId(1L)
                    .buyerId(2L)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now().plusHours(1))
                    .build();

            // when
            Receipt saved = receiptCoreRepository.save(receipt);

            // then
            assertAll(
                    () -> assertThat(saved.getProductName()).isEqualTo(receipt.getProductName()),
                    () -> assertThat(saved.getPrice()).isEqualTo(receipt.getPrice()),
                    () -> assertThat(saved.getQuantity()).isEqualTo(receipt.getQuantity()),
                    () -> assertThat(saved.getReceiptStatus()).isEqualTo(receipt.getReceiptStatus()),
                    () -> assertThat(saved.getAuctionId()).isEqualTo(receipt.getAuctionId()),
                    () -> assertThat(saved.getSellerId()).isEqualTo(receipt.getSellerId()),
                    () -> assertThat(saved.getBuyerId()).isEqualTo(receipt.getBuyerId())
            );
        }

        @Test
        @DisplayName("이미 존재하는 id면 거래내역을 수정한다.")
        void saveReceipt_ExistId() {
            // given
            Receipt receipt = Receipt.builder()
                    .id(UUID.randomUUID())
                    .productName("상품 이름")
                    .price(1000L)
                    .quantity(1L)
                    .receiptStatus(ReceiptStatus.PURCHASED)
                    .auctionId(1L)
                    .sellerId(1L)
                    .buyerId(2L)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            receiptCoreRepository.save(receipt);

            // when
            receipt.markAsRefund();
            Receipt saved = receiptCoreRepository.save(receipt);

            // then
            Receipt savedReceipt = receiptCoreRepository.findById(saved.getId()).get();
            assertThat(savedReceipt.getReceiptStatus()).isEqualTo(ReceiptStatus.REFUND);
        }
    }
}

