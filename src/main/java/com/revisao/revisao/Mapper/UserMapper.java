package com.revisao.revisao.Mapper;

import com.revisao.revisao.domain.User;
import com.revisao.revisao.request.UserPostRequest;
import com.revisao.revisao.request.UserPutRequest;
import com.revisao.revisao.response.UserGetResponse;
import com.revisao.revisao.response.UserPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toUserPut(UserPutRequest userPutRequest);
    User toUserPostrequest(UserPostRequest userPostRequest);


    UserGetResponse toUserGetResponse(User user);
    List<UserGetResponse> toUserGetResponseList(List<User> user);

    UserPostResponse toUserPostResponse(User user);











}
