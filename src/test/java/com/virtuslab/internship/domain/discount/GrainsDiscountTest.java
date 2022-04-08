package com.virtuslab.internship.domain.discount;

import com.virtuslab.internship.domain.product.ProductDb;
import com.virtuslab.internship.domain.receipt.Receipt;
import com.virtuslab.internship.domain.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrainsDiscountTest {

    @Test
    void shouldApply15PercentGrainsDiscountWhenAtLeast3GrainProducts(){
//        Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread").get();
        var cereals = productDb.getProduct("Cereals").get();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(receiptEntries);
        var discount = new GrainsDiscount();
        var expectedTotalPrice = bread.price().add(cereals.price().multiply(BigDecimal.valueOf(2))).multiply(BigDecimal.valueOf(0.85));

//        When
        var receiptAfterDiscount = discount.apply(receipt);

//        Then
        assertEquals(expectedTotalPrice,receiptAfterDiscount.totalPrice());
        assertEquals(1,receiptAfterDiscount.discounts().size());

    }

    @Test
    void shouldNotApply15PercentGrainsDiscountWhenLessThan3GrainProducts(){
//        Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread").get();
        var cereals = productDb.getProduct("Cereals").get();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);
        var discount = new GrainsDiscount();
        var expectedTotalPrice = bread.price().add(cereals.price());

//        When
        var receiptAfterDiscount = discount.apply(receipt);

//        Then
        assertEquals(expectedTotalPrice,receiptAfterDiscount.totalPrice());
        assertEquals(0,receiptAfterDiscount.discounts().size());

    }

}