package com.darkneees.soapfrontservice.mapper;

import com.darkneees.soapfrontservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import users_soap.api.UserInfo;

@Mapper(uses = {RoleMapper.class, SocialMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "info.roleList", target = "roleSet")
    @Mapping(source = "info.socialList", target = "socialSet")
    User toUser(UserInfo info);

    @Mapping(source = "user.roleSet", target = "roleList")
    @Mapping(source = "user.socialSet", target = "socialList")
    UserInfo toUserInfo(User user);

}
