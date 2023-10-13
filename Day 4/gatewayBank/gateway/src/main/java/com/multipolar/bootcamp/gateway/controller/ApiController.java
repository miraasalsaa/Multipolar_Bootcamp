package com.multipolar.bootcamp.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.gateway.dto.ProductDTO;
import com.multipolar.bootcamp.gateway.dto.ErrorMessageDTO;
import com.multipolar.bootcamp.gateway.kafka.AccessLog;
import com.multipolar.bootcamp.gateway.service.AccessLogService;
import com.multipolar.bootcamp.gateway.util.RestTemplateUtil;
//import org.apache.catalina.AccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")

public class ApiController {

    private static final String PRODUCT_URL = "http://localhost:8081/product";
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;
    private final AccessLogService logService;

    @Autowired
    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper, AccessLogService logService){
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.logService = logService;
    }

    //http://localhost:8080/api/getProduct
    @GetMapping("/getProducts")
    public ResponseEntity<?> getProduct() throws JsonProcessingException {
        //akses API customer dan dapatkan datanya

        try{
            ResponseEntity<?> response = restTemplateUtil.getList(PRODUCT_URL, new ParameterizedTypeReference<>() {});
            //kirim akses log
            AccessLog accessLog = new AccessLog("GET", PRODUCT_URL, response.getStatusCodeValue(), LocalDateTime.now(),"Successful");
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(),List.class);
            AccessLog accessLog = new AccessLog("GET", PRODUCT_URL, ex.getRawStatusCode(), LocalDateTime.now(),"Failed");
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    /*
    {
    "dateCreated": "2023-10-12T06:00:13.648Z", //isi otomatis, di postman hapus aja
    "id": "string",
    "interestRate": 0,
    "maximumLoanAmount": 0,
    "minimumBalance": 0,
    "productName": "string",
    "productType": "CHECKING_ACCOUNT",
    "termAndConditions": "string"
  }
     */
    @PostMapping("/createProducts")
    public ResponseEntity<?> postProduct(@RequestBody ProductDTO productDTO) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.post(PRODUCT_URL, productDTO, ProductDTO.class);
            AccessLog accessLog = new AccessLog("CREATE", PRODUCT_URL, response.getStatusCodeValue(), LocalDateTime.now(),"Successful");
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("CREATE", PRODUCT_URL, ex.getRawStatusCode(), LocalDateTime.now(),"Failed");
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.delete(PRODUCT_URL+"/"+id);
            //kirim accesslog
            AccessLog accessLog =  new AccessLog("DELETE",PRODUCT_URL, response.getStatusCodeValue(),LocalDateTime.now(),"Successful");
            logService.logAccess(accessLog);
            return ResponseEntity.notFound().build();
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog =  new AccessLog("DELETE",PRODUCT_URL, ex.getRawStatusCode(),LocalDateTime.now(),"Failed");
            logService.logAccess(accessLog);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.put(PRODUCT_URL+"/"+id , productDTO, ProductDTO.class);
            //kirim accesslog
            AccessLog accessLog =  new AccessLog("UPDATE",PRODUCT_URL, response.getStatusCodeValue(),LocalDateTime.now(),"Successful");
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog =  new AccessLog("UPDATE",PRODUCT_URL, ex.getRawStatusCode(),LocalDateTime.now(),"Failed");
            logService.logAccess(accessLog);
            return  ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    //get product by id
    @GetMapping("/getProducts/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.getList(PRODUCT_URL+"/"+id , new ParameterizedTypeReference<>(){});
            //kirim accesslog
            AccessLog accessLog =  new AccessLog("GET",PRODUCT_URL, response.getStatusCodeValue(),LocalDateTime.now(),"Successful");
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog =  new AccessLog("GET",PRODUCT_URL, ex.getRawStatusCode(),LocalDateTime.now(),"Failed");
            logService.logAccess(accessLog);
            return  ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }

    }
}
