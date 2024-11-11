package org.indoles.receiptserviceserver.core.auction.infra;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.auction.dto.AuctionSearchCondition;
import org.indoles.receiptserviceserver.core.auction.dto.SellerAuctionSearchCondition;
import org.indoles.receiptserviceserver.core.auction.entity.AuctionEntity;

import java.util.List;

//@RequiredArgsConstructor
//public class AuctionQueryDslRepositoryImpl implements AuctionQueryDslRepository {
//
//    private final JPAQueryFactory query;
//
//    @Override
//    public List<AuctionEntity> findAllBy(AuctionSearchCondition condition) {
//        QAuctionEntity auction = QAuctionEntity.auctionEntity;
//        return query
//                .select(auction)
//                .from(auction)
//                .orderBy(auction.id.desc())
//                .limit(condition.size())
//                .offset(condition.offset())
//                .fetch();
//    }
//
//    @Override
//    public List<AuctionEntity> findAllBy(SellerAuctionSearchCondition condition) {
//        QAuctionEntity auction = QAuctionEntity.auctionEntity;
//        return query
//                .select(auction)
//                .from(auction)
//                .where(auction.sellerId.eq(condition.sellerId()))
//                .orderBy(auction.id.desc())
//                .limit(condition.size())
//                .offset(condition.offset())
//                .fetch();
//    }
//}
