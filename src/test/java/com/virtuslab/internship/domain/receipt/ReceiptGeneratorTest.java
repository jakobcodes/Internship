package com.virtuslab.internship.domain.receipt;

import com.virtuslab.internship.domain.basket.Basket;
import com.virtuslab.internship.domain.product.ProductDb;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReceiptGeneratorTest {

    @Test
    void shouldGenerateReceiptForGivenBasket() {
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk").get();
        var bread = productDb.getProduct("Bread").get();
        var apple = productDb.getProduct("Apple").get();
        var expectedTotalPrice = milk.price().multiply(BigDecimal.valueOf(2)).add(bread.price()).add(apple.price());

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(apple);

        // When
        var receipt = ReceiptGenerator.generate(cart);

        // Then
        assertNotNull(receipt);
        assertEquals(3, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }

    @Test
    void shouldGenerateEmptyReceipt() {
        // Given
        var cart = new Basket();
        var expectedTotalPrice = BigDecimal.valueOf(0);

        // When
        var receipt = ReceiptGenerator.generate(cart);

        // Then
        assertNotNull(receipt);
        assertEquals(0, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }


}
