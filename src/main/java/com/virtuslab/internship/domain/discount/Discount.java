package com.virtuslab.internship.domain.discount;

import com.virtuslab.internship.domain.receipt.Receipt;

public interface Discount {
    public Receipt apply(Receipt receipt);
}
