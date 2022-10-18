package com.darkneees.soapfrontservice.service;

import com.darkneees.soapfrontservice.entity.Role;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RoleService {

    CompletableFuture<List<Role>> getAllRoles();
    CompletableFuture<Void> addRole(Role role);

}
