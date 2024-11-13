package org.indoles.receiptserviceserver.core.receipt.dto.validate;

import org.indoles.receiptserviceserver.global.exception.BadRequestException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;

public class ValidateReceiptSearchCondition {

    public static void validateSizeBetween(int from, int to, int size){
        if (size < from || size > to) {
            throw new BadRequestException("size는 " + from + " 이상 " + to + " 이하의 값이어야 합니다.", ErrorCode.G001);
        }
    }
}
