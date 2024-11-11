package org.indoles.receiptserviceserver.core.auction.service.auctioneer;



import java.util.Map;

//@Service
//@RequiredArgsConstructor
//public class LazyAuctioneer implements Auctioneer {
//
//    private final StringRedisTemplate redisTemplate;
//    private final ObjectMapper objectMapper;
//    @Value("${stream.key}")
//    private String streamKey;
//
//    @Override
//    public void process(AuctionPurchaseRequestMessage message) {
//
//        String messageType = "purchase";
//
//        try {
//            String stringMessage = objectMapper.writeValueAsString(message);
//
//            StringRecord record = StreamRecords
//                    .string(Map.of(messageType, stringMessage))
//                    .withStreamKey(streamKey);
//            redisTemplate.opsForStream().add(record);
//
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void refund(AuctionRefundRequestMessage message) {
//        String messageType = "refund";
//
//        try {
//            String stringMessage = objectMapper.writeValueAsString(message);
//
//            StringRecord record = StreamRecords
//                    .string(Map.of(messageType, stringMessage))
//                    .withStreamKey(streamKey);
//            redisTemplate.opsForStream().add(record);
//
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//}
