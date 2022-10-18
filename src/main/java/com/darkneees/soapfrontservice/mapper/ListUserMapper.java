package com.darkneees.soapfrontservice.mapper;

import com.darkneees.soapfrontservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import users_soap.api.UserInfo;

import java.util.List;

@Mapper(uses = {UserMapper.class})
public interface ListUserMapper {

    ListUserMapper INSTANCE = Mappers.getMapper(ListUserMapper.class);

    @Mapping(target = "socialList", ignore = true)
    @Mapping(target = "roleList", ignore = true)
    List<UserInfo> toListUserInfos(Iterable<User> users);

    @Mapping(target = "socialList", ignore = true)
    @Mapping(target = "roleList", ignore = true)
    List<User> toListUsers(List<UserInfo> userInfos);

}