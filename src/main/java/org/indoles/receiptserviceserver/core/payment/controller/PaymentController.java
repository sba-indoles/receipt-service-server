package org.indoles.receiptserviceserver.core.payment.controller;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.member.controller.Login;
import org.indoles.receiptserviceserver.core.member.controller.Roles;
import org.indoles.receiptserviceserver.core.member.domain.Role;
import org.indoles.receiptserviceserver.core.member.dto.SignInInfo;
import org.indoles.receiptserviceserver.core.payment.dto.BuyerChargePointCommand;
import org.indoles.receiptserviceserver.core.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 사용자는 포인트를 충전한다.
    @Roles({Role.BUYER, Role.SELLER})
    @PostMapping("/points/charge")
    public ResponseEntity<Void> chargePoint(@Login SignInInfo memberInfo,
                                            @RequestBody BuyerChargePointCommand command) {
        paymentService.chargePoint(memberInfo, command.amount());
        return ResponseEntity.ok().build();
    }
}