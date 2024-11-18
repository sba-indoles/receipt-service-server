package org.indoles.receiptserviceserver.global.util;

import lombok.Getter;
import org.indoles.receiptserviceserver.core.receipt.dto.request.SignInfoRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class AuthenticationContext {

    private SignInfoRequest principal;

    public void setPrincipal(SignInfoRequest principal) {
        this.principal = principal;
    }
}

