package org.indoles.receiptserviceserver.core.dto;

import org.indoles.receiptserviceserver.core.receipt.dto.request.BuyerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.request.SellerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.global.exception.BadRequestException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class SellerReceiptSearchConditionTest {

    @Test
    @DisplayName("판매자가 거래 목록을 정상적으로 조회할 수 있다.")
    void sellerReceiptSearch_Success() {
        // given
        int size = 10;
        int offset = 0;

        // when
        SellerReceiptSearchConditionRequest sellerReceiptSearchCondition = new SellerReceiptSearchConditionRequest(offset, size);

        // then
        assertAll(
                () -> assertThat(sellerReceiptSearchCondition.size()).isEqualTo(size),
                () -> assertThat(sellerReceiptSearchCondition.offset()).isEqualTo(offset)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 101})
    @DisplayName("판매자가 거래 목록을 조회할 때 size가 1미만이거나 100초과인 경우 예외가 발생한다.")
    void sellerReceiptSearch_SizeInvalid_ThrowsException(int size) {

        assertThatThrownBy(() -> new SellerReceiptSearchConditionRequest(0, size))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.G001);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10})
    @DisplayName("판매자가 거래 목록을 조회할 때 offset이 0미만인 경우 예외가 발생한다.")
    void sellerReceiptSearch_OffsetInvalid_ThrowsException(int offset) {

        assertThatThrownBy(() -> new SellerReceiptSearchConditionRequest(offset, 10))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.G002);
    }
}
