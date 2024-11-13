package org.indoles.receiptserviceserver.core.auction.controller;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.auction.controller.dto.PurchaseRequest;
import org.indoles.receiptserviceserver.core.auction.controller.dto.PurchaseResponse;
import org.indoles.receiptserviceserver.core.auction.dto.AuctionSearchCondition;
import org.indoles.receiptserviceserver.core.auction.dto.BuyerAuctionInfo;
import org.indoles.receiptserviceserver.core.auction.dto.BuyerAuctionSimpleInfo;
import org.indoles.receiptserviceserver.core.auction.service.AuctionService;
import org.indoles.receiptserviceserver.core.member.controller.BuyerOnly;
import org.indoles.receiptserviceserver.core.member.dto.SignInInfo;
import org.indoles.receiptserviceserver.global.dto.AuctionPurchaseRequestMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BuyerAuctionController {

    private final AuctionService auctionService;

    /**
     * 경매 목록 조회 API(구매자 전용)
     */

    @GetMapping("/auctions")
    public ResponseEntity<List<BuyerAuctionSimpleInfo>> getAuctions(@RequestParam(name = "offset") int offset,
                                                                    @RequestParam(name = "size") int size) {
        AuctionSearchCondition condition = new AuctionSearchCondition(offset, size);
        List<BuyerAuctionSimpleInfo> infos = auctionService.getBuyerAuctionSimpleInfos(condition);
        return ResponseEntity.ok(infos);
    }

    /**
     * 경매 목록 상세 조회 API(구매자 전용)
     */
    @GetMapping("/auctions/{auctionId}")
    public ResponseEntity<BuyerAuctionInfo> getAuction(@PathVariable("auctionId") Long auctionId) {
        BuyerAuctionInfo result = auctionService.getBuyerAuction(auctionId);
        return ResponseEntity.ok(result);
    }

    /**
     * 경매 입찰 API(구매자 전용)
     */
    @BuyerOnly
    @PostMapping("/auctions/{auctionId}/purchase")
    public ResponseEntity<PurchaseResponse> submitAuction(SignInInfo signInInfo,
                                                          @CurrentTime LocalDateTime now,
                                                          @PathVariable(name = "auctionId") Long auctionId,
                                                          @RequestBody PurchaseRequest purchaseRequest) {
        AuctionPurchaseRequestMessage requestMessage = AuctionPurchaseRequestMessage.builder()
                .requestId(UUID.randomUUID())
                .buyerId(signInInfo.id())
                .auctionId(auctionId)
                .price(purchaseRequest.price())
                .quantity(purchaseRequest.quantity())
                .requestTime(now)
                .build();

        PurchaseResponse response = new PurchaseResponse(requestMessage.requestId());
        return ResponseEntity.ok(response);
    }
}
