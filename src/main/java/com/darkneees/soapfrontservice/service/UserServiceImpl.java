package com.darkneees.soapfrontservice.service;


import com.darkneees.soapfrontservice.entity.Social;
import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.mapper.ListUserMapper;
import com.darkneees.soapfrontservice.mapper.SocialMapper;
import com.darkneees.soapfrontservice.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import users_soap.api.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl INSTANCE;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    UserServiceSoapHttpService soap;
    ExecutorService service = Executors.newFixedThreadPool(8);

    private UserServiceImpl() {
        this.soap = new UserServiceSoapHttpService();
    }

    public static UserServiceImpl getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public CompletableFuture<List<User>> getAllUsers() {
        return CompletableFuture.supplyAsync(() -> {
            GetAllUsersResponse response = soap.getUserServiceSoapHttpSoap11()
                    .getAllUsers(new GetAllUsersRequest());
            log.info("Try get all users");
            log.info("Service status: " + response.getStatus().isSuccess());
            if(response.getStatus().isSuccess()) return ListUserMapper.INSTANCE.toListUsers(response.getUsers());
            else {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        }, service);
    }

    @Override
    public CompletableFuture<User> getUserByUsername(String username) {
        return CompletableFuture.supplyAsync(() -> {
            GetUserByUsernameRequest request = new GetUserByUsernameRequest();
            request.setUsername(username);
            GetUserByUsernameResponse response = soap.getUserServiceSoapHttpSoap11().getUserByUsername(request);
            log.info("Try get user with username {}", username);
            log.info("Service status: " + response.getStatus().isSuccess());
            if(response.getStatus().isSuccess()) return UserMapper.INSTANCE.toUser(response.getUsers());
            else {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        }, service);
    }

    @Override
    public CompletableFuture<Void> addUser(User user) {
        return CompletableFuture.runAsync(() -> {
            AddUserRequest request = new AddUserRequest();
            request.setUser(UserMapper.INSTANCE.toUserInfo(user));
            AddUserResponse response = soap.getUserServiceSoapHttpSoap11().addUser(request);
            log.info("Try add user");
            log.info("Service status: " + response.getStatus().isSuccess());
            if(!response.getStatus().isSuccess()) {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        }, service);
    }

    @Override
    public CompletableFuture<Void> deleteUserByUsername(String username) {
        return CompletableFuture.runAsync(() -> {
            DeleteUserByUsernameRequest request = new DeleteUserByUsernameRequest();
            request.setUsername(username);
            DeleteUserByUsernameResponse response = soap.getUserServiceSoapHttpSoap11().deleteUserByUsername(request);
            log.info("Try delete user with username: {}", username);
            log.info("Service status: " + response.getStatus().isSuccess());
            if(!response.getStatus().isSuccess()) {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        }, service);
    }

    @Override
    public CompletableFuture<Void> deleteRoleUser(String username, long id) {
        return CompletableFuture.runAsync(() -> {
            DeleteRoleByUserRequest request = new DeleteRoleByUserRequest();
            request.setUsername(username);
            request.setRole(id);
            DeleteRoleByUserResponse response = soap.getUserServiceSoapHttpSoap11().deleteRoleByUser(request);
            log.info("Service status: " + response.getStatus().isSuccess());
            if(!response.getStatus().isSuccess()) {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        }, service);
    }

    @Override
    public CompletableFuture<Void> editUser(User user) {
        return CompletableFuture.runAsync(() -> {
            EditUserByUsernameRequest request = new EditUserByUsernameRequest();
            request.setUser(UserMapper.INSTANCE.toUserInfo(user));
            EditUserByUsernameResponse response = soap.getUserServiceSoapHttpSoap11().editUserByUsername(request);
            log.info("Try edit user with username: {}", user.getUsername());
            log.info("Service status: " + response.getStatus().isSuccess());
            if(!response.getStatus().isSuccess()) {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        }, service);
    }

    @Override
    public CompletableFuture<Void> addRoleUser(String username, long id) {
        return CompletableFuture.runAsync(() -> {
            AddUserRolesRequest request = new AddUserRolesRequest();
            request.setRole(id);
            request.setUsername(username);
            AddUserRolesResponse response = soap.getUserServiceSoapHttpSoap11().addUserRoles(request);
            log.info("Service status: " + response.getStatus().isSuccess());
            if(!response.getStatus().isSuccess()) {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        }, service);
    }

    @Override
    public CompletableFuture<Void> addSocialUser(String username, Social social) {
        return CompletableFuture.runAsync(() -> {
            AddSocialRequest request = new AddSocialRequest();
            request.setUsername(username);
            request.setSocial(SocialMapper.INSTANCE.toSocialInfo(social));
            AddSocialResponse response = soap.getUserServiceSoapHttpSoap11().addSocial(request);
            log.info("Service status: " + response.getStatus().isSuccess());
            if(!response.getStatus().isSuccess()) {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        }, service);
    }

    @Override
    public CompletableFuture<Void> deleteSocialUser(String username, String identifierSocial) {
        return CompletableFuture.runAsync(() -> {
            DeleteSocialRequest request = new DeleteSocialRequest();
            request.setIdentifierSocial(identifierSocial);
            request.setUsername(username);
            DeleteSocialResponse response = soap.getUserServiceSoapHttpSoap11().deleteSocial(request);
            log.info("Service status: " + response.getStatus().isSuccess());
            if(!response.getStatus().isSuccess()) {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        });
    }
}
