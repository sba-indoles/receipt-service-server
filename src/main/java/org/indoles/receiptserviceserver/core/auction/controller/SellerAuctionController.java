package org.indoles.receiptserviceserver.core.auction.controller;

import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.auction.controller.dto.CreateAuctionRequest;
import org.indoles.receiptserviceserver.core.auction.service.AuctionService;
import org.indoles.receiptserviceserver.core.auction.dto.*;
import org.indoles.receiptserviceserver.core.member.controller.Login;
import org.indoles.receiptserviceserver.core.member.controller.SellerOnly;
import org.indoles.receiptserviceserver.core.member.dto.SignInInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/auctions")
@RequiredArgsConstructor
public class SellerAuctionController {

    private final AuctionService auctionService;

    /**
     * 경매 등록 API(판매자 전용)
     */

    @SellerOnly
    @PostMapping
    public ResponseEntity<Void> createAuction(@Login SignInInfo sellerInfo,
                                              @RequestBody CreateAuctionRequest request,
                                              @CurrentTime LocalDateTime now) {
        CreateAuctionCommand command = new CreateAuctionCommand(request.productName(), request.originPrice(),
                request.stock(), request.maximumPurchaseLimitCount(), request.pricePolicy(),
                request.variationDuration(), now, request.startedAt(), request.finishedAt(), request.isShowStock());
        auctionService.createAuction(sellerInfo, command);
        return ResponseEntity.ok().build();
    }

    /**
     * 경매 취소 API(판매자 전용)
     */

    @SellerOnly
    @DeleteMapping("/{auctionId}")
    public void cancelAuction(@Login SignInInfo sellerInfo,
                              @PathVariable("auctionId") Long auctionId,
                              @CurrentTime LocalDateTime now) {
        CancelAuctionCommand command = new CancelAuctionCommand(now, auctionId);
        auctionService.cancelAuction(sellerInfo, command);
    }

    /**
     * 경매 조회 API(판매자 전용)
     */
    @SellerOnly
    @GetMapping("/seller")
    public ResponseEntity<List<SellerAuctionSimpleInfo>> getSellerAuctions(@Login SignInInfo sellerInfo,
                                                                           @RequestParam(name = "offset") int offset,
                                                                           @RequestParam(name = "size") int size) {
        SellerAuctionSearchCondition condition = new SellerAuctionSearchCondition(sellerInfo.id(), offset, size);
        List<SellerAuctionSimpleInfo> infos = auctionService.getSellerAuctionSimpleInfos(condition);
        return ResponseEntity.ok(infos);
    }

    /**
     * 경매 상세 조회 API(판매자 전용)
     */
    @SellerOnly
    @GetMapping("/{auctionId}/seller")
    public ResponseEntity<SellerAuctionInfo> getSellerAuction(@Login SignInInfo sellerInfo,
                                                              @PathVariable("auctionId") Long auctionId) {
        SellerAuctionInfo sellerAuctionInfo = auctionService.getSellerAuction(sellerInfo, auctionId);
        return ResponseEntity.ok(sellerAuctionInfo);
    }
}
