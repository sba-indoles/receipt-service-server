package org.indoles.receiptserviceserver.core.dto;

import org.indoles.receiptserviceserver.core.fixture.MemberTransferObjectFixture;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.Role;
import org.indoles.receiptserviceserver.core.receipt.dto.response.ReceiptInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReceiptInfoResponseTest {

    @Test
    @DisplayName("거래 내역 생성을 정상적으로 처리한다.")
    void createReceiptInfoResponse_Success() {
        // given
        String productName = "상품이름";
        long price = 10000L;
        long quantity = 1L;
        ReceiptStatus receiptStatus = ReceiptStatus.PURCHASED;
        long auctionId = 1L;

        MemberTransferObjectFixture seller = MemberTransferObjectFixture.builder()
                .id(1L)
                .signInId("sellerId")
                .password("password00")
                .role(Role.SELLER)
                .point(1000L)
                .build();

        MemberTransferObjectFixture buyer = MemberTransferObjectFixture.builder()
                .id(2L)
                .signInId("buyerId")
                .password("password00")
                .role(Role.BUYER)
                .point(1000L)
                .build();

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // when
        UUID id = UUID.randomUUID();
        ReceiptInfoResponse receiptInfoResponse = new ReceiptInfoResponse(
                id,
                productName,
                price,
                quantity,
                receiptStatus,
                auctionId,
                seller.getId(),
                buyer.getId(),
                createdAt,
                updatedAt
        );

        // then
        assertAll(
                () -> assertThat(receiptInfoResponse.receiptId()).isEqualTo(id),
                () -> assertThat(receiptInfoResponse.productName()).isEqualTo(productName),
                () -> assertThat(receiptInfoResponse.price()).isEqualTo(price),
                () -> assertThat(receiptInfoResponse.quantity()).isEqualTo(quantity),
                () -> assertThat(receiptInfoResponse.receiptStatus()).isEqualTo(receiptStatus),
                () -> assertThat(receiptInfoResponse.auctionId()).isEqualTo(auctionId),
                () -> assertThat(receiptInfoResponse.sellerId()).isEqualTo(seller.getId()),
                () -> assertThat(receiptInfoResponse.buyerId()).isEqualTo(buyer.getId()),
                () -> assertThat(receiptInfoResponse.createdAt()).isEqualTo(createdAt),
                () -> assertThat(receiptInfoResponse.updatedAt()).isEqualTo(updatedAt)
        );
    }
}
