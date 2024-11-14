package org.indoles.receiptserviceserver.core.fixture;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.Role;

@NoArgsConstructor
class MemberTransferObjectFixture {
    private String signInId;
    private String password;
    private Role role;
    private Long point;

    @Builder
    public MemberTransferObjectFixture(
            String signInId,
            String password,
            Role role,
            Long point
    ) {
        this.signInId = signInId;
        this.password = password;
        this.role = role;
        this.point = point;
    }
}
