package com.virtuslab.internship.api.receipt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReceiptControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void shouldReturnSuccessfulResponse200() throws Exception {

        ReceiptController.CreateReceiptEntry steak = new ReceiptController.CreateReceiptEntry();
        steak.setName("Steak");
        ReceiptController.CreateReceiptEntry bread = new ReceiptController.CreateReceiptEntry();
        bread.setName("Bread");

        ReceiptController.CreateReceiptRequest rq = new ReceiptController.CreateReceiptRequest();
        rq.setReceiptEntries(List.of(steak,bread));

        final String baseUrl = "http://localhost:"+randomServerPort +"/receipt";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<ReceiptController.CreateReceiptRequest> request = new HttpEntity<>(rq, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri,request,String.class);

        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
    }
}