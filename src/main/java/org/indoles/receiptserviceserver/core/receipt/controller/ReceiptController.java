package org.indoles.receiptserviceserver.core.receipt.controller;


import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.BuyerOnly;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.Roles;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.SellerOnly;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.Role;
import org.indoles.receiptserviceserver.core.receipt.dto.*;
import org.indoles.receiptserviceserver.core.receipt.service.ReceiptService;
import org.indoles.receiptserviceserver.global.exception.AuthorizationException;
import org.indoles.receiptserviceserver.global.exception.ErrorResponse;
import org.indoles.receiptserviceserver.global.exception.NotFoundException;
import org.indoles.receiptserviceserver.global.util.JwtTokenProvider;
import org.springframework.http.HttpStatus;
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
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 거래 내역 조회 API(구매자 전용)
     *
     * @param authorizationHeader
     * @param offset
     * @param size
     * @return
     */
    @BuyerOnly
    @GetMapping("/buyer")
    public ResponseEntity<List<BuyerReceiptSimpleInfo>> getReceipts(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "size") int size
    ) {

        String token = authorizationHeader.substring(7);

        if (jwtTokenProvider.validateToken(token)) {
            try {
                SignInInfo signInInfo = jwtTokenProvider.getSignInInfoFromToken(token);
                BuyerReceiptSearchCondition condition = new BuyerReceiptSearchCondition(offset, size);
                List<BuyerReceiptSimpleInfo> infos = receiptService.getBuyerReceiptSimpleInfos(signInInfo, condition);
                return ResponseEntity.ok(infos);
            } catch (Exception e) {
                log.error("Error during chargePoint: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            log.error("Unauthorized: JWT validation failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * 거래 내역 조회 API(판매자 전용)
     *
     * @param
     * @param offset
     * @param size
     * @return
     */
    @SellerOnly
    @GetMapping("/seller")
    public ResponseEntity<List<SellerReceiptSimpleInfo>> getSellerReceipts(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "size") int size
    ) {

        String token = authorizationHeader.substring(7);

        if (jwtTokenProvider.validateToken(token)) {
            try {
                SignInInfo signInInfo = jwtTokenProvider.getSignInInfoFromToken(token);
                SellerReceiptSearchCondition condition = new SellerReceiptSearchCondition(offset, size);
                List<SellerReceiptSimpleInfo> infos = receiptService.getSellerReceiptSimpleInfos(signInInfo, condition);
                return ResponseEntity.ok(infos);
            } catch (Exception e) {
                log.error("Error during chargePoint: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            log.error("Unauthorized: JWT validation failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * 거래 내역 상세 조회 API(구매자, 판매자 공통)
     *
     * @param authorizationHeader
     * @param receiptId
     * @return
     */
    @Roles({Role.BUYER, Role.SELLER})
    @GetMapping("/{receiptId}")
    public ResponseEntity<ReceiptInfo> getReceipt(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("receiptId") UUID receiptId
    ) {
        String token = authorizationHeader.substring(7);

        if (jwtTokenProvider.validateToken(token)) {
            try {
                SignInInfo signInInfo = jwtTokenProvider.getSignInInfoFromToken(token);
                ReceiptInfo receiptInfo = receiptService.getReceiptInfo(signInInfo, receiptId);
                return ResponseEntity.ok(receiptInfo);
            } catch (NotFoundException e) {
                log.warn("Not Found: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (AuthorizationException e) {
                log.warn("Unauthorized Access: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } catch (Exception e) {
                log.error("Error during chargePoint: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            log.error("Unauthorized: JWT validation failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
