package org.indoles.receiptserviceserver.core.receipt.controller;


import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.Buyer;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.Login;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.Roles;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.Seller;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.Role;
import org.indoles.receiptserviceserver.core.receipt.dto.request.BuyerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.request.CreateReceiptRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.request.SellerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.response.BuyerReceiptSimpleInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.response.ReceiptInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.response.SellerReceiptSimpleInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.request.SignInfoRequest;
import org.indoles.receiptserviceserver.core.receipt.service.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    /**
     * 거래 내역 조회 API(구매자 전용)
     *
     * @param signInfoRequest
     * @param offset
     * @param size
     * @return
     */
    @Buyer
    @GetMapping("/buyer")
    public ResponseEntity<List<BuyerReceiptSimpleInfoResponse>> getReceipts(
            @Login SignInfoRequest signInfoRequest,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "size") int size
    ) {
        BuyerReceiptSearchConditionRequest condition = new BuyerReceiptSearchConditionRequest(offset, size);
        List<BuyerReceiptSimpleInfoResponse> infos = receiptService.getBuyerReceiptSimpleInfos(signInfoRequest, condition);

        return ResponseEntity.ok(infos);
    }

    /**
     * 거래 내역 조회 API(판매자 전용)
     *
     * @param signInfoRequest
     * @param offset
     * @param size
     * @return
     */
    @Seller
    @GetMapping("/seller")
    public ResponseEntity<List<SellerReceiptSimpleInfoResponse>> getSellerReceipts(
            @Login SignInfoRequest signInfoRequest,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "size") int size
    ) {
        SellerReceiptSearchConditionRequest condition = new SellerReceiptSearchConditionRequest(offset, size);
        List<SellerReceiptSimpleInfoResponse> infos = receiptService.getSellerReceiptSimpleInfos(signInfoRequest, condition);

        return ResponseEntity.ok(infos);
    }

    /**
     * 거래 내역 상세 조회 API(구매자, 판매자 공통)
     *
     * @param signInfoRequest
     * @param receiptId
     * @return
     */
    @Roles({Role.BUYER, Role.SELLER})
    @GetMapping("/{receiptId}")
    public ResponseEntity<ReceiptInfoResponse> getReceipt(
            @Login SignInfoRequest signInfoRequest,
            @PathVariable("receiptId") UUID receiptId
    ) {
        ReceiptInfoResponse receiptInfoResponse = receiptService.getReceiptInfo(signInfoRequest, receiptId);
        return ResponseEntity.ok(receiptInfoResponse);
    }

    /**
     * 경매 입찰 시 거래 내역 생성 API
     */

    @PostMapping("create")
    public ResponseEntity<Void> createReceipt(
            @Login SignInfoRequest signInfoRequest,
            @RequestBody CreateReceiptRequest request
    ){
        receiptService.createReceipt(signInfoRequest, request);
        return ResponseEntity.ok().build();
    }


//
//    // 거래 상세 조회 API
//    @GetMapping("/transactions/{receiptId}")
//    public ResponseEntity<ReceiptInfoResponse> getReceiptById(@PathVariable("receiptId") UUID receiptId) {
//        ReceiptInfoResponse receiptInfo = receiptService.getReceiptById(receiptId);
//        return ResponseEntity.ok(receiptInfo);
//    }
}
