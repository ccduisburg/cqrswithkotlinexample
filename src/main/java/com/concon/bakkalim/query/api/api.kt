package com.concon.bakkalim.query.api

import com.concon.bakkalim.read_model.OrderSummary
import com.concon.bakkalim.read_model.ProductSummary
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

class GetOrdersQuery
class GetProductsQuery

interface OrderSummaryRepository:JpaRepository<OrderSummary,UUID>
interface ProductSummaryRepository:JpaRepository<ProductSummary,String>