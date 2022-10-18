package com.darkneees.soapfrontservice.mapper;

import com.darkneees.soapfrontservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import users_soap.api.RoleInfo;

import java.util.List;

@Mapper(uses = {RoleMapper.class})
public interface ListRoleMapper {

    ListRoleMapper INSTANCE = Mappers.getMapper(ListRoleMapper.class);

    List<Role> toListRoles(List<RoleInfo> roleInfos);
}
