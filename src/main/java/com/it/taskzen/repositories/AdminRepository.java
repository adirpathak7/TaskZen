/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.it.taskzen.repositories;

import com.it.taskzen.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aditya Pathak R
 */
@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    AdminEntity findByEmail(String email);

}
