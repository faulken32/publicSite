/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinity.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infinity.dto.Candidat;
import com.infinity.dto.Clients;
import com.infinity.repository.UserRepository;
import com.infinity.entity.Users;
import com.infinity.entity.UsersRoles;
import com.infinity.exception.UserException;
import com.infinity.repository.UserRolesRepository;
import com.infinity.service.CandidatService;
import com.infinity.service.ClientsService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
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
    @Autowired
    private ClientsService clientsService;

    private final static String USER_EXIST = "user already existe";

    /**
     *
     * @param name
     * @param pass
     * @return
     * @throws UserException
     * @throws IOException
     */
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
            } else if (byEmail.isEmpty()) {
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
            throw new UserException(USER_EXIST);
        }
        return users;
    }

    /**
     * procedure to store new client creation
     *
     * @param name
     * @param pass
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws UserException
     */
    public Users saveClient(String name, String pass) throws JsonProcessingException, IOException, InterruptedException, ExecutionException, UserException {

        Users findByName = repository.findByname(name);
        Users users = new Users();
        if (findByName == null) {

            Clients clients = new Clients();
            clients.setEmail(name);

            String newClientId = clientsService.addClient(clients);
            clients.setId(newClientId);

            clientsService.updateOneById(clients);
            users.setId(newClientId);
            users.setName(name);
            users.setPass(pass);
            users.setEnable(1);
            repository.save(users);

            UsersRoles usersRoles = new UsersRoles();
            usersRoles.setId(newClientId);
            usersRoles.setName(name);
            usersRoles.setRole("ROLE_CLIENT");
            repositoryRoles.save(usersRoles);
        } else {
            throw new UserException(USER_EXIST);
        }
        return users;
    }

    /**
     *
     * @param name
     * @return a user from db
     */
    public Users findByName(String name) {

        return repository.findByname(name);

    }

    public void saveOrUpdate(Users users) {

        repository.save(users);

    }

}
