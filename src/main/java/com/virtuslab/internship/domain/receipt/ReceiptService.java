package com.virtuslab.internship.domain.receipt;


import com.virtuslab.internship.common.exception.NotFoundException;
import com.virtuslab.internship.domain.basket.Basket;
import com.virtuslab.internship.domain.product.Product;
import com.virtuslab.internship.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    @Autowired
    private ProductService productService;

    public Receipt GetReceipt(List<String> products){
        Basket basket = new Basket();

        products.forEach(productName -> {
            Product product = productService
                    .findByName(productName)
                    .orElseThrow(() -> new NotFoundException("Product with given name does not exist"));

            basket.addProduct(product);
        });

        return ReceiptGenerator.generate(basket);
    }

}
