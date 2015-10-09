/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.repository;

import com.infinity.entity.Users;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author t311372
 */


public interface UserRepository extends CrudRepository<Users, String>{

    
   
        Users findByname(String name);
        
    
    
    
}
