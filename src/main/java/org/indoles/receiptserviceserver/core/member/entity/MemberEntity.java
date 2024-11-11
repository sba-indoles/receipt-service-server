package org.indoles.receiptserviceserver.core.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.indoles.receiptserviceserver.core.member.domain.Role;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String signInId;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private Long point;

    @Builder
    private MemberEntity(Long id, String signInId, String password, Role role, Long point) {
        this.id = id;
        this.signInId = signInId;
        this.password = password;
        this.role = role;
        this.point = point;
    }
}
