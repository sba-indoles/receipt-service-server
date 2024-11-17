package org.indoles.receiptserviceserver.core.service;

import org.indoles.receiptserviceserver.core.context.ServiceTest;
import org.indoles.receiptserviceserver.core.fixture.MemberFixture;
import org.indoles.receiptserviceserver.core.fixture.MemberTransferObjectFixture;
import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.ReceiptStatus;
import org.indoles.receiptserviceserver.core.receipt.dto.request.BuyerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.request.SellerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.response.BuyerReceiptSimpleInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.response.ReceiptInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.response.SellerReceiptSimpleInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.response.SignInInfoResponse;
import org.indoles.receiptserviceserver.global.exception.AuthorizationException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;
import org.indoles.receiptserviceserver.global.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.indoles.receiptserviceserver.core.receipt.domain.enums.Role.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
class ReceiptServiceTest extends ServiceTest {

    @Nested
    class getBuyerReceiptSimpleInfos_Method {

        @Test
        @DisplayName("정상적인 요청이 들어오면 특정 구매자의 거래이력을 조회할 수 있다")
        void findReceipt_ByBuyer_Success() {
            // given
            MemberTransferObjectFixture seller = MemberFixture.sellerBuilder();
            MemberTransferObjectFixture buyer = MemberFixture.buyerBuilder();

            Receipt receipt = Receipt.builder()
                    .id(UUID.randomUUID())
                    .sellerId(seller.getId())
                    .buyerId(buyer.getId())
                    .build();
            receiptRepository.save(receipt);

            // when
            List<BuyerReceiptSimpleInfoResponse> buyerReceiptSimpleInfoResponses = receiptService.getBuyerReceiptSimpleInfos(
                    new SignInInfoResponse(buyer.getId(), BUYER),
                    new BuyerReceiptSearchConditionRequest(0, 5)
            );

            // then
            assertAll(
                    () -> assertThat(buyerReceiptSimpleInfoResponses).hasSize(1)
            );
        }
    }

    @Nested
    class getSellerReceiptSimpleInfos_Method {

        @Test
        @DisplayName("정상적인 요청이 들어오면 특정 구매자의 거래이력을 조회할 수 있다")
        void findReceipt_BySeller_Success() {
            // given
            MemberTransferObjectFixture seller = MemberFixture.sellerBuilder();
            MemberTransferObjectFixture buyer = MemberFixture.buyerBuilder();

            Receipt receipt = Receipt.builder()
                    .id(UUID.randomUUID())
                    .sellerId(seller.getId())
                    .buyerId(buyer.getId())
                    .build();
            receiptRepository.save(receipt);

            // when
            List<SellerReceiptSimpleInfoResponse> sellerReceiptSimpleInfos = receiptService.getSellerReceiptSimpleInfos(
                    new SignInInfoResponse(seller.getId(), SELLER),
                    new SellerReceiptSearchConditionRequest(0, 5)
            );

            // then
            assertAll(
                    () -> assertThat(sellerReceiptSimpleInfos).hasSize(1)
            );
        }
    }

    @Nested
    class getReceiptInfo_Method {

        static Stream<Arguments> provideMembersForSuccess() {
            MemberTransferObjectFixture seller = MemberFixture.sellerBuilder();
            MemberTransferObjectFixture buyer = MemberFixture.buyerBuilder();

            return Stream.of(
                    Arguments.of(seller.getId(), "판매자의 구매이력 조회"),
                    Arguments.of(buyer.getId(), "구매자의 구매이력 조회")
            );
        }

        @ParameterizedTest(name = "정상응답을 성공한다.")
        @MethodSource("provideMembersForSuccess")
        @DisplayName("정상적인 요청이 들어오면 특정 구매자의 거래이력을 조회할 수 있다")
        void findReceipt_BySeller_Success() {
            // given
            MemberTransferObjectFixture seller = MemberFixture.sellerBuilder();
            MemberTransferObjectFixture buyer = MemberFixture.buyerBuilder();

            Receipt receipt = Receipt.builder()
                    .id(UUID.randomUUID())
                    .productName("멋진 상품")
                    .price(1000000)
                    .quantity(1)
                    .receiptStatus(ReceiptStatus.PURCHASED)
                    .auctionId(1L)
                    .sellerId(seller.getId())
                    .buyerId(buyer.getId())
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            Receipt savedReceipt = receiptRepository.save(receipt);

            // when
            ReceiptInfoResponse receiptInfo = receiptService.getReceiptInfo(
                    new SignInInfoResponse(seller.getId(), SELLER), savedReceipt.getId());

            // then
            assertAll(
                    () -> assertThat(receiptInfo.auctionId()).isEqualTo(1L),
                    () -> assertThat(receiptInfo.productName()).isEqualTo("멋진 상품"),
                    () -> assertThat(receiptInfo.price()).isEqualTo(1000000),
                    () -> assertThat(receiptInfo.quantity()).isEqualTo(1),
                    () -> assertThat(receiptInfo.receiptStatus()).isEqualTo(ReceiptStatus.PURCHASED),
                    () -> assertThat(receiptInfo.auctionId()).isEqualTo(1L),
                    () -> assertThat(receiptInfo.sellerId()).isEqualTo(seller.getId()),
                    () -> assertThat(receiptInfo.buyerId()).isEqualTo(buyer.getId())
            );
        }
    }

    @Test
    @DisplayName("존재하지 않은 거래 내역 조회 시 예외가 발생한다.")
    void findNotExist_Receipt_TrowException() {
        // given
        MemberTransferObjectFixture member = MemberFixture.buyerBuilder();
        UUID receiptId = UUID.randomUUID();

        // expect
        assertThatThrownBy(
                () -> receiptService.getReceiptInfo(
                        new SignInInfoResponse(member.getId(), BUYER), receiptId
                )
        ).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("해당 거래 내역의 소유자가 아닌 경우 조회 시 예외가 발생한다.")
    void findReceipt_NotOwnedByMember_ThrowException() {
        // given
        MemberTransferObjectFixture seller = MemberFixture.sellerBuilder();
        MemberTransferObjectFixture buyer = MemberFixture.buyerBuilder();

        SignInInfoResponse nonOwner = new SignInInfoResponse(3L, BUYER);

        Receipt receipt = Receipt.builder()
                .id(UUID.randomUUID())
                .sellerId(seller.getId())
                .buyerId(buyer.getId())
                .build();
        Receipt savedReceipt = receiptRepository.save(receipt);

        // expect
        assertThatThrownBy(() -> receiptService.getReceiptInfo(nonOwner, savedReceipt.getId()))
                .isInstanceOf(AuthorizationException.class)
                .satisfies(exception -> assertThat(exception).hasFieldOrPropertyWithValue("errorCode",
                        ErrorCode.R001));
    }
}
