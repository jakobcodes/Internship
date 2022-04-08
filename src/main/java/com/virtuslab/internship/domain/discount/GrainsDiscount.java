package com.virtuslab.internship.domain.discount;

import com.virtuslab.internship.domain.product.Product;
import com.virtuslab.internship.domain.receipt.Receipt;
import com.virtuslab.internship.domain.receipt.ReceiptEntry;

import java.math.BigDecimal;

public class GrainsDiscount implements Discount{

    public static String NAME = "GrainsDiscount";

    public Receipt apply(Receipt receipt){
        if(shouldApply(receipt)){
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt){
        return receipt
                .entries()
                .stream()
                .filter(receiptEntry -> receiptEntry.product().type().equals(Product.Type.GRAINS))
                .map(ReceiptEntry::quantity)
                .mapToInt(i -> i)
                .sum() >= 3;
    }
}
