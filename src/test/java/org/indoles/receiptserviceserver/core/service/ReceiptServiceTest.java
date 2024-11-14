package org.indoles.receiptserviceserver.core.service;

import org.indoles.receiptserviceserver.core.context.ServiceTest;
import org.indoles.receiptserviceserver.core.fixture.MemberFixture;
import org.indoles.receiptserviceserver.core.fixture.MemberTransferObjectFixture;
import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.Role;
import org.indoles.receiptserviceserver.core.receipt.dto.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
class ReceiptServiceTest extends ServiceTest {

        @Nested
        class getBuyerReceiptSimpleInfos_Method {

            @Test
            @DisplayName("정상적인 요청이 들어오면 특정 구매자의 거래이력을 조회할 수 있다")
            void findReceiptByBuyer() {
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
                List<BuyerReceiptSimpleInfo> buyerReceiptSimpleInfos = receiptService.getBuyerReceiptSimpleInfos(
                        new SignInInfo(buyer.getId(), Role.BUYER),
                        new BuyerReceiptSearchCondition(0, 5)
                );

                // then
                assertAll(
                        () -> assertThat(buyerReceiptSimpleInfos).hasSize(1)
                );
            }
        }

        @Nested
        class getSellerReceiptSimpleInfos_Method {

            @Test
            @DisplayName("정상적인 요청이 들어오면 특정 구매자의 거래이력을 조회할 수 있다")
            void findReceiptBySeller() {
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
                List<SellerReceiptSimpleInfo> sellerReceiptSimpleInfos = receiptService.getSellerReceiptSimpleInfos(
                        new SignInInfo(seller.getId(), Role.SELLER),
                        new SellerReceiptSearchCondition(0, 5)
                );

                // then
                assertAll(
                        () -> assertThat(sellerReceiptSimpleInfos).hasSize(1)
                );
            }
        }
}
