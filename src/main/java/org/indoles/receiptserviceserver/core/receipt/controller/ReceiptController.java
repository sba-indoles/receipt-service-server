package org.indoles.receiptserviceserver.core.receipt.controller;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.member.controller.BuyerOnly;
import org.indoles.receiptserviceserver.core.member.controller.Login;
import org.indoles.receiptserviceserver.core.member.controller.Roles;
import org.indoles.receiptserviceserver.core.member.controller.SellerOnly;
import org.indoles.receiptserviceserver.core.member.domain.Role;
import org.indoles.receiptserviceserver.core.member.dto.SignInInfo;
import org.indoles.receiptserviceserver.core.receipt.dto.*;
import org.indoles.receiptserviceserver.core.receipt.service.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    /**
     * 거래 내역 조회 API(구매자 전용)
     * @param buyerInfo
     * @param offset
     * @param size
     * @return
     */
    @BuyerOnly
    @GetMapping("/buyer")
    public ResponseEntity<List<BuyerReceiptSimpleInfo>> getReceipts( SignInInfo buyerInfo,
                                                                    @RequestParam(name = "offset") int offset,
                                                                    @RequestParam(name = "size") int size) {
        BuyerReceiptSearchCondition condition = new BuyerReceiptSearchCondition(offset, size);
        List<BuyerReceiptSimpleInfo> infos = receiptService.getBuyerReceiptSimpleInfos(buyerInfo, condition);
        return ResponseEntity.ok(infos);
    }

    /**
     * 거래 내역 조회 API(판매자 전용)
     * @param sellerInfo
     * @param offset
     * @param size
     * @return
     */
    @SellerOnly
    @GetMapping("/seller")
    public ResponseEntity<List<SellerReceiptSimpleInfo>> getSellerReceipts( SignInInfo sellerInfo,
                                                                           @RequestParam(name = "offset") int offset,
                                                                           @RequestParam(name = "size") int size) {
        SellerReceiptSearchCondition condition = new SellerReceiptSearchCondition(offset, size);
        List<SellerReceiptSimpleInfo> infos = receiptService.getSellerReceiptSimpleInfos(sellerInfo, condition);
        return ResponseEntity.ok(infos);
    }

    /**
     * 거래 내역 상세 조회 API(구매자, 판매자 공통)
     * @param memberInfo
     * @param receiptId
     * @return
     */
    @Roles({Role.BUYER, Role.SELLER})
    @GetMapping("/{receiptId}")
    public ResponseEntity<ReceiptInfo> getReceipt( SignInInfo memberInfo,
                                                  @PathVariable("receiptId") UUID receiptId) {
        ReceiptInfo receiptInfo = receiptService.getReceiptInfo(memberInfo, receiptId);
        return ResponseEntity.ok(receiptInfo);
    }
}
