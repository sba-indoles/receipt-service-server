package org.indoles.receiptserviceserver.core.receipt.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.BuyerOnly;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.Roles;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.SellerOnly;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.Role;
import org.indoles.receiptserviceserver.core.receipt.dto.*;
import org.indoles.receiptserviceserver.core.receipt.service.ReceiptService;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;
import org.indoles.receiptserviceserver.global.exception.InfraStructureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;
    private final ObjectMapper objectMapper;


    /**
     * 거래 내역 조회 API(구매자 전용)
     *
     * @param signInInfoString
     * @param offset
     * @param size
     * @return
     */
    @BuyerOnly
    @GetMapping("/buyer")
    public ResponseEntity<List<BuyerReceiptSimpleInfo>> getReceipts(
            @RequestHeader("X-SignIn-Info") String signInInfoString,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "size") int size
    ) {

        SignInInfo signInInfo = convertToSignInInfo(signInInfoString);

        BuyerReceiptSearchCondition condition = new BuyerReceiptSearchCondition(offset, size);
        List<BuyerReceiptSimpleInfo> infos = receiptService.getBuyerReceiptSimpleInfos(signInInfo, condition);
        return ResponseEntity.ok(infos);
    }

    /**
     * 거래 내역 조회 API(판매자 전용)
     *
     * @param signInInfoString
     * @param offset
     * @param size
     * @return
     */
    @SellerOnly
    @GetMapping("/seller")
    public ResponseEntity<List<SellerReceiptSimpleInfo>> getSellerReceipts(
            @RequestHeader("X-SignIn-Info") String signInInfoString,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "size") int size
    ) {

        SignInInfo signInInfo = convertToSignInInfo(signInInfoString);

        SellerReceiptSearchCondition condition = new SellerReceiptSearchCondition(offset, size);
        List<SellerReceiptSimpleInfo> infos = receiptService.getSellerReceiptSimpleInfos(signInInfo, condition);
        return ResponseEntity.ok(infos);
    }

    /**
     * 거래 내역 상세 조회 API(구매자, 판매자 공통)
     *
     * @param signInInfoString
     * @param receiptId
     * @return
     */
    @Roles({Role.BUYER, Role.SELLER})
    @GetMapping("/{receiptId}")
    public ResponseEntity<ReceiptInfo> getReceipt(
            @RequestHeader("X-SignIn-Info") String signInInfoString,
            @PathVariable("receiptId") UUID receiptId
    ) {

        SignInInfo signInInfo = convertToSignInInfo(signInInfoString);


        ReceiptInfo receiptInfo = receiptService.getReceiptInfo(signInInfo, receiptId);
        return ResponseEntity.ok(receiptInfo);
    }

    private SignInInfo convertToSignInInfo(String signInInfoString) {
        try {
            return objectMapper.readValue(signInInfoString, SignInInfo.class);
        } catch (Exception e) {
            throw new InfraStructureException("SignInfo 변환 실패" + e, ErrorCode.A031);
        }
    }
}
