package org.indoles.receiptserviceserver.core.auction.service;


import org.indoles.receiptserviceserver.global.dto.AuctionPurchaseRequestMessage;
import org.indoles.receiptserviceserver.global.dto.AuctionRefundRequestMessage;

/**
 * 경매 입찰 로직 분리
 * <a href="{https://github.com/woowa-techcamp-2024/Team7-ELEVEN/issues/246}">#246</a>
 */
public interface Auctioneer {

    void process(AuctionPurchaseRequestMessage message);

    void refund(AuctionRefundRequestMessage message);

}
