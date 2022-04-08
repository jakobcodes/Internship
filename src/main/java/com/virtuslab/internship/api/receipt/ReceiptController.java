package com.virtuslab.internship.api.receipt;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.virtuslab.internship.common.exception.NotFoundException;
import com.virtuslab.internship.domain.receipt.Receipt;
import com.virtuslab.internship.domain.receipt.ReceiptEntry;
import com.virtuslab.internship.domain.receipt.ReceiptService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;

    @RequestMapping("/receipt")
    public ReceiptResponse createReceipt(@RequestBody CreateReceiptRequest request){
        try {
            Receipt receipt = receiptService.GetReceipt(request.getReceiptEntries().stream().map(CreateReceiptEntry::getName).toList());

            return ReceiptResponse.fromReceipt(receipt);
        } catch(NotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Product not found",exc);
        }
    }

    @Data
    @Builder
    static class ReceiptResponse{
        private List<ReceiptEntryResponse> products;
        private List<String> discounts;
        BigDecimal totalPrice;

        static ReceiptResponse fromReceipt(Receipt receipt){
            return ReceiptResponse.builder()
                    .products(receipt.entries()
                            .stream()
                            .map(ReceiptEntryResponse::fromReceiptEntry)
                            .collect(Collectors.toList())
                    )
                    .discounts(receipt.discounts())
                    .totalPrice(receipt.totalPrice())
                    .build();
        }
    }

    @Data
    @Builder
    static class ReceiptEntryResponse {
        private String name;
        private int quantity;
        private BigDecimal totalPrice;

        static ReceiptEntryResponse fromReceiptEntry(ReceiptEntry receiptEntry){
            return ReceiptEntryResponse.builder()
                    .name(receiptEntry.product().name())
                    .quantity(receiptEntry.quantity())
                    .totalPrice(receiptEntry.totalPrice())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    static class CreateReceiptRequest{
        @JsonProperty("basket")
        private List<CreateReceiptEntry> receiptEntries;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class CreateReceiptEntry{
        @JsonProperty("name")
        private String name;
    }
}
