package org.indoles.receiptserviceserver.global;

import org.indoles.receiptserviceserver.core.fixture.MemberFixture;
import org.indoles.receiptserviceserver.core.fixture.MemberTransferObjectFixture;
import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.indoles.receiptserviceserver.core.receipt.dto.response.BuyerReceiptSimpleInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.response.SellerReceiptSimpleInfoResponse;
import org.indoles.receiptserviceserver.global.util.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperTest {

    @Test
    @DisplayName("거래내역 엔티티를 BuyerReceiptResponse로 변환하면 도메인의 정보가 동일하다.")
    void transfer_ReceiptEntity_ToBuyerReceipt_Response() {
        // given
        MemberTransferObjectFixture buyer = MemberFixture.buyerBuilder();
        MemberTransferObjectFixture seller = MemberFixture.sellerBuilder();

        Receipt history = Receipt.builder()
                .id(UUID.randomUUID())
                .auctionId(232L)
                .productName("상품 이름")
                .price(1000L)
                .quantity(1L)
                .sellerId(seller.getId())
                .buyerId(buyer.getId())
                .receiptStatus(PURCHASED)
                .build();

        // when
        BuyerReceiptSimpleInfoResponse dto = Mapper.convertToBuyerReceiptSimpleInfo(history);

        // then
        assertAll(
                () -> assertEquals(history.getId(), dto.id()),
                () -> assertThat(history.getReceiptStatus()).isEqualTo(dto.type()),
                () -> assertEquals(history.getAuctionId(), dto.auctionId()),
                () -> assertEquals(history.getQuantity(), dto.quantity()),
                () -> assertEquals(history.getPrice(), dto.price())
        );
    }

    @Test
    @DisplayName("거래내역 엔티티를 SellerReceiptResponse로 변환하면 도메인의 정보가 동일하다.")
    void transfer_ReceiptEntity_ToSellerReceiptResponse() {
        // given
        MemberTransferObjectFixture buyer = MemberFixture.buyerBuilder();
        MemberTransferObjectFixture seller = MemberFixture.sellerBuilder();

        Receipt history = Receipt.builder()
                .id(UUID.randomUUID())
                .auctionId(232L)
                .productName("상품 이름")
                .price(1000L)
                .quantity(1L)
                .sellerId(seller.getId())
                .buyerId(buyer.getId())
                .receiptStatus(ReceiptStatus.REFUND)
                .build();

        // when
        SellerReceiptSimpleInfoResponse dto = Mapper.convertToSellerReceiptSimpleInfo(history);

        // then
        assertAll(
                () -> assertEquals(history.getId(), dto.id()),
                () -> assertThat(history.getReceiptStatus()).isEqualTo(dto.type()),
                () -> assertEquals(history.getAuctionId(), dto.auctionId()),
                () -> assertEquals(history.getQuantity(), dto.quantity()),
                () -> assertEquals(history.getPrice(), dto.price())
        );
    }
}
