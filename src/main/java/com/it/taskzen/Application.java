/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Aditya Pathak R
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println("Server ready to start.");
        SpringApplication.run(Application.class, args);
        System.out.println("Server started.");
    }
}
