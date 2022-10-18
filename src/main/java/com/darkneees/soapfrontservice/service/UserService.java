package com.darkneees.soapfrontservice.service;


import com.darkneees.soapfrontservice.entity.Social;
import com.darkneees.soapfrontservice.entity.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    CompletableFuture<List<User>> getAllUsers();
    CompletableFuture<User> getUserByUsername(String username);
    CompletableFuture<Void> addUser(User user);
    CompletableFuture<Void> deleteUserByUsername(String username);
    CompletableFuture<Void> deleteRoleUser(String username, long id);
    CompletableFuture<Void> editUser(User user);
    CompletableFuture<Void> addRoleUser(String username, long id);
    CompletableFuture<Void> addSocialUser(String username, Social social);
    CompletableFuture<Void> deleteSocialUser(String username, String identifierSocial);

}