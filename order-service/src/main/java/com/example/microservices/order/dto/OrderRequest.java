package com.example.microservices.order.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity) {
}
