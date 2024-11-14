package org.indoles.receiptserviceserver.core.fixture;

import static org.indoles.receiptserviceserver.core.receipt.domain.enums.Role.*;

class MemberFixture {

    public static MemberTransferObjectFixture BuyerBuilder() {
        return MemberTransferObjectFixture.builder()
                .signInId("buyerId")
                .password("password00")
                .role(BUYER)
                .point(1000L)
                .build();
    }

    public static MemberTransferObjectFixture sellerBuilder() {
        return MemberTransferObjectFixture.builder()
                .signInId("sellerId")
                .password("password00")
                .role(SELLER)
                .point(1000L)
                .build();
    }
}
