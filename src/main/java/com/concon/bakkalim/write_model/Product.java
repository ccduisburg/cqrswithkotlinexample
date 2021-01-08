package com.concon.bakkalim.write_model;

import com.concon.bakkalim.api.AddProductCommand;
import com.concon.bakkalim.api.ProductAddedEvent;
import com.concon.bakkalim.api.StockUpdatedEvent;
import com.concon.bakkalim.api.UpdateStockCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Product {
    @AggregateIdentifier
    private String id;
    private Double price;
    private Integer stock;
    private String description;

    public Product() {
    }
    @CommandHandler
    public Product(AddProductCommand cmd) {
       apply(new ProductAddedEvent(
               cmd.getId(),
               cmd.getPrice(),
               cmd.getStock(),
               cmd.getDescription()
       ));
    }

    @CommandHandler
    public void handle(UpdateStockCommand cmd){
        if(this.stock>=cmd.getStock()){
            apply(new StockUpdatedEvent(
                    cmd.getId(),
                    cmd.getStock()
            ));
        }
    }
    @EventSourcingHandler
    public void on(StockUpdatedEvent evt){
        id=evt.getId();
        stock=stock=evt.getStock();
    }

    @EventSourcingHandler
    public void on(ProductAddedEvent evt){
        id = evt.getId();
        price = evt.getPrice();
        stock = evt.getStock();
        description = evt.getDescription();
    }
}
