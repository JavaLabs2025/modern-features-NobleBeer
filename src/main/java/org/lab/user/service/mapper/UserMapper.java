package org.lab.user.service.mapper;

import org.lab.storage.postgres.model.Role;
import org.lab.storage.postgres.model.User;
import org.lab.user.controller.dto.RegisterNewUserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "passwordHash", source = "passwordHash")
    @Mapping(target = "role", source = "role")
    User toUser(RegisterNewUserRequestDto registerNewUserRequestDto, String passwordHash, Role role);

}
