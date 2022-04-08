package com.virtuslab.internship.domain.receipt;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Builder
public record Receipt(
        List<ReceiptEntry> entries,
        List<String> discounts,
        BigDecimal totalPrice) {

    public Receipt(List<ReceiptEntry> entries) {
        this(entries,
                new LinkedList<>(),
                entries.stream()
                        .map(ReceiptEntry::totalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }
}
