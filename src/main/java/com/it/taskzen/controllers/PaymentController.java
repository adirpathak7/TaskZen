/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.controllers;

import com.it.taskzen.entities.ClientProjectEntity;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.entities.PaymentEntity;
import com.it.taskzen.entities.PostEntity;
import com.it.taskzen.repositories.ClientMstRepository;
import com.it.taskzen.repositories.ClientProjectRepository;
import com.it.taskzen.repositories.FreelancerMstRepository;
import com.it.taskzen.repositories.PaymentRepository;
import com.it.taskzen.repositories.PostRepository;
import com.it.taskzen.services.PaymentService;
import com.razorpay.Payment;
import com.razorpay.RazorpayException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aditya Pathak R
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping(value = "/api/client")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ClientMstRepository clientMstRepository;

    @Autowired
    private FreelancerMstRepository freelancerRepository;

    @Autowired
    private ClientProjectRepository clientProjectRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping(value = "/pay/freelancer/{freelancer}/{project}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> processPayment(
            @RequestBody Map<String, String> paymentRequest,
            @PathParam("freelancer") FreelancerMasterEntity freelancer,
            @PathParam("project") ClientProjectEntity project) throws RazorpayException {

        
        String amount = paymentRequest.get("amount");

        PaymentEntity payment = paymentService.processPayment(amount,freelancer,project);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Payment initialized successfully.");
        response.put("paymentId", payment.getRazorpayId().toString());
        response.put("status", payment.getStatus());

        return ResponseEntity.ok(response);
    }

}
