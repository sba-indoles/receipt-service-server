package org.indoles.receiptserviceserver.core.member.domain;

import lombok.Getter;
import org.indoles.receiptserviceserver.core.member.dto.SignInInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class AuthenticationContext {

    private SignInInfo principal;

    public void setPrincipal(SignInInfo principal) {
        this.principal = principal;
    }
}
