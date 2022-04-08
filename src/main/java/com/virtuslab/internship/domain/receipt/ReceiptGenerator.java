package com.virtuslab.internship.domain.receipt;

import com.virtuslab.internship.domain.basket.Basket;
import com.virtuslab.internship.domain.discount.GrainsDiscount;
import com.virtuslab.internship.domain.discount.TenPercentDiscount;
import com.virtuslab.internship.domain.product.Product;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReceiptGenerator {

    public static Receipt generate(Basket basket) {
        Map<Product, AtomicInteger> instances = new HashMap<Product, AtomicInteger>();

        basket.getProducts().forEach(product -> {
            if(instances.containsKey(product)){
                AtomicInteger value = instances.get(product);
                value.incrementAndGet();
            }else{
                instances.put(product,new AtomicInteger(1));
            }
        });

        List<ReceiptEntry> receiptEntries = new ArrayList<>();

        instances.keySet()
            .forEach(product -> {
                receiptEntries.add(new ReceiptEntry(product, instances.get(product).get()));
            });

        Receipt receipt = new Receipt(receiptEntries);
        TenPercentDiscount tenPercentDiscount = new TenPercentDiscount();
        GrainsDiscount grainsDiscount = new GrainsDiscount();
        receipt = grainsDiscount.apply(receipt);
        receipt = tenPercentDiscount.apply(receipt);
        return receipt;
    }
}
