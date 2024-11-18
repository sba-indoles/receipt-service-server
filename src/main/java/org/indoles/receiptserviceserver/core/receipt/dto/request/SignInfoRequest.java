package org.indoles.receiptserviceserver.core.receipt.dto.request;



import org.indoles.receiptserviceserver.core.receipt.domain.enums.Role;
import org.indoles.receiptserviceserver.global.exception.BadRequestException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;

import java.util.Objects;

public record SignInfoRequest(Long id, Role role) {
    private static final String ERROR_NULL_VALUE = "%s는 Null일 수 없습니다.";

    public SignInfoRequest {
        validateNotNull(id, "로그인한 사용자의 식별자");
        validateNotNull(role, "로그인한 사용자의 역할");
    }

    private void validateNotNull(Object value, String fieldName) {
        if (Objects.isNull(value)) {
            throw new BadRequestException(String.format(ERROR_NULL_VALUE, fieldName), ErrorCode.G000);
        }
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }

    public boolean isType(Role role) {
        return this.role.equals(role);
    }
}
