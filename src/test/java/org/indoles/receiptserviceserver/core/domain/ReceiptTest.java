package org.indoles.receiptserviceserver.core.domain;

import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.indoles.receiptserviceserver.global.exception.BadRequestException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReceiptTest {

    @Test
    @DisplayName("사용자가 환불 성공 시 환불 표시를 나타낸다.")
    void markAsRefund_Success() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Receipt refundReceipt = Receipt.builder()
                .id(UUID.randomUUID())
                .auctionId(1L)
                .productName("test")
                .price(100L)
                .quantity(1L)
                .receiptStatus(ReceiptStatus.PURCHASED)
                .createdAt(now)
                .updatedAt(now.plusHours(1))
                .build();

        // when
        refundReceipt.markAsRefund();

        // then
        assertThat(refundReceipt.getReceiptStatus()).isEqualTo(ReceiptStatus.REFUND);
    }

    @Test
    @DisplayName("이미 환불된 경매에 환불 표시를 하면 예외가 발생한다.")
    void markAsRefund_AlreadyRefunded() {
        // given
        Receipt refundReceipt = Receipt.builder()
                .receiptStatus(ReceiptStatus.REFUND)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(1))
                .build();

        // expect
        assertThatThrownBy(refundReceipt::markAsRefund)
                .isInstanceOf(BadRequestException.class)
                .hasMessage("이미 환불된 입찰 내역입니다.")
                .satisfies(exception -> assertThat(exception).hasFieldOrPropertyWithValue("errorCode", ErrorCode.R002));

    }
}
