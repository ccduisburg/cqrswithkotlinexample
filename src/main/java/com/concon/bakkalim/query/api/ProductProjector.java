package com.concon.bakkalim.query.api;

import com.concon.bakkalim.api.ProductAddedEvent;
import com.concon.bakkalim.api.StockUpdatedEvent;
import com.concon.bakkalim.read_model.ProductSummary;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductProjector {
private final ProductSummaryRepository repository;

    public ProductProjector(ProductSummaryRepository repository) {
        this.repository = repository;
    }
    @EventHandler
    public void on(ProductAddedEvent evt){
        ProductSummary summary=new ProductSummary(
                evt.getId(),
                evt.getPrice(),
                evt.getStock(),
                evt.getDescription()
        );
        repository.save(summary);
    }
    @EventHandler
    public void on(StockUpdatedEvent evt){
        ProductSummary summary =repository.findById(evt.getId()).orElse(null);
        summary.setStock(summary.getStock()-evt.getStock());
        repository.save(summary);
    }

    @QueryHandler
    public List<ProductSummary> handle(GetProductsQuery query){
        return repository.findAll();
    }
}
