package org.lab.user.service.mapper;

import org.lab.user.controller.dto.RegisterNewUserRequestDto;
import org.lab.user.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "passwordHash", source = "passwordHash")
    UserEntity toUser(RegisterNewUserRequestDto registerNewUserRequestDto, String passwordHash);

}
