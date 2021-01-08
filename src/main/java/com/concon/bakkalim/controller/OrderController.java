package com.concon.bakkalim.controller;

import com.concon.bakkalim.api.CreateOrderCommand;
import com.concon.bakkalim.query.api.GetOrdersQuery;
import com.concon.bakkalim.query.api.GetProductsQuery;
import com.concon.bakkalim.read_model.OrderSummary;
import com.concon.bakkalim.read_model.ProductSummary;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class OrderController {
    private CommandGateway commandGateway;
    private QueryGateway queryGateway;

    public OrderController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/createOrder")
    public void handle(@RequestBody OrderSummary summary){
        CreateOrderCommand cmd= new CreateOrderCommand(
                UUID.randomUUID(),
                summary.getPrice(),
                summary.getNumber(),
                summary.getProductid()
        );
        commandGateway.sendAndWait(cmd);
    }
    @GetMapping("/orders")
    public CompletableFuture<List<OrderSummary>> getProducts(){
        return queryGateway.query(new GetOrdersQuery(), ResponseTypes.multipleInstancesOf(OrderSummary.class));
    }
}
