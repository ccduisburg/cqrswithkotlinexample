package com.concon.bakkalim.controller;

import com.concon.bakkalim.api.AddProductCommand;
import com.concon.bakkalim.query.api.GetProductsQuery;
import com.concon.bakkalim.read_model.ProductSummary;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class ProductController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public ProductController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/addProduct")
    public void handle(@RequestBody ProductSummary summary){
        AddProductCommand cmd= new AddProductCommand(
                summary.getId(),
                summary.getPrice(),
                summary.getStock(),
                summary.getDescription()
        );
        commandGateway.sendAndWait(cmd);
    }
    @GetMapping("/products")
    public CompletableFuture<List<ProductSummary>> getProducts(){
        return queryGateway.query(new GetProductsQuery(), ResponseTypes.multipleInstancesOf(ProductSummary.class));
    }


}
