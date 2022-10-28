package com.darkneees.soapfrontservice.service;

import com.darkneees.soapfrontservice.entity.Role;
import com.darkneees.soapfrontservice.mapper.ListRoleMapper;
import com.darkneees.soapfrontservice.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import users_soap.api.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    UserServiceSoapHttpService soap;

    public RoleServiceImpl() {
        this.soap = new UserServiceSoapHttpService();
    }

    @Override
    public CompletableFuture<List<Role>> getAllRoles() {
        return CompletableFuture.supplyAsync(() -> {
            GetAllRolesResponse response = soap.getUserServiceSoapHttpSoap11().getAllRoles(new GetAllRolesRequest());
            log.info("Service status: " + response.getStatus().isSuccess());
            if(response.getStatus().isSuccess()) return ListRoleMapper.INSTANCE.toListRoles(response.getRoles());
            else {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        });
    }

    @Override
    public CompletableFuture<Void> addRole(Role role) {
        return CompletableFuture.runAsync(() -> {
            AddRoleRequest request = new AddRoleRequest();
            request.setRoles(RoleMapper.INSTANCE.toRoleInfo(role));
            AddRoleResponse response = soap.getUserServiceSoapHttpSoap11().addRole(request);
            log.info("Service status: " + response.getStatus().isSuccess());
            if(!response.getStatus().isSuccess()) {
                log.error(response.getStatus().getErrors());
                throw new RuntimeException(response.getStatus().getErrors());
            }
        });
    }
}
