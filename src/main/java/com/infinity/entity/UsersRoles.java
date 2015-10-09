/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author t311372
 */
@Entity
@Table(name = "USERS_ROLES")
public class UsersRoles implements Serializable {

    private String id;
    private String name; 
    private String role;

    @Id 
    @Column(name = "ID", unique = true, nullable = false, length = 20 )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "NAME", nullable = true, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name = "ROLE", nullable = true, length = 40)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
   
    

}
