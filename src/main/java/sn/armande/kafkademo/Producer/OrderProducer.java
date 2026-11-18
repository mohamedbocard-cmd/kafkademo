package sn.armande.kafkademo.Producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import sn.armande.kafkademo.dto.OrderEvent;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${kafka.topics.orders}") ///  pour referencier nottre topics.
    private String ordersTopics;

    public CompletableFuture<SendResult<String, OrderEvent>> sendorder(OrderEvent event){
        log.info("Send Order: {} to topics: {}",  event, ordersTopics);

        var future =kafkaTemplate.send(ordersTopics,event);

        future.whenComplete(((result, ex) -> {
            if (ex!=null){
                log.error("Error sending order: {}", event.orderId(),ex);
            }else {
                var metadata = result.getRecordMetadata();
                log.info("Order Sending Succeesfully: {} to partition: {} at offset: {}", event.orderId(), metadata.partition(),metadata.offset());
            }
        }));


        return future;

    }

}
