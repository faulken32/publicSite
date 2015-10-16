/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.dao;

import com.infinity.dto.Candidat;
import com.infinity.repository.UserRepository;
import com.infinity.entity.Users;
import com.infinity.entity.UsersRoles;
import com.infinity.exception.UserException;
import com.infinity.repository.UserRolesRepository;
import com.infinity.service.CandidatService;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author t311372
 */
@Service
public class UsersDao {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserRolesRepository repositoryRoles;

    @Autowired
    private CandidatService candidatService;

    public Users saveUser(String name, String pass) throws UserException, IOException {

        Users FindByName = repository.findByname(name);
        Users users = new Users();

        if (FindByName == null) {

            users.setName(name);
            users.setPass(pass);
            users.setEnable(1);
            Candidat candidat = new Candidat();
            candidat.setEmail(name);

            ArrayList<Candidat> byEmail = candidatService.getByEmail(name);
            if (byEmail.size() > 0) {

                Candidat get = byEmail.get(0);
                users.setId(get.getId());
                repository.save(users);
                UsersRoles usersRoles = new UsersRoles();
                usersRoles.setId(get.getId());
                usersRoles.setName(name);
                usersRoles.setRole("ROLE_USER");
                repositoryRoles.save(usersRoles);

            } else if(byEmail.isEmpty() ) {

                String addCandidat = candidatService.addCandidat(candidat);
                users.setId(addCandidat);
                candidat.setId(addCandidat);
                candidatService.updateOneById(candidat);
                repository.save(users);
                UsersRoles usersRoles = new UsersRoles();
                usersRoles.setId(addCandidat);
                usersRoles.setName(name);
                usersRoles.setRole("ROLE_USER");
                repositoryRoles.save(usersRoles);
            }

        } else {

            throw new UserException("user already existe");
        }

        return users;

    }
    /**
     * 
     * @param name
     * @return a user from db
     */
    public Users findByName(String name){
    
        return repository.findByname(name);
        
    }
    
    public void saveOrUpdate(Users users){
    
        
        repository.save(users);
    
    
    }
    

}
