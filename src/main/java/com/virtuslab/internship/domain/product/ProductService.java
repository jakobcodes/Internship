package com.virtuslab.internship.domain.product;

import com.virtuslab.internship.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDb productDb;

    public Optional<Product> findByName(String name){
        if(productDb.getProduct(name).isPresent()){
            return Optional.of(productDb.getProduct(name).get());
        }else
            throw new NotFoundException("Product with given name does not exist");
    }

}
