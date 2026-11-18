package sn.armande.kafkademo.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.armande.kafkademo.Producer.OrderProducer;
import sn.armande.kafkademo.dto.CreateOrderRequest;
import sn.armande.kafkademo.dto.OrderEvent;

import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/orders")
public class OrderController {

    private  OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>>placeOrder(
            @Valid @RequestBody CreateOrderRequest request
    ) {
        var orderId = UUID.randomUUID().toString();
        var event = OrderEvent.of(
                orderId,
                request.customerId(),
                request.productName(),
                request.quantity(),
                request.totalAmount()

        );
        orderProducer.sendorder(event);
        log.info("orders sending SucceesFully : {}", orderId);
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(Map.of(
                            "orderID",orderId,
                            "status", "CREATED",
                            "message", "OrderMessage SuccessFully and under processing"
                    ));
    }






}
