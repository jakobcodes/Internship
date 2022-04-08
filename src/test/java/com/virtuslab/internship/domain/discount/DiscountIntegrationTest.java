package com.virtuslab.internship.domain.discount;

import com.virtuslab.internship.domain.basket.Basket;
import com.virtuslab.internship.domain.product.ProductDb;
import com.virtuslab.internship.domain.receipt.ReceiptGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountIntegrationTest {

    @Test
    void shouldApply15PercentGrainsDiscountAnd10PercentDiscountWhenAtLeast3GrainProductsAndPriceAbove50(){
        //        Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread").get();
        var cereals = productDb.getProduct("Cereals").get();
        var steak = productDb.getProduct("Steak").get();
        Basket basket = new Basket();
        basket.addProduct(bread);
        basket.addProduct(cereals);
        basket.addProduct(cereals);
        basket.addProduct(steak);

        var expectedTotalPrice = bread.price().add(cereals.price().multiply(BigDecimal.valueOf(2)).add(steak.price())).multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));

        // When
        var receipt = ReceiptGenerator.generate(basket);

        // Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(2, receipt.discounts().size());
    }
    @Test
    void shouldApply15PercentGrainsDiscountButNotApply10PercentDiscountWhenAtLeast3GrainProductsAndPriceIsBelow50(){
        //        Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread").get();
        var cereals = productDb.getProduct("Cereals").get();
        var pork = productDb.getProduct("Pork").get();
        Basket basket = new Basket();
        basket.addProduct(bread);
        basket.addProduct(cereals);
        basket.addProduct(cereals);
        basket.addProduct(pork);
        basket.addProduct(pork);

        var expectedTotalPrice = bread.price().add(cereals.price().multiply(BigDecimal.valueOf(2)).add(pork.price().multiply(BigDecimal.valueOf(2)))).multiply(BigDecimal.valueOf(0.85));

        // When
        var receipt = ReceiptGenerator.generate(basket);

        // Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
    }

    @Test
    void shouldNotApply15PercentGrainsDiscountButShouldApply10PercentDiscountWhenLessThan3GrainProductsAndPriceIsAbove50(){
        //        Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread").get();
        var cereals = productDb.getProduct("Cereals").get();
        var pork = productDb.getProduct("Pork").get();
        Basket basket = new Basket();
        basket.addProduct(bread);
        basket.addProduct(cereals);
        basket.addProduct(pork);
        basket.addProduct(pork);
        basket.addProduct(pork);

        var expectedTotalPrice = bread.price().add(cereals.price().add(pork.price().multiply(BigDecimal.valueOf(3)))).multiply(BigDecimal.valueOf(0.9));

        // When
        var receipt = ReceiptGenerator.generate(basket);

        // Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
    }

    @Test
    void shouldNotApplyAnyDiscountWhenLessThan3GrainProductsAndPriceIsBelow50(){
        //        Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread").get();
        var pork = productDb.getProduct("Pork").get();
        Basket basket = new Basket();
        basket.addProduct(bread);
        basket.addProduct(pork);

        var expectedTotalPrice = bread.price().add(pork.price());

        // When
        var receipt = ReceiptGenerator.generate(basket);

        // Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }
}
