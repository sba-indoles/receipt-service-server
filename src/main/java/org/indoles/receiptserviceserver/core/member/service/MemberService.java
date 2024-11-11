package org.indoles.receiptserviceserver.core.member.service;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.member.controller.dto.SignInRequestInfo;
import org.indoles.receiptserviceserver.core.member.controller.dto.SignUpRequestInfo;
import org.indoles.receiptserviceserver.core.member.domain.Member;
import org.indoles.receiptserviceserver.core.member.domain.MemberRepository;
import org.indoles.receiptserviceserver.core.member.dto.SignInInfo;
import org.indoles.receiptserviceserver.global.exception.BadRequestException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void signUp(SignUpRequestInfo signUpRequestInfo) {
        if (memberRepository.isExist(signUpRequestInfo.signUpId())) {
            throw new BadRequestException("이미 존재하는 아이디입니다. input=" + signUpRequestInfo.signUpId(), ErrorCode.M000);
        }
        Member member = Member.createMemberWithRole(
                signUpRequestInfo.signUpId(),
                signUpRequestInfo.password(),
                signUpRequestInfo.userRole()
        );

        memberRepository.save(member);
    }

    public SignInInfo signIn(SignInRequestInfo signInRequestInfo) {
        Member member = memberRepository.findBySignInId(signInRequestInfo.signInId())
                .orElseThrow(() -> new BadRequestException(
                        "아이디에 해당되는 사용자를 찾을 수 없습니다. signInId=" + signInRequestInfo.signInId(),
                        ErrorCode.M002));

        if (!member.confirmPassword(signInRequestInfo.password())) {
            throw new BadRequestException("패스워드가 일치하지 않습니다.", ErrorCode.M003);
        }

        return new SignInInfo(member.getId(), member.getRole());
    }
}
