package org.indoles.receiptserviceserver.core.member.infra;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.member.domain.Member;
import org.indoles.receiptserviceserver.core.member.domain.MemberRepository;
import org.indoles.receiptserviceserver.core.member.entity.MemberEntity;
import org.indoles.receiptserviceserver.global.util.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberCoreRepository implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean isExist(String signInId) {
        return memberJpaRepository.existsBySignInId(signInId);
    }

    @Override
    public Member save(Member member) {
        MemberEntity entity = Mapper.convertToMemberEntity(member);
        MemberEntity saved = memberJpaRepository.save(entity);
        return Mapper.convertToMember(saved);
    }

    @Override
    public Optional<Member> findById(Long id) {
        Optional<MemberEntity> found = memberJpaRepository.findById(id);
        return found.map(Mapper::convertToMember);
    }

    @Override
    public Optional<Member> findBySignInId(String signInId) {
        Optional<MemberEntity> found = memberJpaRepository.findBySignInId(signInId);
        return found.map(Mapper::convertToMember);
    }
}
