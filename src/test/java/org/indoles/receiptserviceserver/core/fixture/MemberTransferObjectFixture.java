package org.indoles.receiptserviceserver.core.fixture;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.Role;

@Getter
@NoArgsConstructor
public class MemberTransferObjectFixture {
    private Long id;
    private String signInId;
    private String password;
    private Role role;
    private Long point;

    @Builder
    public MemberTransferObjectFixture(
            Long id,
            String signInId,
            String password,
            Role role,
            Long point
    ) {
        this.id = id;
        this.signInId = signInId;
        this.password = password;
        this.role = role;
        this.point = point;
    }
}
