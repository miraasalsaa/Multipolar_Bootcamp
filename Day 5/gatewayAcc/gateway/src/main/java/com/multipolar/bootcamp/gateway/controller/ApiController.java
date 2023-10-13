package com.multipolar.bootcamp.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.gateway.dto.AccountDTO;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")

public class ApiController {

    private static final String ACCOUNT_URL = "http://localhost:8081/account";
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;
    private final AccessLogService logService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper, AccessLogService logService){
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.logService = logService;
    }

    //<?> krn dinamis, balikannya bisa error message atau data cust
    //http://localhost:8080/api/getAccounts
    @GetMapping("/getAccounts")
    public ResponseEntity<?> getAccount() throws JsonProcessingException {

        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        //akses API customer dan dapatkan datanya
        try{
            ResponseEntity<?> response = restTemplateUtil.getList(ACCOUNT_URL, new ParameterizedTypeReference<>() {});
            //kirim akses log
            AccessLog accessLog = new AccessLog("GET", ACCOUNT_URL, response.getStatusCodeValue(), "Successful", clientIP, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(),List.class);
            AccessLog accessLog = new AccessLog("GET", ACCOUNT_URL, ex.getRawStatusCode(), "successful", clientIP, userAgent, LocalDateTime.now());
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
    @PostMapping("/createAccounts")
    public ResponseEntity<?> postProduct(@RequestBody AccountDTO accountDTO) throws JsonProcessingException {
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        //akses API customer dan dapatkan datanya
        try {
            ResponseEntity<?> response = restTemplateUtil.post(ACCOUNT_URL, accountDTO, AccountDTO.class);
            AccessLog accessLog = new AccessLog("CREATE", ACCOUNT_URL, response.getStatusCodeValue(), "Successful", clientIP, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("CREATE", ACCOUNT_URL, ex.getRawStatusCode(), "Failed", clientIP, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id) throws JsonProcessingException {
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.delete(ACCOUNT_URL+"/"+id);
            //kirim accesslog
            AccessLog accessLog = new AccessLog("DELETE", ACCOUNT_URL, response.getStatusCodeValue(), "Successful", clientIP, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.notFound().build();
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("DELETE", ACCOUNT_URL, ex.getRawStatusCode(), "Failed", clientIP, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody AccountDTO accountDTO) throws JsonProcessingException {
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.put(ACCOUNT_URL+"/"+id , accountDTO, AccountDTO.class);
            //kirim accesslog
            AccessLog accessLog = new AccessLog("UPDATE", ACCOUNT_URL, response.getStatusCodeValue(), "Successful", clientIP, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("UPDATE", ACCOUNT_URL, ex.getRawStatusCode(), "Failed", clientIP, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return  ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    //get product by id
    @GetMapping("/getAccounts/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable String id) throws JsonProcessingException {
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        try {
            ResponseEntity<?> response = restTemplateUtil.getList(ACCOUNT_URL+"/"+id , new ParameterizedTypeReference<>(){});
            //kirim accesslog
            AccessLog accessLog = new AccessLog("GET", ACCOUNT_URL+"/"+id, response.getStatusCodeValue(), "Successful", clientIP, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException ex){
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog("GET", ACCOUNT_URL+"/"+id, ex.getRawStatusCode(), "Failed", clientIP, userAgent, LocalDateTime.now());
            logService.logAccess(accessLog);
            return  ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }

    }
}
