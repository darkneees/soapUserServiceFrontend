package com.darkneees.soapfrontservice.service;

import com.darkneees.soapfrontservice.entity.Role;
import com.darkneees.soapfrontservice.mapper.ListRoleMapper;
import com.darkneees.soapfrontservice.mapper.RoleMapper;
import users_soap.api.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RoleServiceImpl implements RoleService {

    UserServiceSoapHttpService soap;

    public RoleServiceImpl() {
        this.soap = new UserServiceSoapHttpService();
    }

    @Override
    public CompletableFuture<List<Role>> getAllRoles() {
        return CompletableFuture.supplyAsync(() -> {
            GetAllRolesResponse response = soap.getUserServiceSoapHttpSoap11().getAllRoles(new GetAllRolesRequest());
            if(response.getStatus().isSuccess()) return ListRoleMapper.INSTANCE.toListRoles(response.getRoles());
            else throw new RuntimeException(response.getStatus().getErrors());
        });
    }

    @Override
    public CompletableFuture<Void> addRole(Role role) {
        return CompletableFuture.runAsync(() -> {
            AddRoleRequest request = new AddRoleRequest();
            request.setRoles(RoleMapper.INSTANCE.toRoleInfo(role));
            AddRoleResponse response = soap.getUserServiceSoapHttpSoap11().addRole(request);
            if(!response.getStatus().isSuccess()) throw new RuntimeException(response.getStatus().getErrors());
        });
    }
}
