package org.indoles.receiptserviceserver.core.fixture;

import static org.indoles.receiptserviceserver.core.receipt.domain.enums.Role.*;

public class MemberFixture {

    public static MemberTransferObjectFixture buyerBuilder() {
        return MemberTransferObjectFixture.builder()
                .id(1L)
                .signInId("buyerId")
                .password("password00")
                .role(BUYER)
                .point(1000L)
                .build();
    }

    public static MemberTransferObjectFixture sellerBuilder() {
        return MemberTransferObjectFixture.builder()
                .id(2L)
                .signInId("sellerId")
                .password("password00")
                .role(SELLER)
                .point(1000L)
                .build();
    }
}
