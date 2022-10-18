package com.darkneees.soapfrontservice.service;


import com.darkneees.soapfrontservice.entity.Social;
import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.mapper.ListUserMapper;
import com.darkneees.soapfrontservice.mapper.SocialMapper;
import com.darkneees.soapfrontservice.mapper.UserMapper;
import users_soap.api.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserServiceImpl implements UserService {

    UserServiceSoapHttpService soap;

    public UserServiceImpl() {
        this.soap = new UserServiceSoapHttpService();
    }

    @Override
    public CompletableFuture<List<User>> getAllUsers() {
        return CompletableFuture.supplyAsync(() -> {
            GetAllUsersResponse response = soap.getUserServiceSoapHttpSoap11()
                    .getAllUsers(new GetAllUsersRequest());
            if(response.getStatus().isSuccess()) return ListUserMapper.INSTANCE.toListUsers(response.getUsers());
            else throw new RuntimeException(response.getStatus().getErrors());
        });
    }

    @Override
    public CompletableFuture<User> getUserByUsername(String username) {
        return CompletableFuture.supplyAsync(() -> {
            GetUserByUsernameRequest request = new GetUserByUsernameRequest();
            request.setUsername(username);
            GetUserByUsernameResponse response = soap.getUserServiceSoapHttpSoap11().getUserByUsername(request);
            if(response.getStatus().isSuccess()) return UserMapper.INSTANCE.toUser(response.getUsers());
            else throw new RuntimeException(response.getStatus().getErrors());
        });
    }

    @Override
    public CompletableFuture<Void> addUser(User user) {
        return CompletableFuture.runAsync(() -> {
            AddUserRequest request = new AddUserRequest();
            request.setUser(UserMapper.INSTANCE.toUserInfo(user));
            AddUserResponse response = soap.getUserServiceSoapHttpSoap11().addUser(request);
            if(!response.getServiceStatus().isSuccess()) throw new RuntimeException(response.getServiceStatus().getErrors());
        });
    }

    @Override
    public CompletableFuture<Void> deleteUserByUsername(String username) {
        return CompletableFuture.runAsync(() -> {
            DeleteUserByUsernameRequest request = new DeleteUserByUsernameRequest();
            request.setUsername(username);
            DeleteUserByUsernameResponse response = soap.getUserServiceSoapHttpSoap11().deleteUserByUsername(request);
            if(!response.getStatus().isSuccess()) throw new RuntimeException(response.getStatus().getErrors());
        });
    }

    @Override
    public CompletableFuture<Void> deleteRoleUser(String username, long id) {
        return CompletableFuture.runAsync(() -> {
            DeleteRoleByUserRequest request = new DeleteRoleByUserRequest();
            request.setUsername(username);
            request.setRole(id);
            DeleteRoleByUserResponse response = soap.getUserServiceSoapHttpSoap11().deleteRoleByUser(request);
            if(!response.getStatus().isSuccess()) throw new RuntimeException(response.getStatus().getErrors());
        });
    }

    @Override
    public CompletableFuture<Void> editUser(User user) {
        return CompletableFuture.runAsync(() -> {
            EditUserByUsernameRequest request = new EditUserByUsernameRequest();
            request.setUser(UserMapper.INSTANCE.toUserInfo(user));
            EditUserByUsernameResponse response = soap.getUserServiceSoapHttpSoap11().editUserByUsername(request);
            if(!response.getStatus().isSuccess()) throw new RuntimeException(response.getStatus().getErrors());
        });
    }

    @Override
    public CompletableFuture<Void> addRoleUser(String username, long id) {
        return CompletableFuture.runAsync(() -> {
            AddUserRolesRequest request = new AddUserRolesRequest();
            request.setRole(id);
            request.setUsername(username);
            AddUserRolesResponse response = soap.getUserServiceSoapHttpSoap11().addUserRoles(request);
            if(!response.getStatus().isSuccess()) throw new RuntimeException(response.getStatus().getErrors());
        });
    }

    @Override
    public CompletableFuture<Void> addSocialUser(String username, Social social) {
        return CompletableFuture.runAsync(() -> {
            AddSocialRequest request = new AddSocialRequest();
            request.setUsername(username);
            request.setSocial(SocialMapper.INSTANCE.toSocialInfo(social));
            AddSocialResponse response = soap.getUserServiceSoapHttpSoap11().addSocial(request);
            if(!response.getStatus().isSuccess()) throw new RuntimeException(response.getStatus().getErrors());
        });
    }

    @Override
    public CompletableFuture<Void> deleteSocialUser(String username, String identifierSocial) {
        return CompletableFuture.runAsync(() -> {
            DeleteSocialRequest request = new DeleteSocialRequest();
            request.setIdentifierSocial(identifierSocial);
            request.setUsername(username);
            DeleteSocialResponse response = soap.getUserServiceSoapHttpSoap11().deleteSocial(request);
            if(!response.getStatus().isSuccess()) throw new RuntimeException(response.getStatus().getErrors());
        });
    }
}
