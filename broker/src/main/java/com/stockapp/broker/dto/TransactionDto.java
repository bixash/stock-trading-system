package com.stockapp.broker.dto;

public record TransactionDto(
        long tradeId,
        String clientId,
        String ticker,
        long creditQuantity,
        long debitQuantity,
        String description,
        float tradedPrice,
        String tradeDate
) { }
