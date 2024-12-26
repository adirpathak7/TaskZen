/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.services;

import com.it.taskzen.entities.ClientProjectEntity;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.entities.PaymentEntity;
import com.it.taskzen.jwt.JWTService;
import com.it.taskzen.repositories.ClientMstRepository;
import com.it.taskzen.repositories.ClientProjectRepository;
import com.it.taskzen.repositories.FreelancerMstRepository;
import com.it.taskzen.repositories.PaymentRepository;
import com.it.taskzen.repositories.PostRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${razorpay.key_id}")
    private String razorpayId;

    @Value("${razorpay.key_secret}")
    private String razorpaySecret;

    private RazorpayClient razorpayClient;

    @Autowired
    private JWTService jWTService;

    @Autowired
    private ClientMstRepository clientMstRepository;

    @Autowired
    private FreelancerMstRepository freelancerMstRepository;

    @Autowired
    private ClientProjectRepository clientProjectRepository;

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    public void init() {
//        System.out.println("Razorpay ID: " + razorpayId);
//        System.out.println("Razorpay Secret: " + razorpaySecret);
        try {
            razorpayClient = new RazorpayClient(razorpayId, razorpaySecret);
        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to initialize RazorpayClient", e);
        }
    }

    @Transactional
    public PaymentEntity processPayment(String amount, FreelancerMasterEntity freelancer, ClientProjectEntity project) throws RazorpayException {
        long amountInPaise = Long.parseLong(amount) / 100; // Convert to paise

        JSONObject requestJSON = new JSONObject();
        requestJSON.put("amount", amountInPaise);
        requestJSON.put("currency", "INR");
//        System.out.println("the amountInPaise: " + amountInPaise);
//        System.out.println("the amount: " + amount);

        String receipt = "adi_" + UUID.randomUUID().toString();
        receipt = receipt.substring(0, 40);

        requestJSON.put("receipt", receipt);

        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(requestJSON);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(amountInPaise);
        paymentEntity.setRazorpayId(razorpayOrder.get("id"));
        paymentEntity.setStatus("created");
        paymentEntity.setFreelancer(freelancer);
        paymentEntity.setProject(project);
        
        return paymentRepository.save(paymentEntity);
    }

    public String createOrderViaHttpRequest(PaymentEntity payment) {
        try {
            String authValue = razorpayId + ":" + razorpaySecret;
            String encodedAuth = Base64.getEncoder().encodeToString(authValue.getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            headers.set("Content-Type", "application/json");

            JSONObject requestJSON = new JSONObject();
            Long amountInPaise = payment.getAmount() * 100;
            requestJSON.put("amount", amountInPaise);
            requestJSON.put("currency", "INR");
            requestJSON.put("receipt", "adi");

            RestTemplate restTemplate = new RestTemplate();
            String razorpayUrl = "https://api.razorpay.com/v1/orders";
            ResponseEntity<String> response = restTemplate.postForEntity(
                    razorpayUrl,
                    new org.springframework.http.HttpEntity<>(requestJSON.toString(), headers),
                    String.class
            );

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating order via HTTP request: " + e.getMessage();
        }
    }
    
    public List<PaymentEntity> getPaymentsWithCreatedStatusAndProjects() {
        return paymentRepository.findPaymentsWithCreatedStatusAndClientProject();
    }
}
