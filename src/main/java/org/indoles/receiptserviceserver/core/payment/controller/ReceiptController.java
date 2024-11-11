package org.indoles.receiptserviceserver.core.payment.controller;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.member.controller.BuyerOnly;
import org.indoles.receiptserviceserver.core.member.controller.Login;
import org.indoles.receiptserviceserver.core.member.controller.Roles;
import org.indoles.receiptserviceserver.core.member.controller.SellerOnly;
import org.indoles.receiptserviceserver.core.member.domain.Role;
import org.indoles.receiptserviceserver.core.member.dto.SignInInfo;
import org.indoles.receiptserviceserver.core.payment.dto.*;
import org.indoles.receiptserviceserver.core.payment.service.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;
//
//    // 구매자는 자신의 거래 이력 목록을 조회할 수 있다.
//    @BuyerOnly
//    @GetMapping("/buyer")
//    public ResponseEntity<List<BuyerReceiptSimpleInfo>> getReceipts(@Login SignInInfo buyerInfo,
//                                                                    @RequestParam(name = "offset") int offset,
//                                                                    @RequestParam(name = "size") int size) {
//        BuyerReceiptSearchCondition condition = new BuyerReceiptSearchCondition(offset, size);
//        List<BuyerReceiptSimpleInfo> infos = receiptService.getBuyerReceiptSimpleInfos(buyerInfo, condition);
//        return ResponseEntity.ok(infos);
//    }
//
//    // 판매자는 자신의 거래 이력 목록을 조회할 수 있다.
//    @SellerOnly
//    @GetMapping("/seller")
//    public ResponseEntity<List<SellerReceiptSimpleInfo>> getSellerReceipts(@Login SignInInfo sellerInfo,
//                                                                           @RequestParam(name = "offset") int offset,
//                                                                           @RequestParam(name = "size") int size) {
//        SellerReceiptSearchCondition condition = new SellerReceiptSearchCondition(offset, size);
//        List<SellerReceiptSimpleInfo> infos = receiptService.getSellerReceiptSimpleInfos(sellerInfo, condition);
//        return ResponseEntity.ok(infos);
//    }
//
//    // 사용자는 자신의 거래 이력을 상세 조회할 수 있다.
//    @Roles({Role.BUYER, Role.SELLER})
//    @GetMapping("/{receiptId}")
//    public ResponseEntity<ReceiptInfo> getReceipt(@Login SignInInfo memberInfo,
//                                                  @PathVariable("receiptId") UUID receiptId) {
//        ReceiptInfo receiptInfo = receiptService.getReceiptInfo(memberInfo, receiptId);
//        return ResponseEntity.ok(receiptInfo);
//    }
}