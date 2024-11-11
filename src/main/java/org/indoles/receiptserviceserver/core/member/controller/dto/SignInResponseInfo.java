package org.indoles.receiptserviceserver.core.member.controller.dto;


import org.indoles.receiptserviceserver.core.member.domain.Role;

public record SignInResponseInfo(
        Role role
) {
}
